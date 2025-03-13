
<div id="songs-list" style="width:600px; display: flex;  flex-flow: column;">
    <div style="display: flex; flex-flow: row nowrap;">
        <div style="display: flex; flex: 1;">
            <p style="margin-bottom:0px;"><b>Peak</b></p>
        </div>
        <div style="display: flex; flex: 8;">
        </div>
        <div style="display: flex; justify-content: end;">
            <p style="margin-bottom:0px;"><b>WOC</b></p>
        </div>
        <div style="display: flex; flex: 1;">
        </div>
    </div>
    <c:forEach items="${songs}" var="song">
        <%@include file="song-entry.jsp"%>
    </c:forEach>
</div>

<%@include file="chart-run-template.jsp"%>
