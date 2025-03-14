<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>TOP40 - Songs</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script type="text/javascript"><%@include file="/components/song-history.js"%></script>
    <style><%@include file="/css/style.css"%></style>
    <script type = "text/javascript" >
        $(document).ready(function() {
            if ($('[name="searchPhrase"]').val() == '') {
                $('#searchPhraseInfo').css('display', 'none');
                $('#songs-list').css('display', 'none');
            }

            $('[name="edit-link"]').on('click', function () {
                var idSong = $(this).closest('[name="song"]').find('[name="song-id"]').val();

                $(this).attr("href", "/songedit?id=" + idSong + "&artist=" + '${artist}');
            });
        });
    </script>
</head>
<body>
<div class="container">
    <h1>Список песен</h1>
    <div class="nav">
        <div name="menu" style="margin-bottom:5px;">
            <a href="/">Главная</a>
            |
            <a href="/chartadd">Добавить чарт</a>
            |
            <a href="/artists">Артисты</a>
            |
            <a href="/songs">Песни</a>
        </div>
    </div>

    <h2>${artist}</h2>
    <div id="artist-stats" style="width: 600px; display: flex; justify-content: space-evenly; flex-flow: row nowrap; border: 1px black solid;">
        <div id="stats-position-no1s" style="display: flex; flex-flow: column; align-items:center;">
            <h3 style="margin-top:15px; margin-bottom:15px;">${stats[0]}</h3>
            <p style="margin-top:0px; margin-bottom:15px;">No 1s</p>
        </div>
        <div id="stats-position-top10s" style="display: flex; flex-flow: column; align-items:center;">
            <h3 style="margin-top:15px; margin-bottom:15px;">${stats[1]}</h3>
            <p style="margin-top:0px; margin-bottom:15px;">Top 10s</p>
        </div>
        <div id="stats-position-top40s" style="display: flex; flex-flow: column; align-items:center;">
            <h3 style="margin-top:15px; margin-bottom:15px;">${stats[2]}</h3>
            <p style="margin-top:0px; margin-bottom:15px;">Top 40s</p>
        </div>
    </div>

    <%@include file="components/song-list-search.jsp"%>
</div>
</body>
</html>