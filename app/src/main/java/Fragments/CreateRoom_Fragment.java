package Fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AdditionalUserInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.myapp.contractdrafter.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Models.Contact;
import Models.Room;
import Models.User;
import RVAdapters.RVAdapterContacts;
import RVAdapters.RVAdapterRooms;

public class CreateRoom_Fragment extends Fragment
{
    private static final int REQUEST_READ_CONTACTS = 1;
    HashMap<String, String> phonebook = new HashMap<String, String>();
    private List<String> selectedContacts;
    private RecyclerView rv_contact;
    private RVAdapterContacts adapter;
    private List<Contact> contacts;
    private EditText searchbar;
    private ImageView back;
    private FloatingActionButton createroom;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ProgressBar progressBar;
    private EditText room_name_;
    List<Contact> myparticipants;
    //Reference number 2
    private FirebaseDatabase db;
    private DatabaseReference Reference;
    private static final String ROOM = "ROOM";
    private String UID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        final View root = inflater.inflate(R.layout.create_room_fragment, container, false);

        rv_contact = root.findViewById(R.id.rvcontacts);
        searchbar = (EditText) root.findViewById(R.id.search_contact);
        back = (ImageView) root.findViewById(R.id.backtorooms);
        createroom = (FloatingActionButton) root.findViewById(R.id.createroombtn);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("USER");
        progressBar = (ProgressBar) root.findViewById(R.id.progressBarCreateRoom);
        room_name_ = root.findViewById(R.id.roomname);
        UID = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        contacts = new ArrayList<>();
        get_contacts();
        progressBar.setVisibility(View.VISIBLE);
        for (Map.Entry<String, String> entry : phonebook.entrySet())
        {
            final String key = entry.getKey();
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot)
                {
                    for(DataSnapshot d : snapshot.getChildren())
                    {
                        if(!d.child("email_id").getValue().equals(UID))
                        {
                            if(d.child("phone_number").getValue().equals(key))
                            {
                                String first_name = d.child("first_name").getValue(String.class);
                                String last_name = d.child("last_name").getValue(String.class);
                                String email = d.child("email_id").getValue(String.class);
                                String img = d.child("image").getValue(String.class);
                                contacts.add(new Contact(email, first_name, last_name, "Offline", false, img));
                                rv_contact.setLayoutManager(new LinearLayoutManager(getContext()));
                                adapter = new RVAdapterContacts(contacts, getContext(), selectedContacts);
                                selectedContacts = adapter.getSelectedItems();
                                rv_contact.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error)
                {
                    progressBar.setVisibility(View.GONE);
                }
            });
        }

        /*contacts.add(new Contact("jahanzaibpalh@yahoo.com", "Irfan", "Ullah", "Online",  true));
        contacts.add(new Contact("jahanzaibpalh@yahoo.com", "Raqeeb", "Hamza", "Offline", false));
        contacts.add(new Contact("jahanzaibpalh@yahoo.com", "Pirah", "Syed", "Online", true));
        contacts.add(new Contact("jahanzaibpalh@yahoo.com", "Hasnain", "Barkat", "Offline", false));
        contacts.add(new Contact("jahanzaibpalh@yahoo.com", "Junaid", "Mehmood", "Online", false));
        contacts.add(new Contact("jahanzaibpalh@yahoo.com", "Salman", "Zafar", "Offline", true));
        contacts.add(new Contact("jahanzaibpalh@yahoo.com", "Hamaad", "Ubuntu", "Online", true));
        contacts.add(new Contact("jahanzaibpalh@yahoo.com", "Farrukh", "Ahmed", "Offline", true));
        contacts.add(new Contact("jahanzaibpalh@yahoo.com", "Imran", "Haider", "Online", true));*/


        SearchContactsInRecycleView();

        myparticipants = new ArrayList<>();

        createroom.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Log.d("Selected Item:",adapter.getSelectedItems().toString());

                /*if(!adapter.getSelectedItems().toString().isEmpty())
                {
                    Toast.makeText(getContext(), "Please, select the participants", Toast.LENGTH_SHORT).show();
                }*/
                if(room_name_.getText().toString().equals(""))
                {
                    Toast.makeText(getContext(), "Please, Enter the room name", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String room_n = room_name_.getText().toString();
                    List<String> list = new ArrayList<>();
                    for(int i = 0; i<adapter.getSelectedItems().size(); i++)
                    {
                        list.add(i, adapter.getSelectedItems().get(i));
                    }

                    list.add(UID);
                    Toast.makeText(getContext(), "Room Created Successfully.", Toast.LENGTH_LONG).show();
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();

                    db = FirebaseDatabase.getInstance();
                    Reference = db.getReference(ROOM);
                    String key = Reference.push().getKey();

                    Reference.child(key).child("room_id").setValue(room_n);
                    Reference.child(key).child("time").setValue(dtf.format(now));
                    Reference.child(key).child("PARTICIPANTS").setValue(list);
                    //SaveRoomToFirebase();
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.fragment_container, new Rooms_Fragment());
                    fr.commit();
                }
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

        return root;
    }

    public void SaveRoomToFirebase()
    {

    }

    private void SearchContactsInRecycleView()
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

    private void BackToRooms()
    {

    }

    private void filter(String text)
    {
        ArrayList<Contact> filteredList= new ArrayList<>();

        for(Contact contact : contacts)
        {
            if(contact.getCFirstName().toLowerCase().contains(text.toLowerCase()))
            {
                filteredList.add(contact);
            }
            else if(contact.getCEmail().toLowerCase().contains(text.toLowerCase()))
            {
                filteredList.add(contact);
            }
            else if(contact.getCLastName().toLowerCase().contains(text.toLowerCase()))
            {
                filteredList.add(contact);
            }
        }

        if(adapter!=null)
        {
            adapter.filterList(filteredList);
        }
    }

    public boolean checkAndAskForContactPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
            {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_CONTACTS))
                {

                }
                else
                    {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_READ_CONTACTS);
                }
            }
            else
                {
                return true;
                // Permission has already been granted
            }
            return false;
        } else return true;
    }

    public void get_contacts() {

        Cursor cursor = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null);
        while (cursor.moveToNext())
        {
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phn_number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            phn_number = phn_number.replaceAll("[()\\s-]", "");
            String regx = "^\\+[1-9]{1}[0-9]{3,14}$";
            if (phn_number.matches(regx))
            {
                phonebook.put(phn_number, name);
                //book.add(phn_number);
            }
        }
        cursor.close();
    }
}
