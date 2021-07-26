package Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.myapp.contractdrafter.R;

public class SignInScreen extends AppCompatActivity
{
    protected TextView registertextview, emailfield, passwordfield;
    protected Button signinbtn;
    private FirebaseAuth SigninMe;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_screen);

        registertextview = (TextView) findViewById(R.id.registerhere);
        signinbtn = (Button) findViewById(R.id.signInButton);
        emailfield = (EditText) findViewById(R.id.emailFieldSignin);
        passwordfield = (EditText) findViewById(R.id. passwordFieldSignin);
        progressBar = findViewById(R.id.progressBarLoginScreen);
        SigninMe = FirebaseAuth.getInstance();

        SignInAuthentication();
        GotoRegisterActivity();
    }

    private void SignInAuthentication()
    {
        signinbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                progressBar.setVisibility(View.VISIBLE);
                signinbtn.setEnabled(false);
                final String emailId = emailfield.getText().toString();
                String password = passwordfield.getText().toString();
                HideKeyboard(getApplicationContext());
                if(emailId.isEmpty()) { emailfield.setError("Please, Enter Email ID"); emailfield.requestFocus(); progressBar.setVisibility(View.GONE);}
                else if (password.isEmpty()) {passwordfield.setError("Please, Enter Password"); passwordfield.requestFocus(); progressBar.setVisibility(View.GONE);}
                else if (password.isEmpty() && emailId.isEmpty()) { progressBar.setVisibility(View.GONE); Toast.makeText(getApplicationContext(), "Please, fill all the fields!", Toast.LENGTH_LONG).show(); }
                else if (!password.isEmpty() && !emailId.isEmpty())
                {
                    //progressBar.setVisibility(View.VISIBLE);
                    SigninMe.signInWithEmailAndPassword(emailId, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Intent intohom = new Intent (SignInScreen.this, MainActivity.class);
                                intohom.putExtra("UID", emailId);
                                startActivity(intohom);
                                progressBar.setVisibility(View.GONE);
                                signinbtn.setEnabled(true);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Not Signed In !", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                                signinbtn.setEnabled(true);
                            }
                        }
                    });
                }
                else
                {
                    HideKeyboard(SignInScreen.this);
                }
            }
        });
    }

    private void GotoRegisterActivity()
    {
        registertextview.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               Intent signmeup = new Intent(getApplicationContext(), SignUpScreen.class);
               startActivity(signmeup);
               finish();
            }
        });
    }

    public void HideKeyboard(Context c)
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view == null) {
            view = new View(c);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
