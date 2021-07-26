package Fragments;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myapp.contractdrafter.R;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import Models.Participants;
import RVAdapters.RVAdapterMeetingRoom;

public class Participant_Fragment extends Fragment
{
    private RecyclerView rv_participant;
    private RVAdapterMeetingRoom adapter;
    private List<Participants> participants;
    private EditText searchbar;
    private ImageView back;
    private FloatingActionButton docall;
    private BottomNavigationView bottomNavigationView;
    private String ParticipantID;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;
    private List<String> parti;
    private String UID;
    private ProgressBar progressBar;
    private FirebaseDatabase mdb;
    private DatabaseReference mReferece;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.participant_fragment, container, false);

        rv_participant = root.findViewById(R.id.rvparticipants);
        searchbar = (EditText) root.findViewById(R.id.search_participant);
        participants = new ArrayList<>();
        rv_participant.setLayoutManager(new LinearLayoutManager(getContext()));
        back = (ImageView) root.findViewById(R.id.backtorooms);
        docall = (FloatingActionButton) root.findViewById(R.id.performcall);
        mDatabase = (FirebaseDatabase) FirebaseDatabase.getInstance();
        UID = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        mDatabaseReference = (DatabaseReference) mDatabase.getReference("ROOM");
        parti = new ArrayList<>();
        parti.clear();
        progressBar = root.findViewById(R.id.progressBarParticipants);
        ParticipantID = getArguments().getString("RoomID");

        try
        {
            JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(new URL("https://meet.jit.si"))
                    .setWelcomePageEnabled(false)
                    .build();
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        Log.d("participant", ParticipantID);
        /*participants.add(new Participants("jahanzaibpalh@yahoo.com", "Jahan Zaib",  "Offline"));
        participants.add(new Participants("jahanzaibpalh@yahoo.com", "Junaid Mehmood",  "Online"));
        participants.add(new Participants("jahanzaibpalh@yahoo.com", "Salman Zafar",  "Offline"));
        participants.add(new Participants("jahanzaibpalh@yahoo.com", "Irfan Ullah",  "Online"));
        participants.add(new Participants("jahanzaibpalh@yahoo.com", "Farrukh Ahmed",  "Offline"));
        participants.add(new Participants("jahanzaibpalh@yahoo.com", "Imran Haider",  "Online"));
        participants.add(new Participants("jahanzaibpalh@yahoo.com", "Raqeeb Hamza",  "Offline"));*/

        docall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //bottomNavigationView.setVisibility(View.GONE);
                /*FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container,new CallingUser_Fragment());
                fr.commit();*/

                JitsiMeetConferenceOptions option = new JitsiMeetConferenceOptions.Builder()
                        .setRoom(ParticipantID)
                        .build();
                JitsiMeetActivity.launch(getContext(), option);
            }
        });

        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container,new Rooms_Fragment());
                fr.commit();
            }
        });

        SearchParticipantsInRecycleView();
        if(ParticipantID!=null)
        {
            progressBar.setVisibility(View.VISIBLE);
            FetchingParticipantsFromFirebase();
        }

        return root;
    }

    private void SearchParticipantsInRecycleView()
    {
        searchbar.requestFocus();
        searchbar.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                filter(s.toString());
            }
        });
    }

    private void filter(String text)
    {
        ArrayList<Participants> filteredList= new ArrayList<Participants>();

        for(Participants participant : participants)
        {
            if(participant.getParticipant_Name().toLowerCase().contains(text.toLowerCase()))
            {
                filteredList.add(participant);
            }
            else if(participant.getParticipant_ID().toLowerCase().contains(text.toLowerCase()))
            {
                filteredList.add(participant);
            }
        }

        if(adapter!=null)
        {
            adapter.filterList(filteredList);
        }
    }

    private void SearchRoomInFirebase()
    {
        parti.clear();
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for(DataSnapshot d: snapshot.getChildren())
                {
                    if(d.child("room_id").getValue(String.class).equals(ParticipantID))
                    {
                        final DatabaseReference reference = d.child("PARTICIPANTS").getRef();

                        reference.addValueEventListener(new ValueEventListener()
                        {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot)
                            {
                                if(snapshot.exists())
                                {
                                    parti.clear();
                                    for(DataSnapshot d: snapshot.getChildren())
                                    {
                                        String id = d.getValue(String.class).toString();
                                        parti.add(id);
                                    }
                                    Log.d("val", parti.toString());
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error)
                            {
                                progressBar.setVisibility(View.GONE);
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }

    private void FetchingParticipantsFromFirebase()
    {
        SearchRoomInFirebase();

        mdb = FirebaseDatabase.getInstance();
        mDatabaseReference = mdb.getReference("USER");

        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for(int i = 0; i < parti.size(); i++)
                {
                    for(DataSnapshot d: snapshot.getChildren())
                    {
                        if(d.child("email_id").getValue().equals(parti.get(i)))
                        {
                            String email = d.child("email_id").getValue(String.class);
                            String name = d.child("first_name").getValue(String.class) + " "
                                    + d.child("last_name").getValue(String.class);
                            String image = d.child("image").getValue(String.class);
                            String status = "Online";
                            Log.d("parti_email", email);
                            Log.d("parti_email", name);

                            participants.add(new Participants(email, name, image, status));
                            adapter = new RVAdapterMeetingRoom(participants, getContext());
                            rv_participant.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
                progressBar.setVisibility(View.GONE);
                parti.clear();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

}
