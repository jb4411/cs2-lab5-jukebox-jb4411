package jukebox;

/**
 * A class to represent a song. This class holds the songs artist as a string,
 * as well as the name of the song, also as a string.
 *
 * @author Jesse Burdick-Pless jb4411@g.rit.edu
 */
public class Song implements Comparable<Song> {
    /** the songs artist */
    private String artist;
    /** the name of the song */
    private String song;

    /**
     * Construct a new Song object.
     * @param artist the songs artist
     * @param song the name of the song
     */
    public Song(String artist, String song) {
        this.artist = artist;
        this.song = song;
    }

    /**
     * Get the songs artist
     * @return the songs artist
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Get the name of the song.
     * @return the name of the song
     */
    public String getSong() {
        return song;
    }

    /**
     * The string representation of a song is: "Artist: {artist}, Song: {song}
     * @return the song string
     */
    @Override
    public String toString() {
        return "Artist: " + artist + ", Song: " + song;
    }

    /**
     * Two songs are equal if they have the artist and same song.
     * @param object the song to compare with
     * @return whether they are equal or not
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || this.getClass() != object.getClass()) return false;
        Song song1 = (Song) object;
        return this.artist.equals(song1.artist) &&
                this.song.equals(song1.song);
    }

    /**
     * The hashcode of a song is the hashcode of the artist plus the hashcode
     * of the song name.
     * @return the songs hashcode
     */
    @Override
    public int hashCode() {
        return this.artist.hashCode() + this.song.hashCode();
    }

    /**
     * Songs are first compared alphabetically by artist name. Then, if the
     * artist is the same, the song names are compared alphabetically.
     * @param s the song to compare with
     * @return -1, 1, 0, if the song is less than, greater than, or equal to the song it
     * is being compared to
     */
    @Override
    public int compareTo(Song s) {
        int result = this.artist.compareTo(s.artist);
        if (result == 0) {
            result = this.song.compareTo(s.song);
        }
        return result;
    }
}
