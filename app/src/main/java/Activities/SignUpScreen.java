package Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.myapp.contractdrafter.R;

public class SignUpScreen extends AppCompatActivity
{
    protected TextView signintextview;
    private EditText emailfield, password, confirmpassword;
    protected Button registerme;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_screen);
        signintextview = (TextView) findViewById(R.id.gotosignin);
        emailfield = (EditText) findViewById(R.id.emailFieldRegister);
        password = (EditText) findViewById(R.id.passwordFieldRegister);
        confirmpassword = (EditText) findViewById(R.id.passwordConfirmFieldRegister);
        registerme = (Button) findViewById(R.id.registerButton);

        RegisterMeBtnClick();
        GotoSignInScreen();
    }

    private void RegisterMeBtnClick()
    {
        registerme.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String user_email = emailfield.getText().toString();
                String password1 = password.getText().toString();
                String password2 = confirmpassword.getText().toString();

                HideKeyboard(getApplicationContext());
                if(user_email.isEmpty()) { emailfield.setError("Please, Enter Email ID"); emailfield.requestFocus(); }
                else if (password1.isEmpty()) {password.setError("Please, Enter Password"); password.requestFocus(); }
                else if (password2.isEmpty()) {confirmpassword.setError("Please, Enter Password"); confirmpassword.requestFocus(); }
                else if (!password1.equals(password2)) { confirmpassword.setError("Password Does not match"); confirmpassword.requestFocus(); }
                else if(!user_email.isEmpty() && !password1.isEmpty() && !password2.isEmpty())
                {
                    Intent signupbtnclicked = new Intent(SignUpScreen.this, CreateProfile.class);
                    signupbtnclicked.putExtra("email", user_email);
                    signupbtnclicked.putExtra("password", password1);
                    signupbtnclicked.putExtra("confirm_password", password2);
                    startActivity(signupbtnclicked);
                    finish();
                }
                else
                {
                    HideKeyboard(SignUpScreen.this);
                }
            }
        });
    }

    private void GotoSignInScreen()
    {
        signintextview.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               Intent signinscreen = new Intent(getApplicationContext(), SignInScreen.class);
               startActivity(signinscreen);
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
