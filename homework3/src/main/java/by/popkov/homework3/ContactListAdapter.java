package by.popkov.homework3;


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
    private List<Contact> contactItemList = new ArrayList<>();
    private List<Contact> contactItemListFull = new ArrayList<>();

    void addContact(Contact contact) {
        contactItemList.add(contact);
        contactItemListFull.add(contact);
        notifyItemChanged(contactItemList.size() - 1);
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
        if (contactItemList != null) return contactItemListFull.size();
        else return 0;
    }

    ContactListAdapter() {
    }

    private ContactListAdapter(Parcel source) {
        String[] listFullNames = new String[0];
        source.readStringArray(listFullNames);
        String[] listFullData = new String[0];
        source.readStringArray(listFullData);
        int[] listFullImages = new int[0];
        source.readIntArray(listFullImages);

        String[] listNames = new String[0];
        source.readStringArray(listNames);
        String[] listData = new String[0];
        source.readStringArray(listData);
        int[] listImages = new int[0];
        source.readIntArray(listImages);

        for (int i = 0; i < listFullNames.length; i++) {
            if (listFullImages[i] == Contact.IMAGE_ID_PHONE) {
                contactItemListFull.add(new ContactPhone(listFullNames[i], listFullData[i], Contact.IMAGE_ID_PHONE));
            } else if (listFullImages[i] == Contact.IMAGE_ID_EMAIL) {
                contactItemListFull.add(new ContactEmail(listFullNames[i], listFullData[i], Contact.IMAGE_ID_EMAIL));
            }
        }
        for (int i = 0; i < listNames.length; i++) {
            if (listImages[i] == Contact.IMAGE_ID_PHONE) {
                contactItemList.add(new ContactPhone(listNames[i], listData[i], Contact.IMAGE_ID_PHONE));
            } else if (listImages[i] == Contact.IMAGE_ID_EMAIL) {
                contactItemList.add(new ContactEmail(listNames[i], listData[i], Contact.IMAGE_ID_EMAIL));
            }
        }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        String[] listFullNames = new String[contactItemListFull.size()];
        for (int i = 0; i < listFullNames.length; i++) {
            listFullNames[i] = contactItemListFull.get(i).getName();
        }
        String[] listFullData = new String[contactItemListFull.size()];
        for (int i = 0; i < listFullData.length; i++) {
            listFullData[i] = contactItemListFull.get(i).getData();
        }

        int[] listFullImages = new int[contactItemListFull.size()];
        for (int i = 0; i < listFullImages.length; i++) {
            listFullImages[i] = contactItemListFull.get(i).getImageID();
        }


        String[] listNames = new String[contactItemList.size()];
        for (int i = 0; i < listNames.length; i++) {
            listNames[i] = contactItemList.get(i).getName();
        }
        String[] listData = new String[contactItemList.size()];
        for (int i = 0; i < listData.length; i++) {
            listData[i] = contactItemList.get(i).getData();
        }
        int[] listImages = new int[contactItemList.size()];
        for (int i = 0; i < listImages.length; i++) {
            listImages[i] = contactItemList.get(i).getImageID();
        }
        dest.writeStringArray(listFullNames);
        dest.writeStringArray(listFullData);
        dest.writeIntArray(listFullImages);
        dest.writeStringArray(listNames);
        dest.writeStringArray(listData);
        dest.writeIntArray(listImages);

    }


    public static final Parcelable.Creator<ContactListAdapter> CREATOR = new Parcelable.Creator<ContactListAdapter>() {

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

        void bingData(Contact contact) {
            contactImageView.setImageResource(contact.getImageID());
            textViewName.setText(contact.getName());
            textViewData.setText(contact.getData());
        }
    }
}
