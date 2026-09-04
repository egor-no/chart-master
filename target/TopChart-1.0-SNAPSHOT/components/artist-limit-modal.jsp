<div id="artist-limit-modal" class="share-modal no-display">
    <div class="share-modal-window">
        <div class="share-titlebar">
            <span>Artist limit</span>
            <button type="button" name="artist-limit-close">x</button>
        </div>

        <div class="share-modal-body">
            <div class="artist-limit-message" name="artist-limit-message">
            </div>

            <div class="share-modal-actions">
                <button type="button" name="artist-limit-cancel">
                    Cancel
                </button>

                <button type="button" name="artist-limit-ok">
                    Keep song
                </button>
            </div>

        </div>
    </div>
</div>

<script>
    function showArtistLimitConfirm(artists, onOk, onCancel) {
        const $modal = $('#artist-limit-modal');

        const text = artists
            .map(function(artist) {
                return artist.name +
                    ' has ' +
                    artist.weight +
                    ' weighted chart entries.';
            })
            .join(' ');

        $modal.find('[name="artist-limit-message"]')
            .text(
                text +
                ' The recommended maximum is 3.5. Keep this song anyway?'
            );

        $modal.removeClass('no-display');

        $modal.find('[name="artist-limit-ok"]')
            .off('click.artistLimit')
            .on('click.artistLimit', function() {
                $modal.addClass('no-display');

                if (onOk) {
                    onOk();
                }
            });

        $modal.find(
            '[name="artist-limit-cancel"], [name="artist-limit-close"]'
        )
            .off('click.artistLimit')
            .on('click.artistLimit', function() {
                $modal.addClass('no-display');

                if (onCancel) {
                    onCancel();
                }
            });
    }
</script>