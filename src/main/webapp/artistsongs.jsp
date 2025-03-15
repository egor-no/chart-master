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
    <div id="artist-stats-border">
        <div id="artist-stats">
            <div id="stats-position-no1s" class="artist-stat">
                <h3>${stats[0]}</h3>
                <p>No 1s</p>
            </div>
            <div id="stats-position-top10s" class="artist-stat">
                <h3>${stats[1]}</h3>
                <p>Top 10s</p>
            </div>
            <div id="stats-position-top40s" class="artist-stat">
                <h3>${stats[2]}</h3>
                <p>Top 40s</p>
            </div>
        </div>
    </div>

    <%@include file="components/song-list-search.jsp"%>
</div>
</body>
</html>