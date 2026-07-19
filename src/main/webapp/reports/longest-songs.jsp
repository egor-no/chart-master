<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
    <title>TOP40 - Longest on chart</title>
    <jsp:include page="/components/head.jsp"/>
    <style>
        .report-filter-form {
            margin: 10px 0 14px 0;
            display: flex;
            align-items: center;
            gap: 8px;
            font-size: 13px;
        }

        .win95-select {
            background: #eaeaea;
            color: #282929;
            border-top: 2px solid #fff;
            border-left: 2px solid #fff;
            border-right: 2px solid #192428;
            border-bottom: 2px solid #192428;
            padding: 3px 24px 3px 6px;
            font-family: Arial, sans-serif;
            font-size: 13px;
            outline: none;
        }

        .win95-select:active,
        .win95-select:focus {
            border-top: 2px solid #192428;
            border-left: 2px solid #192428;
            border-right: 2px solid #fff;
            border-bottom: 2px solid #fff;
        }
    </style>

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
            <h2>Longest on chart</h2>
        </div>
        <div class="report-description">
            <p>
                Песни, которые провели наибольшее количество недель
                <c:choose>
                    <c:when test="${range == 'top10'}">в первой десятке чарта.</c:when>
                    <c:when test="${range == 'top20'}">в первой двадцатке чарта.</c:when>
                    <c:otherwise>в чарте.</c:otherwise>
                </c:choose>
            </p>
        </div>
        <form method="get" action="/reports" class="report-filter-form">
            <input type="hidden" name="report" value="longestSongs">

            <label for="range">Count weeks in:</label>

            <select id="range"
                    name="range"
                    class="win95-select"
                    onchange="this.form.submit()">

                <option value="top40" ${range == 'top40' ? 'selected' : ''}>
                    Top 40
                </option>

                <option value="top20" ${range == 'top20' ? 'selected' : ''}>
                    Top 20
                </option>

                <option value="top10" ${range == 'top10' ? 'selected' : ''}>
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
                <div class="flex9">
                </div>
                <div class="flex-end">
                    <p style="margin-bottom:0px;">
                        <b>
                            <c:choose>
                                <c:when test="${range == 'top10'}">W Top10</c:when>
                                <c:when test="${range == 'top20'}">W Top20</c:when>
                                <c:otherwise>WoC</c:otherwise>
                            </c:choose>
                        </b>
                    </p>
                </div>
            </div>
            <c:set var="i" value="1" />

            <c:forEach items="${songs}" var="song">
                <div name="song">
                    <div class="song-row">
                        <input name="song-id"
                               style="display:none;"
                               type="text"
                               value="${song[1]}" />

                        <div class="flex1 i-counter">
                            <p>${i}</p>
                        </div>

                        <div class="flex1 data-center">
                            <p>${song[2]}</p>
                        </div>

                        <div class="flex4">
                            <p>
                    <span class="artist-link"
                          data-artists="${song[4]}"
                          title="Open artist page"
                          tabindex="0">
                            ${song[4]}
                    </span>
                            </p>
                        </div>

                        <div class="flex5">
                            <p>
                                    ${song[5]}

                                <c:if test="${currentlyChartingSongs[song[1]]}">
                                    <span class="currently-charting">CHARTING</span>
                                </c:if>
                            </p>
                        </div>

                        <div class="flex1 flex-end data-end">
                            <p>${song[0]}</p>
                        </div>
                    </div>

                    <%@include file="../components/song-history.jsp"%>
                </div>

                <c:set var="i" value="${i + 1}" />
            </c:forEach>
        </div>
    </div>

    <%@include file="../components/chart-run-template.jsp"%>
    <%@include file="../components/artist-picker-modal.jsp"%>
</div>

</body>
</html>