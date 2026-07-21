<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="home-box theme-switcher">
    <div class="home-box-head">Appearance</div>

    <div class="home-box-body">
        <div class="theme-caption">
            Choose theme:
        </div>

        <div class="theme-options">
            <a href="${pageContext.request.contextPath}/theme?name=win95"
               class="theme-option ${currentTheme == 'win95' ? 'active' : ''}">

                <span class="theme-option-marker">
                    <c:choose>
                        <c:when test="${currentTheme == 'win95'}">&#9679;</c:when>
                        <c:otherwise>&#9675;</c:otherwise>
                    </c:choose>
                </span>

                <span>Windows 95</span>
            </a>

            <a href="${pageContext.request.contextPath}/theme?name=magazine"
               class="theme-option ${currentTheme == 'magazine' ? 'active' : ''}">

                <span class="theme-option-marker">
                    <c:choose>
                        <c:when test="${currentTheme == 'magazine'}">&#9679;</c:when>
                        <c:otherwise>&#9675;</c:otherwise>
                    </c:choose>
                </span>

                <span>Magazine</span>
            </a>
        </div>
    </div>
</div>