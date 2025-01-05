<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>TOP40 - Artists</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script type = "text/javascript" >
        $(document).ready(function() {

        });
    </script>
</head>
<body>
<h1>Список артистов</h1>

<div name="menu" style="margin-bottom:5px;">
    <a href="/">Главная</a>
    |
    <a href="/chartadd">Добавить чарт</a>
    |
    <b>Артисты</b>
    |
    <a href="/songs">Песни</a>
</div>

<form method="GET" action="/artists">
    <label for="artistSearch">Поиск по артистам:</label>
    <input name="search" id="artistSearch" type="text" /><input type="submit" value="Искать" />
</form>
<div id="artists-list" style="columns: 4; -webkit-columns: 4; -moz-columns: 4;">
    <c:forEach items="${artists}" var="artist">
        <a href="/songs?artist=${artist}">${artist}</a><br/>
    </c:forEach>
</div>
</body>
</html>