<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <title>TopChart — Profile</title>
    <jsp:include page="components/head.jsp"/>
    <style><%@include file="/css/home.css"%></style>
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
        }
        .profile-actions{
            display:flex;
            justify-content:flex-end;
            gap:8px;
            margin-top:8px;
        }
        .chart-cards{
            display:flex;
            flex-direction:column;
            gap:10px;
        }
        .chart-card{
            background:#ffffff;
            border-top:2px solid #192428;
            border-left:2px solid #192428;
            border-right:2px solid #fff;
            border-bottom:2px solid #fff;
            padding:10px 12px;
        }
        .chart-card-head{
            display:flex;
            justify-content:space-between;
            align-items:baseline;
            gap:10px;
            border-bottom:1px solid #192428;
            padding-bottom:6px;
            margin-bottom:8px;
        }
        .chart-card-title a{
            color:#0000a8;
            font-weight:bold;
            text-decoration:none;
        }
        .chart-card-title a:hover{ text-decoration:underline; }
        .chart-card-meta{
            font-size:12px;
        }
        .dates-row{
            display:flex;
            flex-wrap:wrap;
            gap:6px;
            margin-top:6px;
        }
        .date-pill{
            background:#dce0e2;
            border-top:1px solid #fff;
            border-left:1px solid #fff;
            border-right:1px solid #192428;
            border-bottom:1px solid #192428;
            padding:2px 6px;
            font-size:12px;
        }
        .card-actions{
            margin-top:8px;
            display:flex;
            gap:8px;
            align-items:center;
        }

        .date-pill-link{
            display:inline-block;
            color:#0000a8;
            text-decoration:none;
        }

        .date-pill-link:hover{
            text-decoration:underline;
        }

        .date-pill-link:visited{
            color:#76367a;
        }

        .danger-actions{
            display:flex;
            gap:8px;
            flex-wrap:wrap;
            align-items:center;
        }

        .danger-actions form{
            margin:0;
        }

        .danger-note{
            line-height:1.4;
        }

        .inactive-badge{
            color:#7a5a00;
            font-size:12px;
            margin-left:6px;
        }

        .inactive-warning{
            background:#fcfbdd;
            border-top:2px solid #192428;
            border-left:2px solid #192428;
            border-right:2px solid #fff;
            border-bottom:2px solid #fff;
            padding:10px 12px;
            margin-bottom:12px;
        }

        .inactive-view{
            opacity:0.55;
            filter:grayscale(100%);
        }

        .inactive-view a,
        .inactive-view input,
        .inactive-view button{
            pointer-events:none;
        }

        .danger-only{
            display:none !important;
        }

        .danger-mode .danger-only{
            display:inline-flex !important;
        }

        .danger-mode .home-titlebar{
            background:#8b0000;
        }

        .danger-mode {
            background:#b9b0a1;
        }

        .danger-mode .home-page,
        .danger-mode .home-box,
        .danger-mode .chart-card{
            background:#fff0f0;
        }

        .danger-mode .home-page-head,
        .danger-mode .home-box-head{
            color:#8b0000;
        }

        .danger-mode .danger-block{
            background:#ffdcdc;
        }

        .danger-mode .chart-delete-form{
            display:inline-flex !important;
        }

        .danger-toggle-actions {
            display:flex;
            gap:8px;
            flex-wrap:wrap;
            align-items:center;
        }

        .chart-delete-form{
            margin:0 0 0 6px;
        }

        .chart-title-disabled {
            color: #666;
            font-weight: bold;
            cursor: default;
        }
    </style>
</head>

<script>
    function enterDangerZone() {
        document.body.classList.add('danger-mode');
        document.getElementById('enterDangerBtn').style.display = 'none';
    }

    function exitDangerZone() {
        document.body.classList.remove('danger-mode');
        document.getElementById('enterDangerBtn').style.display = 'inline-block';
    }
</script>

<body class="home-body">
<div class="home-wrap">
    <div class="home-window">

        <div class="home-titlebar">
            <div class="home-title">User profile</div>
            <div class="home-title-right">
                <a href="/" class="home-link">portal</a>
            </div>
        </div>

        <div class="home-statusbar">
            <div class="home-status">
                <c:choose>
                    <c:when test="${loggedIn}">
                        <span class="home-hello">Hey, <b>${sessionScope.userLogin}</b>!</span>
                        <a href="/" class="menu-icon" title="Home"><i class="fa fa-home"></i></a>
                        <a href="javascript:void(0)"  class="menu-icon" title="Поделиться профилем" aria-label="Поделиться профилем" onclick="copyText(window.location.origin + '/profile?u=${profileUser.id}')"><i class="fa fa-link"></i></a>
                        <a href="/profile" class="menu-icon" title="My profile"><i class="fa fa-user"></i></a>
                        <a href="/logout" class="menu-icon" title="Logout"><i class="fa fa-sign-out"></i></a>
                    </c:when>
                    <c:otherwise>
                        <a href="/" class="menu-icon" title="Home"><i class="fa fa-home"></i></a>
                        <a href="javascript:void(0)"  class="menu-icon" title="Поделиться профилем" aria-label="Поделиться профилем" onclick="copyText(window.location.origin + '/profile?u=${profileUser.id}')"><i class="fa fa-link"></i></a>
                        <a class="home-login-link" href="/login">login</a>
                        <span class="muted tiny">(to create & edit)</span>
                    </c:otherwise>
                </c:choose>
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
                        <c:if test="${isMine}">
                            <div class="tiny muted" style="margin-top:8px;">
                                This is your page.
                            </div>
                        </c:if>
                        <div class="side-row">
                            <span class="muted">status</span>
                            <span class="side-num">
                                <c:choose>
                                    <c:when test="${profileUser.active}">active</c:when>
                                    <c:otherwise>inactive</c:otherwise>
                                </c:choose>
                            </span>
                        </div>
                    </div>
                </div>
            </div>

            <div class="home-main">

                <div class="home-page">
                    <div class="home-page-head">
                        ${fn:escapeXml(profileUser.nickname)}

                        <c:if test="${not profileUser.active}">
                            <span class="inactive-badge">(user inactive)</span>
                        </c:if>

                        <c:if test="${not empty profileUser.slogan}">
                            <span class="muted tiny"> — ${fn:escapeXml(profileUser.slogan)}</span>
                        </c:if>
                    </div>

                    <div class="home-page-body">

                        <c:if test="${isMine and not profileUser.active}">
                            <div class="inactive-warning">
                                <div>
                                    <b>This profile is inactive.</b><br/>

                                    <c:if test="${not empty deactivatedFormatted}">
                                        <span class="muted tiny">
                                            Deactivated at: ${deactivatedFormatted}
                                        </span>
                                    </c:if>
                                </div>

                                <div style="margin-top:10px;">
                                    <form method="post"
                                          action="${pageContext.request.contextPath}/profile?action=activate"
                                          style="margin:0;">
                                        <input type="submit" value="Activate again"/>
                                    </form>
                                </div>
                            </div>
                        </c:if>

                        <div class="${not profileUser.active ? 'inactive-view' : ''}">
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
                                <c:if test="${not empty profileUser.bio}">
                                    <p style="margin-top:0; white-space: pre-line;">
                                            ${fn:escapeXml(profileUser.bio)}
                                    </p>
                                </c:if>

                                <c:if test="${empty profileUser.bio}">
                                    <p class="muted tiny" style="margin-top:0;">
                                        No bio yet.
                                    </p>
                                </c:if>

                                <div class="profile-actions">
                                    <c:if test="${isMine}">
                                        <!-- пока заглушка -->
                                        <input type="button" value="Add new chart"
                                               onclick="window.location.href='${pageContext.request.contextPath}/chartInfo?action=add';" />
                                        <input type="button" value="Edit profile"
                                               onclick="window.location.href='${pageContext.request.contextPath}/profile?action=edit';" />
                                    </c:if>
                                </div>
                            </div>
                        </div>
                        </div>
                    </div>
                </div>

                <div class="home-page ${not profileUser.active ? 'inactive-view' : ''}" style="margin-top:12px;">
                    <div class="home-page-head">Charts</div>
                        <div class="home-page-body">
                        <div class="chart-cards">
                        <c:forEach items="${cards}" var="card">
                            <div class="chart-card">
                                <div class="chart-card-head">
                                    <div class="chart-card-title">
                                        <c:choose>
                                            <c:when test="${card.lastIssues != null && card.lastIssues.size() > 0}">
                                                <a href="${pageContext.request.contextPath}/chart?ci=${card.ci.id}">
                                                        ${fn:escapeXml(card.ci.title)}
                                                </a>
                                            </c:when>

                                            <c:when test="${isMine}">
                                                <a href="${pageContext.request.contextPath}/chartadd?ci=${card.ci.id}&emptyChart=1">
                                                        ${fn:escapeXml(card.ci.title)}
                                                </a>
                                            </c:when>

                                            <c:otherwise>
                                                <span class="chart-title-disabled">
                                                        ${fn:escapeXml(card.ci.title)}
                                                </span>
                                            </c:otherwise>
                                        </c:choose>

                                        <c:if test="${isMine}">
                                            <a class="home-link"
                                               href="${pageContext.request.contextPath}/chartInfo?action=edit&ci=${card.ci.id}"
                                               title="Edit chart">
                                                <i class="fa fa-pencil"></i>
                                            </a>

                                            <form method="post"
                                                  action="${pageContext.request.contextPath}/chartInfo?action=delete"
                                                  class="danger-only chart-delete-form"
                                                  onsubmit="return confirm('Delete this chart? All issues and chart positions will be removed. This cannot be undone.');">
                                                <input type="hidden" name="ci" value="${card.ci.id}">
                                                <button type="submit"
                                                        class="home-link"
                                                        title="Delete chart"
                                                        style="border:0; background:none; padding:0; cursor:pointer;">
                                                    <i class="fa fa-trash"></i>
                                                </button>
                                            </form>
                                        </c:if>
                                    </div>
                                    <div class="chart-card-meta muted">
                                        size: ${card.ci.size}
                                        <c:if test="${not empty card.ci.description}">
                                            | ${fn:escapeXml(card.ci.description)}
                                        </c:if>
                                    </div>
                                </div>

                                <div class="muted tiny">Latest issues:</div>
                                <div class="dates-row">
                                    <c:if test="${card.lastIssues == null || card.lastIssues.size() == 0}">
                                        <span class="muted tiny">No issues yet.</span>
                                    </c:if>
                                    <c:forEach items="${card.lastIssues}" var="it">
                                        <a class="date-pill date-pill-link"
                                           href="/chart?ci=${card.ci.id}&chartNumber=${it.issueNumber}"
                                           title="Open issue #${it.issueNumber}">
                                                ${fn:escapeXml(it.date)}
                                        </a>
                                    </c:forEach>
                                </div>

                                <div class="card-actions">
                                    <c:if test="${card.lastIssues != null && card.lastIssues.size() > 0}">
                                        <a class="home-link" href="/chart?ci=${card.ci.id}">Open</a>
                                        <c:if test="${isMine}">
                                            <span class="muted tiny">|</span>
                                        </c:if>
                                    </c:if>

                                    <c:if test="${isMine}">
                                        <a class="home-link" href="/chartadd?ci=${card.ci.id}">Add issue</a>
                                        <span class="muted tiny">(for this chart)</span>
                                    </c:if>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                     </div>
                </div>

                <c:if test="${isMine and profileUser.active}">
                    <div class="home-page danger-block" style="margin-top:12px;">
                        <div class="home-page-head">Danger zone</div>
                        <div class="home-page-body">

                            <div class="danger-note muted tiny" style="margin-bottom:10px;">
                                Dangerous actions are hidden by default.
                            </div>

                            <div class="danger-toggle-actions">
                                <input id="enterDangerBtn"
                                       type="button"
                                       value="Enter danger zone"
                                       onclick="enterDangerZone();"/>

                                <input id="exitDangerBtn"
                                       class="danger-only"
                                       type="button"
                                       value="Exit danger zone"
                                       onclick="exitDangerZone();"/>
                            </div>

                            <div class="danger-actions danger-only" style="margin-top:12px;">
                                <form method="post"
                                      action="${pageContext.request.contextPath}/profile?action=deactivate"
                                      onsubmit="return confirm('Deactivate your account?');">
                                    <input type="submit" value="Deactivate account"/>
                                </form>

                                <form method="post"
                                      action="${pageContext.request.contextPath}/profile?action=delete"
                                      onsubmit="return confirm('Delete account permanently? All your charts and issues will be removed. This cannot be undone.');">
                                    <input type="submit" value="Delete permanently"/>
                                </form>
                            </div>

                        </div>
                    </div>
                </c:if>

            </div>

        </div>

        <jsp:include page="components/footer.jsp"/>
    </div>
</div>
</body>
</html>
