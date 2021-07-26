package Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.myapp.contractdrafter.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class CallingUser_Fragment extends Fragment
{
    private CircleImageView end_call;
    private BottomNavigationView bottomNavigationView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        final View root = inflater.inflate(R.layout.callinguser_fragment, container, false);

        end_call = root.findViewById(R.id.endcall);
        bottomNavigationView = root.findViewById(R.id.bottom_navigationView);

        end_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //bottomNavigationView.setVisibility(View.VISIBLE);
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container,new Rooms_Fragment());
                fr.commit();
            }
        });

        return root;
    }
}
