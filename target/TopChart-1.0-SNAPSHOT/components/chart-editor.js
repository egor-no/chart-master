function updateNum() {
    var num = 1;
    $('#songs .song-row').each(function() {
        $(this).find('[name="num"]').html(num);
        num++;
    });
}

function hasDuplicateSong(songId, $currentRow) {
    songId = String(songId);

    return $('#songs .song-row').filter(function () {
        const $row = $(this);

        if ($row.is($currentRow)) {
            return false;
        }

        return String(
            $row.find('input[name="idSong[]"]').val()
        ) === songId;
    }).length > 0;
}

function checkDuplicateSong(songId, $row, $activeInput, oldValues,  reopenAutocomplete) {
    if (!hasDuplicateSong(songId, $row)) {
        return false;
    }

    showDuplicateSongModal(function() {
        $row.find('input[name="idSong[]"]').val(oldValues.id);
        $row.find('[name="artists[]"]').val(oldValues.artists);
        $row.find('[name="name[]"]').val(oldValues.name);

        $row.find('[name="num"]').removeClass('new-song re-song');
        $row.find('input[name="prevInChart[]"]').val('0');
        $row.find('input[name="prevWoc[]"]').val('0');

        if (reopenAutocomplete) {
            $activeInput.focus();

            const searchText = $activeInput.val().trim();

            if (searchText.length >= 2) {
                $activeInput.autocomplete('search', searchText);
            }
        }
    });

    return true;
}

function normalizeSongText(value) {
    return (value || '')
        .trim()
        .replace(/\s+/g, ' ')
        .toLowerCase();
}

function findExistingSong(artists, title, songs) {
    const normalizedArtists = normalizeSongText(artists);
    const normalizedTitle = normalizeSongText(title);

    return songs.find(function(song) {
        return normalizeSongText(song.artists) === normalizedArtists
            && normalizeSongText(song.name) === normalizedTitle;
    });
}

function checkExistingSong($row, songs, markRow) {
    const id = $row.find('input[name="idSong[]"]').val();

    if (id) {
        return;
    }

    const artists = $row.find('[name="artists[]"]').val().trim();
    const title = $row.find('[name="name[]"]').val().trim();

    if (!artists || !title) {
        return;
    }

    const songKey = normalizeSongText(artists) + '|' + normalizeSongText(title);

    if ($row.data('createNewSongKey') === songKey) {
        return;
    }

    const existingSong = findExistingSong(
        artists,
        title,
        songs
    );

    if (!existingSong) {
        return;
    }

    showExistingSongModal(
        existingSong,
        function() {
            $row.removeData('createNewSongKey');
            $row.find('input[name="idSong[]"]').val(existingSong.id);
            $row.find('[name="artists[]"]').val(existingSong.artists);
            $row.find('[name="name[]"]').val(existingSong.name);
            $row.find('[name="num"]').removeClass('new-song');
            if (markRow) {
                markRow($row, existingSong.id);
            }

            if (hasDuplicateSong(existingSong.id, $row)) {
                $row.find('[name="num"]').addClass('duplicate-song');
                showDuplicateSongModal(function() {
                    $row.find('input[name="idSong[]"]').val('');
                    $row.find('[name="artists[]"]').val(artists);
                    $row.find('[name="name[]"]').val(title);
                    $row.find('[name="num"]').addClass('new-song');
                });
                return;
            }
        },

        // CREATE NEW
        function() {
            $row.data('createNewSongKey', songKey);

            $row.find('input[name="idSong[]"]').val('');
            $row.find('[name="num"]').addClass('new-song');
        }
    );
}


function clearDuplicateMark($row) {
    $row.find('[name="num"]').removeClass('duplicate-song');
}

function markDuplicate($row) {
    $row.find('[name="num"]').addClass('duplicate-song');
}

function clearMarks($row) {
    $row.find('[name="num"]').removeClass('new-song re-song');
    $row.find('input[name="prevInChart[]"]').val('0');
    $row.find('input[name="prevWoc[]"]').val('0');
}

function getArtistWeights() {
    const artistWeights = new Map();

    $('#songs .song-row').each(function() {
        const artistsString = $(this)
            .find('input[name="artists[]"]')
            .val()
            .trim();

        if (!artistsString) {
            return;
        }

        const artists = artistsString
            .split(',')
            .map(function(artist) {
                return artist.trim().toLowerCase();
            })
            .filter(Boolean);

        const weight = artists.length > 1 ? 0.5 : 1;

        artists.forEach(function(artist) {
            artistWeights.set(
                artist,
                (artistWeights.get(artist) || 0) + weight
            );
        });
    });

    return artistWeights;
}

function checkArtistLimit(addedArtistsString, $row, oldValues) {
    const artistWeights = getArtistWeights();

    const overLimit = addedArtistsString
        .split(',')
        .map(function(artist) {
            return artist.trim();
        })
        .filter(Boolean)
        .map(function(artist) {
            return {
                name: artist,
                weight: artistWeights.get(artist.toLowerCase()) || 0
            };
        })
        .filter(function(artist) {
            return artist.weight > 3.5;
        });

    if (overLimit.length === 0) {
        return;
    }

    showArtistLimitConfirm(
        overLimit,

        function() {
            // Keep song
        },

        function() {
            if (oldValues) {
                $row.find('input[name="idSong[]"]').val(oldValues.id);
                $row.find('[name="artists[]"]').val(oldValues.artists);
                $row.find('[name="name[]"]').val(oldValues.name);

                $row.find('[name="num"]')
                    .removeClass('new-song re-song');

                $row.find('input[name="prevInChart[]"]').val('0');
                $row.find('input[name="prevWoc[]"]').val('0');
            } else {
                $row.find('input[name="idSong[]"]').val('');
                $row.find('[name="artists[]"]').val('');
                $row.find('[name="name[]"]').val('');

                $row.find('[name="num"]')
                    .removeClass('new-song re-song');
            }
        }
    );
}

function initChartFormValidation() {
    $('form').on('submit', function(e) {
        const usedIds = new Set();
        const usedNewSongs = new Set();

        let duplicateFound = false;
        let emptyFound = false;

        $('#songs .song-row').each(function() {
            const $row = $(this);
            const id = $row.find('input[name="idSong[]"]').val();
            const artists = $row.find('input[name="artists[]"]').val().trim();
            const title = $row.find('input[name="name[]"]').val().trim();

            clearDuplicateMark($row);

            if (artists === '' || title === '') {
                emptyFound = true;
                markDuplicate($row);
                return;
            }

            if (id) {
                if (usedIds.has(id)) {
                    markDuplicate($row);
                    duplicateFound = true;
                } else {
                    usedIds.add(id);
                }
            } else {
                const newSongKey =
                    (artists + ' - ' + title).toLowerCase();

                if (usedNewSongs.has(newSongKey)) {
                    markDuplicate($row);
                    duplicateFound = true;
                } else {
                    usedNewSongs.add(newSongKey);
                }
            }
        });

        if (emptyFound) {
            e.preventDefault();
            alert('Все позиции чарта должны быть заполнены.');
            return;
        }

        if (duplicateFound) {
            e.preventDefault();
            alert(
                'В чарте есть повторяющиеся песни. Удали дубль перед сохранением.'
            );
        }
    });
}