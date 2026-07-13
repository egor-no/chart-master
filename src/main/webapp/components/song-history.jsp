<div class="no-display" name="song-history">
    <div class="song-stats">
        <div class="song-stat">
            <h3 name="stats-weeks-no1s"></h3>
            <p>No1</p>
        </div>
        <div class="song-stat">
            <h3 name="stats-weeks-top10s"></h3>
            <p>Top10</p>
        </div>
        <div class="song-stat">
            <h3 name="stats-weeks-top20s"></h3>
            <p>Top20</p>
        </div>
    </div>

    <c:if test="${showSongShare == true}">
        <div class="song-history-actions">
            <button type="button"
                    name="share-song"
                    class="song-share-button"
                    title="Share song">
                <i class="fa fa-share" aria-hidden="true"></i>
                Share song
            </button>
        </div>
    </c:if>
</div>

