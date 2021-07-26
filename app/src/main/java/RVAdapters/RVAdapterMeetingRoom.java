package RVAdapters;

import android.content.Context;
import android.provider.Telephony;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.myapp.contractdrafter.R;
import com.squareup.picasso.Picasso;

import org.jitsi.meet.sdk.JitsiMeetActivityInterface;
import org.jitsi.meet.sdk.JitsiMeetOngoingConferenceService;
import org.jitsi.meet.sdk.JitsiMeetViewListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Models.Participants;
import de.hdodenhof.circleimageview.CircleImageView;

public class RVAdapterMeetingRoom extends RecyclerView.Adapter<RVAdapterMeetingRoom.MyViewHolder> implements JitsiMeetViewListener
{
    List<Participants> ls;
    Context c;

    public RVAdapterMeetingRoom(List<Participants> l, Context c)
    {
        this.c=c;
        this.ls = l;
    }

    @NonNull
    @Override
    public RVAdapterMeetingRoom.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemrow= LayoutInflater.from(c).inflate(R.layout.participant_row, parent,false);
        return new  MyViewHolder(itemrow);
    }

    @Override
    public void onBindViewHolder(@NonNull final RVAdapterMeetingRoom.MyViewHolder holder, final int position)
    {
        holder.participant_name.setText(ls.get(position).getParticipant_Name());
        //holder.participant_status.setText(ls.get(position).getParticipant_Status());
        //holder.contactpic.setImageDrawable(R.drawable.jahan);
        if(ls.get(position).getParticipant_Status()== "Online")
        {
            holder.participant_status.setImageResource(R.color.color_primary);
        }
        else if (ls.get(position).getParticipant_Status() == "Offline")
        {
            holder.participant_status.setImageResource(R.color.offline);
        }

        Picasso.get().load(ls.get(position).getParticipant_Photo()).into(holder.participant_image);
    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    @Override
    public void onConferenceJoined(Map<String, Object> map) {
       
    }

    @Override
    public void onConferenceTerminated(Map<String, Object> map) {

    }

    @Override
    public void onConferenceWillJoin(Map<String, Object> map) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        CircleImageView participant_image, participant_status;
        TextView participant_name;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);

            participant_name = itemView.findViewById(R.id.participantname);
            participant_status = itemView.findViewById(R.id.participant_onlinestatus);
            participant_image = itemView.findViewById(R.id.participantimage);
        }
    }

    public void update (List<Participants> lst)
    {
        notifyDataSetChanged();
    }

    public void filterList(ArrayList<Participants> filteredList)
    {
        ls = filteredList;
        Log.d("list", filteredList.toString());
        notifyDataSetChanged();
    }
}


