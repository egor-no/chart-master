<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <title>TOP40 - Biggest leaps</title>
    <link rel="icon" href="/icons/icon.png" type="image/x-icon">
    <jsp:include page="/components/head.jsp"/>
    <script type="text/javascript">
        <%@include file="/components/song-history.js"%>
    </script>
</head>
<body>
<div class="report">
    <div id="report-cap">
        <h1>Отчёты</h1>
        <div class="nav">
            <jsp:include page="../components/menu.jsp">
                <jsp:param name="active" value="none"/>
            </jsp:include>
        </div>
    </div>
    <div id="report-body">
        <div class="report-header">
            <h2>Biggest Leaps</h2>
        </div>
        <div class="report-description">
            <p>Самые большие скачки вверх внутри чарта. Он показывает резкие всплески интереса, неожиданные рывки и моменты, когда песня внезапно набирала популярность.</p>
        </div>
        <div id="report-list" style="width:720px;">
            <div class="song-row">
                <div class="flex1">
                    <b>Mov</b>
                </div>
                <div class="flex2" style="flex:0 0 110px;">
                    <b>Date</b>
                </div>
                <div class="flex1">
                    <b>Jump</b>
                </div>
                <div class="flex9">
                </div>
            </div>
            <c:forEach items="${rows}" var="r">
                <c:set var="jump" value="${r[0]}" />
                <c:set var="p" value="${r[1]}" />
                <c:set var="chart" value="${r[2]}" />
                <div name="song">
                    <div class="song-row">
                        <input name="song-id" style="display:none;" type="text" value="${p.pk.song.id}" />
                        <input name="chart-id" type="hidden" value="${chart.id}" />
                        <div name="mov-info" class="flex-mov up">
                            <div name="mov">&#9650;</div>
                            <div name="mov-val">${jump}</div>
                        </div>
                        <div class="flex2" style="flex:0 0 110px;">
                            <p style="white-space:nowrap;">${p.pk.chart.date}</p>
                        </div>
                        <div class="flex1" style="flex:0 0 90px;">
                                <p title="LW → Pos">${p.lastWeek} → ${p.position}</p>
                        </div>
                        <div class="flex4" style="padding-left:12px;">
                            <p>${p.pk.song.artists}</p>
                        </div>
                        <div class="flex5">
                                <p>${p.pk.song.name}</p>
                        </div>
                    </div>
                    <%@include file="../components/song-history.jsp"%>
                </div>
            </c:forEach>
        </div>
        <%@include file="../components/chart-run-template.jsp"%>
    </div>
</div>
</body>
</html>