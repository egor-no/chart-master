<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>TOP40 - Songs</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script type = "text/javascript" >
        $(document).ready(function() {
            if ($('[name="searchPhrase"]').val() == '') {
                $('#searchPhraseInfo').css('display', 'none');
                $('#songs-list').css('display', 'none');
            }

            $('[name="history-link"]').on('click', function () {
                event.preventDefault();
                var songDiv =  $(this).closest('[name="song"]')
                if (songDiv.find('[name="position"]').length) {
                    if (songDiv.find('[name="song-history"]').is(":visible")) {
                        songDiv.find('[name="song-history"]').slideUp(500);
                    } else {
                        songDiv.find('[name="song-history"]').slideDown(500);
                    }
                } else {
                    var idSong = $(this).closest('[name="song"]').find('[name="song-id"]').val();
                    $.get("songhistory?idSong=" + idSong, function(songhistory) {
                        var peak = songhistory.peak;
                        var currentChart = songhistory.currentChart;
                        $.each(songhistory.chartRuns, function (index, chartRun) {
                            var chartRunDiv = $(document).find('[name="chart-run-template"]').clone();
                            chartRunDiv.attr('name', 'chart-run');

                            chartRunDiv.find('[name="chart-run-header"]').html(chartRun.firstChart.date + " - " + chartRun.lastChart.date);
                            var chartId = chartRun.firstChart.id;
                            $.each(chartRun.positions, function(i, position) {
                                chartRunDiv.find('[name="position"]:last').find('[name="chartLink"]').html(position);

                                if (position == peak) {
                                    chartRunDiv.find('[name="position"]:last').find('[name="chartLink"]').css('font-weight', 'bold');
                                } else {
                                    chartRunDiv.find('[name="position"]:last').find('[name="chartLink"]').css('font-weight', 'normal');
                                }

                                if (chartId == currentChart) {
                                    chartRunDiv.find('[name="position"]:last').find('[name="chartLink"]').css('font-style', 'italic');
                                } else {
                                    chartRunDiv.find('[name="position"]:last').find('[name="chartLink"]').css('font-style', 'normal');
                                }

                                chartRunDiv.find('[name="position"]:last').find('[name="chartLink"]').attr('href', '/?chartNumber=' + chartId);
                                var positionDiv = chartRunDiv.find('[name="position"]:last').clone();
                                chartRunDiv.find('[name="positions"]').append(positionDiv);
                                chartId++;
                            });
                            chartRunDiv.find('[name="position"]:last').remove();

                            songDiv.find('[name="song-history"]').append(chartRunDiv);
                            chartRunDiv.css('display', 'block');
                            songDiv.find('[name="song-history"]').slideDown(500);
                        });
                    });
                }
            });
        });
    </script>
</head>
<body>
<h1>Список песен</h1>

<div name="menu" style="margin-bottom:5px;">
    <a href="/">Главная</a>
    |
    <a href="/chartadd">Добавить чарт</a>
    |
    <a href="/artists">Артисты</a>
    |
    <b>Песни</b>
</div>

<form method="GET" action="/songs">
    <label for="songSearch">Поиск по песням:</label>
    <input name="search" id="songSearch" type="text" /><input type="submit" value="Искать" />
    <input name="searchPhrase" style="display:none;" type="text" value="${search}" />
</form>
<div id="searchPhraseInfo">
    <h2>Поиск по <i>${search}</i></h2>
</div>
<div id="songs-list" style="width:600px; display: flex;  flex-flow: column;">
    <div style="display: flex; flex-flow: row nowrap;">
        <div style="display: flex; flex: 1;">
            <p style="margin-bottom:0px;"><b>Peak</b></p>
        </div>
        <div style="display: flex; flex: 8;">
        </div>
        <div style="display: flex; justify-content: end;">
            <p style="margin-bottom:0px;"><b>WOC</b></p>
        </div>
        <div style="display: flex; flex: 1;">
        </div>
    </div>
    <c:forEach items="${songs}" var="song">
        <div name="song" style="display: flex; flex-flow: column; border-bottom: 1px solid grey;">
            <div style="display: flex; flex-flow: row nowrap;">
                <input name="song-id" style="display:none;" type="text" value="${song.id}" />

                <div style="display: flex; flex: 1;">
                    <p>${song.peak}</p>
                </div>
                <div style="display: flex; flex: 4;">
                    <p>${song.artists}</p>
                </div>
                <div style="display: flex; flex: 5;">
                    <p>${song.name}</p>
                </div>
                <div style="display: flex; flex: 1; justify-content: end;">
                    <p> ${song.weeks}</p>
                </div>
                <div style="display: flex; flex: 1;">
                    <a name="history-link" href="#">?</a>
                </div>
            </div>
            <div style="display:none;" name="song-history">

            </div>
        </div>
    </c:forEach>
</div>

<div style="display:none;" name="chart-run-template">
    <p name="chart-run-header"></p>
    <div name="positions" style="display: flex; flex-flow: row wrap;">
        <div style="width:30px;" name="position">
            <a href="#" target="_blank" alt="Посмотреть чарт" name="chartLink"></a>
        </div>
    </div>
</div>

</body>
</html>