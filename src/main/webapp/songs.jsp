<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>TOP40 - Songs</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<%--    <script src="/components/song-history.js" type="text/javascript"></script>--%>
    <script type = "text/javascript" >
        $(document).ready(function() {
            if ($('[name="searchPhrase"]').val() == '') {
                $('#searchPhraseInfo').css('display', 'none');
                $('#songs-list').css('display', 'none');
            }

            $('[name="edit-link"]').on('click', function () {
                var idSong = $(this).closest('[name="song"]').find('[name="song-id"]').val();

                $(this).attr("href", "/songedit?id=" + idSong + "&search=" + '${search}');
            });
        });

        $(document).ready(function() {
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
                    var idChart = $(document).find('[name="chart-number"]').text();
                    $.get("songhistory?idSong=" + idSong + "&chartNumber=" + idChart, function(songhistory) {
                        var peak = songhistory.peak;
                        var currentChart = songhistory.currentChart;
                        $.each(songhistory.chartRuns, function (index, chartRun) {
                            var chartRunDiv = $(document).find('[name="chart-run-template"]').clone();
                            chartRunDiv.attr('name', 'chart-run');

                            if (chartRun.firstChart.date != chartRun.lastChart.date) {
                                chartRunDiv.find('[name="chart-run-header"]').html(chartRun.firstChart.date + " - " + chartRun.lastChart.date);
                            } else {
                                chartRunDiv.find('[name="chart-run-header"]').html(chartRun.firstChart.date);
                            }

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
                                chartRunDiv.find('[name="position"]:last').find('[name="chartLink"]').attr('title', 'Посмотреть чарт №' + chartId);
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
</form>

<input name="searchPhrase" style="display:none;" type="text" value="${search}" />
<div id="searchPhraseInfo">
    <h2>Поиск по <i>${search}</i></h2>
</div>

<%@include file="components/song-list-search.jsp"%>

</body>
</html>