<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>TOP40 - Songs</title>
    <jsp:include page="components/head.jsp"/>
    <script type="text/javascript"><%@include file="/components/song-history.js"%></script>
    <style>
    .artist-stat {
        display: flex;
        flex-flow: column;
        align-items:center;
    }

    #artist-stats-border{
        width: 620px;
        border: 0;
        padding: 0;
        margin: 6px 0 12px 0;
    }

    #artist-stats{
        width: 620px;
        display: flex;
        justify-content: space-evenly;
        flex-flow: row nowrap;
        background:#dce0e2;
        border-top:2px solid #fff;
        border-left:2px solid #fff;
        border-right:2px solid #192428;
        border-bottom:2px solid #192428;
        padding: 8px 10px;
        gap: 10px;
    }

    #artist-stats .artist-stat{
        flex: 1;
        padding: 6px 8px;
        border-top:1px solid #fff;
        border-left:1px solid #fff;
        border-right:1px solid #192428;
        border-bottom:1px solid #192428;
        background:#eaeaea;
    }

    #artist-stats h3{
        margin: 0;
        font-size: 26px;
        line-height: 26px;
        font-family: "Arial Black";
        color:#000676;
    }

    #artist-stats p{
        margin: 3px 0 0 0;
        font-size: 12px;
        color:#282929;
    }

    </style>
    <script type = "text/javascript" >
        $(document).ready(function() {
            if ($('[name="searchPhrase"]').val() == '') {
                $('#searchPhraseInfo').css('display', 'none');
                $('#song-list').css('display', 'none');
            }

            $('[name="edit-link"]').on('click', function () {
                var idSong = $(this).closest('[name="song"]').find('[name="song-id"]').val();

                $(this).attr("href", "/songedit?id=" + idSong + "&artist=" + '${artist}');
            });
        });
    </script>
</head>
<body>
<div class="container">
    <h1>Список песен</h1>
    <div class="nav">
        <jsp:include page="components/menu.jsp">
            <jsp:param name="active" value="none"/>
        </jsp:include>
    </div>

    <div class="sub-header">
        <h2>${artist}</h2>
    </div>
    <div id="artist-stats-border">
        <div id="artist-stats">
            <div id="stats-position-no1s" class="artist-stat">
                <h3>${stats[0]}</h3>
                <p>No 1s</p>
            </div>
            <div id="stats-position-top10s" class="artist-stat">
                <h3>${stats[1]}</h3>
                <p>Top 10s</p>
            </div>
            <div id="stats-position-top40s" class="artist-stat">
                <h3>${stats[2]}</h3>
                <p>Top 40s</p>
            </div>
        </div>
    </div>
    <c:set var="artistPickerEnabled" value="false" />
    <%@include file="components/song-list-search.jsp"%>
</div>
</body>
</html>