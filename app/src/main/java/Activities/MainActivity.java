package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseUser;
import com.myapp.contractdrafter.R;

import Fragments.Home_Fragment;
import Fragments.Profile_Fragment;
import Fragments.Rooms_Fragment;

public class MainActivity extends AppCompatActivity
{
    //private FirebaseUser Email;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home_Fragment()).commit();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationView);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selected_fragment = null;

                    switch (menuItem.getItemId()) {
                        case R.id.PROFILE:
                            selected_fragment = new Profile_Fragment();
                            break;
                        case R.id.ROOMS:
                            selected_fragment = new Rooms_Fragment();
                            break;
                        case R.id.HOME:
                            selected_fragment = new Home_Fragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selected_fragment).commit();
                    return true;
                }
            };
}