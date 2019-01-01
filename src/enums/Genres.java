package enums;

import java.util.Random;

public enum Genres {
    Horror, Comedy, Western, Adventure, Action, Drama, Thriller, Documentary, Musical;
    public static Genres randomGenre() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}
