package Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myapp.contractdrafter.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile_Fragment extends Fragment
{
    private TextView first_name, last_name, email_id, user_type, phone_number, bio;
    private RadioGroup radioGroup;
    private RadioButton radioButton, femaleRadiobtn, otherRadiobtn;
    private int selectedID;
    private CircleImageView profilepicture;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private String USER_ID;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        final View root = inflater.inflate(R.layout.profile_fragment, container, false);
        first_name = root.findViewById(R.id.firstNameFieldProfile);
        last_name = root.findViewById(R.id.lastNameFieldProfile);
        email_id = root.findViewById(R.id.EmailIDUserProfile);
        user_type = root.findViewById(R.id.UserTypeProfile);
        phone_number = root.findViewById(R.id.phoneNoFieldProfile);
        bio = root.findViewById(R.id.bioFieldProfile);
        radioGroup = root.findViewById(R.id.genderGroupProfile);
        selectedID = radioGroup.getCheckedRadioButtonId();
        radioButton = root.findViewById(R.id.genderMaleProfile);
        femaleRadiobtn = root.findViewById(R.id.genderFemaleProfile);
        otherRadiobtn = root.findViewById(R.id.genderOtherProfile);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("USER");
        USER_ID = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        profilepicture = root.findViewById(R.id.circularImageProfile);
        progressBar = (ProgressBar) root.findViewById(R.id.progressBarProfile);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
                for(DataSnapshot d : snapshot.getChildren())
                {
                    if(d.child("email_id").getValue().equals(USER_ID))
                    {
                        first_name.setText(d.child("first_name").getValue(String.class));
                        last_name.setText(d.child("last_name").getValue(String.class));
                        email_id.setText(d.child("email_id").getValue(String.class));
                        phone_number.setText(d.child("phone_number").getValue(String.class));
                        user_type.setText(d.child("user_type").getValue(String.class));
                        if(d.child("gender").getValue(String.class).equals("Male"))
                        {
                            radioButton.setChecked(true);
                            femaleRadiobtn.setChecked(false);
                            otherRadiobtn.setChecked(false);
                        }
                        else if(d.child("gender").getValue(String.class).equals("Female"))
                        {
                            radioButton.setChecked(false);
                            femaleRadiobtn.setChecked(true);
                            otherRadiobtn.setChecked(false);
                        }
                        else if(d.child("gender").getValue(String.class).equals("Prefer not to"))
                        {
                            radioButton.setChecked(false);
                            femaleRadiobtn.setChecked(false);
                            otherRadiobtn.setChecked(true);
                        }

                        bio.setText(d.child("bio").getValue(String.class));
                        Picasso.get().load(d.child("image").getValue(String.class)).into(profilepicture);
                    }
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return root;
    }
}
