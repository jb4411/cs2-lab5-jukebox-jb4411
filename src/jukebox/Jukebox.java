package jukebox;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * The main jukebox simulation class is run on the command line as:
 * $ java Jukebox filename seed_#
 *
 * The filename is the name of the file containing the songs to be played by
 * the jukebox simulation, the seed integer is used to seed the random number
 * generator used when picking random songs.
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

    /**
     * Create the jukebox and initialize the values needed for the simulation.
     * @param filename the name of the file containing the songs to be played
     * by the jukebox simulation
     * @param seed the integer used to seed the random number generator used
     * when picking random songs
     * @throws FileNotFoundException if the filename provided on the command
     * line cannot be found
     */
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

    /**
     * The jukebox runs 50000 simulations and keeps track of the first five
     * songs played, the total time in seconds it takes to run the simulation,
     * the total number of songs played throughout the entire simulation, the
     * average number of songs played to get a duplicate, across the entire
     * simulation and the song that was played the most.
     *
     * @return the total amount of time the simulation took to run in seconds
     */
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

    /**
     * This method is used to display the statistics gained from playing the
     * jukebox. The statistics displayed are as follows:
     * the total number of songs in the jukebox, the first five songs that are
     * played when the simulation is run, the total time in seconds it takes to
     * run the simulation, the number of times the simulation is run, the total
     * number of songs played throughout the entire simulation, the average
     * number of songs played to get a duplicate, across the entire simulation
     * the song that was played the most, and finally for the song that was
     * played the most, a list of all the songs by that song's artist that are
     * in the jukebox is displayed, alphabetically by song name along with the
     * total number of times each song was played.
     *
     * @param time the total amount of time the simulation took to run in seconds
     */
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

    /**
     * The main method expects there to be three command line arguments:
     * 1: The filename is the name of the file containing the songs to be
     * played by the jukebox simulation
     * 2: the seed integer used to seed the random number generator used when
     * picking random songs
     *
     * @param args command line arguments
     * @throws FileNotFoundException if the filename provided on the command
     * line cannot be found
     */
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
