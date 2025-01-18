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
    <a href="/songs">Песни</a>
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
    </div>
    <c:forEach items="${songs}" var="song">
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
        </div>
    </c:forEach>
</div>
</body>
</html>