<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>TOP40 - Songs</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script type = "text/javascript" >
        $(document).ready(function() {

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
</form>
<div id="songs-list">
    <c:forEach items="${songs}" var="song">
        ${song.artists} - ${song.name} <br/>
    </c:forEach>
</div>
</body>
</html>