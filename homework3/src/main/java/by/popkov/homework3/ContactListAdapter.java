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


public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ItemViewHolder>
        implements Filterable {
    private List<Contact> contactItemList = new ArrayList<>();
    private List<Contact> contactItemListFull = new ArrayList<>();

    void addContact(Contact contact) {
        contactItemList.add(contact);
        contactItemListFull.add(contact);
        notifyItemChanged(contactItemList.size() - 1);
    }

    void removeContact(int fullListPosition) {
        Contact removed = contactItemListFull.remove(fullListPosition);
        contactItemList.remove(removed);
        notifyDataSetChanged();
    }

    void editContact(Contact newContact, int fullListPosition) {
        Contact removed = contactItemListFull.remove(fullListPosition);
        contactItemListFull.add(fullListPosition, newContact);
        int indexOfRemoved = contactItemList.indexOf(removed);
        contactItemList.remove(removed);
        contactItemList.add(indexOfRemoved, newContact);
        notifyDataSetChanged();
    }

    int getFullItemCount() {
        if (contactItemList != null) return contactItemListFull.size();
        else return 0;
    }

    private ContactsActivity contactsActivity;

    void setContactsActivity(ContactsActivity contactsActivity) {
        this.contactsActivity = contactsActivity;
    }

    ContactListAdapter() {
    }

    ContactListAdapter(List<Contact> contactItemListList) {
        this.contactItemList = new ArrayList<>(contactItemListList);
        this.contactItemListFull = new ArrayList<>(contactItemListList);
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

    private Filter contactFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Contact> filteredContactItemList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredContactItemList.addAll(contactItemListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Contact contact : contactItemListFull) {
                    if (contact.getName().toLowerCase().contains(filterPattern)) {
                        filteredContactItemList.add(contact);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredContactItemList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            contactItemList.clear();
            contactItemList.addAll((List) results.values);
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
                    Contact clickedContact = contactItemList.get(getAdapterPosition());
                    contactsActivity.startEditContact(clickedContact, contactItemListFull.indexOf(clickedContact));
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
