package Activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.myapp.contractdrafter.R;
import java.util.ArrayList;
import java.util.List;
import Models.Participants;
import RVAdapters.RVAdapterMeetingRoom;

public class Participant_Activity extends AppCompatActivity
{
    private RecyclerView rv_participant;
    private RVAdapterMeetingRoom adapter;
    private List<Participants> participants;
    private EditText searchbar;
    private ImageView back;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.participant_activity);

        rv_participant = findViewById(R.id.rvparticipants);
        searchbar = (EditText) findViewById(R.id.search_participant);
        participants = new ArrayList<>();
        rv_participant.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        back = (ImageView) findViewById(R.id.backtorooms);

        participants.add(new Participants("jahanzaibpalh@yahoo.com", "Jahan",  "Offline"));
        participants.add(new Participants("jahanzaibpalh@yahoo.com", "Jahan",  "Online"));
        participants.add(new Participants("jahanzaibpalh@yahoo.com", "Jahan",  "Offline"));
        participants.add(new Participants("jahanzaibpalh@yahoo.com", "Jahan",  "Online"));
        participants.add(new Participants("jahanzaibpalh@yahoo.com", "Jahan",  "Offline"));
        participants.add(new Participants("jahanzaibpalh@yahoo.com", "Jahan",  "Online"));
        participants.add(new Participants("jahanzaibpalh@yahoo.com", "Jahan",  "Offline"));

        /*back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container,new Rooms_Fragment());
                fr.commit();
            }
        });*/

        SearchParticipantsInRecycleView();

        adapter = new RVAdapterMeetingRoom(participants, getApplicationContext());
        rv_participant.setAdapter(adapter);

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
        ArrayList<Participants> filteredList= new ArrayList<>();

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
        adapter.filterList(filteredList);
    }
}
