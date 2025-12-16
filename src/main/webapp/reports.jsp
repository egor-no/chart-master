<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Title</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <style><%@include file="/css/style.css"%>
    .report-card{
        width: 330px;
        height: auto;
        background: #dce0e2;
        border-top: 2px solid #fff;
        border-left: 2px solid #fff;
        border-right: 2px solid #192428;
        border-bottom: 2px solid #192428;
        padding: 8px 10px;
        margin-bottom: 10px;
        cursor: pointer;
    }

    .report-card h2{
        margin:0 0 6px 0;
        font-size: 16px;
        color:#000;
        border:0;
    }

    .report-card p{
        margin:0;
        font-size: 12px;
        color:#222;
    }

    .report-card:hover{
        outline: 1px dotted #000;
        outline-offset: -3px;
    }

    .report-card:active{
        border-top: 2px solid #192428;
        border-left: 2px solid #192428;
        border-right: 2px solid #fff;
        border-bottom: 2px solid #fff;
    }

    .report-card:focus{
        outline: 1px dotted #000;
        outline-offset: -3px;
    }

    [name="reports"]{
        display: flex;
        flex-wrap: wrap;
        gap: 10px;
    }
    </style>
    <script type = "text/javascript" >
        $(document).ready(function() {
            $('#longest-songs').on('click', function () {
                top.location.href = "/reports?report=longestSongs";
            });
            $('#no1-songs').on('click', function () {
                top.location.href = "/reports?report=no1Songs";
            });
            $('#no1-debuts').on('click', function () {
                top.location.href = "/reports?report=no1Debuts";
            });
            $('#longest-stallers').on('click', function () {
                top.location.href = "/reports?report=longestStallers";
            });
            $('#biggest-leaps').on('click', function () {
                top.location.href = "/reports?report=biggestLeaps";
            });
            $('#biggest-falls').on('click', function () {
                top.location.href = "/reports?report=biggestFalls";
            });
            $('#top-songs').on('click', function () {
                top.location.href = "/reports?report=topSongs";
            });
            $('#top-songs-date').on('click', function () {
                top.location.href = "/reports?report=topSongsDate";
            });
            $('#top-artists').on('click', function () {
                top.location.href = "/reports?report=topArtists";
            });
            $('#top-artists-date').on('click', function () {
                top.location.href = "/reports?report=topArtistsDate";
            });
            $('#effective-artists').on('click', function () {
                top.location.href = "/reports?report=effectiveArtists";
            });
        });
    </script>
</head>
<body>
<div class="report">
    <div id="report-cap">
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
    </div>
    <div id="report-body">
        <div class="report-header">
            <h2>Доступные отчёты для истории чартов</h2>
        </div>
        <div class="report-description">
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
            <div class="report-card" id="no1-debuts">
                <h2>Number 1 debuts</h2>
                <p>Песни, которые попали в самое сердце, и сразу же дебютировали с первого места.</p>
            </div>
            <div class="report-card" id="longest-stallers">
                <h2>Longest stallers</h2>
                <p>Песни, которые понадобилось время, чтобы понравиться больше. Рекордно долгие пути к топ-10.</p>
            </div>
            <div class="report-card" id="biggest-leaps">
                <h2>Biggest Leaps</h2>
                <p>Самые большие скачки вверх.</p>
            </div>
            <div class="report-card" id="biggest-falls">
                <h2>Biggest Falls</h2>
                <p>Самые большие падения, включая выбывания из чарта.</p>
            </div>
            <div class="report-card" id="top-songs">
                <h2>Top songs</h2>
                <p>Самые топовые песни по взвешенному расчёту за всё время. Учитывается длительность пребывания и позиции в чарте.</p>
            </div>
            <div class="report-card" id="top-songs-date">
                <h2>Top songs (period)</h2>
                <p>Топовые песни по взвешенному расчёту за выбранный период. Учитывается длительность пребывания и позиции в чарте.</p>
            </div>
            <div class="report-card" id="top-artists" >
                <h2>Top artists</h2>
                <p>Топовые артисты по взвешенному расчёту по позициям в чарте для всех их песен.</p>
            </div>
            <div class="report-card" id="top-artists-date" >
                <h2>Top artists (period)</h2>
                <p>Топовые артисты по взвешенному расчёту по позициям в чарте для всех их песен за выбранный период.</p>
            </div>
            <div class="report-card" id="effective-artists">
                <h2>Effective artists</h2>
                <p>Самые эффективные артисты по количеству хитов. В первую очередь учитываются чарттопперы, потом топ10 хиты и потом общее количество хитов.</p>
            </div>

        </div>
    </div>
</div>
</body>
</html>
