<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>TOP40 - Artists</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <style><%@include file="/css/style.css"%></style>
    <script type = "text/javascript" >
        $(document).ready(function() {
            if ($('[name="searchPhrase"]').val() == '') {
                $('#searchPhraseInfo').css('display', 'none');
            }

            if ($('[name="length"]').val() == '0') {
                $('[name="not-found"]').removeClass('no-display');
            }
        });
    </script>
</head>
<body>
<div class="container">
    <h1>Список артистов</h1>
    <div class="nav">
        <div name="menu" style="margin-bottom:5px;">
            <a href="/">Главная</a>
            |
            <a href="/chartadd">Добавить чарт</a>
            |
            <b>Артисты</b>
            |
            <a href="/songs">Песни</a>
            |
            <a href="/reports">Отчёты</a>
        </div>

        <form method="GET" action="/artists">
            <label for="artistSearch">Поиск по артистам:</label>
            <input name="search" id="artistSearch" type="text" /><input type="submit" value="Искать" />
            <input name="searchPhrase" style="display:none;" type="text" value="${search}" />
        </form>
    </div>
    <input name="length" style="display:none;" type="text" value="${artists == null ? 0 : artists.size()}" />
    <div id="searchPhraseInfo">
        <h2>Поиск по <i>${search}</i></h2>
    </div>
    <div id="artists-list">
        <c:forEach items="${artists}" var="artist">
            <a href="/artist?artist=${artist}">${artist}</a><br/>
        </c:forEach>
    </div>
    <div class="no-display" name="not-found">
        <i>Никого не найдено</i>
    </div>
</div>
</body>
</html>