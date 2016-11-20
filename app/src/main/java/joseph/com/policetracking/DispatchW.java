package joseph.com.policetracking;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static joseph.com.policetracking.MainActivity.databaseReference;
import static joseph.com.policetracking.MainActivity.policeReference;

public class DispatchW extends AppCompatActivity implements View.OnClickListener {

    private Button police;
    private Button ambulance;
    private Button firemen;

    Context context = this;

    public static String address;
    private EditText inputAddress;

    ArrayList<String>names = new ArrayList<>();
    ArrayList<Double[]>values = new ArrayList<>();

    public static ArrayList<String> closestOfficers = new ArrayList<>();



    int count = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch_w);

        police = (Button) findViewById(R.id.police);
        ambulance = (Button) findViewById(R.id.ambulance);
        firemen = (Button) findViewById(R.id.firemen);
        inputAddress = (EditText) findViewById(R.id.input);

        police.setOnClickListener(this);
        ambulance.setOnClickListener(this);
        firemen.setOnClickListener(this);


    }

    String x = "Alerts";


    Boolean die = false;

    @Override
    public void onClick(View view) {
        address = inputAddress.getText().toString();

        databaseReference.child("Address").setValue(address);


        switch (view.getId()) {
            case R.id.police:
                count = 0;
                die = false;
                policeReference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        if (!die) {
                            System.out.println("It is running:" + dataSnapshot.getKey());
                            long x = dataSnapshot.getChildrenCount();
                         //   System.out.println("x = " + x);
                            //System.out.println(dataSnapshot.getKey() + " " + dataSnapshot.getValue());
                            System.out.println("alive before count++");
                            count++;
                            System.out.println("alive after count++");
                            names.add(dataSnapshot.getKey());
                            //'System.out.println(names);
                            System.out.println("alive after add to names");
                            System.out.println("count = " + count);

                            String y = dataSnapshot.getValue().toString();
                            String z = y.substring(y.indexOf(",") + 1, y.length());
                            y = y.substring(0, y.indexOf(","));
                            double c = Double.parseDouble(z);
                            double b = Double.parseDouble(y);
                            Double[] array = {b, c};
                            values.add(array);
                        }


                        if (count == 13) {
                            count = 0;
                            die = true;
                            System.out.println("Names (in if) = " + names);



                            ArrayList<Officer> listOfOfficers = makeOfficerList(names, values);
                            Double[] callerCoords = {0.0, 0.0};
                            OfficerTracker otracker = new OfficerTracker(context);
                            GoogleMatrixRequest gmr = new GoogleMatrixRequest(context);
                            try {
                                callerCoords = gmr.getCoordsFromAddress(address);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            try {
                                closestOfficers = otracker.getClosestOfficers(listOfOfficers, callerCoords, 3);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }




                            System.out.println("closestOfficers = " + closestOfficers);
                        } else {
                            System.out.println("x is not equal to 13");
                        }

                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                break;

            case R.id.ambulance:
                address = inputAddress.getText().toString();
                break;

            case R.id.firemen:
                address = inputAddress.getText().toString();
                break;

            default:
                break;
        }
    }

    public ArrayList<Officer> makeOfficerList(ArrayList<String>officerNames, ArrayList<Double[]> officerLocations) {
        ArrayList<Officer> result = new ArrayList<>();

        for (int i = 0; i < officerNames.size(); i++){
            System.out.println("officerNames.get(i) = " + officerNames.get(i));
            System.out.println("officerLocations.size() = " + officerLocations.size());
            System.out.println("officerNames.size() = " + officerNames.size());
            result.add(new Officer(officerNames.get(i), officerLocations.get(i)));
            System.out.println("in makeOfficerList, i = " + i);
        }

        return result;
    }




}
