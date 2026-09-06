<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div id="existing-song-modal"
     class="share-modal no-display">

    <div class="share-modal-window">

        <div class="share-titlebar">
            <span>Existing song found</span>

            <button type="button"
                    name="existing-song-close">
                x
            </button>
        </div>

        <div class="share-modal-body">

            <div class="artist-limit-message">
                <strong name="existing-song-label"></strong>
                already exists in the database.
                Do you want to use the existing song
                or create a new one?
            </div>

            <div class="share-modal-actions">

                <button type="button"
                        name="existing-song-new">
                    Create new
                </button>

                <button type="button"
                        name="existing-song-use">
                    Use existing
                </button>

            </div>
        </div>
    </div>
</div>

<script>
    function showExistingSongModal(song, onUse, onCreateNew) {
        const $modal = $('#existing-song-modal');

        $modal.find('[name="existing-song-label"]')
            .text(song.artists + ' — ' + song.name);

        $modal.removeClass('no-display');

        $modal.find('[name="existing-song-use"]')
            .off('click.existingSong')
            .on('click.existingSong', function() {
                $modal.addClass('no-display');

                if (onUse) {
                    onUse();
                }
            });

        $modal.find('[name="existing-song-new"]')
            .off('click.existingSong')
            .on('click.existingSong', function() {
                $modal.addClass('no-display');

                if (onCreateNew) {
                    onCreateNew();
                }
            });

        $modal.find('[name="existing-song-close"]')
            .off('click.existingSong')
            .on('click.existingSong', function() {
                $modal.addClass('no-display');

                // Крестик трактуем как Create new не будем.
                // Просто закрываем окно.
            });
    }
</script>