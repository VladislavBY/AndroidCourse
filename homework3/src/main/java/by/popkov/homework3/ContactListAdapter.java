package by.popkov.homework3;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ItemViewHolder> implements Filterable {
    private List<Contact> contactList = new ArrayList<>();
    private List<Contact> contactListFull = new ArrayList<>();

    void addContact(Contact contact) {
        contactList.add(contact);
        contactListFull.add(contact);
        notifyItemChanged(contactList.size() - 1);
    }

    void removeContact(int adapterPosition) {
        contactList.remove(adapterPosition);
        contactListFull.remove(adapterPosition);
        notifyDataSetChanged();
    }

    void editContact(Contact newContact, int adapterPosition) {
        contactList.remove(adapterPosition);
        contactList.add(adapterPosition, newContact);
        contactListFull.remove(adapterPosition);
        contactListFull.add(adapterPosition, newContact);
        notifyDataSetChanged();
    }

    private ContactsActivity contactsActivity;

    void setContactsActivity(ContactsActivity contactsActivity) {
        this.contactsActivity = contactsActivity;
    }

    ContactListAdapter() {
    }

    ContactListAdapter(List<Contact> contactList) {
        this.contactList = contactList;
        this.contactListFull = new ArrayList<>(contactList);

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
        holder.bingData(contactList.get(position));
    }

    @Override
    public int getItemCount() {
        if (contactList != null) return contactList.size();
        else return 0;
    }

    private Filter contactFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Contact> filteredContactList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredContactList.addAll(contactListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Contact contact : contactListFull) {
                    if (contact.getName().toLowerCase().contains(filterPattern)) {
                        filteredContactList.add(contact);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredContactList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            contactList.clear();
            contactList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    @Override
    public Filter getFilter() {
        return contactFilter;
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
                    contactsActivity.startEditContact(contactList.get(adapterPosition), adapterPosition);
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
