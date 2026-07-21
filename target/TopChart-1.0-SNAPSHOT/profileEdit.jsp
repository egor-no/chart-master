<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
    <title>TopChart — Edit Profile</title>
    <jsp:include page="components/head.jsp"/>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/theme-${currentTheme}-home.css">
    <style>
        .profile-header{
            display:flex;
            gap:12px;
            align-items:flex-start;
        }
        .profile-avatar{
            width:110px;
            height:110px;
            background:#ffffff;
            border-top:2px solid #192428;
            border-left:2px solid #192428;
            border-right:2px solid #fff;
            border-bottom:2px solid #fff;
            display:flex;
            align-items:center;
            justify-content:center;
            font-size:12px;
            overflow:hidden;
        }
        .profile-actions{
            display:flex;
            justify-content:flex-end;
            gap:8px;
            margin-top:8px;
        }

        .profile-form{
            display:flex;
            flex-direction:column;
            gap:10px;
        }

        .profile-form-row{
            display:flex;
            flex-direction:column;
            gap:4px;
        }

        .profile-label{
            font-size:12px;
            color:#334;
        }

        .profile-input,
        .profile-textarea{
            width:100%;
            padding:6px 8px;
            background:#ffffff;
            border-top:2px solid #192428;
            border-left:2px solid #192428;
            border-right:2px solid #fff;
            border-bottom:2px solid #fff;
            font-family:inherit;
            font-size:13px;
            box-sizing:border-box;
        }

        .profile-textarea{
            min-height:110px;
            resize:vertical;
        }

        .error-text{
            color:#8b0000;
            font-size:12px;
        }

        .charts-edit-note{
            background:#ffffff;
            border-top:2px solid #192428;
            border-left:2px solid #192428;
            border-right:2px solid #fff;
            border-bottom:2px solid #fff;
            padding:10px 12px;
        }
    </style>
</head>

<body class="home-body">
<div class="home-wrap">
    <div class="home-window">

        <div class="home-titlebar">
            <div class="home-title">Edit profile</div>
            <div class="home-title-right">
                <a href="/" class="home-link">portal</a>
            </div>
        </div>

        <div class="home-statusbar">
            <div class="home-status">
                <span class="home-hello">Hey, <b>${sessionScope.userLogin}</b>!</span>
                <a href="/" class="menu-icon" title="Home"><i class="fa fa-home"></i></a>
                <a href="/profile" class="menu-icon" title="My profile"><i class="fa fa-user"></i></a>
                <a href="/logout" class="menu-icon" title="Logout"><i class="fa fa-sign-out"></i></a>
            </div>
        </div>

        <div class="home-layout">
            <div class="home-sidebar">
                <div class="home-box">
                    <div class="home-box-head">Profile</div>
                    <div class="home-box-body">
                        <div class="side-row">
                            <span class="muted">nickname</span>
                            <span class="side-num">${fn:escapeXml(profileUser.nickname)}</span>
                        </div>
                        <div class="side-row">
                            <span class="muted">charts</span>
                            <span class="side-num">${profileUser.charts == null ? 0 : profileUser.charts.size()}</span>
                        </div>
                        <div class="tiny muted" style="margin-top:8px;">
                            Edit your public profile here.
                        </div>
                    </div>
                </div>

                <jsp:include page="components/theme-switcher.jsp"/>

            </div>

            <div class="home-main">

                <div class="home-page">
                    <div class="home-page-head">
                        Edit: ${fn:escapeXml(profileUser.nickname)}
                    </div>
                    <div class="home-page-body">

                        <form method="post" action="/profile?action=update" enctype="multipart/form-data" class="profile-form">
                            <div class="profile-header">
                                <div class="profile-avatar">
                                    <c:choose>
                                        <c:when test="${not empty profileUser.avatar}">
                                            <img src="${pageContext.request.contextPath}/avatars/${fn:escapeXml(profileUser.avatar)}"
                                                 alt="${fn:escapeXml(profileUser.nickname)}"
                                                 style="width:100%; height:100%; object-fit:cover; display:block;">
                                        </c:when>
                                        <c:otherwise>
                                            no avatar<br/>
                                        </c:otherwise>
                                    </c:choose>
                                </div>

                                <div style="flex:1;">
                                    <div class="profile-form-row">
                                        <label class="profile-label">Nickname *</label>
                                        <input class="profile-input" type="text" name="nickname"
                                               value="${fn:escapeXml(profileUser.nickname)}" maxlength="80"/>
                                        <c:if test="${not empty nicknameError}">
                                            <div class="error-text">${fn:escapeXml(nicknameError)}</div>
                                        </c:if>
                                    </div>

                                    <div class="profile-form-row" style="margin-top:10px;">
                                        <label class="profile-label">Slogan</label>
                                        <input class="profile-input" type="text" name="slogan"
                                               value="${fn:escapeXml(profileUser.slogan)}" maxlength="120"/>
                                    </div>

                                    <div class="profile-form-row" style="margin-top:10px;">
                                        <label class="profile-label">Bio</label>
                                        <textarea class="profile-textarea" name="bio" maxlength="1000">${fn:escapeXml(profileUser.bio)}</textarea>
                                    </div>

                                    <div class="profile-form-row" style="margin-top:10px;">
                                        <label class="profile-label">New avatar</label>
                                        <input class="profile-input" type="file" name="avatarFile" accept="image/*"/>
                                        <div class="muted tiny">
                                            Leave empty to keep current avatar.
                                        </div>

                                        <c:if test="${not empty profileUser.avatar}">
                                            <label style="margin-top:6px;">
                                                <input type="checkbox" name="removeAvatar" value="1"/>
                                                Remove current avatar
                                            </label>
                                        </c:if>
                                    </div>

                                    <div class="profile-form-row" style="margin-top:10px;">
                                        <label class="profile-label">New password</label>
                                        <input class="profile-input" type="password" name="password" maxlength="100"/>
                                        <div class="muted tiny">
                                            Leave empty to keep current password.
                                        </div>
                                    </div>

                                    <div class="profile-actions">
                                        <input type="submit" value="Save changes"/>
                                        <input type="button" value="Cancel"
                                               onclick="window.location.href='${pageContext.request.contextPath}/profile';" />
                                    </div>
                                </div>
                            </div>
                        </form>

                    </div>
                </div>

                <div class="home-page" style="margin-top:12px;">
                    <div class="home-page-head">Charts</div>
                    <div class="home-page-body">
                        <div class="charts-edit-note">
                            <div class="muted tiny">
                                Chart list stays on the main profile page.
                            </div>
                            <div style="margin-top:8px;">
                                <a class="home-link" href="/profile">Back to full profile</a>
                            </div>
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