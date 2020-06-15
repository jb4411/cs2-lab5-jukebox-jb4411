package jukebox;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * A class to represent a jukebox.
 *
 * @author Jesse Burdick-Pless jb4411@g.rit.edu
 */
public class Jukebox {
    HashMap<Song, Integer> songs;
    Random random;
    ArrayList<Song> songList;
    HashSet<Song> songsPlayed;
    ArrayList<Song> firstFiveSongs;
    Long simulations;
    Long numSongsPlayed;
    Song mostPlayed;
    TreeSet<Song> songTree;

    public Jukebox(String filename, Integer seed) throws FileNotFoundException {
        Scanner in = new Scanner(new File(filename));
        this.random = new Random(seed);
        this.songList = new ArrayList<>();
        this.songs = new HashMap<>();
        this.firstFiveSongs = new ArrayList<>();
        this.simulations = 0L;
        this.numSongsPlayed = 0L;
        this.mostPlayed = null;
        this.songTree = new TreeSet<>((a,b) -> a.getSong().compareTo(b.getSong()));

        while (in.hasNext()) {
            String[] line = in.nextLine().split("<SEP>", 4);
            Song song = new Song(line[2],line[3]);
            if (!this.songs.containsKey(song)) {
                this.songs.put(song, 0);
            }
        }
        this.songList.addAll(this.songs.keySet());
        this.songs.put(mostPlayed, -1);
    }

    public long play() {
        Long startTime = System.currentTimeMillis();
        for (int i = 0; i < 50000; i++) {
            this.simulations += 1;
            this.songsPlayed = new HashSet<>();
            Song currentSong;
            boolean duplicate = false;
            while (!duplicate) {
                currentSong = this.songList.get(this.random.nextInt(this.songList.size()));
                if (songsPlayed.contains(currentSong)) {
                    duplicate = true;
                } else {
                    if (this.firstFiveSongs.size() < 5) {
                        this.firstFiveSongs.add(currentSong);
                    }
                    this.songsPlayed.add(currentSong);
                    this.songs.replace(currentSong, this.songs.get(currentSong)+1);
                    this.numSongsPlayed += 1;
                    if (this.mostPlayed == null) {
                        this.mostPlayed = currentSong;
                    } else {
                        if (this.songs.get(this.mostPlayed) <= this.songs.get(currentSong)) {
                            this.mostPlayed = currentSong;
                        }
                    }
                }
            }
        }
        Long endTime = System.currentTimeMillis();

        return (endTime - startTime)/1000;
    }

    public void display(long time) {
        System.out.println("Printing first 5 songs played...");
        for (Song song : this.firstFiveSongs) {
            System.out.println("        " + song.toString());
        }
        System.out.println("Simulation took " + time +" second/s");
        System.out.println("Number of simulations run: " + this.simulations);
        System.out.println("Total number of songs played: " + this.numSongsPlayed);
        System.out.println("Average number of songs played per simulation to get duplicate: " + (this.numSongsPlayed/this.simulations));
        System.out.println("Most played song: \"" + this.mostPlayed.getSong() + "\" by \"" + this.mostPlayed.getArtist() + "\"");
        System.out.println("All songs alphabetically by \"" + mostPlayed.getArtist() + "\":");
        for (Song song : this.songList) {
            if (song.getArtist().equals(this.mostPlayed.getArtist())) {
                this.songTree.add(song);
            }
        }
        for (Song song : this.songTree) {
            System.out.println("        \"" + song.getSong() + "\" with " + this.songs.get(song) + " plays");
        }

    }
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 2) {
            System.out.println("Usage: java Jukebox filename seed");
        } else {
            Jukebox jukebox = new Jukebox(args[0], Integer.parseInt(args[1]));
            System.out.println("Jukebox of " + jukebox.songList.size() + " songs starts rockin'...");
            long time = jukebox.play();
            jukebox.display(time);
        }
    }
}
