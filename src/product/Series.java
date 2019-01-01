package product;


import enums.Genres;
import single_classes.Episode;

import java.util.ArrayList;
import java.util.Date;

public class Series extends Product {
    private String actors;
    private int numberOfSeasons;
    private ArrayList<Episode> episodes;
    private String genres;

    public Series(int id, int distributorId, String imageLink, String name, String description, int prodDate,
           String durationTime, String prodCountry, float rating, String actors, int numberOfSeasons, String genres) {
        super(id, distributorId, imageLink, name, description, prodDate, durationTime, prodCountry, rating);

        this.actors = actors;
        this.numberOfSeasons = numberOfSeasons;
        this.episodes = null; // to do
        this.genres = genres;
    }

    public void randomize_fields() {}

    // getters
    public String getActors() {
        return actors;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public ArrayList<Episode> getEpisodes() {
        return episodes;
    }

    public String getGenres() {
        return genres;
    }
}
