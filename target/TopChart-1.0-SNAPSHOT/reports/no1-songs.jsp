<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
    <title>TOP40 - Longest no1 songs</title>
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
            <h2>Longest no1s</h2>
        </div>
        <div class="report-description">
            <p>Топ песен, которые дольше всех удерживали первое место чарта. Он отражает не просто популярность, а настоящие доминирующие хиты, которые надолго оставались вне конкуренции.</p>
        </div>
        <div id="report-list">
            <div class="song-row">
                <div class="flex1">
                    <b>No</b>
                </div>
                <div class="flex1">
                    <b>W1</b>
                </div>
                <div class="flex9">
                </div>
                <div class="flex-end">
                    <b>WoC</b>
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
                            <p>${song[5]}</p>
                        </div>
                        <div class="flex1 flex-end data-end">
                            <p>${song[3]}</p>
                        </div>
                    </div>
                    <%@include file="../components/song-history.jsp"%>
                </div>
                <c:set var="i" value="${i+1}" />
            </c:forEach>
        </div>

        <%@include file="../components/chart-run-template.jsp"%>
        <%@include file="../components/artist-picker-modal.jsp"%>

    </div>
</div>

</body>
</html>