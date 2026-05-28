<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
    <title>TOP40 - Longest stallers</title>
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
            <h2>Longest Stallers</h2>
        </div>
        <div class="report-description">
            <p>Песни, которым потребовалось больше всего времени, чтобы добраться до топ-10. Отчёт показывает медленные, но уверенные подъёмы — хиты, которые «раскачивались» неделями, прежде чем добиться признания.</p>
        </div>

        <div id="report-list">
            <div class="song-row">
                <div class="flex1">
                    <p style="margin-bottom:0px;"><b>No</b></p>
                </div>
                <div class="flex1 data-center">
                    <p style="margin-bottom:0px;"><b>Peak</b></p>
                </div>
                <div class="flex9"></div>
                <div class="flex1 data-center">
                    <p style="margin-bottom:0px;"><b>WoC</b></p>
                </div>
                <div class="flex1">
                    <b>Cal.<br>Weeks</b>
                </div>
            </div>
            <c:set var="i" value="1" />
            <c:forEach items="${rows}" var="r">
                <div name="song">
                    <div class="song-row">
                        <input name="song-id" style="display:none;" value="${r[4]}" />
                        <input name="chart-number" style="display:none;" value="${r[6]}" />
                        <div class="flex1 i-counter">
                            <p>${i}</p>
                        </div>
                        <div class="flex1 data-center">
                            <p>${r[7]}</p>
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
                            <p>${r[3]}</p>
                        </div>
                        <div class="flex1 data-center">
                            <p>${r[0]}</p>
                        </div>
                        <div class="flex1 flex-end data-end">
                            <p>${r[1]}</p>
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