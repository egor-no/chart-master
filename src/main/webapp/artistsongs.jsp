<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>TOP40 - Songs</title>
    <jsp:include page="components/head.jsp"/>
    <style><%@include file="/css/share.css"%></style>
    <script src="https://cdn.jsdelivr.net/npm/html2canvas@1.4.1/dist/html2canvas.min.js"></script>
    <script type="text/javascript"><%@include file="/components/song-history.js"%></script>
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
    <div class="artist-stats-border">
        <div class="artist-stats">

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
    <%@include file="components/share-song-card.jsp"%>
</div>
</body>
</html>