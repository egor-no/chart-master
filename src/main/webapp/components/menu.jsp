<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div name="menu" class="menu-bar">
    <div class="menu-left">
        <c:set var="active" value="${param.active}" />
        <a href="/">Главная</a>
        |
        <c:choose>
            <c:when test="${active == 'chart'}"><b>Чарт</b></c:when>
            <c:otherwise><a href="/chart">Чарт</a></c:otherwise>
        </c:choose>
        <c:if test="${loggedIn and isOwner}">
            |
            <c:choose>
                <c:when test="${active == 'chartadd'}"><b>Добавить чарт</b></c:when>
                <c:otherwise><a href="/chartadd">Добавить чарт</a></c:otherwise>
            </c:choose>
        </c:if>
        |
        <c:choose>
            <c:when test="${active == 'artists'}"><b>Артисты</b></c:when>
            <c:otherwise><a href="/artists">Артисты</a></c:otherwise>
        </c:choose>
        |
        <c:choose>
            <c:when test="${active == 'songs'}"><b>Песни</b></c:when>
            <c:otherwise><a href="/songs">Песни</a></c:otherwise>
        </c:choose>
        |
        <c:choose>
            <c:when test="${active == 'reports'}"><b>Отчёты</b></c:when>
            <c:otherwise><a href="/reports">Отчёты</a></c:otherwise>
        </c:choose>
    </div>

    <div class="menu-right">
        <c:if test="${loggedIn}">
            <a href="/profile" class="menu-icon" title="Профиль" aria-label="Профиль">
                <i class="fa fa-user"></i>
            </a>
            <a href="/logout" class="menu-icon" title="Выход" aria-label="Выход">
                <i class="fa fa-sign-out"></i>
            </a>
        </c:if>

        <c:if test="${not loggedIn}">
            <a href="/login" class="menu-icon" title="Вход" aria-label="Вход">
                <i class="fa fa-sign-in"></i>
            </a>
        </c:if>
    </div>
</div>