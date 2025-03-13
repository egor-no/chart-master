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

                $(this).attr("href", "/songedit?id=" + idSong + "&artist=" + '${artist}');
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
    <a href="/songs">Песни</a>
</div>

<h2>${artist}</h2>
<div id="artist-stats" style="width: 600px; display: flex; justify-content: space-evenly; flex-flow: row nowrap; border: 1px black solid;">
    <div id="stats-position-no1s" style="display: flex; flex-flow: column; align-items:center;">
        <h3 style="margin-top:15px; margin-bottom:15px;">${stats[0]}</h3>
        <p style="margin-top:0px; margin-bottom:15px;">No 1s</p>
    </div>
    <div id="stats-position-top10s" style="display: flex; flex-flow: column; align-items:center;">
        <h3 style="margin-top:15px; margin-bottom:15px;">${stats[1]}</h3>
        <p style="margin-top:0px; margin-bottom:15px;">Top 10s</p>
    </div>
    <div id="stats-position-top40s" style="display: flex; flex-flow: column; align-items:center;">
        <h3 style="margin-top:15px; margin-bottom:15px;">${stats[2]}</h3>
        <p style="margin-top:0px; margin-bottom:15px;">Top 40s</p>
    </div>
</div>

<%@include file="components/song-list-search.jsp"%>

</body>
</html>