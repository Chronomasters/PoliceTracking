package joseph.com.policetracking;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class DispatchW extends AppCompatActivity implements View.OnClickListener {

    private Button police;
    private Button ambulance;
    private Button firemen;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch_w);

        police = (Button) findViewById(R.id.police);
        ambulance = (Button) findViewById(R.id.ambulance);
        firemen = (Button) findViewById(R.id.firemen);

        police.setOnClickListener(this);
        ambulance.setOnClickListener(this);
        firemen.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.police:
                break;

            case R.id.ambulance:
                break;

            case R.id.firemen:
                break;

            default:
                break;
        }
    }
}
