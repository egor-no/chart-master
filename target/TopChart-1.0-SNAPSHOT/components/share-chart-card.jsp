<div id="share-modal" class="share-modal no-display">
    <div class="share-modal-window">
        <div class="share-titlebar">
            <span>Share chart</span>
            <button type="button" name="share-close">x</button>
        </div>

        <div class="share-modal-body">
            <div class="share-template-grid">
                <label class="share-template-option">
                    <input type="radio" name="share-template" value="classic95" checked>
                    <span class="share-template-title">Classic 95</span>
                    <span class="share-template-desc">Windows-95 inspired image that matches style of the whole project</span>
                </label>

                <label class="share-template-option">
                    <input type="radio" name="share-template" value="magazine">
                    <span class="share-template-title">Magazine</span>
                    <span class="share-template-desc">Editorial layout. Minimalistic and elegant.</span>
                </label>

                <label class="share-template-option">
                    <input type="radio" name="share-template" value="winamp">
                    <span class="share-template-title">Winamp-coded</span>
                    <span class="share-template-desc">Style that reminds you of classic winamp player</span>
                </label>

                <label class="share-template-option">
                    <input type="radio" name="share-template" value="vinyl">
                    <span class="share-template-title">Vinyl</span>
                    <span class="share-template-desc">Style for vinyl collectors that imitates a sleeve</span>
                </label>
            </div>

            <div class="share-modal-actions">
                <button type="button" name="share-download">Download PNG</button>
            </div>
        </div>
    </div>
</div>

<div id="share-card-wrapper">
    <span name="weeks-at-no1" style="display:none;">${song.weeksAtNo1}</span>

    <div id="share-card-classic95" class="share-card">
        <div class="share-window">
            <div class="share-titlebar">
                <span>TOP40.EXE</span>
                <span>[x]</span>
            </div>

            <div class="share-body">
                <div class="share-header">
                    <h2>${chartTitle}</h2>
                    <p>${chart.date} / Issue #${chart.issueNumber}</p>
                </div>

                <div id="share-top10-classic95"></div>

                <div class="share-footer">
                    <span>author: ${chartAuthorName}</span>
                </div>

                <div class="share-copyright">
                    Generated with TOP40 Chart Engine &copy; Egor Zimowski
                </div>
            </div>
        </div>
    </div>

    <div id="share-card-magazine" class="share-card share-card-magazine">
        <div class="magazine-label">TOP40 CHART</div>

        <div class="magazine-header">
            <h2>${chartTitle}</h2>
            <p>${chart.date} / Issue #${chart.issueNumber}</p>
        </div>

        <div id="share-top10-magazine"></div>

        <div class="magazine-footer">
            <span>chart by ${chartAuthorName}</span>
            <span>Generated with TOP40 Chart Engine &copy; Egor Zimowski</span>
        </div>
    </div>

    <div id="share-card-winamp" class="share-card share-card-winamp">
        <div class="winamp-window">
            <div class="winamp-titlebar">
                <span>TOP40 PLAYLIST</span>
                <span>_ &#9633; x</span>
            </div>

            <div class="winamp-display">
                <div class="winamp-spectrum"></div>

                <div class="winamp-big-number">00:24</div>

                <div class="winamp-track-panel">
                    <div class="winamp-title">${chartTitle}</div>
                    <div class="winamp-meta">${chart.date} / ISSUE #${chart.issueNumber}</div>
                </div>
            </div>

            <div class="winamp-buttons">
                <span>&#9654;</span>      <!-- ▶ -->
                <span>&#10074;&#10074;</span>  <!-- ❚❚ -->
                <span>&#9632;</span>      <!-- ■ -->
                <span>&#9664;&#9664;</span>     <!-- ◀◀ -->
                <span>&#9654;&#9654;</span>     <!-- ▶▶ -->
            </div>

            <div id="share-top10-winamp"></div>

            <div class="winamp-footer">
                <span>compiled by ${chartAuthorName}</span>
                <span>128 kbps / stereo</span>
            </div>

            <div class="winamp-copyright">
                Generated with TOP40 Chart Engine &copy; Egor Zimowski
            </div>
        </div>
    </div>

    <div id="share-card-vinyl" class="share-card share-card-vinyl">
        <div class="vinyl-record">
            <img src="${pageContext.request.contextPath}/images/share/vinyl.png"
                 alt=""
                 draggable="false">

            <div class="vinyl-center-label">
                <div class="vinyl-center-title">TOP40</div>
                <div class="vinyl-center-chart">${chartTitle}</div>

                <div class="vinyl-center-meta">
                    <span>Issue #${chart.issueNumber}</span>
                    <span>${chart.date}</span>
                </div>

                <div class="vinyl-center-author">by ${chartAuthorName}</div>
            </div>
        </div>

        <div class="vinyl-tracklist">
            <div class="vinyl-label">VINYL COLLECTION</div>

            <div class="vinyl-header">
                <h2>${chartTitle}</h2>
                <p>${chart.date} / Issue #${chart.issueNumber}</p>
            </div>

            <div id="share-top10-vinyl"></div>

            <div class="vinyl-footer">
                <span>curated by ${chartAuthorName}</span>
                <span>TOP40 Chart Engine &copy; Egor Zimowski</span>
            </div>
        </div>
    </div>
</div>

<script>
    function getShareTop10Data() {
        var songs = [];

        $('#chart-table > [name="song"]').not(':has([name="outsider"])').slice(0, 10).each(function () {
            var row = $(this).find('.song-row').first();

            var movInfo = row.find('[name="mov-info"]');
            var mov = row.find('[name="mov"]').text().trim();
            var movVal = row.find('[name="mov-val"]').text().trim();

            var movClassic = mov;
            if (movVal !== '') {
                movClassic += movVal;
            }

            var movMagazine = '=';

            if (movInfo.hasClass('up')) {
                movMagazine = '+' + movVal;
            } else if (movInfo.hasClass('down')) {
                movMagazine = '-' + movVal;
            } else if (movInfo.hasClass('new')) {
                movMagazine = 'NEW';
            } else if (movInfo.hasClass('re')) {
                movMagazine = 'RE';
            }

            var movClass = '';
            if (movInfo.hasClass('up')) movClass = 'up';
            if (movInfo.hasClass('down')) movClass = 'down';
            if (movInfo.hasClass('equal')) movClass = 'equal';
            if (movInfo.hasClass('new')) movClass = 'new';
            if (movInfo.hasClass('re')) movClass = 're';

            songs.push({
                pos: row.find('[name="pos"]').text().trim(),
                artist: row.find('.artist-link').text().trim(),
                song: row.find('.flex5 p').text().trim(),
                movClassic: movClassic,
                movMagazine: movMagazine,
                movClass: movClass,
                weeksAtNo1: row.find('[name="weeks-at-no1"]').text().trim()
            });
        });

        return songs;
    }

    function buildWinamp(songs) {
        var container = $('#share-top10-winamp');
        container.empty();

        songs.forEach(function (item, index) {
            container.append(
                '<div class="winamp-row">' +
                '<span class="winamp-pos">' + item.pos + '.</span>' +
                '<span class="winamp-mov">' + item.movMagazine + '</span>' +
                '<span class="winamp-track">' + item.artist + ' - ' + item.song + '</span>' +
                '</div>'
            );
        });
    }

    function buildClassic95(songs) {
        var container = $('#share-top10-classic95');
        container.empty();

        songs.forEach(function (item) {
            container.append(
                '<div class="share-row">' +
                '<div class="share-pos">' + item.pos + '</div>' +
                '<div class="share-mov ' + item.movClass + '">' + item.movClassic + '</div>' +
                '<div>' +
                '<div class="share-artist">' + item.artist + '</div>' +
                '<div class="share-song">' + item.song + '</div>' +
                '</div>' +
                '</div>'
            );
        });
    }

    function buildMagazine(songs) {
        var container = $('#share-top10-magazine');
        container.empty();

        songs.forEach(function (item, index) {
            if (index === 0) {
                var no1WeeksHtml = '';
                if (item.weeksAtNo1 && Number(item.weeksAtNo1) > 1) {
                    no1WeeksHtml =
                        '<div class="magazine-no1-weeks">' +
                        item.weeksAtNo1 + ' weeks at no. 1' +
                        '</div>';
                }
                container.append(
                    '<div class="magazine-number-one">' +
                    '<div class="magazine-rank">#1</div>' +
                    '<div>' +
                    '<div class="magazine-main-artist">' + item.artist + '</div>' +
                    '<div class="magazine-main-song">' + item.song + '</div>' +
                    no1WeeksHtml +
                    '</div>' +
                    '</div>'
                );
            } else {
                container.append(
                    '<div class="magazine-row">' +
                    '<div class="magazine-pos">' + item.pos + '</div>' +
                    '<div class="magazine-mov">' + item.movMagazine + '</div>' +
                    '<div class="magazine-text">' +
                    '<span>' + item.artist + '</span> &mdash; ' + item.song +
                    '</div>' +
                    '</div>'
                );
            }
        });
    }

    function buildVinyl(songs) {
        var container = $('#share-top10-vinyl');
        container.empty();

        songs.forEach(function (item, index) {
            var side = index < 5 ? 'A' : 'B';
            var trackNumber = index < 5 ? index + 1 : index - 4;
            var vinylPos = side + trackNumber;

            if (index === 0) {
                var no1WeeksHtml = '';

                if (item.weeksAtNo1 && Number(item.weeksAtNo1) > 1) {
                    no1WeeksHtml =
                        '<div class="vinyl-no1-weeks">' +
                        item.weeksAtNo1 + ' weeks at no. 1' +
                        '</div>';
                }

                container.append(
                    '<div class="vinyl-number-one">' +
                    '<div class="vinyl-main-pos">' + vinylPos + '</div>' +
                    '<div>' +
                    '<div class="vinyl-main-artist">' + item.artist + '</div>' +
                    '<div class="vinyl-main-song">' + item.song + '</div>' +
                    no1WeeksHtml +
                    '</div>' +
                    '</div>'
                );
            } else {
                container.append(
                    '<div class="vinyl-row">' +
                    '<div class="vinyl-pos">' + vinylPos + '</div>' +
                    '<div>' +
                    '<div class="vinyl-artist">' + item.artist + '</div>' +
                    '<div class="vinyl-song">' + item.song + '</div>' +
                    '</div>' +
                    '</div>'
                );
            }
        });
    }

    function buildShareCard(template) {
        var songs = getShareTop10Data();

        if (template === 'classic95') {
            buildClassic95(songs);
        } else if (template === 'magazine') {
            buildMagazine(songs);
        } else if (template === 'winamp') {
            buildWinamp(songs);
            randomizeWinamp();
        } else if (template === 'vinyl') {
            buildVinyl(songs);
        }
    }

    function downloadShareCard(template) {
        var elementId = '#share-card-classic95';

        if (template === 'magazine') {
            elementId = '#share-card-magazine';
        } else if (template === 'winamp') {
            elementId = '#share-card-winamp';
        } else if (template === 'vinyl') {
            elementId = '#share-card-vinyl';
        }

        html2canvas(document.querySelector(elementId), {
            width: 1080,
            height: 1080,
            scale: 1,
            backgroundColor: null
        }).then(function (canvas) {
            var link = document.createElement('a');
            link.download = 'top40-${chart.issueNumber}-' + template + '.png';
            link.href = canvas.toDataURL('image/png');
            link.click();
        });
    }

    $(document).ready(function () {
        $('[name="share-chart"]').on('click', function (e) {
            e.preventDefault();
            $('#share-modal').removeClass('no-display');
        });

        $('[name="share-close"]').on('click', function () {
            $('#share-modal').addClass('no-display');
        });

        $('[name="share-download"]').on('click', function () {
            var template = $('[name="share-template"]:checked').val();

            buildShareCard(template);
            downloadShareCard(template);

            $('#share-modal').addClass('no-display');
        });

    });

    function randomizeWinamp() {

        $('.winamp-big-number').text(randomWinampTime());
        buildSpectrum();

        var bitrates = [128, 160, 192, 256, 320];
        var rates = [44, 48];

        $('.winamp-footer span:last')
            .text(
                bitrates[Math.floor(Math.random() * bitrates.length)] +
                ' kbps / ' +
                rates[Math.floor(Math.random() * rates.length)] +
                ' kHz'
            );
    }

    function buildSpectrum() {
        var spectrum = $('.winamp-spectrum');
        spectrum.empty();

        for (var i = 0; i < 14; i++) {
            var h = 12 + Math.floor(Math.random() * 58);

            spectrum.append(
                '<span style="--h:' + h + '"></span>'
            );
        }
    }

    function randomWinampTime() {
        var min = Math.floor(Math.random() * 5);
        var sec = Math.floor(Math.random() * 60);

        return (
            String(min).padStart(2, '0') +
            ':' +
            String(sec).padStart(2, '0')
        );
    }
</script>