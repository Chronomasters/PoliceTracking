package joseph.com.policetracking;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Brian on 11/19/2016.
 */

public class GoogleMatrixRequest {

    private static final String API_KEY = "AIzaSyCsSixxnzPX4pIN4F7UNP4ot0rSJVbW7hU";

    Context mContext;

    int finalTravelDuration = -1;   //initially set to -1 to determine if it is later changed

    boolean readyToReturn = false;

    OkHttpClient client = new OkHttpClient();


    public GoogleMatrixRequest (Context context){
        this.mContext = context;
    }


    public String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public String makeRequestUrl(Double officerCoords[], Double callerCoords[]) {
        String origin=officerCoords[0] + "," + officerCoords[1];
        String destination = callerCoords[0] + "," + callerCoords[1];

        String url_request = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + origin + "&destinations=" + destination + "&mode=driving&language=en&key=" + API_KEY;

        return url_request;
    }

    public String getResponse(String url) {

      // Double officerCoords[] = {40.744463, -73.985282};   //CHANGE TO ACTUAL ASSIGNMENT
      //  Double callerCoords[] = {40.849221, -73.004903};    //CHANGE TO ACTUAL ASSIGNMENT

        String response = null;
        try {
            response = run(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public int getDurationFromJSON(String response) throws JSONException {
        int duration = 0;

        JSONObject obj = new JSONObject(response);

        String number = obj.getJSONArray("rows").getJSONObject(0).getJSONArray("elements").getJSONObject(0).getJSONObject("duration").get("value").toString();
        duration = Integer.parseInt(number);

        return duration;

    }



    private class GetResponseTask extends AsyncTask<String, String, String> {

        ProgressDialog progressDialog;

        protected String doInBackground(String... strings){
            System.out.println("strings[0] = " + strings[0]);

            return getResponse(strings[0]); //returns the response json

        }

        protected void onPostExecute(String response){
//            System.out.println("onPostExecute: response=" + response);
            int durationFromJSON = 0;
            try {
               durationFromJSON  = getDurationFromJSON(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println("durationFromJSON = " + durationFromJSON);


            finalTravelDuration = durationFromJSON;
            readyToReturn = true;

            progressDialog.cancel();



        }

        @Override
        protected void onPreExecute() {

            progressDialog = new ProgressDialog((Activity) mContext);
            progressDialog.setMessage("Searching for units...");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

    }

    public  int getRouteDuration(Double officerCoords[], Double callerCoords[]) throws JSONException {


        String url = makeRequestUrl(officerCoords, callerCoords);

     //   new GetResponseTask().execute(url, url, url);


        try {
            String result = new GetResponseTask().execute(url, url, url).get();

            int durationFromJSON = 0;
            try {
                durationFromJSON  = getDurationFromJSON(result);
                finalTravelDuration = durationFromJSON;
                System.out.println("in getRouteDuration, finalTravelDuration = " + finalTravelDuration);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return finalTravelDuration;
    }


}
