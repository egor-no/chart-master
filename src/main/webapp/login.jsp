<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <title>TOP40 - Login</title>

    <link rel="icon"
          href="${pageContext.request.contextPath}/icons/favico.png"
          type="image/x-icon">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

    <c:set var="currentTheme"
           value="${empty sessionScope.theme ? 'win95' : sessionScope.theme}" />

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/theme-${currentTheme}.css">

    <script type="text/javascript">
        $(document).ready(function () {
            $('[name="login"]').focus();

            $('[name="password"]').on('keydown', function (e) {
                if (e.key === 'Enter') {
                    $('form').submit();
                }
            });
        });
    </script>
</head>

<body>
<div class="container">
    <h1>TOP40!</h1>

    <div class="nav">
        <div name="menu" class="login-menu-title">
            <b>Вход в личный кабинет</b>
        </div>
    </div>

    <div class="inner-gray-block login-box">
        <div class="sub-header login-sub-header">
            <h2 class="login-title">Авторизация</h2>
        </div>

        <c:if test="${param.registered == '1'}">
            <div class="success-box">
                <b>Success:</b> Profile created! You can now log in.
            </div>
        </c:if>

        <c:if test="${not empty error}">
            <div class="error-box">
                <b>Error:</b> ${error}
            </div>
        </c:if>

        <form method="post"
              action="${pageContext.request.contextPath}/login">

            <div class="login-row">
                <label for="login">Логин</label>

                <input id="login"
                       type="text"
                       name="login"
                       value="zimowski"
                       autocomplete="username"
                       required />
            </div>

            <div class="login-row">
                <label for="password">Пароль</label>

                <input id="password"
                       type="password"
                       name="password"
                       value="123456"
                       autocomplete="current-password"
                       required />
            </div>

            <div class="login-actions">
                <input type="submit" value="Войти" />

                <input type="button"
                       value="Назад"
                       onclick="top.location.href='${pageContext.request.contextPath}/';" />
            </div>
        </form>
    </div>
</div>
</body>
</html>