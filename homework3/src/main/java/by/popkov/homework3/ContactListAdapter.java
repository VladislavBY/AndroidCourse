package by.popkov.homework3;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ItemViewHolder> {

    private ContactsActivity contactsActivity;

    void setContactsActivity(ContactsActivity contactsActivity) {
        this.contactsActivity = contactsActivity;
    }

    private List<Contact> contactItemList = new ArrayList<>();

    void addContact(Contact contact) {
        contactItemList.add(contact);
        notifyItemChanged(contactItemList.size() - 1);
    }

    void removeContact(int adapterPosition) {
        contactItemList.remove(adapterPosition);
        notifyDataSetChanged();
    }

    void editContact(Contact newContact, int adapterPosition) {
        contactItemList.remove(adapterPosition);
        contactItemList.add(adapterPosition, newContact);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact, parent, false);
        return new ItemViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bingData(contactItemList.get(position));
    }

    @Override
    public int getItemCount() {
        if (contactItemList != null) return contactItemList.size();
        else return 0;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView contactImageView;
        private TextView textViewName;
        private TextView textViewData;


        ItemViewHolder(@NonNull final View itemView) {
            super(itemView);
            contactImageView = itemView.findViewById(R.id.contactImageView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewData = itemView.findViewById(R.id.textViewData);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = getAdapterPosition();
                    contactsActivity.startEditContact(contactItemList.get(adapterPosition), adapterPosition);
                }
            });
        }

        void bingData(Contact contact) {
            contactImageView.setImageResource(contact.getImageID());
            textViewName.setText(contact.getName());
            textViewData.setText(contact.getData());
        }
    }
}
