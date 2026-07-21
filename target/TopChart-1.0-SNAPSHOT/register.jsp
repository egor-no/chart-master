<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
    <title>TopChart — Register</title>
    <jsp:include page="components/head.jsp"/>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/theme-${currentTheme}-home.css">
    <style>
        .form-grid{
            display:flex;
            flex-direction:column;
            gap:10px;
        }
        .form-row{
            display:flex;
            flex-direction:column;
            gap:4px;
        }
        .retro-input,
        .retro-textarea{
            width:100%;
            padding:6px 8px;
            background:#fff;
            border-top:2px solid #192428;
            border-left:2px solid #192428;
            border-right:2px solid #fff;
            border-bottom:2px solid #fff;
            font-family:inherit;
            font-size:13px;
            box-sizing:border-box;
        }
        .retro-textarea{
            min-height:100px;
            resize:vertical;
        }
        .error-text{
            color:#8b0000;
            font-size:12px;
        }
        .profile-actions{
            display:flex;
            gap:8px;
            margin-top:8px;
            align-items:center;
        }
    </style>
</head>

<body class="home-body">
<div class="home-wrap">
    <div class="home-window">

        <div class="home-titlebar">
            <div class="home-title">Register</div>
            <div class="home-title-right">
                <a href="/" class="home-link">portal</a>
            </div>
        </div>

        <div class="home-statusbar">
            <div class="home-status">
                <a href="/" class="menu-icon" title="Home"><i class="fa fa-home"></i></a>
                <a class="home-login-link" href="/login">login</a>
            </div>
        </div>

        <div class="home-layout">
            <div class="home-sidebar">
                <div class="home-box">
                    <div class="home-box-head">Tip</div>
                    <div class="home-box-body">
                        <div class="tiny muted">
                            Avatar field stores only the file name from ROOT/avatars.
                        </div>
                    </div>
                </div>
            </div>

            <div class="home-main">
                <div class="home-page">
                    <div class="home-page-head">Create your profile</div>
                    <div class="home-page-body">

                        <form method="post" action="/register" enctype="multipart/form-data">
                            <div class="form-row">
                                <label>Login *</label>
                                <input class="retro-input" type="text" name="login"
                                       value="${fn:escapeXml(formLogin)}" maxlength="50"/>
                                <c:if test="${not empty loginError}">
                                    <div class="error-text">${fn:escapeXml(loginError)}</div>
                                </c:if>
                            </div>

                            <div class="form-row">
                                <label>Password *</label>
                                <input class="retro-input" type="password" name="password" maxlength="100"/>
                                <c:if test="${not empty passwordError}">
                                    <div class="error-text">${fn:escapeXml(passwordError)}</div>
                                </c:if>
                            </div>

                            <div class="form-row">
                                <label>Nickname *</label>
                                <input class="retro-input" type="text" name="nickname"
                                       value="${fn:escapeXml(formNickname)}" maxlength="80"/>
                                <c:if test="${not empty nicknameError}">
                                    <div class="error-text">${fn:escapeXml(nicknameError)}</div>
                                </c:if>
                            </div>

                            <div class="form-row">
                                <label>Slogan</label>
                                <input class="retro-input" type="text" name="slogan"
                                       value="${fn:escapeXml(formSlogan)}" maxlength="120"/>
                            </div>

                            <div class="form-row">
                                <label>Bio</label>
                                <textarea class="retro-textarea" name="bio" maxlength="1000">${fn:escapeXml(formBio)}</textarea>
                            </div>

                            <div class="form-row">
                                <label>Avatar </label>
                                <input type="file" name="avatarFile" accept="image/*">
                            </div>

                            <div class="profile-actions">
                                <input type="submit" value="Create profile"/>
                                <input type="button" value="Cancel"
                                       onclick="window.location.href='${pageContext.request.contextPath}/';"/>
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