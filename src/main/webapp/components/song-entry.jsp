<div name="song">
    <div class="song-row">
        <input name="song-id" style="display:none;" type="text" value="${song.id}" />

        <div class="flex1 data-start">
            <p>${song.peak}</p>
        </div>
        <div class="flex4">
            <p>${song.artists}</p>
        </div>
        <div class="flex5">
            <p>${song.name}</p>
        </div>
        <div class="flex1 flex-end data-end">
            <p> ${song.weeks}</p>
        </div>
        <div class="flex1">
            <a name="history-link" href="#">?</a>
            <a name="edit-link" href="#">+</a>
        </div>
    </div>
    <div class="no-display" name="song-history">

    </div>
</div>