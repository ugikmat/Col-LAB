package id.poros.filkom.col_lab;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import static id.poros.filkom.col_lab.model.CurrentUser.currentUser;

public class ProfileActivity extends AppCompatActivity {

    private TextView mUsernameText;
    private TextView mEmailText;
    private Button btnLogout;
    private Button btnTest;

    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();

        mUsernameText = findViewById(R.id.text_username);
        mEmailText = findViewById(R.id.text_email);
        btnLogout = findViewById(R.id.btn_logout);
        btnTest = findViewById(R.id.btn_test);

        mUsernameText.setText(mAuth.getCurrentUser().getUid());
        mEmailText.setText(mAuth.getCurrentUser().getEmail());
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mAuth.signOut();
            }
        });
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
            }
        });
    }
}
