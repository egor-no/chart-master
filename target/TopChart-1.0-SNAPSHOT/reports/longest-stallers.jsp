<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
    <title>TOP40 - Longest stallers</title>
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
            <h2>Longest Stallers</h2>
        </div>
        <div class="report-description">
            <p>Песни, которым потребовалось больше всего времени, чтобы добраться до топ-10. Отчёт показывает медленные, но уверенные подъёмы — хиты, которые «раскачивались» неделями, прежде чем добиться признания.</p>
        </div>

        <div id="report-list" style="width:720px;">
            <div class="song-row">
                <div class="flex1" style="flex:0 0 45px;">
                    <b>WoC</b>
                </div>
                <div class="flex2" style="flex:0 0 120px;">
                    <b>Calendar Weeks</b>
                </div>
                <div class="flex8"></div>
            </div>
            <c:forEach items="${rows}" var="r">
                <div name="song">
                    <div class="song-row">
                        <input name="song-id" style="display:none;" value="${r[4]}" />
                        <input name="chart-id" style="display:none;" value="${r[6]}" />
                        <div class="flex1 data-start">
                            <p>${r[0]}</p>
                        </div>
                        <div class="flex1 data-start">
                            <p>${r[1]}</p>
                        </div>
                        <div class="flex4">
                            <p>${r[2]}</p>
                        </div>
                        <div class="flex5">
                            <p>${r[3]}</p>
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