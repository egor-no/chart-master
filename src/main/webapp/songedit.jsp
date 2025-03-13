<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script type = "text/javascript" >
        $(document).ready(function() {
            $('[name="cancel"]').on('click', function () {
                var search = $('[name="search"]').val();
                window.location.href= "/songs?search=" + search;
            });
        });
    </script>
</head>
<body>
<h1>Редактирование песни</h1>

<div name="menu" style="margin-bottom:5px;">
    <a href="/">Главная</a>
    |
    <a href="/chartadd">Добавить чарт</a>
    |
    <a href="/artists">Артисты</a>
    |
    <b>Песни</b>
</div>

<form method="POST" action="/songedit">
    <div id="song-info" style="width:600px; display: flex;  flex-flow: column;">
        <input name="search" type="text" style="display:none;" value="${search}" />
        <input name="id" type="text" style="display:none;" value="${song.id}" />
        <div style="display: flex; flex-flow: row nowrap; justify-content: space-between;">
            <label for="artists">Исполнители:</label>
            <input name="artists" id="artists" type="text" value="${song.artists}" />
        </div>
        <div style="display: flex; flex-flow: row nowrap; justify-content: space-between; ">
            <label for="name">Название:</label>
            <input name="name" id="name" type="text" value="${song.name}" />
        </div>
            <div style="display: flex; flex-flow: row nowrap; justify-content: space-between;">
            <label for="weeks">Поиск по песням:</label>
            <input name="weeks" id="weeks" type="text" value="${song.weeks}" disabled />
        </div>
        <div style="display: flex; flex-flow: row nowrap; justify-content: space-between;">
            <label for="peak">Поиск по песням:</label>
            <input name="peak" id="peak" type="text" value="${song.peak}" disabled />
        </div>
        <div style="text-align: center;">
            <input type="submit" value="Сохранить" /> <input name="cancel" type="button" value="Отмена" />
        </div>
    </div>

</form>

</body>
</html>
