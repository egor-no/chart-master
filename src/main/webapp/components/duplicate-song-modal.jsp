<div id="duplicate-song-modal" class="share-modal no-display">
    <div class="share-modal-window">
        <div class="share-titlebar">
            <span>Duplicate song</span>
            <button type="button" name="duplicate-song-close">x</button>
        </div>

        <div class="share-modal-body">
            <div class="artist-limit-message">
                This song is already in the chart.
                Please choose another song.
            </div>

            <div class="share-modal-actions">
                <button type="button" name="duplicate-song-ok">
                    Choose another song
                </button>
            </div>
        </div>
    </div>
</div>

<script>
    function showDuplicateSongModal(onClose) {
        const $modal = $('#duplicate-song-modal');

        $modal.removeClass('no-display');

        $modal.find(
            '[name="duplicate-song-ok"], [name="duplicate-song-close"]'
        )
            .off('click.duplicateSong')
            .on('click.duplicateSong', function() {
                $modal.addClass('no-display');

                if (onClose) {
                    onClose();
                }
            });
    }
</script>