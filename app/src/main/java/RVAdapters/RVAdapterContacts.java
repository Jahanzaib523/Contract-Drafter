package RVAdapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.myapp.contractdrafter.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import Models.Contact;
import de.hdodenhof.circleimageview.CircleImageView;

public class RVAdapterContacts extends RecyclerView.Adapter<RVAdapterContacts.MyViewHolder>
{
    List<Contact> ls;
    List<String> selectedItemsList;
    Context c;

    public RVAdapterContacts(List<Contact> l, Context c, List<String> selectedItems)
    {
        this.c=c;
        this.ls = l;
        selectedItemsList = selectedItems;
    }

    @NonNull
    @Override
    public RVAdapterContacts.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemrow= LayoutInflater.from(c).inflate(R.layout.create_room_row, parent,false);
        return new  MyViewHolder(itemrow);
    }


    @Override
    public void onBindViewHolder(@NonNull final RVAdapterContacts.MyViewHolder holder, final int position)
    {
        holder.contactusername.setText(ls.get(position).getCFirstName() + " " + ls.get(position).getCLastName());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (holder.checkBox.isChecked())
                {
                    holder.checkBox.setChecked(true);
                    ls.get(position).Set_Checked(true);
                }
                else {
                    holder.checkBox.setChecked(false);
                    ls.get(position).Set_Checked(false);
                }
            }
        });

        if(ls.get(position).getCOnlineStatus() == "Online")
        {
           holder.online_or_not.setImageResource(R.color.color_primary);
        }
        else if (ls.get(position).getCOnlineStatus() == "Offline")
        {
            holder.online_or_not.setImageResource(R.color.offline);
        }
        Picasso.get().load(ls.get(position).getCImage()).into(holder.contactpic);
    }

    @Override
    public int getItemCount()
    {
        return ls.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        CircleImageView contactpic, online_or_not;
        TextView online_status, contactusername;
        CheckBox checkBox;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);

            checkBox = itemView.findViewById(R.id.selectcontact);
            contactpic = itemView.findViewById(R.id.contactimage);
            contactusername = itemView.findViewById(R.id.contactname);
            online_or_not = itemView.findViewById(R.id.onlinestatus);
        }
    }

    public void update (List<Contact> lst)
    {
        notifyDataSetChanged();
    }

    public void filterList(ArrayList<Contact> filteredList)
    {
        ls = filteredList;
        notifyDataSetChanged();
    }

    public List<String> getSelectedItems()
    {
        List<String> selectedItems = new ArrayList<>();
        for(Contact list: ls)
        {
            if(list.Get_Checked() == true)
            {
                selectedItems.add(list.getCEmail());
            }
        }
        return selectedItems;
    }
}

