package by.popkov.homework6;


import android.os.Parcel;
import android.os.Parcelable;
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
        implements Filterable, Parcelable {
    private ArrayList<Contact> contactItemList = new ArrayList<>();
    private ArrayList<Contact> contactItemListFull = new ArrayList<>();

    ArrayList<Contact> getContactItemListFull() {
        return contactItemListFull;
    }

    void addContact(Contact contact) {
        contactItemList.add(contact);
        contactItemListFull.add(contact);
        notifyDataSetChanged();
    }

    void removeContact(int fullListPosition, int listPosition) {
        contactItemListFull.remove(fullListPosition);
        contactItemList.remove(listPosition);
        notifyDataSetChanged();
    }

    void editContact(Contact newContact, int fullListPosition, int listPosition) {
        contactItemListFull.remove(fullListPosition);
        contactItemListFull.add(fullListPosition, newContact);
        contactItemList.remove(listPosition);
        contactItemList.add(listPosition, newContact);
        notifyDataSetChanged();
    }

    int getFullItemCount() {
        if (contactItemList != null) {
            return contactItemListFull.size();
        } else {
            return 0;
        }
    }

    ContactListAdapter() {

    }

    ContactListAdapter(ArrayList<Contact> contacts) {
        contactItemList = new ArrayList<>(contacts);
        contactItemListFull = new ArrayList<>(contacts);
    }

    private ContactListAdapter(Parcel source) {
        contactItemListFull = (ArrayList<Contact>) source.readSerializable();
        contactItemList = (ArrayList<Contact>) source.readSerializable();
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
        if (contactItemList != null) {
            return contactItemList.size();
        } else {
            return 0;
        }
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(contactItemListFull);
        dest.writeSerializable(contactItemList);
    }


    public static final Creator<ContactListAdapter> CREATOR = new Creator<ContactListAdapter>() {

        @Override
        public ContactListAdapter createFromParcel(Parcel source) {
            return new ContactListAdapter(source);
        }

        @Override
        public ContactListAdapter[] newArray(int size) {
            return new ContactListAdapter[size];
        }
    };


    private ItemListenerWithData itemListenerWithData;

    interface ItemListenerWithData {
        void onClick(Contact oldContact, int positionFullList, int positionList);
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
                        itemListenerWithData.onClick(clickedContact,
                                contactItemListFull.indexOf(clickedContact),
                                contactItemList.indexOf(clickedContact));
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
