<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>TOP40 - Add new chart</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">
    <script src="//code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <style><%@include file="/css/style.css"%></style>
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

            $('[name="artists[]"], [name="name[]"]').autocomplete({

                minLength: 2,
                source: function (request, response) {
                    response($.map(songs, function (obj, key) {

                        var name = obj.artists + " - " + obj.name;

                        if (name.toUpperCase().indexOf(request.term.toUpperCase()) != -1) {
                            return {
                                label: obj.artists + " - " + obj.name, // Label for Display
                                value: obj // Value
                            }
                        } else {
                            return null;
                        }
                    }));
                },

                select: function(event, ui) {
                    event.preventDefault();
                    var idSong = ui.item.value.id;
                    var songName = ui.item.value.name
                    var artists = ui.item.value.artists;

                    $(this).closest('.song-row').find('[name="idSong[]"]').val(idSong);
                    $(this).closest('.song-row').find('[name="artists[]"]').val(artists);
                    $(this).closest('.song-row').find('[name="name[]"]').val(songName);
                    $(this).closest('.song-row').find('[name="num"]').removeClass('new-song');
                }
            });

            $('[name="artists[]"], [name="name[]"]').on('input', function () {
                $(this).closest('.song-row').find('[name="idSong[]"]').val('');
                $(this).closest('.song-row').find('[name="num"]').addClass('new-song');
            });

            $('#songs').sortable({
                update: updateNum
            });
            $('#songs').disableSelection();
        });
    </script>
</head>
<body>
<div class="container">
    <h1>Добавление нового чарта</h1>
    <div class="nav">
        <div name="menu" style="margin-bottom:5px;">
            <a href="/">Главная</a>
            |
            <b>Добавить чарт</b>
            |
            <a href="/artists">Артисты</a>
            |
            <a href="/songs">Песни</a>
            |
            <a href="/reports">Отчёты</a>
        </div>
    </div>
    <form method="POST" action="/chartadd">
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
                <c:forEach begin="1" end="40" var="val">
                    <div id="song-row-draggable" class="song-row">
                        <div style="display:none;"><input name="idSong[]" type="text"  /></div>
                        <div class="flex1" name="num"><c:out value="${val}"/></div>
                        <div class="flex9"><input style="width:100%;" name="artists[]" type="text" /></div>
                        <div style="text-align:center; width: 30px;"> - </div>
                        <div class="flex9"><input style="width:100%;" name="name[]" type="text" /></div>
                    </div>
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