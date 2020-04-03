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

    private ContactsActivity contactsActivity;

    void setContactsActivity(ContactsActivity contactsActivity) {
        this.contactsActivity = contactsActivity;
    }

    ContactListAdapter() {
    }

    private ContactListAdapter(Parcel source) {
        int sizeContactFull = source.readInt();
        int sizeContact = (int) source.readLong();
        String[] commonNames = new String[sizeContactFull + sizeContact];
        source.readStringArray(commonNames);
        ArrayList<String> commonData = new ArrayList<>(sizeContactFull + sizeContact);
        source.readStringList(commonData);
        int[] commonImageID = new int[sizeContactFull + sizeContact];
        source.readIntArray(commonImageID);
        for (int i = 0; i < commonNames.length; i++) {
            if (i < sizeContactFull) {
                if (commonImageID[i] == Contact.IMAGE_ID_PHONE) {
                    contactItemListFull.add(new ContactPhone(commonNames[i], commonData.get(i), commonImageID[i]));
                } else if (commonImageID[i] == Contact.IMAGE_ID_EMAIL) {
                    contactItemListFull.add(new ContactEmail(commonNames[i], commonData.get(i), commonImageID[i]));
                }
            } else {
                if (commonImageID[i] == Contact.IMAGE_ID_PHONE) {
                    contactItemList.add(new ContactPhone(commonNames[i], commonData.get(i), commonImageID[i]));
                } else if (commonImageID[i] == Contact.IMAGE_ID_EMAIL) {
                    contactItemList.add(new ContactEmail(commonNames[i], commonData.get(i), commonImageID[i]));
                }
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
        int sizeContactFull = contactItemListFull.size();
        int sizeContact = contactItemList.size();
        String[] commonNames = new String[sizeContactFull + sizeContact];
        for (int i = 0; i < commonNames.length; i++) {
            if (i < sizeContactFull) commonNames[i] = contactItemListFull.get(i).getName();
            else commonNames[i] = contactItemList.get(i - sizeContactFull).getName();
        }

        ArrayList<String> commonData = new ArrayList<>(sizeContactFull + sizeContact);
        for (Contact contact : contactItemListFull) {
            commonData.add(contact.getData());
        }
        for (Contact contact : contactItemList) {
            commonData.add(contact.getData());
        }

        int[] commonImageID = new int[sizeContactFull + sizeContact];
        for (int i = 0; i < commonImageID.length; i++) {
            if (i < sizeContactFull)
                commonImageID[i] = contactItemListFull.get(i).getImageID();
            else commonImageID[i] = contactItemList.get(i - sizeContactFull).getImageID();
        }
        dest.writeStringArray(commonNames);
        dest.writeStringList(commonData);
        dest.writeIntArray(commonImageID);
        dest.writeInt(sizeContactFull);
        dest.writeLong(sizeContact);
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
                    contactsActivity.startEditContact(clickedContact,
                            contactItemListFull.indexOf(clickedContact),
                            contactItemList.indexOf(clickedContact));
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
