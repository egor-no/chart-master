<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
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
    
    <title>TOP40 - Effective artists</title>
    <jsp:include page="/components/head.jsp"/>

    <script type="text/javascript">
        <%@include file="/components/artistPicker.js"%>
    </script>

    <script type="text/javascript">
        $(document).ready(function () {
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
            <h2>Effective Artists</h2>
        </div>

        <div class="report-description">
            <p>Самые эффективные артисты по количеству хитов. В первую очередь учитываются чарттопперы, потом топ10 хиты и потом общее количество хитов.</p>
        </div>

        <form method="get" action="/reports" class="report-filter-form">
            <input type="hidden" name="report" value="effectiveArtists">

            <label for="sort">Sort by:</label>

            <select id="sort" name="sort" class="win95-select" onchange="this.form.submit()">
                <option value="no1" ${sort == 'no1' ? 'selected' : ''}>No 1s</option>
                <option value="top10" ${sort == 'top10' ? 'selected' : ''}>Top 10s</option>
                <option value="top40" ${sort == 'top40' ? 'selected' : ''}>Top 40s</option>
            </select>
        </form>

        <div id="report-list">

            <div class="song-row">
                <div class="flex1">
                    <p style="margin-bottom:0px;"><b>No</b></p>
                </div>

                <div class="flex9"></div>

                <div class="flex1">
                    <p style="margin-bottom:0px;"><b>no1</b></p>
                </div>

                <div class="flex1">
                    <p style="margin-bottom:0px;"><b>top10</b></p>
                </div>

                <div class="flex-end">
                    <p style="margin-bottom:0px;"><b>top40</b></p>
                </div>
            </div>

            <c:set var="i" value="1" />

            <c:forEach items="${artists}" var="artist">

                <div name="song">
                    <div class="song-row">

                        <div class="flex1 i-counter">
                            <p>${i}</p>
                        </div>

                        <div class="flex9 data-start">
                            <p>
                                <span class="artist-link"
                                      data-artists="${artist[0]}"
                                      title="Open artist page"
                                      tabindex="0">
                                        ${artist[0]}
                                </span>
                            </p>
                        </div>

                        <div class="flex1 flex-end data-end">
                            <p>${artist[1]}</p>
                        </div>

                        <div class="flex1 flex-end data-end">
                            <p>${artist[2]}</p>
                        </div>

                        <div class="flex1 flex-end data-end">
                            <p>${artist[3]}</p>
                        </div>

                    </div>
                </div>

                <c:set var="i" value="${i+1}" />

            </c:forEach>

        </div>

        <%@include file="../components/artist-picker-modal.jsp"%>

    </div>
</div>

</body>
</html>