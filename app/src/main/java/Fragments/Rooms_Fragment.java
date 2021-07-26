package Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myapp.contractdrafter.R;
import java.util.ArrayList;
import java.util.List;

import Models.Contact;
import Models.Room;
import RVAdapters.RVAdapterContacts;
import RVAdapters.RVAdapterRooms;

public class Rooms_Fragment extends Fragment
{
    private RecyclerView rv_rooms;
    private RVAdapterRooms adapter;
    private List<Room> rooms;
    private EditText searchbar;
    public FloatingActionButton newRoom;
    private FirebaseDatabase db;
    private DatabaseReference dbReference;
    private TextView name_room, time, room_status;
    private ProgressBar progressBar;
    private String UID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        final View root = inflater.inflate(R.layout.rooms_fragment, container, false);

        rv_rooms = root.findViewById(R.id.rvrooms);
        searchbar = (EditText) root.findViewById(R.id.search_room);
        rooms = new ArrayList<>();
        newRoom = (FloatingActionButton) root.findViewById(R.id.newroom);
        rv_rooms.setLayoutManager(new LinearLayoutManager(getContext()));
        db = FirebaseDatabase.getInstance();
        dbReference = db.getReference("ROOM");
        name_room = root.findViewById(R.id.nameof_room);
        time = root.findViewById(R.id.startdateof_room);
        room_status = root.findViewById(R.id.statusof_room);
        progressBar = root.findViewById(R.id.progressBarRoom);
        UID = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        adapter = new RVAdapterRooms(rooms, getContext());

        progressBar.setVisibility(View.VISIBLE);
        dbReference.keepSynced(true);
        dbReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for(final DataSnapshot d : snapshot.getChildren())
                {
                    String room_name = d.child("room_id").getValue(String.class);
                    String time_room_creation = d.child("time").getValue(String.class);
                    String status = "Active";

                    rooms.add(new Room(room_name, time_room_creation, status));
                    adapter = new RVAdapterRooms(rooms, getContext());
                    LinearLayoutManager l = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,true);
                    l.setStackFromEnd(true);
                    rv_rooms.setLayoutManager(l);

                    rv_rooms.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
               progressBar.setVisibility(View.GONE);
            }
        });

        newRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container,new CreateRoom_Fragment());
                fr.commit();
            }
        });

        SearchItemsInRecycleView();

        return root;
    }

    private void SearchItemsInRecycleView()
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
        ArrayList<Room> filteredList= new ArrayList<>();

        for(Room room : rooms)
        {
            if(room.getRoom_name().toLowerCase().contains(text.toLowerCase()))
            {
                filteredList.add(room);
            }
            else if(room.getStart_time().toLowerCase().contains(text.toLowerCase()))
            {
                filteredList.add(room);
            }
            else if(room.getStatus().toLowerCase().contains(text.toLowerCase()))
            {
                filteredList.add(room);
            }
        }
        if (adapter != null)
        {
            adapter.filterList(filteredList);
        }

    }
}
