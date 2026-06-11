<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
    <title>TopChart — ${empty chartInfo ? 'Add chart' : 'Edit chart'}</title>
    <jsp:include page="components/head.jsp"/>
    <style><%@include file="/css/home.css"%></style>
    <style>
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

        .profile-actions{
            display:flex;
            justify-content:flex-end;
            gap:8px;
            margin-top:8px;
        }
    </style>
</head>

<body class="home-body">
<div class="home-wrap">
    <div class="home-window">

        <div class="home-titlebar">
            <div class="home-title">
                ${empty chartInfo ? 'Add new chart' : 'Edit chart'}
            </div>
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
            <div class="home-main" style="width:100%;">

                <div class="home-page">
                    <div class="home-page-head">
                        ${empty chartInfo ? 'Create chart info' : 'Edit: '} ${fn:escapeXml(chartInfo.title)}
                    </div>

                    <div class="home-page-body">

                        <form method="post"
                              action="${pageContext.request.contextPath}/chartInfo?action=${empty chartInfo ? 'create' : 'update'}"
                              class="profile-form">

                            <c:if test="${not empty chartInfo}">
                                <input type="hidden" name="ci" value="${chartInfo.id}">
                            </c:if>

                            <div class="profile-form-row">
                                <label class="profile-label">Title *</label>
                                <input class="profile-input" type="text" name="title"
                                       value="${fn:escapeXml(chartInfo.title)}"
                                       maxlength="120" required/>
                            </div>

                            <div class="profile-form-row">
                                <label class="profile-label">Size *</label>
                                <input class="profile-input" type="number" name="size"
                                       value="${chartInfo.size}"
                                       min="1" max="200" required/>
                            </div>

                            <div class="profile-form-row">
                                <label class="profile-label">Description</label>
                                <textarea class="profile-textarea" name="description" maxlength="1000">${fn:escapeXml(chartInfo.description)}</textarea>
                            </div>

                            <div class="profile-form-row">
                                <label class="profile-label">Fields amount</label>
                                <input class="profile-input" type="number" name="fieldsAmount"
                                       value="${chartInfo.fieldsAmount}"
                                       min="0" max="2"/>
                            </div>

                            <div class="profile-form-row">
                                <label class="profile-label">Field 1 name</label>
                                <input class="profile-input" type="text" name="field1Name"
                                       value="${fn:escapeXml(chartInfo.field1Name)}"
                                       maxlength="80"/>
                            </div>

                            <div class="profile-form-row">
                                <label class="profile-label">Field 2 name</label>
                                <input class="profile-input" type="text" name="field2Name"
                                       value="${fn:escapeXml(chartInfo.field2Name)}"
                                       maxlength="80"/>
                            </div>

                            <div class="profile-actions">
                                <input type="submit" value="Save chart"/>
                                <input type="button" value="Cancel"
                                       onclick="window.location.href='${pageContext.request.contextPath}/profile';"/>
                            </div>

                        </form>

                    </div>
                </div>

            </div>
        </div>

        <div class="home-footer muted">
            Ⓒ egor_no 2025-2026
        </div>
    </div>
</div>
</body>
</html>