
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
        <%@include file="songEntry.jsp"%>
    </c:forEach>
</div>

<div style="display:none;" name="chart-run-template">
    <p name="chart-run-header"></p>
    <div name="positions" style="display: flex; flex-flow: row wrap;">
        <div style="width:30px;" name="position">
            <a href="#" target="_blank" title="Посмотреть чарт" name="chartLink"></a>
        </div>
    </div>
</div>