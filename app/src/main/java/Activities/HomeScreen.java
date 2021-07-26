package Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.myapp.contractdrafter.R;

import org.w3c.dom.Text;

public class HomeScreen extends AppCompatActivity
{
    protected Button signinbtn, signupbtn;
    protected ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);

        signinbtn = (Button) findViewById(R.id.signInFromMain);
        signupbtn = (Button) findViewById(R.id.registerFromMain);
        logo = (ImageView) findViewById(R.id.logoMain);

        SignInBtnClick();
        SignUpBtnClick();
    }

    private void SignInBtnClick()
    {
        signinbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent signin_intent = new Intent(getApplicationContext(), SignInScreen.class);
                ActivityOptions options= ActivityOptions.makeSceneTransitionAnimation(HomeScreen.this, logo, "mainLogoTransition");
                startActivity(signin_intent, options.toBundle());
            }
        });
    }

    private void SignUpBtnClick()
    {
        signupbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent signup_intent = new Intent(getApplicationContext(), SignUpScreen.class);

                ActivityOptions options= ActivityOptions.makeSceneTransitionAnimation(HomeScreen.this, logo, "mainLogoTransition");
                startActivity(signup_intent, options.toBundle());
            }
        });
    }
}