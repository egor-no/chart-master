var ArtistPicker = (function () {
    var selectors = {
        trigger: '.artist-link',
        overlay: '#artist-picker-overlay',
        modal: '#artist-picker-modal',
        text: '#artist-picker-text',
        options: '#artist-picker-options',
        close: '#artist-picker-close',
        cancel: '#artist-picker-cancel'
    };

    function splitArtists(artistsRaw) {
        if (!artistsRaw) {
            return [];
        }

        return artistsRaw
            .split(',')
            .map(function (item) {
                return $.trim(item);
            })
            .filter(function (item) {
                return item.length > 0;
            });
    }

    function buildArtistUrl(artistName) {
        return '/artist?artist=' + encodeURIComponent(artistName);
    }

    function goToArtist(artistName) {
        window.open(buildArtistUrl(artistName), '_blank');
    }

    function close() {
        $(selectors.overlay).addClass('no-display');
        $(selectors.options).empty();
        $(selectors.text).text('Choose where to go:');
        $(selectors.overlay).removeData('active-trigger');
    }

    function createOption(label, value) {
        var $btn = $('<button type="button" class="artist-picker-option"></button>');
        $btn.text(label);

        $btn.on('click', function () {
            close();
            goToArtist(value);
        });

        return $btn;
    }

    function open(artistsRaw, triggerElement) {
        var artists = splitArtists(artistsRaw);
        var $options = $(selectors.options);

        $options.empty();

        if (artists.length <= 1) {
            goToArtist(artistsRaw);
            return;
        }

        $(selectors.text).text('Choose artist page for: ' + artistsRaw);

        artists.forEach(function (artist) {
            $options.append(createOption(artist, artist));
        });

        $options.append(createOption(artistsRaw + ' (collaboration credit)', artistsRaw));

        $(selectors.overlay)
            .data('active-trigger', triggerElement)
            .removeClass('no-display');
    }

    function bindTriggers() {
        $(document).on('mousedown', function (event) {
            event.stopPropagation();
        });

        $(selectors.trigger).on('click', function (event) {
            event.preventDefault();
            event.stopPropagation();
            open($(this).data('artists'), this);
        });

        $(selectors.trigger).on('keydown', function (event) {
            if (event.key === 'Enter' || event.key === ' ') {
                event.preventDefault();
                event.stopPropagation();
                open($(this).data('artists'), this);
            }
        });
    }

    function bindClosing() {
        $(selectors.close + ', ' + selectors.cancel).on('click', function () {
            close();
        });

        $(selectors.overlay).on('click', function (event) {
            if (event.target === this) {
                close();
            }
        });

        $(document).on('keydown', function (event) {
            if (event.key === 'Escape' && !$(selectors.overlay).hasClass('no-display')) {
                close();
            }
        });
    }

    function init() {
        bindTriggers();
        bindClosing();
    }

    return {
        init: init,
        open: open,
        close: close
    };
})();