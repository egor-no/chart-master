<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Top40 - Song Edit</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <style><%@include file="/css/style.css"%></style>
    <script type = "text/javascript" >
        $(document).ready(function() {
            $('[name="cancel"]').on('click', function () {
                var search = $('[name="search"]').val();
                var artist = $('[name="artist"]').val();
                if (search != "") {
                    window.location.href= "/songs?search=" + search;
                } else {
                    window.location.href= "/artist?artist=" + artist;
                }
            });
        });
    </script>
</head>
<body>
<div class="container">
    <h1>Редактирование песни</h1>
    <div class="nav">
        <div name="menu" style="margin-bottom:5px;">
            <a href="/chart">Главная</a>
            |
            <a href="/chartadd">Добавить чарт</a>
            |
            <a href="/artists">Артисты</a>
            |
            <b>Песни</b>
            |
            <a href="/reports">Отчёты</a>
        </div>
    </div>
    <form method="POST" action="/songedit">
        <div id="song-info">
            <input name="search" type="text" style="display:none;" value="${search}" />
            <input name="artist" type="text" style="display:none;" value="${artist}" />
            <input name="id" type="text" style="display:none;" value="${song.id}" />
            <div class="edit-fields">
                <label for="artists">Исполнители:</label>
                <input name="artists" id="artists" type="text" value="${song.artists}" />
            </div>
            <div class="edit-fields">
                <label for="name">Название:</label>
                <input name="name" id="name" type="text" value="${song.name}" />
            </div>
                <div class="edit-fields">
                <label for="weeks">Недель в чарте:</label>
                <input name="weeks" id="weeks" type="text" value="${song.weeks}" disabled />
            </div>
            <div class="edit-fields">
                <label for="peak">Пиковая позиция:</label>
                <input name="peak" id="peak" type="text" value="${song.peak}" disabled />
            </div>
            <div style="text-align: center;">
                <input type="submit" value="Сохранить" /> <input name="cancel" type="button" value="Отмена" />
            </div>
        </div>

    </form>
</div>
</body>
</html>
