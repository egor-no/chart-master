
<div id="song-list">
    <div class="song-row">
        <div class="flex-date">
            <p style="margin-bottom:0;"></p>
        </div>

        <div class="flex1">
            <p style="margin-bottom:0;"><b>Peak</b></p>
        </div>

        <div class="flex9"></div>

        <div class="flex-end">
            <p style="margin-bottom:0;"><b>WoC</b></p>
        </div>

        <div class="flex1"></div>
    </div>
    <c:forEach items="${songRows}" var="row">
        <c:set var="song" value="${row.song}" />
        <c:set var="firstEntryDate" value="${row.firstEntryDate}" />
        <%@include file="song-entry.jsp"%>
    </c:forEach>
</div>

<%@include file="chart-run-template.jsp"%>
