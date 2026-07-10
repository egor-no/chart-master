package com.chart.TopChart.service;

import com.chart.TopChart.data.model.Song;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArtistSongServiceTest {

    @Test
    void shouldCalculateArtistTopStats() {
        Song numberOneSong = createSongWithPeak(1);
        Song topTenSong = createSongWithPeak(7);
        Song topFortySong = createSongWithPeak(25);
        Song outsideTopFortySong = createSongWithPeak(55);

        List<Song> songs = Arrays.asList(numberOneSong, topTenSong, topFortySong, outsideTopFortySong);

        List<Long> result = ArtistSongService.getArtistTopStatsFromSongs(songs);

        assertEquals(1L, result.get(0));
        assertEquals(2L, result.get(1));
        assertEquals(3L, result.get(2));
    }

    @Test
    void shouldReturnZerosForEmptyList() {
        List<Long> result = ArtistSongService.getArtistTopStatsFromSongs(Collections.emptyList());

        assertEquals(Arrays.asList(0L, 0L, 0L), result);
    }

    @Test
    void shouldIgnoreNullSongs() {
        Song numberOneSong = createSongWithPeak(1);
        List<Song> songs = Arrays.asList(numberOneSong, null);
        List<Long> result = ArtistSongService.getArtistTopStatsFromSongs(songs);

        assertEquals(Arrays.asList(1L, 1L, 1L), result);
    }

    @Test
    void shouldIncludeBoundaryPositions() {
        Song positionOne = createSongWithPeak(1);
        Song positionTen = createSongWithPeak(10);
        Song positionForty = createSongWithPeak(40);
        Song positionFortyOne = createSongWithPeak(41);

        List<Long> result = ArtistSongService.getArtistTopStatsFromSongs(
                Arrays.asList(positionOne, positionTen, positionForty, positionFortyOne));

        assertEquals(Arrays.asList(1L, 2L, 3L), result);
    }

    @Test
    void shouldBuildArtistStatsFromSongs() {
        List<Object[]> rows = Arrays.asList(
                new Object[]{1L, 1, "Madonna"},
                new Object[]{2L, 5, "Madonna"},
                new Object[]{3L, 8, "Britney Spears"},
                new Object[]{4L, 1, "Madonna, Justin Timberlake"}
        );

        List<List<String>> result = ArtistSongService.buildTopArtistsBySongs(rows, "no1");

        assertEquals(3, result.size());
        assertEquals(Arrays.asList("Madonna", "2", "3", "3"), result.get(0));
        assertEquals(Arrays.asList("Justin Timberlake", "1", "1", "1"), result.get(1));
        assertEquals(Arrays.asList("Britney Spears", "0", "1", "1"), result.get(2));
    }

    @Test
    void shouldCountEachArtistInCollaboration() {
        List<Object[]> rows = Collections.singletonList(new Object[]{1L, 3, "Madonna, Justin Timberlake, Timbaland"});
        List<List<String>> result = ArtistSongService.buildTopArtistsBySongs(rows, "top10");

        assertEquals(3, result.size());
        assertTrue(result.contains(Arrays.asList("Madonna", "0", "1", "1")));
        assertTrue(result.contains(Arrays.asList("Justin Timberlake", "0", "1", "1")));
        assertTrue(result.contains(Arrays.asList("Timbaland", "0", "1", "1")));
    }

    @Test
    void shouldSortArtistsByTopTenCount() {
        List<Object[]> rows = Arrays.asList(
                new Object[]{1L, 1, "Artist A"},
                new Object[]{2L, 5, "Artist B"},
                new Object[]{3L, 7, "Artist B"},
                new Object[]{4L, 20, "Artist A"}
        );

        List<List<String>> result = ArtistSongService.buildTopArtistsBySongs(rows, "top10");

        assertEquals("Artist B", result.get(0).get(0));
        assertEquals("2", result.get(0).get(2));
        assertEquals("Artist A", result.get(1).get(0));
        assertEquals("1", result.get(1).get(2));
    }

    @Test
    void shouldIgnoreInvalidRows() {
        List<Object[]> rows = Arrays.asList(
                null,
                new Object[]{1},
                new Object[]{1L, null, "Madonna"},
                new Object[]{2L, 1, null},
                new Object[]{3L, 1, ""},
                new Object[]{4L, 1, "Madonna"}
        );

        List<List<String>> result = ArtistSongService.buildTopArtistsBySongs(rows, "no1");

        assertEquals(1, result.size());
        assertEquals(Arrays.asList("Madonna", "1", "1", "1"), result.get(0));
    }

    @Test
    void shouldBuildTopArtistsStatsByPositions() {
        List<Object[]> rows = Arrays.asList(
                new Object[]{1, "Madonna"},
                new Object[]{10, "Madonna"},
                new Object[]{20, "Britney Spears"},
                new Object[]{40, "Britney Spears"}
        );
        List<List<String>> result = ArtistSongService.buildTopArtistsOnePass(rows);
        assertEquals(2, result.size());

        // Madonna: 40 + 31 = 71 очко
        // №1 = 1, Top 10 = 2, Top 40 = 2
        assertEquals(Arrays.asList("Madonna", "71", "1", "2", "2"), result.get(0));

        // Britney: 21 + 1 = 22 очка
        assertEquals(Arrays.asList("Britney Spears", "22", "0", "0", "2"), result.get(1));
    }

    @Test
    void shouldGivePointsToEveryArtistInCollaboration() {
        List<Object[]> rows = Collections.singletonList(
                new Object[]{5, "Madonna, Justin Timberlake, Timbaland"}
        );

        List<List<String>> result = ArtistSongService.buildTopArtistsOnePass(rows);

        assertEquals(3, result.size());

        List<String> expectedMadonna = Arrays.asList("Madonna", "36", "0", "1", "1");
        List<String> expectedJustin = Arrays.asList("Justin Timberlake", "36", "0", "1", "1");
        List<String> expectedTimbaland = Arrays.asList("Timbaland", "36", "0", "1", "1");

        assertTrue(result.contains(expectedMadonna));
        assertTrue(result.contains(expectedJustin));
        assertTrue(result.contains(expectedTimbaland));
    }

    @Test
    void shouldSortArtistsByTotalScore() {
        List<Object[]> rows = Arrays.asList(
                new Object[]{20, "Artist A"},
                new Object[]{20, "Artist A"},
                new Object[]{1, "Artist B"}
        );

        List<List<String>> result = ArtistSongService.buildTopArtistsOnePass(rows);

        // Artist A: 21 + 21 = 42
        // Artist B: 40
        assertEquals("Artist A", result.get(0).get(0));
        assertEquals("42", result.get(0).get(1));
        assertEquals("Artist B", result.get(1).get(0));
        assertEquals("40", result.get(1).get(1));
    }

    @Test
    void shouldUseNumberOnesWhenScoresAreEqual() {
        List<Object[]> rows = Arrays.asList(
                new Object[]{1, "Artist A"},
                new Object[]{2, "Artist B"},
                new Object[]{40, "Artist B"}
        );

        List<List<String>> result = ArtistSongService.buildTopArtistsOnePass(rows);

        // Artist A: 40 очков и одно №1
        // Artist B: 39 + 1 = 40 очков, но без №1
        assertEquals("Artist A", result.get(0).get(0));
        assertEquals("Artist B", result.get(1).get(0));
    }


    @Test
    void shouldIgnoreInvalidPositionRows() {
        List<Object[]> rows = Arrays.asList(
                null,
                new Object[]{1},
                new Object[]{null, "Madonna"},
                new Object[]{1, null},
                new Object[]{1, ""},
                new Object[]{1, "Madonna"}
        );

        List<List<String>> result = ArtistSongService.buildTopArtistsOnePass(rows);

        assertEquals(1, result.size());
        assertEquals(Arrays.asList("Madonna", "40", "1", "1", "1"), result.get(0));
    }

    private Song createSongWithPeak(int peak) {
        Song song = new Song();
        song.setPeak(peak);
        return song;
    }
}