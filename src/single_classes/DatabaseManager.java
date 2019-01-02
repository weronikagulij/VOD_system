package single_classes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import product.*;

import java.io.*;
import java.lang.invoke.CallSite;
import java.net.URLConnection;
import java.util.*;

import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;


public class DatabaseManager {
    private String APIkey;
    private String url;

    public DatabaseManager() {
        url = "http://www.omdbapi.com/?";
        APIkey = "apikey=f576cf93";
    }

    private JsonObject objectFromUrl(String tempUrl) throws IOException {
        URL url = new URL(tempUrl);
        URLConnection request = url.openConnection();

        // do not wait infinitely for connection
        request.setConnectTimeout(3000);
        request.setReadTimeout(3000);

        request.connect();

        // Convert to a JSON object to print data
        JsonParser jp = new JsonParser();
        InputStream tmp = (InputStream) request.getContent();
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
        JsonObject rootobj = root.getAsJsonObject();
        return rootobj;
    }

    private Event createNewEvent(int id, int distributorId, String imageLink, String name, String description,
                                 String durationTime, String prodCountry, float rating, String specialGuests) {

        Date d = new Date(System.currentTimeMillis() + (int)(Math.random() * 1000000000 + 1000));
        int entryPrice = (int)(Math.random() * 200 + 50);

        return new Event(id, distributorId, imageLink, name, description,
                durationTime, prodCountry, rating, entryPrice, specialGuests, d);
    }

    private Movie createNewMovie(int productId, int distributorId, String title, String imageLink, String description, int prodYear, String durationTime,
                                 String prodCountry, float rating, String actors, String genres) {
        return  new Movie(productId, distributorId, imageLink, title, description, prodYear,
        durationTime, prodCountry, rating, actors, (int)(Math.random() * 30 + 5), genres);
    }

    private Series createNewSeries(int id, int distributorId, String imageLink, String name, String description, int prodDate,
                                   String durationTime, String prodCountry, float rating, String actors, int numberOfSeasons, String genres) {

        return new Series(id, distributorId, imageLink, name, description, prodDate,
        durationTime, prodCountry, rating, actors, numberOfSeasons, genres);
    }

    private LiveStreaming createNewLivestreaming(int id, int distributorId, String imageLink, String name, String description,
                                                 int prodDate, String durationTime, String prodCountry, float rating) {

        Date startDate = new Date(System.currentTimeMillis() + (int)(Math.random() * 1000000000 + 1000));
        int price = (int)(Math.random() * 100 + 50);

        return new LiveStreaming(id, distributorId, imageLink, name, description, prodDate,
        durationTime, prodCountry, rating, price, startDate);
    }

    public boolean getNextMovie(HashMap<Integer, Product> productList, Integer productId, int distributorId) {
        // to do: products cant repeat

        // randomize id on imdb
        String id = "tt" + Integer.toString( (int)(Math.random() * 7000000 + 1) );

        // create url by id
        String tempUrl = url + "i=" + id + "&" + APIkey;
        try {
            JsonObject obj = objectFromUrl(tempUrl);
            if(obj.get("Response").getAsString().equals("False")) {
                return false;
            }

            // if downloaded Product is an episode, check for the series
            if(obj.get("Type").getAsString().equals("episode")) {
                String seriesID = obj.get("seriesID").getAsString();
                if(!seriesID.equals("N/A")) {
                    String newUrl = url + "i=" + seriesID + "&" + APIkey;
                    obj = objectFromUrl(newUrl);
                }
            }

            String title = obj.get("Title").getAsString();
            String imageLink = obj.get("Poster").getAsString();

            if(imageLink.equals("N/A")) imageLink = "https://cdn.pixabay.com/photo/2013/07/12/13/56/film-reel-147631_1280.png";

            String description = obj.get("Plot").getAsString();

            String tempYear = obj.get("Year").getAsString();
            tempYear = tempYear.replaceAll("[^\\d.]", "");
            int prodYear = Integer.parseInt(tempYear);

            String durationTime = obj.get("Runtime").getAsString(); // in minutes
            String prodCountry = obj.get("Country").getAsString();
            String actors = obj.get("Actors").getAsString();
            String genres = obj.get("Genre").getAsString();

            float rating;

            String temp = obj.get("imdbRating").getAsString();

            if(temp.equals("N/A")) {
                rating = (float)(Math.random() * 5 + 1);
            } else rating = Float.parseFloat(temp);

            if(obj.get("Type").getAsString().equals("movie")) {
                double choose = Math.random();
                // 60% chance for creating new movie, 40% for creating new event
                if(choose > 0.4) {
                    // create movie
                    productList.put(productId, createNewMovie(productId, distributorId, title, imageLink, description, prodYear, durationTime,
                            prodCountry, rating, actors, genres) );
                } else {
                    // create event
                    // what kind of event? 50% chance for meeting with actors, 50% for meeting with director
                    double second_choose = Math.random();
                    String name, desc, guests;
                    if(second_choose > 0.5) {
                        name = "Meeting with the actors of '" + title + "'";
                        desc = "Meeting with: " + actors + ". ";
                        guests = actors;
                    } else {
                        guests = obj.get("Director").getAsString();
                        name = "Meeting with the director of '" + title + "'";
                        desc = "Meeting with: " + guests;
                    }

                    productList.put(productId, createNewEvent(productId, distributorId, imageLink, name, desc,
                                     durationTime, prodCountry, rating, guests));

                }
            } else if (obj.get("Type").getAsString().equals("series")) {
                    int totalSeasons;

                    String tempSeasons = obj.get("totalSeasons").getAsString();

                    if(tempSeasons.equals("N/A")) {
                        totalSeasons = (int)(Math.random() * 5 + 1);
                    } else totalSeasons = Integer.parseInt(tempSeasons);

                    productList.put(productId, createNewSeries(productId, distributorId, imageLink, title, description, prodYear,
                            durationTime, prodCountry, rating, actors, totalSeasons, genres));
            } else {
                // create livestreaming
                    String name;

                    if(obj.get("Type").getAsString().equals("episode")) {
                        name = "Watching live '" + title + "' played in theater " + prodCountry;
                    } else {
                        name = "Playing online '" + title + "'";
                    }

                    productList.put(productId, createNewLivestreaming(productId, distributorId, imageLink, name, description, prodYear,
                            durationTime, prodCountry, rating) );
            }


        } catch (IOException e) {
            return false;
//            System.out.println("There was a problem with connection to database. " + e.getMessage());
        }

        return true;
    }
}

