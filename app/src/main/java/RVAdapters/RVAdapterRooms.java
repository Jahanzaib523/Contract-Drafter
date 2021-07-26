package RVAdapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myapp.contractdrafter.R;

import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import Activities.Participant_Activity;
import Fragments.Participant_Fragment;
import Fragments.Rooms_Fragment;
import Models.Room;
import de.hdodenhof.circleimageview.CircleImageView;

public class RVAdapterRooms extends RecyclerView.Adapter<RVAdapterRooms.MyViewHolder>
{
    List<Room> ls;
    Context c;

    public RVAdapterRooms (List<Room> l, Context c)
    {
        this.c=c;
        this.ls = l;
    }

    @NonNull
    @Override
    public RVAdapterRooms.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemrow= LayoutInflater.from(c).inflate(R.layout.room_row, parent,false);
        return new  MyViewHolder(itemrow);
    }

    @Override
    public void onBindViewHolder(@NonNull final RVAdapterRooms.MyViewHolder holder, final int position)
    {
           holder.room_name.setText(ls.get(position).getRoom_name());
           holder.start_date.setText(ls.get(position).getStart_time());
           holder.newview.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v)
               {

                   AppCompatActivity activity = (AppCompatActivity) v.getContext();
                   FragmentTransaction fr = activity.getSupportFragmentManager().beginTransaction();
                   Participant_Fragment participant_fragment = new Participant_Fragment();
                   Bundle bundle = new Bundle();
                   bundle.putString("RoomID", ls.get(position).getRoom_name().toString());
                   participant_fragment.setArguments(bundle);
                   fr.replace(R.id.fragment_container, participant_fragment);
                   fr.commit();
               }
           });
    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView room_name, room_status, start_date;
        View newview;
        FloatingActionButton call;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);

            room_name = itemView.findViewById(R.id.nameof_room);
            room_status = itemView.findViewById(R.id.status);
            start_date = itemView.findViewById(R.id.startdateof_room);
            call = itemView.findViewById(R.id.joinroomcall);
            newview = itemView;
        }
    }

    public void update (List<Room> lst)
    {
        notifyDataSetChanged();
    }

    public void filterList(ArrayList<Room> filteredList)
    {
        ls = filteredList;
        notifyDataSetChanged();
    }
}

