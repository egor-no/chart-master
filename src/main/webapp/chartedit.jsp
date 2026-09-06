<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>TOP40 - Edit your last chart</title>
    <jsp:include page="components/head.jsp"/>
    <script src="//code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script src="${pageContext.request.contextPath}/components/chart-editor.js"></script>
    <style><%@include file="/css/share.css"%></style>

    <script type="text/javascript">

        $(document).ready(function() {

            var songs = ${songs};

            var prev = { ids: new Set() };

            <c:if test="${prevChart != null}">
            <c:forEach items="${prevChart.positions}" var="p">
            prev.ids.add(${p.pk.song.id});
            </c:forEach>
            </c:if>

            let silentFill = false;

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
                    const $activeInput = $(this);
                    const idSong = ui.item.value.id;

                    const oldValues = $row.data('oldValues');
                    const rowWasEmpty = oldValues && oldValues.id === '' && oldValues.artists === '' && oldValues.name === '';

                    const valuesToRestore = rowWasEmpty ? {
                            id: '',
                            artists: $row.find('[name="artists[]"]').val(),
                            name: $row.find('[name="name[]"]').val()
                        } : oldValues;

                    if (checkDuplicateSong(idSong, $row, $activeInput, valuesToRestore, rowWasEmpty)) {
                        silentFill = false;
                        return;
                    }

                    clearDuplicateMark($row);

                    $row.find('input[name="idSong[]"]').val(idSong);
                    $row.find('[name="artists[]"]').val(ui.item.value.artists);
                    $row.find('[name="name[]"]').val(ui.item.value.name);

                    silentFill = false;

                    markRow($row, idSong);
                    checkArtistLimit(ui.item.value.artists, $row, oldValues);
                }
            });

            $('[name="artists[]"], [name="name[]"]').on('input', function(){
                if (silentFill) return;

                const $row = $(this).closest('.song-row');
                $row.removeData('createNewSongKey');

                $row.find('input[name="idSong[]"]').val('');
                clearMarks($row);
                clearDuplicateMark($row);
                $row.find('[name="num"]').addClass('new-song');

                const currentArtists =
                    $row.find('[name="artists[]"]').val().trim();

                const currentName =
                    $row.find('[name="name[]"]').val().trim();

                if (currentArtists === '' && currentName === '') {
                    $row.data('oldValues', {id: '', artists: '', name: ''});
                    return;
                }

                if ($(this).attr('name') === 'artists[]') {
                    if (currentArtists !== '') {
                        checkArtistLimit(currentArtists, $row, $row.data('oldValues'));
                    }
                }
            });

            $('[name="artists[]"], [name="name[]"]').on('focus', function() {
                const $row = $(this).closest('.song-row');

                if (!$row.data('oldValues')) {
                    $row.data('oldValues', {
                        id: $row.find('input[name="idSong[]"]').val(),
                        artists: $row.find('[name="artists[]"]').val(),
                        name: $row.find('[name="name[]"]').val()
                    });
                }
            });

            $('[name="artists[]"], [name="name[]"]').on('blur', function() {
                const $row = $(this).closest('.song-row');

                setTimeout(function() {
                    checkExistingSong(
                        $row,
                        songs,
                        markRow
                    );
                }, 100);
            });

            $('#songs').sortable({ update: updateNum });
            $('#songs').disableSelection();

            initChartFormValidation();
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
    <form method="POST"
          action="${pageContext.request.contextPath}/chartedit">

        <input name="chartNumber"
               type="hidden"
               value="${chart.issueNumber}" />

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
                <c:set var="num" value="1" />

                <c:forEach items="${chart.positions}" var="position">
                    <div class="song-row song-row-draggable">

                        <input type="hidden"
                               name="idSong[]"
                               value="${position.pk.song.id}" />

                        <input type="hidden"
                               name="prevInChart[]"
                               value="0" />

                        <input type="hidden"
                               name="prevWoc[]"
                               value="0" />

                        <div class="flex1 i-counter"
                             name="num">
                            <c:out value="${num}"/>
                        </div>

                        <div class="flex9">
                            <input class="chart-create-input"
                                   name="artists[]"
                                   type="text"
                                   value="${position.pk.song.artists}" />
                        </div>

                        <div class="chart-create-separator">
                            -
                        </div>

                        <div class="flex9">
                            <input class="chart-create-input"
                                   name="name[]"
                                   type="text"
                                   value="${position.pk.song.name}" />
                        </div>

                    </div>

                    <c:set var="num" value="${num + 1}" />
                </c:forEach>
            </div>

            <div class="chart-create-actions">
                <input type="submit" value="Сохранить" />
            </div>

        </div>
    </form>
</div>
<jsp:include page="components/artist-limit-modal.jsp"/>
<jsp:include page="components/duplicate-song-modal.jsp"/>
<jsp:include page="components/existing-song-modal.jsp"/>
</body>
</html>