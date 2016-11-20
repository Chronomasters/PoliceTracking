package joseph.com.policetracking;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Brian on 11/19/2016.
 */

public class OfficerTracker {

    Context mContext;

    public OfficerTracker(Context context){
        this.mContext = context;
    }



    public void testOfficerTracker() throws JSONException {

        Officer officer1 = new Officer("officer1", new Double[]{40.845407, -72.993863});
        Officer officer2 = new Officer("officer2", new Double[]{41.119325, -74.597127});
        Officer officer3 = new Officer("officer3", new Double[]{42.219533, -99.821070});

        ArrayList<Officer> officers = new ArrayList<>();
        officers.add(officer1);
        officers.add(officer2);
        officers.add(officer3);

        Double callerLocation[] = {40.849221, -73.004903};

        ArrayList<String>closestOfficers = getClosestOfficers(officers, callerLocation, 2);
        System.out.println("closest Officers test results: " + closestOfficers);

    }



    public ArrayList<String> getClosestOfficers(ArrayList<Officer> officerList, Double callerLocation[],  int numOfficers) throws JSONException {
        ArrayList<String> closestOfficers = new ArrayList<>();
        ArrayList<String>officerIDs = new ArrayList<>();
        ArrayList<Double>officersDistanceAway = new ArrayList<>();

        for (Officer officer : officerList) {   //for every officer
            double distanceFromCaller = getDistanceBetween(officer.location, callerLocation); //calculate distance from caller
            officerIDs.add(officer.ID);
            officersDistanceAway.add(distanceFromCaller);
        }

        //TESTING PURPOSES: PLEASE DELETE AFTERWaRDS
/*        officersDistanceAway.add(3.0);
        officersDistanceAway.add(6.0);
        officersDistanceAway.add(4.3);*/

        //find indices of x closest officers
        Double[] offDistAway = new Double[officersDistanceAway.size()];
        offDistAway = officersDistanceAway.toArray(offDistAway);    //convert officers distance arraylist to array

        System.out.println("officers distances away:");
        for (int i = 0; i < offDistAway.length; i++) {
            System.out.println(offDistAway[i]);
        }

        //sort distances least to greatest
        Arrays.sort(offDistAway);
        System.out.println("officers distances away, after sorting least to greatest:");
        for (int i = 0; i < offDistAway.length; i++) {
            System.out.println(offDistAway[i]);
        }

        //add x closest to final ArrayList
        for (int i = 0; i < numOfficers; i++) {
            closestOfficers.add(officerIDs.get(officersDistanceAway.indexOf(offDistAway[i])));
        }

        System.out.println("Closest officers IDs:");
        System.out.println(closestOfficers);


        return closestOfficers;
    }

    private double getDistanceBetween(Double[] officerCoords, final Double[] callerCoords) throws JSONException {
        GoogleMatrixRequest matrixRequest = new GoogleMatrixRequest(mContext);

        Handler myHandler = new Handler() {
            @Override
            public void handleMessage(Message msg){
                switch (msg.what){
                    case 0:
                        //
                        break;
                    default:
                        break;
                }
            }
        };

        return matrixRequest.getRouteDuration(officerCoords, callerCoords);

    }


}
