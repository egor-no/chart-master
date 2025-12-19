<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
    String[] slogans = {
            "public / retro web",
            "best viewed at 800x600",
            "slow internet friendly",
            "charts powered by passion",
            "made with love & numbers",
            "no cookies, just charts",
            "loading charts since 1999",
            "handcrafted music statistics",
            "because spreadsheets are boring",
            "for music nerds only",
            "charts never sleep",
            "still counting weeks on chart",
            "turn your obsession into data",
            "more charts than sense",
            "insert disk 2 to continue",
            "now counting... please wait",
            "optimized for dial-up",
            "warning: may contain charts",
            "charting in progress...",
            "do not turn off your computer"
    };

    String slogan = slogans[(int)(Math.random() * slogans.length)];
    request.setAttribute("homeSlogan", slogan);
%>

<html>
<head>
    <title>TopChart — Portal</title>
    <jsp:include page="components/head.jsp"/>
    <style><%@include file="/css/home.css"%></style>
</head>

<body class="home-body">
<div class="home-wrap">

    <div class="home-window">
        <div class="home-titlebar">
            <div class="home-title">TopChart Portal</div>
            <div class="home-title-right">${homeSlogan}</div>
        </div>

        <div class="home-layout">

            <div class="home-sidebar">

                <div class="home-side-top">
                    <c:choose>
                        <c:when test="${loggedIn}">
                            <div class="home-side-icons">
                                <span class="home-hello">
                                    Hey, <b>${sessionScope.userLogin}</b>!
                                </span>

                                <a href="/profile" class="menu-icon" title="Профиль" aria-label="Профиль">
                                    <i class="fa fa-user"></i>
                                </a>
                                <a href="/logout" class="menu-icon" title="Выход" aria-label="Выход">
                                    <i class="fa fa-sign-out"></i>
                                </a>
                            </div>
                        </c:when>

                        <c:otherwise>
                            <a class="home-login-link" href="/login">login</a>
                            <span class="muted tiny"> (to create & edit)</span>
                        </c:otherwise>
                    </c:choose>
                </div>

                <div class="home-box">
                    <div class="home-box-head">Site stats</div>
                    <div class="home-box-body">
                        <div class="side-row">
                            <span class="muted">users</span>
                            <span class="side-num">${totalUsers}</span>
                        </div>
                        <div class="side-row">
                            <span class="muted">chart infos</span>
                            <span class="side-num">${totalChartInfos}</span>
                        </div>
                        <div class="side-row">
                            <span class="muted">issues</span>
                            <span class="side-num">${totalCharts}</span>
                        </div>
                    </div>
                </div>

                <!-- "Top users" (олдскул: просто первые N из списка) -->
                <div class="home-box">
                    <div class="home-box-head">Users (featured)</div>
                    <div class="home-box-body">
                        <c:forEach items="${users}" var="u" begin="0" end="7">
                            <div class="side-row">
                                <span class="side-name">${fn:escapeXml(u.nickname)}</span>
                                <span class="side-num">${u.chartsCount}</span>
                            </div>
                        </c:forEach>
                        <div class="tiny muted" style="margin-top:8px;">
                            Full directory later...
                        </div>
                    </div>
                </div>

                <!-- "Top chart infos" (олдскул: первые N) -->
                <div class="home-box">
                    <div class="home-box-head">Charts (featured)</div>
                    <div class="home-box-body">
                        <c:forEach items="${chartInfos}" var="ci" begin="0" end="9">
                            <a class="side-link" href="/chart?ci=${ci.id}">
                                    ${fn:escapeXml(ci.title)}
                                <span class="muted">(${ci.issuesCount})</span>
                            </a>
                        </c:forEach>
                        <div class="tiny muted" style="margin-top:8px;">
                            Full directory later...
                        </div>
                    </div>
                </div>

            </div>

            <!-- MAIN ZONE -->
            <div class="home-main">

                <!-- MAIN TEXT AREA -->
                <div class="home-page">
                    <div class="home-page-body">
                        <div class="home-article">
                            <h2>Welcome to TOP40!</h2>

                            <p>
                                This is a personal music chart project: weekly issues, position history,
                                and reports about top songs, artists, highest debuts, and longest runs.
                            </p>

                            <p>
                                It's a project for people addicted to music and numbers. Maybe you've always
                                enjoyed reading Billboard hit lists, checking chart positions of the biggest
                                hits on Wikipedia, or listening to BBC Radio 1 to find out who's at the top
                                this week? Now you can bring that excitement into your own chart.
                            </p>

                            <p>
                                You can browse other people’s charts and reports in guest mode.
                                Login is only needed to create and edit your own charts. And yes — you can
                                have more than just one ;)
                            </p>
                        </div>
                    </div>
                </div>

                <!-- UPDATES -->
                <div class="home-page" style="margin-top:12px;">
                    <div class="home-page-head">Updates / latest issues</div>
                    <div class="home-page-body">

                        <table class="home-table">
                            <thead>
                            <tr>
                                <th>Chart</th>
                                <th>Owner</th>
                                <th>Date</th>
                                <th style="text-align:right;">#</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${latest}" var="l">
                                <tr>
                                    <td>
                                        <a class="home-link" href="/chart?ci=${l.chartInfoId}&chartNumber=${l.chartId}">
                                                ${fn:escapeXml(l.chartInfoTitle)}
                                        </a>
                                    </td>
                                    <td>${fn:escapeXml(l.ownerNickname)}</td>
                                    <td>${fn:escapeXml(l.date)}</td>
                                    <td style="text-align:right;">${l.chartId}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>

                        <div class="muted tiny" style="margin-top:8px;">
                            Links open charts via <b>?ci=</b> and a specific issue via <b>&chartNumber=</b>.
                        </div>

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
