<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
    <title>TOP40 - Top songs</title>
    <jsp:include page="/components/head.jsp"/>
    <script type="text/javascript"><%@include file="/components/song-history.js"%></script>
    <script type = "text/javascript" >

        $(document).ready(function() {

            var isListLoaded = ${isListLoaded};

            if (isListLoaded == false) {
                $('#report-list').css('display', 'none');
            }
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
                <input style="display: none" name="report" type="text" value="topSongsDate" />
                <input type="submit" value="Сформировать" />
            </form>
        </div>
    </div>
    <div id="report-body">
        <div class="report-header">
            <h2>Top Songs</h2>
        </div>
        <div class="report-description">
            <p>В этом отчёте собраны самые успешные песни за выбранный период по взвешенному рейтингу. При расчёте учитываются и позиции в чарте, и продолжительность пребывания, что позволяет выявить действительно значимые хиты, а не разовые успехи.</p>
        </div>

        <input style="display: none" name="date1-data" type="text" value="${date1}" />
        <input style="display: none" name="date2-data" type="text" value="${date2}" />

        <div id="report-list" >
            <c:if test="${date2 == ''}">
                <c:set var="date2" value="now"/>
            </c:if>
            <b>From ${date1} til ${date2} </b>
            <div class="song-row">
                <div class="flex1">
                    <p style="margin-bottom:0px;"><b>No</b></p>
                </div>
                <div class="flex1">
                    <p style="margin-bottom:0px;"><b>Score</b></p>
                </div>
                <div class="flex9">
                </div>
                <div class="flex1">
                    <p style="margin-bottom:0px;"><b>Peak</b></p>
                </div>
                <div class="flex-end">
                    <p style="margin-bottom:0px;"><b>WoC</b></p>
                </div>
            </div>
            <c:set var="i" value="1" />
            <c:forEach items="${songs}" var="song">
                <div name="song">
                    <div class="song-row">
                        <input name="song-id" style="display:none;" type="text" value="${song[1]}" />

                        <div class="flex1 i-counter">
                            <p>${i}</p>
                        </div>
                        <div class="flex1 data-start">
                            <p>${song[0]}</p>
                        </div>
                        <div class="flex4">
                            <p>${song[4]}</p>
                        </div>
                        <div class="flex5">
                            <p>${song[5]}</p>
                        </div>
                        <div class="flex-end data-end">
                            <p>${song[2]}</p>
                        </div>
                        <div class="flex1 flex-end data-end">
                            <p> ${song[3]}</p>
                        </div>
                    </div>
                    <%@include file="../components/song-history.jsp"%>
                </div>
                <c:set var="i" value="${i+1}" />
            </c:forEach>
        </div>

        <%@include file="../components/chart-run-template.jsp"%>

    </div>
</div>

</body>
</html>