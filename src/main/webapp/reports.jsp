<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Title</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <style><%@include file="/css/style.css"%></style>
</head>
<body>
<div class="container">
    <h1>Отчёты</h1>
    <div class="nav">
        <div name="menu" style="margin-bottom:5px;">
            <a href="/">Главная</a>
            |
            <a href="/chartadd">Добавить чарт</a>
            |
            <a href="/artists">Артисты</a>
            |
            <a href="/songs">Песни</a>
            |
            <b>Отчёты</b>
        </div>
    </div>
    <div name="reports-title">
        <h2>Доступные отчёты для истории чартов</h2>
        <p>Stats are <i>FUN</i>! Хочется узнать,
            какие песни провели на первом месте дольше всего?
            Или какие песни были в чарте рекордное время?
            Или какие артисты произвели больше всех хитов?
            Какие их хиты оказались самыми эффективными?
            Просто выберите отчёт и наслаждайтесь статистикой. </p>
    </div>

    <div name="reports">
        <div class="report-card" id="longest-songs">
            <h2>Longest on chart</h2>
            <p>Топ песен, которые провели в чарте больше всех недель</p>
        </div>
        <div class="report-card" id="no1-songs">
            <h2>Longest no1s</h2>
            <p>Топ песен, которые провели на первом месте чарта дольше остальных</p>
        </div>
        <div class="report-card" id="top-songs">
            <h2>Top songs</h2>
            <p>Самые топовые песни по взвешенному расчёту. Учитывается длительность пребывания и позиции в чарте.</p>
        </div>
        <div class="report-card" id="top-artists">
            <h2>Top artists</h2>
            <p>Топовые артисты по количеству хитов. В первую очередь учитываются чарттопперы, потом топ10 хиты и потом общее количество хитов.</p>
        </div>
    </div>
</div>
</body>
</html>
