<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>TOP40 - Songs</title>
    <jsp:include page="components/head.jsp"/>
    <script type="text/javascript"><%@include file="/components/song-history.js"%></script>
    <script type="text/javascript"><%@include file="/components/artistPicker.js"%></script>
    <script type="text/javascript">
        $(document).ready(function() {
            if ($('[name="searchPhrase"]').val() == '') {
                $('#searchPhraseInfo').css('display', 'none');
                $('#song-list').css('display', 'none');
            } else if ($('[name="length"]').val() == '0') {
                $('#song-list').css('display', 'none');
                $('[name="not-found"]').removeClass('no-display');
            }

            ArtistPicker.init();
        });
    </script>
</head>
<body>
<div class="container">
    <h1>Список песен</h1>
    <div class="nav">
        <jsp:include page="components/menu.jsp">
            <jsp:param name="active" value="songs"/>
        </jsp:include>

        <form method="GET" action="/songs">
            <label for="songSearch">Поиск по песням:</label>
            <input name="search" id="songSearch" type="text" /><input type="submit" value="Искать" />
        </form>
    </div>

    <input name="searchPhrase" style="display:none;" type="text" value="${search}" />
    <input name="length" style="display:none;" type="text" value="${songs == null ? 0 : songs.size()}" />

    <div id="searchPhraseInfo" class="sub-header">
        <h2>Поиск по <i>${search}</i></h2>
    </div>

    <div class="no-display" name="not-found" style="margin-top: 8px; margin-left:8px;">
        <i>Ничего не найдено</i>
    </div>

    <c:set var="artistPickerEnabled" value="true" />
    <%@include file="components/song-list-search.jsp"%>
    <%@ include file="components/artist-picker-modal.jsp" %>
</div>
</body>
</html>