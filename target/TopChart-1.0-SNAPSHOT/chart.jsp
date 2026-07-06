<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
    <title>TOP40 - Music Chart</title>
    <jsp:include page="components/head.jsp"/>
    <style><%@include file="/css/share.css"%></style>
    <script type="text/javascript"><%@include file="/components/song-history.js"%></script>
    <script type="text/javascript"><%@include file="/components/artistPicker.js"%></script>
    <script src="https://cdn.jsdelivr.net/npm/html2canvas@1.4.1/dist/html2canvas.min.js"></script>
</head>
<body>
<div class="container">
    <h1>${chartTitle}</h1>
    <div class="nav">
        <jsp:include page="components/menu.jsp">
            <jsp:param name="active" value="chart"/>
        </jsp:include>

        <form method="GET" action="/chart">
            <label for="chartSearch">Поиск по дате чарта:</label>
            <input name="date" id="chartSearch" type="date" /><input type="submit" value="Искать" />
        </form>
    </div>

    <%@include file="components/chart-table.jsp"%>
</div>

</body>
</html>