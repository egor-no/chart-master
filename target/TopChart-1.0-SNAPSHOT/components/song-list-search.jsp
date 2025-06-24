
<div id="song-list">
    <div class="song-row">
        <div class="flex1">
            <p style="margin-bottom:0px;"><b>Peak</b></p>
        </div>
        <div class="flex9">
        </div>
        <div class="flex-end">
            <p style="margin-bottom:0px;"><b>WOC</b></p>
        </div>
        <div class="flex1">
        </div>
    </div>
    <c:forEach items="${songs}" var="song">
        <%@include file="song-entry.jsp"%>
    </c:forEach>
</div>

<%@include file="chart-run-template.jsp"%>
