<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>TOP40 - Edit your last chart</title>
    <jsp:include page="components/head.jsp"/>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">
    <script src="//code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <style>
    #chart-create [name="num"]{
        width: 40px;
        justify-content: center;
        align-items: center;
        display: flex;
        font-weight: bold;
        color:#282929;
    }
    #chart-create .song-row{ margin-bottom: 3px; }

    #chart-create .new-song{
        background:#fcfbdd;
        box-shadow: inset 3px 0 0 #0000CC;
    }
    #chart-create .re-song{
        background:#f6f6ff;
        box-shadow: inset 3px 0 0 #76367a;
    }

    .ui-autocomplete {
        max-width: 520px;
        width: auto !important;
        box-sizing: border-box;
        overflow-x: hidden;
        z-index: 9999;
        font-size: 12px;
    }

    .ui-autocomplete .ui-menu-item-wrapper {
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
    }

    .ui-state-active{
        background:#000676;
        color:#fff;
        margin: 0;
        border: 0;
    }
    </style>
    <script type="text/javascript">
        function updateNum() {
            var num = 1;
            $('#songs .song-row').each(function() {
                $(this).find('[name="num"]').html(num);
                num++;
            });
        }

        $(document).ready(function() {

            var songs = ${songs};

            var prev = { ids: new Set() };

            <c:if test="${prevChart != null}">
            <c:forEach items="${prevChart.positions}" var="p">
            prev.ids.add(${p.pk.song.id});
            </c:forEach>
            </c:if>

            let silentFill = false;

            function clearMarks($row){
                $row.find('[name="num"]').removeClass('new-song re-song');
                $row.find('input[name="prevInChart[]"]').val('0');
                $row.find('input[name="prevWoc[]"]').val('0');
            }

            function markRow($row, songId){
                clearMarks($row);

                songId = parseInt(songId, 10);
                if (isNaN(songId) || songId <= 0) return;

                if (prev.ids.size > 0 && !prev.ids.has(songId)) {
                    $row.find('[name="num"]').addClass('re-song');
                    $row.find('input[name="prevInChart[]"]').val('1');
                }
            }

            $('#songs .song-row').each(function(){
                const $row = $(this);
                const idSong = $row.find('input[name="idSong[]"]').val();
                if (idSong) markRow($row, idSong);
            });

            $('[name="artists[]"], [name="name[]"]').autocomplete({
                minLength: 2,
                source: function(request, response){
                    response($.map(songs, function(obj){
                        var label = obj.artists + " - " + obj.name;
                        return label.toUpperCase().includes(request.term.toUpperCase())
                            ? { label: label, value: obj }
                            : null;
                    }));
                },
                select: function(event, ui){
                    event.preventDefault();

                    silentFill = true;

                    const $row = $(this).closest('.song-row');
                    $row.find('input[name="idSong[]"]').val(ui.item.value.id);
                    $row.find('[name="artists[]"]').val(ui.item.value.artists);
                    $row.find('[name="name[]"]').val(ui.item.value.name);

                    silentFill = false;

                    markRow($row, ui.item.value.id);
                }
            });

            $('[name="artists[]"], [name="name[]"]').on('input', function(){
                if (silentFill) return;

                const $row = $(this).closest('.song-row');
                $row.find('input[name="idSong[]"]').val('');
                clearMarks($row);
                $row.find('[name="num"]').addClass('new-song');
            });

            $('#songs').sortable({ update: updateNum });
            $('#songs').disableSelection();
        });
    </script>
</head>
<body>
<div class="container">
    <h1>Редактирование чарта</h1>
    <div class="nav">
        <jsp:include page="components/menu.jsp">
            <jsp:param name="active" value="none"/>
        </jsp:include>
    </div>
    <form method="POST" action="/chartedit">
        <input name="chartNumber" type="text" value="${chart.id}" style="display: none;" />
        <div id="chart-create">
            <div class="song-row">
                <div class="flex1 title">
                    No
                </div>
                <div class="flex9 title">
                    Artists
                </div>
                <div style="text-align:center; width: 30px;">
                </div>
                <div class="flex9 title">
                    Title
                </div>
            </div>
            <div id="songs">
                <c:set var="num" value="1" />
                <c:forEach items="${chart.positions}" var="position" >
                    <div id="song-row-draggable" class="song-row">
                        <input type="hidden" name="idSong[]" value="${position.pk.song.id}" />
                        <input type="hidden" name="prevInChart[]" value="0" />
                        <input type="hidden" name="prevWoc[]" value="0" />

                        <div class="flex1 i-counter" name="num"><c:out value="${num}"/></div>
                        <div class="flex9"><input style="width:100%;" name="artists[]" type="text" value="${position.pk.song.artists}"/></div>
                        <div style="text-align:center; width: 30px;"> - </div>
                        <div class="flex9"><input style="width:100%;" name="name[]" type="text" value="${position.pk.song.name}"/></div>
                    </div>
                    <c:set var="num" value="${num+1}" />
                </c:forEach>
            </div>

            <div style="text-align: center;">
                <input style="margin-top:10px;" type="submit" value="Сохранить" />
            </div>
        </div>
    </form>
</div>
</body>
</html>