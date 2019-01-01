package single_classes;

import java.util.Date;

public class Episode {
    private int duration;
    private Date premiere;

    // getters
    public int getDuration() {
        return duration;
    }

    public Date getPremiere() {
        return premiere;
    }

    // setters
    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setPremiere(Date premiere) {
        this.premiere = premiere;
    }
}
