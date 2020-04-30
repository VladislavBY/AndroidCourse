package by.popkov.homework7;

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
    private ArrayList<Contact> contactItemList = new ArrayList<>();
    private ArrayList<Contact> contactItemListFull = new ArrayList<>();

    ArrayList<Contact> getContactItemList() {
        return contactItemList;
    }

    ArrayList<Contact> getContactItemListFull() {
        return contactItemListFull;
    }

    void setContactLists(ArrayList<Contact> contacts) {
        contactItemList.clear();
        contactItemList.addAll(contacts);
        contactItemListFull.clear();
        contactItemListFull.addAll(contacts);
        notifyDataSetChanged();
    }

    void addContact(Contact contact) {
        contactItemList.add(contact);
        contactItemListFull.add(contact);
        notifyDataSetChanged();
    }

    void editContact(Contact newContact) {
        String newContactId = newContact.getId();
        for (int i = 0; i < contactItemListFull.size(); i++) {
            if (contactItemListFull.get(i).getId().equals(newContactId)) {
                contactItemListFull.remove(i);
                contactItemListFull.add(i, newContact);
                break;
            }
        }
        for (int i = 0; i < contactItemList.size(); i++) {
            if (contactItemList.get(i).getId().equals(newContactId)) {
                contactItemList.remove(i);
                contactItemList.add(i, newContact);
                break;
            }
        }
        notifyDataSetChanged();
    }

    void removeContact(Contact oldContact) {
        String oldContactId = oldContact.getId();
        for (int i = 0; i < contactItemListFull.size(); i++) {
            if (contactItemListFull.get(i).getId().equals(oldContactId)) {
                contactItemListFull.remove(i);
                break;
            }
        }
        for (int i = 0; i < contactItemList.size(); i++) {
            if (contactItemList.get(i).getId().equals(oldContactId)) {
                contactItemList.remove(i);
                break;
            }
        }
        notifyDataSetChanged();
    }

    int getFullItemCount() {
        return (contactItemListFull != null) ? contactItemListFull.size() : 0;
    }

    ContactListAdapter(ArrayList<Contact> contactItemList, ArrayList<Contact> contactItemListFull) {
        this.contactItemList = new ArrayList<>(contactItemList);
        this.contactItemListFull = new ArrayList<>(contactItemListFull);
    }

    ContactListAdapter() {
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
        holder.bindData(contactItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return (contactItemList != null) ? contactItemList.size() : 0;
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

    private ItemListenerWithData itemListenerWithData;

    interface ItemListenerWithData {
        void onClick(Contact oldContact);
    }

    void setItemListenerWithData(ItemListenerWithData itemListenerWithData) {
        this.itemListenerWithData = itemListenerWithData;
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
                    if (itemListenerWithData != null) {
                        itemListenerWithData.onClick(clickedContact);
                    }
                }
            });
        }

        void bindData(Contact contact) {
            contactImageView.setImageResource(contact.getImageID());
            textViewName.setText(contact.getName());
            textViewData.setText(contact.getData());
        }
    }
}
