package joseph.com.policetracking;

/**
 * Created by Brian on 11/19/2016.
 */

public class Officer {

    Double[] location;
    String  ID;

    public Officer(String ID, Double[] location) {
        this.ID = ID;
        this.location = location;
    }
}
