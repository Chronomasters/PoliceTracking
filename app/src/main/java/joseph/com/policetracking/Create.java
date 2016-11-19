package joseph.com.policetracking;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import static joseph.com.policetracking.MainActivity.mAuth;

public class Create extends AppCompatActivity implements View.OnClickListener {

    private Button signUp;
    private String email;
    private String password;
    private EditText emailInput, passwordInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        signUp = (Button) findViewById(R.id.signUp);
        emailInput = (EditText) findViewById(R.id.email);
        passwordInput = (EditText) findViewById(R.id.password);

        signUp.setOnClickListener(this);
    }

    private void registerUser() {
        email = emailInput.getText().toString();
        password = passwordInput.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            System.out.println("It worked");
                            finish();

                          } else {
                            Toast.makeText(Create.this, "An error occurred", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        registerUser();
    }
}
