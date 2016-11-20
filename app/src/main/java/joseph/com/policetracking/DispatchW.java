package joseph.com.policetracking;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

import static joseph.com.policetracking.MainActivity.policeReference;

public class DispatchW extends AppCompatActivity implements View.OnClickListener {

    private Button police;
    private Button ambulance;
    private Button firemen;

    private String address;
    private EditText inputAddress;



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


    @Override
    public void onClick(View view) {
        address = inputAddress.getText().toString();

        switch (view.getId()) {
            case R.id.police:
                /*ArrayList<String>names = new ArrayList<>();
                names.add("officer1");
                names.add("officer2");
                names.add("officer3");
                int i;
                for(i = 0; i < names.size(); i++) {
                    databaseReference.child(x).child("AlertP" + i).setValue("To: " + names.get(i) + " Address: " + address);
                }*/
                policeReference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        long x = dataSnapshot.getChildrenCount();
                        //System.out.println(dataSnapshot.getKey() + " " + dataSnapshot.getValue());

                        ArrayList<String>names = new ArrayList<>();
                        names.add(dataSnapshot.getKey());
                        //'System.out.println(names);


                        ArrayList<Double[]>values = new ArrayList<>();


                        String y = dataSnapshot.getValue().toString();
                        String z = y.substring(y.indexOf(",")+1,  y.length());
                        y = y.substring(0, y.indexOf(","));
                        double c = Double.parseDouble(z);
                        double b = Double.parseDouble(y);
                        Double[]array = {c, b};
                        values.add(array);

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


}
