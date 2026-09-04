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

function checkDuplicateSong(songId, $row, $activeInput, oldValues) {
    if (!hasDuplicateSong(songId, $row)) {
        return false;
    }

    showDuplicateSongModal(function() {
        $row.find('input[name="idSong[]"]').val(oldValues.id);
        $row.find('[name="artists[]"]').val(oldValues.artists);
        $row.find('[name="name[]"]').val(oldValues.name);

        $activeInput.focus();

        const searchText = $activeInput.val().trim();

        if (searchText.length >= 2) {
            $activeInput.autocomplete('search', searchText);
        }
    });

    return true;
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