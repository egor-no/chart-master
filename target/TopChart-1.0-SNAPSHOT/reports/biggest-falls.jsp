<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
    <title>TOP40 - Biggest falls</title>
    <jsp:include page="/components/head.jsp"/>
    <script type="text/javascript"><%@include file="/components/song-history.js"%></script>
    <script type="text/javascript"><%@include file="/components/artistPicker.js"%></script>
    <script type="text/javascript">
        $(document).ready(function() {
            ArtistPicker.init();
        });
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
            <h2>Biggest Falls</h2>
        </div>
        <div class="report-description">
            <p>Самые резкие падения позиций, включая выбывания из чарта. Здесь можно увидеть, какие песни теряли позиции быстрее всего и когда популярность резко шла на спад.</p>
        </div>

        <div id="report-list" style="width:720px;">
            <div class="song-row">
                <div class="flex1">
                    <b>Mov</b>
                </div>
                <div class="flex2" style="flex:0 0 110px;">
                    <b>Date</b>
                </div>
                <div class="flex1" style="flex:0 0 90px;">
                    <b>Drop</b>
                </div>
                <div class="flex9"></div>
            </div>
            <c:forEach items="${rows}" var="r">
                <c:set var="drop" value="${r[0]}" />
                <c:set var="chart" value="${r[1]}" />
                <c:set var="song" value="${r[2]}" />
                <c:set var="lw" value="${r[3]}" />
                <c:set var="pos" value="${r[4]}" />
                <c:set var="isOut" value="${r[5]}" />
                <div name="song">
                    <input name="song-id" type="hidden" value="${song.id}" />
                    <input name="chart-number" type="hidden" value="${chart.issueNumber}" />
                    <div class="song-row">
                        <div name="mov-info" class="flex-mov down">
                            <div name="mov">&#9660;</div>
                            <div name="mov-val">${drop}</div>
                        </div>
                        <div class="flex2" style="flex:0 0 110px;">
                            <p style="white-space:nowrap;">
                                    ${chart.date}
                            </p>
                        </div>
                        <div class="flex1" style="flex:0 0 90px;">
                            <p title="Last week → current">
                                    ${lw} →
                                <c:choose>
                                    <c:when test="${isOut}">out</c:when>
                                    <c:otherwise>${pos}</c:otherwise>
                                </c:choose>
                            </p>
                        </div>
                        <div class="flex4" style="padding-left:12px;">
                            <p>
                                <span class="artist-link"
                                      data-artists="${song.artists}"
                                      title="Open artist page"
                                      tabindex="0">
                                        ${song.artists}
                                </span>
                            </p>
                        </div>
                        <div class="flex5">
                            <p>${song.name}</p>
                        </div>
                    </div>

                    <%@include file="../components/song-history.jsp"%>
                </div>
            </c:forEach>
        </div>

        <%@include file="../components/chart-run-template.jsp"%>
        <%@include file="../components/artist-picker-modal.jsp"%>

    </div>
</div>

</body>
</html>