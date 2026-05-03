
$(document).ready(function() {
    $('[name="edit-link"]').on('click', function () {
        var idSong = $(this).closest('[name="song"]').find('[name="song-id"]').val();
        $(this).attr("href", "/songedit?id=" + idSong + "&search=" + '${search}');
        event.stopPropagation();
    });

    $('[name="song"]').on('click', function() {
        event.preventDefault();
        var no1 = 0,
            top10 = 0,
            top20 = 0;

        var songDiv =  $(this).closest('[name="song"]')
        if (songDiv.find('[name="position"]').length) {
            if (songDiv.find('[name="song-history"]').is(":visible")) {
                songDiv.find('[name="song-history"]').slideUp(500);
                songDiv.find('[name="mov-info"]').removeClass('history-mov');
                songDiv.find('[name="i-report"]').removeClass('i-highlight');
                songDiv.removeClass('history-open');
            } else {
                songDiv.find('[name="song-history"]').slideDown(500);
                songDiv.find('[name="mov-info"]').addClass('history-mov');
                songDiv.find('[name="i-report"]').addClass('i-highlight');
                songDiv.addClass('history-open');
            }
        } else {
            var idSong = $(this).closest('[name="song"]').find('[name="song-id"]').val();
            var chartNumber = '';
            if (songDiv.find('[name="chart-number"]').length && songDiv.find('[name="chart-number"]').val() !== '') {
                chartNumber = songDiv.find('[name="chart-number"]').val();
            } else if ($(document).find('[name="chart-number"]').length) {
                chartNumber = $(document).find('[name="chart-number"]').text();
            }

            var dateSearch = false;
            var date1 = '';
            var date2 = '';

            if ($(document).find('[name="date1-data"]').length
                && $(document).find('[name="date1-data"]').val() !== '') {

                dateSearch = true;
                date1 = $(document).find('[name="date1-data"]').val();

                if ($(document).find('[name="date2-data"]').length) {
                    date2 = $(document).find('[name="date2-data"]').val();
                }
            }

            $.get("songhistory?idSong=" + idSong
                + "&chartNumber=" + chartNumber
                + "&dateSearch=" + dateSearch
                + "&date1=" + date1
                + "&date2=" + date2, function(songhistory) {
                var peak = songhistory.peak;
                var currentChart = songhistory.currentIssue;
                $.each(songhistory.chartRuns, function (index, chartRun) {
                    var chartRunSize = chartRun.positions.length;
                    var chartRunDiv = $(document).find('[name="chart-run-template"]').clone();
                    chartRunDiv.attr('name', 'chart-run');

                    if (chartRun.firstChart.date != chartRun.lastChart.date) {
                        chartRunDiv.find('[name="chart-run-header"]').html(chartRunSize + " weeks: " + chartRun.firstChart.date + " - " + chartRun.lastChart.date);
                        chartRunDiv.find('[name="chart-run-header"]').addClass('chartRunTwo');
                    } else {
                        chartRunDiv.find('[name="chart-run-header"]').html(chartRunSize + " week: " +  chartRun.firstChart.date);
                        chartRunDiv.find('[name="chart-run-header"]').addClass('chartRunOne');
                    }

                    var issueNumber = chartRun.firstChart.issueNumber;
                    var chartDateStr = chartRun.firstChart.date;
                    let chartDate = new Date(chartDateStr);

                    $.each(chartRun.positions, function(i, position) {
                        chartRunDiv.find('[name="position"]:last').find('[name="chartLink"]').html(position);

                        if (position == peak) {
                            chartRunDiv.find('[name="position"]:last').addClass('peak');
                        } else {
                            chartRunDiv.find('[name="position"]:last').removeClass('peak');
                        }

                        if (position == 1) {
                            no1++;
                        }
                        if (position <= 10) {
                            top10++;
                        }

                        if (position <= 20) {
                            top20++;
                        }

                        if (issueNumber == currentChart) {
                            chartRunDiv.find('[name="position"]:last').addClass('current');
                        } else {
                            chartRunDiv.find('[name="position"]:last').removeClass('current');
                        }

                        chartRunDiv.find('[name="position"]:last').find('[name="chartLink"]').on('click', function () {
                            event.stopPropagation();
                        });
                        chartRunDiv.find('[name="position"]:last').find('[name="chartLink"]').attr('href', '/chart?chartNumber=' + issueNumber);
                        chartRunDiv.find('[name="position"]:last').find('[name="chartLink"]').attr('title', 'GOTO: Chart N' + issueNumber + " | " +  formatDate(chartDate));
                        var positionDiv = chartRunDiv.find('[name="position"]:last').clone();
                        chartRunDiv.find('[name="positions"]').append(positionDiv);

                        issueNumber++;
                        chartDate.setDate(chartDate.getDate() + 7);
                    });
                    chartRunDiv.find('[name="position"]:last').remove();

                    songDiv.find('[name="song-history"]').append(chartRunDiv);
                    chartRunDiv.css('display', 'block');
                    songDiv.find('[name="song-history"]').slideDown(500);
                    songDiv.find('[name="mov-info"]').addClass('history-mov')
                    songDiv.find('[name="i-report"]').addClass('i-highlight');

                    songDiv.find('#stats-weeks-no1s').html(no1);
                    songDiv.find('#stats-weeks-top10s').html(top10);
                    songDiv.find('#stats-weeks-top20s').html(top20);
                    songDiv.addClass('history-open');
                });
            });
        }
    });

});

function formatDate(date) {
    let year = date.getFullYear();
    let month = (date.getMonth() + 1).toString().padStart(2, '0'); // Month is 0-indexed
    let day = date.getDate().toString().padStart(2, '0');

    return year + "-" + month + "-" + day;
}