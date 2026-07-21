<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>TOP40 - Add new chart</title>
    <jsp:include page="components/head.jsp"/>
    <script src="//code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

    <script type = "text/javascript" >

        function updateNum() {
            var num = 1;
            $(document).find('#songs').find('.song-row').each(function() {
                $(this).find('[name="num"]').html(num);
                num++;
            })
        }

        $(document).ready(function() {
            var songs = ${songs};

            var prev = { ids: new Set() };

            <c:forEach items="${chart.positions}" var="p">
            prev.ids.add(${p.pk.song.id});
            </c:forEach>

            let silentFill = false;

            function getDuplicateSongRows(songId, $currentRow) {
                songId = String(songId);
                return $('#songs .song-row').filter(function () {
                    const $row = $(this);
                    if ($row.is($currentRow)) {
                        return false;
                    }
                    return String($row.find('input[name="idSong[]"]').val()) === songId;
                });
            }

            function hasDuplicateSong(songId, $currentRow) {
                return getDuplicateSongRows(songId, $currentRow).length > 0;
            }

            function clearDuplicateMark($row) {
                $row.find('[name="num"]').removeClass('duplicate-song');
            }

            function markDuplicate($row) {
                $row.find('[name="num"]').addClass('duplicate-song');
            }

            function clearMarks($row){
                $row.find('[name="num"]').removeClass('new-song re-song');
                $row.find('input[name="prevInChart[]"]').val('0');
                $row.find('input[name="prevWoc[]"]').val('0');
            }

            function markRow($row, songId){
                clearMarks($row);

                songId = parseInt(songId, 10);
                if (isNaN(songId) || songId <= 0) return;

                if (!prev.ids.has(songId)) {
                    $row.find('[name="num"]').addClass('re-song');
                    $row.find('input[name="prevInChart[]"]').val('1');
                }
            }

            $('[name="artists[]"], [name="name[]"]').autocomplete({
                minLength: 2,
                appendTo: "body",
                source: function(request, response){
                    response($.map(songs, function(obj){
                        var label = obj.artists + " - " + obj.name;
                        return label.toUpperCase().includes(request.term.toUpperCase())
                            ? { label: label, value: obj }
                            : null;
                    }));
                },
                open: function() {
                    $(this).autocomplete("widget").outerWidth($(this).outerWidth());
                },
                select: function(event, ui){
                    event.preventDefault();

                    silentFill = true;

                    const $row = $(this).closest('.song-row');
                    const idSong = ui.item.value.id;

                    if (hasDuplicateSong(idSong, $row)) {
                        alert('Эта песня уже есть в чарте.');

                        $row.find('input[name="idSong[]"]').val('');
                        $row.find('[name="artists[]"]').val('');
                        $row.find('[name="name[]"]').val('');

                        clearMarks($row);
                        markDuplicate($row);

                        silentFill = false;
                        return;
                    }

                    clearDuplicateMark($row);

                    $row.find('input[name="idSong[]"]').val(idSong);
                    $row.find('[name="artists[]"]').val(ui.item.value.artists);
                    $row.find('[name="name[]"]').val(ui.item.value.name);

                    silentFill = false;

                    markRow($row, idSong);
                }
            });

            $('[name="artists[]"], [name="name[]"]').on('input', function(){
                if (silentFill) return;

                const $row = $(this).closest('.song-row');
                $row.find('input[name="idSong[]"]').val('');
                clearMarks($row);
                clearDuplicateMark($row);
                $row.find('[name="num"]').addClass('new-song');
            });


            $('#songs').sortable({
                update: updateNum
            });
            $('#songs').disableSelection();

            $('form').on('submit', function(e) {
                const usedIds = new Set();
                const usedNewSongs = new Set();

                let duplicateFound = false;
                let emptyFound = false;

                $('#songs .song-row').each(function() {
                    const $row = $(this);
                    const id = $row.find('input[name="idSong[]"]').val();
                    const artists = $row.find('input[name="artists[]"]').val().trim();
                    const title = $row.find('input[name="name[]"]').val().trim();

                    clearDuplicateMark($row);

                    if (artists === '' || title === '') {
                        emptyFound = true;
                        markDuplicate($row);
                        return;
                    }

                    if (id) {
                        if (usedIds.has(id)) {
                            markDuplicate($row);
                            duplicateFound = true;
                        } else {
                            usedIds.add(id);
                        }
                    } else {
                        const newSongKey = (artists + ' - ' + title).toLowerCase();

                        if (usedNewSongs.has(newSongKey)) {
                            markDuplicate($row);
                            duplicateFound = true;
                        } else {
                            usedNewSongs.add(newSongKey);
                        }
                    }
                });

                if (emptyFound) {
                    e.preventDefault();
                    alert('Все позиции чарта должны быть заполнены.');
                    return;
                }

                if (duplicateFound) {
                    e.preventDefault();
                    alert('В чарте есть повторяющиеся песни. Удали дубль перед сохранением.');
                }
            });
        });
    </script>
</head>
<body>
<div class="container">
    <h1>Добавление нового чарта</h1>
    <div class="nav">
        <jsp:include page="components/menu.jsp">
            <jsp:param name="active" value="chartadd"/>
        </jsp:include>
    </div>

    <c:if test="${param.emptyChart == '1'}">
        <div class="chart-create-note">
            This chart does not have any issues yet.<br/>
            Create the first issue to open the chart page.
        </div>
    </c:if>

    <form method="POST"
          action="${pageContext.request.contextPath}/chartadd">

        <div id="chart-create">
            <div class="song-row chart-create-header">
                <div class="flex1 title">
                    No
                </div>

                <div class="flex9 title">
                    Artists
                </div>

                <div class="chart-create-separator"></div>

                <div class="flex9 title">
                    Title
                </div>
            </div>

            <div id="songs">
                <c:forEach begin="1" end="40" var="val">
                    <div class="song-row song-row-draggable">
                        <input type="hidden" name="idSong[]" />
                        <input type="hidden" name="prevInChart[]" value="0" />
                        <input type="hidden" name="prevWoc[]" value="0" />

                        <div class="flex1 i-counter"
                             name="num">
                            <c:out value="${val}"/>
                        </div>

                        <div class="flex9">
                            <input class="chart-create-input"
                                   name="artists[]"
                                   type="text" />
                        </div>

                        <div class="chart-create-separator">
                            -
                        </div>

                        <div class="flex9">
                            <input class="chart-create-input"
                                   name="name[]"
                                   type="text" />
                        </div>
                    </div>
                </c:forEach>
            </div>

            <div class="chart-create-actions">
                <input type="submit" value="Сохранить" />
            </div>
        </div>
    </form>
</div>
</body>
</html>