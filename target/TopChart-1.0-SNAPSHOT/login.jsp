<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <title>TOP40 - Login</title>
    <link rel="icon" href="/icons/favico.png" type="image/x-icon">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

    <style><%@include file="/css/style.css"%>
    /* локально для логина */
    .login-box{
        width: 520px;
    }

    .login-title{
        margin: 0 0 8px 0;
        font-size: 16px;
        color:#000;
    }

    .login-row{
        display:flex;
        align-items:center;
        margin-bottom: 10px;
    }

    .login-row label{
        width: 120px;
        font-weight: bold;
    }

    .login-row input[type="text"],
    .login-row input[type="password"]{
        width: 320px;
    }

    .error-box{
        background:#fcfbdd;
        border-top: 2px solid #192428;
        border-left: 2px solid #192428;
        border-right: 2px solid #fff;
        border-bottom: 2px solid #fff;
        padding: 8px 10px;
        margin: 10px 0 0 0;
    }

    .error-box b{
        color:#d80000;
    }

    .success-box{
        background:#e6ffea; /* мягкий зелёный */
        border-top: 2px solid #192428;
        border-left: 2px solid #192428;
        border-right: 2px solid #fff;
        border-bottom: 2px solid #fff;
        padding: 8px 10px;
        margin: 10px 0 0 0;
    }

    .success-box b{
        color:#0a7a2f;
    }

    .login-actions{
        display:flex;
        justify-content: end;
        gap: 8px;
        margin-top: 12px;
    }

    .hint{
        font-size: 12px;
        color:#282929;
        margin-top: 6px;
    }
    </style>

    <script type="text/javascript">
        $(document).ready(function(){
            $('[name="login"]').focus();

            $('[name="password"]').on('keydown', function(e){
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
        <div name="menu" style="margin-bottom:5px;">
            <b>Вход в личный кабинет</b>
        </div>
    </div>

    <div class="inner-gray-block login-box">
        <div class="sub-header" style="border:0; padding:0; margin-bottom:8px;">
            <h2 style="margin:0; color:#000676;">Авторизация</h2>
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

        <form method="post" action="/login">
            <div class="login-row">
                <label for="login">Логин</label>
                <input id="login" type="text" name="login" value="zimowski" autocomplete="username" required />
            </div>

            <div class="login-row">
                <label for="password">Пароль</label>
                <input id="password" type="password" name="password" value="123456" autocomplete="current-password" required />
            </div>

            <div class="login-actions">
                <input type="submit" value="Войти" />
                <input type="button" value="Назад" onclick="top.location.href='/';" />
            </div>

        </form>
    </div>
</div>
</body>
</html>