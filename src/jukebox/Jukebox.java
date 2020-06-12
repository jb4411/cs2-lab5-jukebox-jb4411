package jukebox;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Jukebox {
    HashMap<Song, Integer> songs;
    Random random;
    ArrayList<Song> songList;
    HashSet<Song> songsPlayed;
    ArrayList<Song> firstFiveSongs;

    public Jukebox(String filename, Integer seed) throws FileNotFoundException {
        Scanner in = new Scanner(new File(filename));
        this.random = new Random(seed);
        this.songList = new ArrayList<>();
        this.songs = new HashMap<>();

        while (in.hasNext()) {
            String[] line = in.nextLine().split("<SEP>", 4);
            Song song = new Song(line[2],line[3]);
            if (!this.songs.containsKey(song)) {
                this.songs.put(song, 0);
            }
        }
        this.songList.addAll(this.songs.keySet());
    }

    public long play() {
        Long startTime = System.currentTimeMillis();
        for (int i = 0; i < 50000; i++) {
            this.songsPlayed = new HashSet<>();
            Song currentSong;
            this.firstFiveSongs = new ArrayList<>();
            Boolean duplicate = false;
            while (!duplicate) {
                currentSong = this.songList.get(this.random.nextInt(this.songList.size()));
                if (this.firstFiveSongs.size() < 5) {
                    this.firstFiveSongs.add(currentSong);
                }
                if (songsPlayed.contains(currentSong)) {
                    duplicate = true;
                } else {
                    this.songsPlayed.add(currentSong);
                    this.songs.replace(currentSong, this.songs.get(currentSong)+1);
                }
            }
        }
        Long endTime = System.currentTimeMillis();

        return endTime - startTime;
    }

    public void display(Long time) {
        System.out.println("Printing first 5 songs played...");
        for (Song song : this.firstFiveSongs) {
            System.out.println("    " + song.toString());
        }

    }
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 2) {
            System.out.println("Usage: java Jukebox filename seed");
        } else {
            Jukebox jukebox = new Jukebox(args[0], Integer.parseInt(args[1]));
            System.out.println("Jukebox of " + jukebox.songList.size() + " songs starts rockin'...");
            Long time = jukebox.play();
            jukebox.display(time);
        }
    }
}
