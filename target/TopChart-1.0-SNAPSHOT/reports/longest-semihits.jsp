<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
    <title>TOP40 - Longest semihits</title>
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
            <h2>Longest Semihits</h2>
        </div>
        <div class="report-description">
            <p>
                Песни, которые так и не добрались
                <c:choose>
                    <c:when test="${missed == 'top10'}">
                        до топ-10, но всё равно провели в чарте достаточно долго. Лидеры среди тех, кто не добрался до десятки.
                    </c:when>
                    <c:otherwise>
                        до топ-20, но всё равно провели в чарте достаточно долго. Лидеры среди тех, кто не добрался до верхней половины.
                    </c:otherwise>
                </c:choose>
            </p>
        </div>
        <form method="get" action="/reports" class="report-filter-form">
            <input type="hidden" name="report" value="longestSemihits">

            <label for="missed">Failed to reach:</label>

            <select id="missed"
                    name="missed"
                    class="win95-select"
                    onchange="this.form.submit()">

                <option value="top20" ${missed == 'top20' ? 'selected' : ''}>
                    Top 20
                </option>

                <option value="top10" ${missed == 'top10' ? 'selected' : ''}>
                    Top 10
                </option>
            </select>
        </form>
        <div id="report-list">
            <div class="song-row">
                <div class="flex1">
                    <p style="margin-bottom:0px;"><b>No</b></p>
                </div>
                <div class="flex1 data-center">
                    <p style="margin-bottom:0px;"><b>Peak</b></p>
                </div>
                <div class="flex9"></div>
                <div class="flex-end">
                    <p style="margin-bottom:0px;"><b>WoC</b></p>
                </div>
            </div>
            <c:set var="i" value="1" />
            <c:forEach items="${rows}" var="r">
                <div name="song">
                    <div class="song-row">
                        <input name="song-id" style="display:none;" value="${r[4]}" />
                        <div class="flex1 i-counter">
                            <p>${i}</p>
                        </div>
                        <div class="flex1 data-center">
                            <p>${r[1]}</p>
                        </div>
                        <div class="flex4">
                            <p>
                                <span class="artist-link"
                                      data-artists="${r[2]}"
                                      title="Open artist page"
                                      tabindex="0">
                                        ${r[2]}
                                </span>
                            </p>
                        </div>
                        <div class="flex5">
                            <p>
                                    ${r[3]}
                                <c:if test="${currentlyChartingSongs[r[4]]}">
                                    <span class="currently-charting">CHARTING</span>
                                </c:if>
                            </p>
                        </div>
                        <div class="flex1 flex-end data-end">
                            <p>${r[0]}</p>
                        </div>
                    </div>
                    <%@include file="../components/song-history.jsp"%>
                    <c:set var="i" value="${i+1}" />
                </div>
            </c:forEach>
        </div>

        <%@include file="../components/chart-run-template.jsp"%>
        <%@include file="../components/artist-picker-modal.jsp"%>
    </div>
</div>

</body>
</html>