package joseph.com.policetracking;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button dispatch;
    private Button police;
    private Button ambulance;
    private Button firemen;

    public static int pFlag = 0;
    public static int aFlag = 0;
    public static int dFlag = 0;
    public static int fFlag = 0;

    public static FirebaseAuth mAuth;


    private DatabaseReference databaseReference;

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();



        dispatch = (Button) findViewById(R.id.dispatch);
        police = (Button) findViewById(R.id.police);
        ambulance = (Button) findViewById(R.id.ambulance);
        firemen = (Button) findViewById(R.id.firemen);

        dispatch.setOnClickListener(this);
        police.setOnClickListener(this);
        ambulance.setOnClickListener(this);
        firemen.setOnClickListener(this);


        LocationTracker locationTracker = new LocationTracker(context);
        locationTracker.startTrackingLocation();

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.dispatch:
                dFlag = 1;
                Intent intent = new Intent(MainActivity.this, Dispatch.class);
                startActivity(intent);
                break;

            case R.id.police:
                pFlag = 1;
                Intent intent2 = new Intent(MainActivity.this, Dispatch.class);
                startActivity(intent2);
                break;

            case R.id.ambulance:
                aFlag = 1;
                Intent intent3 = new Intent(MainActivity.this, Dispatch.class);
                startActivity(intent3);
                break;

            case R.id.firemen:
                fFlag = 1;
                Intent intent4 = new Intent(MainActivity.this, Dispatch.class);
                startActivity(intent4);
                break;

            default:
                break;
        }
    }


}
