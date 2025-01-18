<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>TOP40 - Songs</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script type = "text/javascript" >
        $(document).ready(function() {
            if ($('[name="searchPhrase"]').val() == '') {
                $('#searchPhraseInfo').css('display', 'none');
                $('#songs-list').css('display', 'none');
            }
        });
    </script>
</head>
<body>
<h1>Список песен</h1>

<div name="menu" style="margin-bottom:5px;">
    <a href="/">Главная</a>
    |
    <a href="/chartadd">Добавить чарт</a>
    |
    <a href="/artists">Артисты</a>
    |
    <b>Песни</b>
</div>

<form method="GET" action="/songs">
    <label for="songSearch">Поиск по песням:</label>
    <input name="search" id="songSearch" type="text" /><input type="submit" value="Искать" />
    <input name="searchPhrase" style="display:none;" type="text" value="${search}" />
</form>
<div id="searchPhraseInfo">
    <h2>Поиск по <i>${search}</i></h2>
</div>
<div id="songs-list" style="width:600px; display: flex;  flex-flow: column;">
    <div style="display: flex; flex-flow: row nowrap;">
        <div style="display: flex; flex: 1;">
            <p style="margin-bottom:0px;"><b>Peak</b></p>
        </div>
        <div style="display: flex; flex: 8;">
        </div>
        <div style="display: flex; justify-content: end;">
            <p style="margin-bottom:0px;"><b>WOC</b></p>
        </div>
        <div style="display: flex; flex: 1;">
        </div>
    </div>
    <c:forEach items="${songs}" var="song">
        <div name="song" style="display: flex; flex-flow: column;">
            <div style="display: flex; flex-flow: row nowrap;">
                <div style="display: flex; flex: 1;">
                    <p>${song.peak}</p>
                </div>
                <div style="display: flex; flex: 4;">
                    <p>${song.artists}</p>
                </div>
                <div style="display: flex; flex: 5;">
                    <p>${song.name}</p>
                </div>
                <div style="display: flex; flex: 1; justify-content: end;">
                    <p> ${song.weeks}</p>
                </div>
                <div style="display: flex; flex: 1;">
                    <a name="history-link" href="#">?</a>
                </div>
            </div>
            <div style="display:none;" name="song-history">

            </div>
        </div>
    </c:forEach>
</div>
</body>
</html>