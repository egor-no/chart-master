<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>TOP40 - Forbidden</title>
    <jsp:include page="/components/head.jsp"/>
</head>
<body>
<div class="container">
    <h1>TOP40!</h1>

    <div class="nav">
        <div name="menu" style="margin-bottom:5px;">
            <b>${title}</b>
        </div>
    </div>

    <div class="inner-gray-block" style="width: 700px;">
        <div class="sub-header" style="border:0; padding:0; margin-bottom:8px;">
            <h2 style="margin:0; color:#000676;">403 — Access denied</h2>
        </div>

        <p style="margin: 0 0 10px 0;">
            ${message}
        </p>

        <div class="home-hr"></div>

        <div style="display:flex; gap:10px; align-items:center; margin-top:10px;">
            <a href="/">Back to home</a>

            <c:if test="${showProfileLink}">
                <span class="muted tiny">|</span>
                <a href="/profile">Go to profile</a>
                <span class="muted tiny">(choose your chart)</span>
            </c:if>

            <span class="muted tiny">|</span>
            <a href="/chart">Go to chart</a>
        </div>
    </div>
</div>
</body>
</html>