package com.liberic.bitcoinwallet.adapter;

import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liberic.bitcoinwallet.R;
import com.liberic.bitcoinwallet.activity.SendActivity;
import com.liberic.bitcoinwallet.model.Contact;
import com.liberic.bitcoinwallet.util.Interface;

import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.RowHolder> {
    private final List<Contact> data;
    private Interface.ClickListener clickListener;

    public ContactsAdapter(List<Contact> data) {
        this.data = data;
    }

    @Override
    public RowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_row, parent, false);
        RowHolder rh = new RowHolder(v);
        return rh;
    }

    @Override
    public void onBindViewHolder(RowHolder holder, int position) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(SendActivity.getContext().getContentResolver(), Uri.parse(data.get(position).getUriImage()));
            holder.imageContact.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            holder.imageContact.setImageResource(R.drawable.bender);
        }
        holder.nameContact.setText(data.get(position).getName());
        holder.phoneContact.setText(data.get(position).getPhone());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setClickListener(Interface.ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public Interface.ClickListener getClickListener() {
        return clickListener;
    }

    public class RowHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CircleImageView imageContact;
        TextView nameContact;
        TextView phoneContact;

        public RowHolder(View itemView) {
            super(itemView);
            imageContact = (CircleImageView) itemView.findViewById(R.id.image_contact);
            nameContact = (TextView) itemView.findViewById(R.id.name_contact);
            phoneContact = (TextView) itemView.findViewById(R.id.phone_contact);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.itemClicked(v, getPosition());
            }
        }
    }
}
