package product;

import single_classes.Episode;
import java.util.ArrayList;

public class Series extends Product {
    private String actors;
    private int numberOfSeasons;
    private ArrayList<Episode> episodes;
    private String genres;

    public Series(int distributorId, String imageLink, String name, String description, int prodDate,
           String durationTime, String prodCountry, float rating, String actors, int numberOfSeasons, String genres) {
        super(distributorId, imageLink, name, description, prodDate, durationTime, prodCountry, rating);

        this.actors = actors;
        this.numberOfSeasons = numberOfSeasons;
        this.episodes = null; // to do
        this.genres = genres;
    }

    public void randomize_fields() {}

    // getters
    @Override
    public String getActors() {
        return actors;
    }

    @Override
    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public ArrayList<Episode> getEpisodes() {
        return episodes;
    }

    @Override
    public String getGenres() {
        return genres;
    }
}
