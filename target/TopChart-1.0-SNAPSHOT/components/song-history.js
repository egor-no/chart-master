
$(document).ready(function() {
    var currentShareSongData = null;

    $('[name="edit-link"]').on('click', function (event) {
        var idSong = $(this)
            .closest('[name="song"]')
            .find('[name="song-id"]')
            .val();

        $(this).attr(
            'href',
            '/songedit?id=' + idSong + '&search=' + '${search}'
        );

        event.stopPropagation();
    });

    $(document).on('click', '[name="share-song"]', function (event) {
        event.preventDefault();
        event.stopPropagation();

        var songDiv = $(this).closest('[name="song"]');

        currentShareSongData = getShareSongData(songDiv);

        $('#share-song-modal').removeClass('no-display');
    });

    $('[name="share-song-close"]').on('click', function (event) {
        event.preventDefault();
        event.stopPropagation();

        $('#share-song-modal').addClass('no-display');
        currentShareSongData = null;
    });

    $('[name="share-song-download"]').on('click', function (event) {
        event.preventDefault();
        event.stopPropagation();

        if (!currentShareSongData) {
            return;
        }

        var template =
            $('[name="share-song-template"]:checked').val();

        buildSongShareCard(template, currentShareSongData);
        downloadSongShareCard(template, currentShareSongData);

        $('#share-song-modal').addClass('no-display');
        currentShareSongData = null;
    });

    $('[name="song"]').on('click', function(event) {
        if ($(event.target).closest(
            '[name="share-song"], ' +
            '[name="edit-link"], ' +
            '[name="chartLink"], ' +
            '.artist-link'
        ).length) {
            return;
        }

        event.preventDefault();
        var no1 = 0,
            top10 = 0,
            top20 = 0;

        var songDiv =  $(this).closest('[name="song"]')
        if (songDiv.find('[name="position"]').length) {
            if (songDiv.find('[name="song-history"]').is(":visible")) {
                songDiv.find('[name="song-history"]').slideUp(500, function () {
                    songDiv.removeClass('history-open');
                    songDiv.removeClass('history-open-outsider');
                    songDiv.find('[name="mov-info"]').removeClass('history-mov');
                    songDiv.find('[name="i-report"]').removeClass('i-highlight');
                });

            } else {
                songDiv.find('[name="song-history"]').slideDown(500);
                songDiv.find('[name="mov-info"]').addClass('history-mov');
                songDiv.find('[name="i-report"]').addClass('i-highlight');
                songDiv.addClass('history-open');
                if (songDiv.find('[name="outsider"]').val() === 'true') {
                    songDiv.addClass('history-open-outsider');
                }
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

                var firstEntryDate = '';
                if (songhistory.chartRuns.length > 0) {
                    firstEntryDate = songhistory.chartRuns[0].firstChart.date;
                }
                songDiv.data('share-first-entry', firstEntryDate);

                $.each(songhistory.chartRuns, function (index, chartRun) {
                    var chartRunSize = chartRun.positions.length;
                    var chartRunDiv = $(document).find('[name="chart-run-template"]').clone();
                    chartRunDiv.attr('name', 'chart-run');

                    if (chartRun.firstChart.date != chartRun.lastChart.date) {
                        chartRunDiv.find('[name="chart-run-header"]').html(
                            chartRunSize + " weeks: " + chartRun.firstChart.date + " - " + chartRun.lastChart.date
                        );
                        chartRunDiv.find('[name="chart-run-header"]').addClass('chartRunTwo');
                    } else {
                        chartRunDiv.find('[name="chart-run-header"]').html(
                            chartRunSize + " week: " + chartRun.firstChart.date
                        );
                        chartRunDiv.find('[name="chart-run-header"]').addClass('chartRunOne');
                    }

                    $.each(chartRun.positions, function(i, item) {
                        var position = item.position;
                        var issueNumber = item.issueNumber;
                        var chartDateStr = item.date;
                        let chartDate = new Date(chartDateStr);

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

                        chartRunDiv.find('[name="position"]:last').find('[name="chartLink"]').on('click', function (event) {
                            event.stopPropagation();
                        });

                        chartRunDiv.find('[name="position"]:last').find('[name="chartLink"]').attr('href', '/chart?chartNumber=' + issueNumber);
                        chartRunDiv.find('[name="position"]:last').find('[name="chartLink"]').attr('title', 'GOTO: Chart N' + issueNumber + " | " + formatDate(chartDate));

                        var positionDiv = chartRunDiv.find('[name="position"]:last').clone();
                        chartRunDiv.find('[name="positions"]').append(positionDiv);
                    });

                    chartRunDiv.find('[name="position"]:last').remove();

                    songDiv.find('[name="song-history"]').append(chartRunDiv);
                    chartRunDiv.css('display', 'block');
                    songDiv.find('[name="song-history"]').slideDown(500);
                    songDiv.find('[name="mov-info"]').addClass('history-mov');
                    songDiv.find('[name="i-report"]').addClass('i-highlight');

                    songDiv.find('[name="stats-weeks-no1s"]').text(no1);
                    songDiv.find('[name="stats-weeks-top10s"]').text(top10);
                    songDiv.find('[name="stats-weeks-top20s"]').text(top20);
                    songDiv.addClass('history-open');

                    if (songDiv.find('[name="outsider"]').val() === 'true') {
                        songDiv.addClass('history-open-outsider');
                    }
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

function getShareSongData(songDiv) {
    var row = songDiv.find('.song-row').first();
    var movInfo = row.find('[name="mov-info"]');

    return {
        songId: row.find('[name="song-id"]').val() || '',

        artist: row.find('[name="song-artist"]').text().trim(),
        title: row.find('[name="song-title"]').text().trim(),

        position: row.find('[name="pos"]').text().trim(),
        movement: getShareSongMovement(movInfo),

        peak: row.find('[name="peak"] p').text().trim(),
        weeksTop40: row.find('[name="woc"]').text().trim(),
        weeksNo1: songDiv.find('[name="stats-weeks-no1s"]').text().trim(),
        weeksTop10: songDiv.find('[name="stats-weeks-top10s"]').text().trim(),
        weeksTop20: songDiv.find('[name="stats-weeks-top20s"]').text().trim(),

        chartTitle: '${sessionScope.chartTitle}',
        chartAuthor: '${sessionScope.chartAuthorName}',

        chartDate: $('[name="share-chart-date"]').first().text().trim(),
        issueNumber: $('[name="chart-number"]').first().text().trim(),
        firstEntryDate: songDiv.data('share-first-entry') || ''
    };
}

function getShareSongMovement(movInfo) {
    if (!movInfo || !movInfo.length) {
        return {
            classic: '',
            text: '',
            label: '',
            cssClass: ''
        };
    }

    var value = movInfo.find('[name="mov-val"]').text().trim();

    if (movInfo.hasClass('out')) {
        return {
            classic: '',
            text: 'OUT',
            label: 'OUT OF CHART',
            cssClass: 'out'
        };
    }

    if (movInfo.hasClass('up')) {
        return {
            classic: '&#9650;' + value,
            text: '+' + value,
            label: 'UP ' + value,
            cssClass: 'up'
        };
    }

    if (movInfo.hasClass('down')) {
        return {
            classic: '&#9660;' + value,
            text: '-' + value,
            label: 'DOWN ' + value,
            cssClass: 'down'
        };
    }

    if (movInfo.hasClass('new')) {
        return {
            classic: '&#9733;',
            text: 'NEW',
            label: 'NEW ENTRY',
            cssClass: 'new'
        };
    }

    if (movInfo.hasClass('re')) {
        return {
            classic: '&#9670;',
            text: 'RE',
            label: 'RE-ENTRY',
            cssClass: 're'
        };
    }

    return {
        classic: '&#9632;',
        text: '=',
        label: 'NO CHANGE',
        cssClass: 'equal'
    };
}

function fillSongCard(card, data, template) {
    card.find('[name="share-song-artist"]').text(data.artist);
    card.find('[name="share-song-title"]').text(data.title);

    var positionText = data.position
        ? '#' + data.position
        : '';

    card.find('[name="share-song-position"]').text(positionText);

    var movementElement =
        card.find('[name="share-song-movement"]');

    movementElement
        .removeClass('up down equal new re out')
        .addClass(data.movement.cssClass);

    if (template === 'classic95') {
        movementElement.html(data.movement.classic);
    } else {
        movementElement.text(data.movement.text);
    }

    card.find('[name="share-song-issue"]').text(
        data.issueNumber ? 'Issue #' + data.issueNumber : ''
    );

    card.find('[name="share-song-no1"]').text(data.weeksNo1);
    card.find('[name="share-song-top10"]').text(data.weeksTop10);
    card.find('[name="share-song-top20"]').text(data.weeksTop20);
    card.find('[name="share-song-top40"]').text(data.weeksTop40);

    card.find('[name="share-song-author"]').text(data.chartAuthor);
    card.find('[name="share-song-chart-title"]').text(data.chartTitle);

    if (data.chartDate) {
        card.find('[name="share-song-date-label"]').text('Chart date:');
        card.find('[name="share-song-date"]').text(data.chartDate);
    } else {
        card.find('[name="share-song-date-label"]').text('First entry:');
        card.find('[name="share-song-date"]').text(data.firstEntryDate);
    }

    var metaText = '';
    if (data.chartDate && data.issueNumber) {
        metaText = data.chartDate + ' / Issue #' + data.issueNumber;
    } else if (data.chartDate) {
        metaText = data.chartDate;
    } else if (data.firstEntryDate) {
        metaText = 'First entry: ' + data.firstEntryDate;
    }
    card.find('[name="share-song-meta-text"]').text(metaText);

    var hasCurrentChartContext = Boolean(data.chartDate || data.issueNumber || data.position);
    var hasAnyDate = Boolean(data.chartDate || data.firstEntryDate);

    card.toggleClass(
        'without-chart-context',
        !hasCurrentChartContext
    );

    card.toggleClass(
        'without-date',
        !hasAnyDate
    );
}

function buildSongShareCard(template, data) {
    var card = $('#share-song-card-' + template);

    if (!card.length) {
        console.error('Song share card not found: ' + template);
        return false;
    }

    fillSongCard(card, data, template);
    return true;
}

function downloadSongShareCard(template, data) {
    var element = document.querySelector(
        '#share-song-card-' + template
    );

    if (!element) {
        console.error('Song share card not found: ' + template);
        return;
    }

    html2canvas(element, {
        width: 1080,
        height: 1080,
        scale: 1,
        backgroundColor: null
    }).then(function (canvas) {
        var link = document.createElement('a');

        link.download = createSongFileName(
            data.artist,
            data.title,
            template
        );

        link.href = canvas.toDataURL('image/png');
        link.click();
    });
}

function createSongFileName(artist, title, template) {
    var fileName = artist + '-' + title;

    fileName = fileName
        .toLowerCase()
        .replace(/[^a-zа-яё0-9]+/gi, '-')
        .replace(/^-+|-+$/g, '');

    return fileName + '-' + template + '.png';
}