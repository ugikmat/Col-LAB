package id.poros.filkom.col_lab;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import id.poros.filkom.col_lab.model.User;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;

    private AutoCompleteTextView mEmailView;
    private AutoCompleteTextView mUsernameView;
    private EditText mPasswordView;
    private EditText mConfirmPasswordView;
    private View mProgressView;
    private View mSignUpFormView;
    private Button mButtonSignUp;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Initiate View
        mEmailView = findViewById(R.id.email);
        mUsernameView = findViewById(R.id.username);
        mPasswordView = findViewById(R.id.password);
        mConfirmPasswordView = findViewById(R.id.confirm_password);
        mProgressView = findViewById(R.id.sign_up_progress);
        mSignUpFormView = findViewById(R.id.email_sign_up_form);
        mButtonSignUp = findViewById(R.id.email_sign_up_button);

        //get Firebase Auth Instance
        mAuth = FirebaseAuth.getInstance();

        //Initiate FirebaseDatabase
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("users");

        mButtonSignUp.setOnClickListener(this);
    }

    private void createAccount(String email, String password){
        //TODO: Validate Form
        showProgress(true);

        //Create User With Email
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignUpActivity.this,"Sign Up Success",Toast.LENGTH_SHORT)
                                    .show();
                            mRef.child(mAuth.getCurrentUser().getUid()).setValue(new User(mUsernameView.getText().toString(),mAuth.getCurrentUser().getEmail()));
                            startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                        }else{
                            Toast.makeText(SignUpActivity.this,"Sign Up Failed",Toast.LENGTH_SHORT)
                                    .show();
                        }
                        showProgress(false);
                    }
                });
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mSignUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mSignUpFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mSignUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mSignUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        createAccount(mEmailView.getText().toString(),mPasswordView.getText().toString());
    }
}
