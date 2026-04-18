<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
    <title>TOP40 - Top artists</title>
    <jsp:include page="/components/head.jsp"/>

    <script type="text/javascript"><%@include file="/components/artistPicker.js"%></script>
    <script type="text/javascript">
        $(document).ready(function() {
            var isListLoaded = ${isListLoaded};

            if (isListLoaded == false) {
                $('#report-list').css('display', 'none');
            }

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

            <form method="GET" action="/reports">
                <label for="date1">Поиск с</label>
                <input name="date1" id="date1" type="date" />
                <label for="date2">по</label>
                <input name="date2" id="date2" type="date" />
                <input style="display: none" name="report" type="text" value="topArtistsDate" />
                <input type="submit" value="Сформировать" />
            </form>
        </div>
    </div>
    <div id="report-body">
        <div class="report-header">
            <h2>Top Artists</h2>
        </div>
        <div class="report-description">
            <p>Топовые артисты за выбранный период на основе взвешенного расчёта по всем их песням. Учитываются позиции композиций в чарте и их длительность, что отражает общий вклад артиста в историю чарта.</p>
        </div>

        <input style="display: none" name="date1-data" type="text" value="${date1}" />
        <input style="display: none" name="date2-data" type="text" value="${date2}" />

        <div id="report-list">
            <div class="song-row">
                <div class="flex1">
                    <b>No</b>
                </div>
                <div class="flex1">
                    <b>Score</b>
                </div>
                <div class="flex5">
                </div>
                <div class="flex1">
                    <b>no1</b>
                </div>
                <div class="flex1">
                    <b>top10</b>
                </div>
                <div class="flex-end">
                    <b>top40</b>
                </div>
            </div>
            <c:set var="i" value="1" />
            <c:forEach items="${artists}" var="artist">
                <div name="song">
                    <div class="song-row">
                        <div class="flex1 i-counter">
                            <p>${i}</p>
                        </div>
                        <div class="flex1 data-start">
                            <p>${artist[1]}</p>
                        </div>
                        <div class="flex5">
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
                            <p title="Weeks at number 1">${artist[2]}</p>
                        </div>
                        <div class="flex1 flex-end data-end">
                            <p title="Weeks in top10">${artist[3]}</p>
                        </div>
                        <div class="flex1 flex-end data-end">
                            <p title="Weeks in top40">${artist[4]}</p>
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