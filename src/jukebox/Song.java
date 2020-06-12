package jukebox;

import java.util.Objects;

public class Song implements Comparable<Song> {
    String artist;
    String song;

    public Song(String artist, String song) {
        this.artist = artist;
        this.song = song;
    }

    public String getArtist() {
        return artist;
    }

    public String getSong() {
        return song;
    }

    @Override
    public String toString() {
        return "Artist: " + artist + ", Song: " + song;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Song song1 = (Song) object;
        return artist.equals(song1.artist) &&
                song.equals(song1.song);
    }

    @Override
    public int hashCode() {
        return this.artist.hashCode() + this.song.hashCode();
    }

    @Override
    public int compareTo(Song s) {
        int result = this.artist.compareTo(s.artist);
        if (result == 0) {
            result = this.song.compareTo(s.song);
        }
        return result;
    }
}
