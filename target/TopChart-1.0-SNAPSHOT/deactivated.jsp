<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <title>TopChart — Profile deactivated</title>
    <jsp:include page="components/head.jsp"/>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/theme-${currentTheme}-home.css">
</head>

<body class="home-body">
<div class="home-wrap">
    <div class="home-window">

        <div class="home-titlebar">
            <div class="home-title">Profile deactivated</div>
            <div class="home-title-right">
                <a href="/" class="home-link">portal</a>
            </div>
        </div>

        <div class="home-layout">
            <div class="home-main deactivate-main">
                <div class="home-page">
                    <div class="home-page-head">
                        Status
                    </div>
                    <div class="home-page-body center-box">

                        <div class="big-text">
                            Your profile has been deactivated.
                        </div>

                        <div class="muted">
                            You can reactivate it anytime or leave the portal.
                        </div>

                        <div class="actions">

                            <form method="post" action="/profile?action=activate">
                                <input type="submit" value="Activate again"/>
                            </form>

                            <input type="button"
                                   value="Home"
                                   onclick="window.location.href='${pageContext.request.contextPath}/'"/>

                        </div>

                    </div>
                </div>

            </div>
        </div>

        <jsp:include page="components/footer.jsp"/>
    </div>
</div>
</body>
</html>