package id.poros.filkom.col_lab;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import static id.poros.filkom.col_lab.model.CurrentUser.currentUser;

public class ProfileActivity extends AppCompatActivity {

    private TextView mUsernameText;
    private TextView mEmailText;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_profile);

        mUsernameText = findViewById(R.id.text_username);
        mEmailText = findViewById(R.id.text_email);

        mUsernameText.setText(currentUser.getUsername());
        mEmailText.setText(currentUser.getEmail());
    }
}
