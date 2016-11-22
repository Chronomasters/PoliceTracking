package joseph.com.policetracking;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static joseph.com.policetracking.MainActivity.alertReference;
import static joseph.com.policetracking.MainActivity.databaseReference;

public class Police extends AppCompatActivity {

    TextView addressTextView;
    String y = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police);


        addressTextView = (TextView)findViewById(R.id.addressText);
        databaseReference.child("Address").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        addressTextView.setText("Address: " + y);

        createNotification();
    }



    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;
    private int notification_id;
    private RemoteViews remoteViews;
    private Context context;

    private void createNotification() {
        System.out.println("Beginning of function");
        alertReference.addChildEventListener(new ChildEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                ArrayList<String> officersToNotify = DispatchW.closestOfficers;

      //          for (String officerName : officersToNotify){

                    System.out.println("before if, in createNotification");
                  //  if (email2.toLowerCase().equals(officerName.toLowerCase())) {
                        System.out.println("after if, in createNotification");

                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext());
                        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
                        mBuilder.setContentTitle("Hey, there's an emergency");
                        mBuilder.setContentText("Can you make it?");


                        Intent resultIntent = new Intent(getApplicationContext(), Police.class);
                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
                        stackBuilder.addParentStack(Police.class);
                        stackBuilder.addNextIntent(resultIntent);

                        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                        mBuilder.setContentIntent(resultPendingIntent);
                        //mNotificationManager.cancel(9);


                        mBuilder.addAction(R.mipmap.ic_launcher, "Accept", resultPendingIntent);
                        mBuilder.addAction(R.mipmap.ic_launcher, "Decline", resultPendingIntent);


                        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        mNotificationManager.notify(9, mBuilder.build());
               //     }
               // }
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



    }
}
