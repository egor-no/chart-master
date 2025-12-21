<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
    <title>TOP40 - Longest on chart</title>
    <jsp:include page="/components/head.jsp"/>
    <script type="text/javascript"><%@include file="/components/song-history.js"%></script>
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
            <h2>Longest on chart</h2>
        </div>
        <div class="report-description">
            <p>Топ песен, которые провели в чарте наибольшее количество недель за всё время наблюдений. Здесь не важны пики и резкие взлёты — в фокусе именно стабильность и длительное присутствие в чарте, независимо от позиции.</p>
        </div>
        <div id="report-list">
            <div class="song-row">
                <div class="flex1">
                    <p style="margin-bottom:0px;"><b>No</b></p>
                </div>
                <div class="flex1 data-center">
                    <p style="margin-bottom:0px;"><b>Peak</b></p>
                </div>
                <div class="flex9">
                </div>
                <div class="flex-end">
                    <p style="margin-bottom:0px;"><b>WoC</b></p>
                </div>
            </div>
            <c:set var="i" value="1" />
            <c:forEach items="${songs}" var="song">
                <div name="song">
                    <div class="song-row">
                        <input name="song-id" style="display:none;" type="text" value="${song.id}" />

                        <div class="flex1 i-counter">
                            <p>${i}</p>
                        </div>
                        <div class="flex1 data-center">
                            <p>${song.peak}</p>
                        </div>
                        <div class="flex4">
                            <p>${song.artists}</p>
                        </div>
                        <div class="flex5">
                            <p>${song.name}</p>
                        </div>
                        <div class="flex1 flex-end data-end">
                            <p> ${song.weeks}</p>
                        </div>
                    </div>
                    <%@include file="../components/song-history.jsp"%>
                </div>
                <c:set var="i" value="${i+1}" />
            </c:forEach>
        </div>
    </div>
    <%@include file="../components/chart-run-template.jsp"%>
</div>

</body>
</html>