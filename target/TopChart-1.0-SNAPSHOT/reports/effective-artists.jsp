<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
    <title>TOP40 - Effective artists</title>
    <jsp:include page="/components/head.jsp"/>
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
        <div id="report-list" >
            <div class="song-row">
                <div class="flex1">
                    <p style="margin-bottom:0px;"><b>No</b></p>
                </div>
                <div class="flex9">
                </div>
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
                            <p><a href="/artist?artist=${artist[0]}" target="_blank">${artist[0]}</a></p>
                        </div>
                        <div class="flex1 flex-end data-end">
                            <p>${artist[1]}</p>
                        </div>
                        <div class="flex1 flex-end data-end">
                            <p>${artist[2]}</p>
                        </div>
                        <div class="flex1 flex-end data-end">
                            <p> ${artist[3]}</p>
                        </div>
                    </div>
                </div>
                <c:set var="i" value="${i+1}" />
            </c:forEach>
        </div>
    </div>
</div>

</body>
</html>