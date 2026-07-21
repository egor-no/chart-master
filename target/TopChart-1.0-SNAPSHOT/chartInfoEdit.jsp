<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
    <title>TopChart — ${empty chartInfo ? 'Add chart' : 'Edit chart'}</title>
    <jsp:include page="components/head.jsp"/>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/theme-${currentTheme}-home.css">
</head>

<body class="home-body">
<div class="home-wrap">
    <div class="home-window">

        <div class="home-titlebar">
            <div class="home-title">
                ${empty chartInfo ? 'Add new chart' : 'Edit chart'}
            </div>
            <div class="home-title-right">
                <a href="${pageContext.request.contextPath}/"
                   class="home-link">
                    portal
                </a>
            </div>
        </div>

        <div class="home-statusbar">
            <div class="home-status">
                <span class="home-hello">Hey, <b>${sessionScope.userLogin}</b>!</span>
                <a href="${pageContext.request.contextPath}/"
                   class="menu-icon"
                   title="Home">
                    <i class="fa fa-home"></i>
                </a>

                <a href="${pageContext.request.contextPath}/profile"
                   class="menu-icon"
                   title="My profile">
                    <i class="fa fa-user"></i>
                </a>

                <a href="${pageContext.request.contextPath}/logout"
                   class="menu-icon"
                   title="Logout">
                    <i class="fa fa-sign-out"></i>
                </a>
            </div>
        </div>

        <div class="home-layout">
            <div class="home-main chart-info-main">

                <div class="home-page">
                    <div class="home-page-head">
                        ${empty chartInfo ? 'Create chart info' : 'Edit: '}
                        ${fn:escapeXml(chartInfo.title)}
                    </div>

                    <div class="home-page-body">

                        <form method="post"
                              action="${pageContext.request.contextPath}/chartInfo?action=${empty chartInfo ? 'create' : 'update'}"
                              class="profile-form">

                            <c:if test="${not empty chartInfo}">
                                <input type="hidden"
                                       name="ci"
                                       value="${chartInfo.id}">
                            </c:if>

                            <div class="profile-form-row">
                                <label class="profile-label"
                                       for="chart-title">
                                    Title *
                                </label>

                                <input id="chart-title"
                                       class="profile-input"
                                       type="text"
                                       name="title"
                                       value="${fn:escapeXml(chartInfo.title)}"
                                       maxlength="120"
                                       required/>
                            </div>

                            <div class="profile-form-row">
                                <label class="profile-label"
                                       for="chart-size">
                                    Size *
                                </label>

                                <input id="chart-size"
                                       class="profile-input"
                                       type="number"
                                       name="size"
                                       value="${chartInfo.size}"
                                       min="1"
                                       max="200"
                                       required/>
                            </div>

                            <div class="profile-form-row">
                                <label class="profile-label"
                                       for="chart-description">
                                    Description
                                </label>

                                <textarea id="chart-description"
                                          class="profile-textarea"
                                          name="description"
                                          maxlength="1000">${fn:escapeXml(chartInfo.description)}</textarea>
                            </div>

                            <div class="profile-form-row">
                                <label class="profile-label"
                                       for="fields-amount">
                                    Fields amount
                                </label>

                                <input id="fields-amount"
                                       class="profile-input"
                                       type="number"
                                       name="fieldsAmount"
                                       value="${chartInfo.fieldsAmount}"
                                       min="0"
                                       max="2"/>
                            </div>

                            <div class="profile-form-row">
                                <label class="profile-label"
                                       for="field1-name">
                                    Field 1 name
                                </label>

                                <input id="field1-name"
                                       class="profile-input"
                                       type="text"
                                       name="field1Name"
                                       value="${fn:escapeXml(chartInfo.field1Name)}"
                                       maxlength="80"/>
                            </div>

                            <div class="profile-form-row">
                                <label class="profile-label"
                                       for="field2-name">
                                    Field 2 name
                                </label>

                                <input id="field2-name"
                                       class="profile-input"
                                       type="text"
                                       name="field2Name"
                                       value="${fn:escapeXml(chartInfo.field2Name)}"
                                       maxlength="80"/>
                            </div>

                            <div class="profile-actions">
                                <input type="submit"
                                       value="Save chart"/>

                                <input type="button"
                                       value="Cancel"
                                       onclick="window.location.href='${pageContext.request.contextPath}/profile';"/>
                            </div>

                        </form>

                    </div>
                </div>

            </div>
        </div>

        <jsp:include page="components/footer.jsp"/>
    </div>
</div>
</body>
</html>