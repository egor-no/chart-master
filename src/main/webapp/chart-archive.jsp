<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <title>TOP40 - Archive</title>
    <jsp:include page="components/head.jsp"/>
</head>
<body>
<div class="container">
    <h1>TOP40 Archive</h1>

    <div class="nav">
        <jsp:include page="components/menu.jsp">
            <jsp:param name="active" value="chart"/>
        </jsp:include>
    </div>

    <div id="archive-list">
        <c:forEach items="${rows}" var="row">
            <div class="archive-row">
                <div class="archive-main">
                    <a href="/chart?ci=${chartInfoId}&chartNumber=${row.issueNumber}">
                        Issue #${row.issueNumber}
                    </a>
                    <span>${row.date}</span>
                </div>

                <div class="archive-top3">
                    <div>1. ${row.top1}</div>
                    <div>2. ${row.top2}</div>
                    <div>3. ${row.top3}</div>
                </div>
            </div>
        </c:forEach>
    </div>

    <div class="archive-pagination">
        <c:if test="${page > 1}">
            <a href="/chartarchive?ci=${chartInfoId}&page=${page - 1}">Previous</a>
        </c:if>

        <div class="archive-pages">
            <c:forEach items="${pages}" var="p">
                <c:choose>
                    <c:when test="${p == page}">
                        <span class="archive-page-current">${p}</span>
                    </c:when>
                    <c:otherwise>
                        <a href="/chartarchive?ci=${chartInfoId}&page=${p}">${p}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>

        <c:if test="${page < totalPages}">
            <a href="/chartarchive?ci=${chartInfoId}&page=${page + 1}">Next</a>
        </c:if>
    </div>
</div>
</body>
</html>