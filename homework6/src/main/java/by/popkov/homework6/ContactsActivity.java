package by.popkov.homework6;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class ContactsActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_FOR_ADD = 7777;
    private static final int REQUEST_CODE_FOR_EDIT = 1111;
    private static final String CONTACT_ITEM_LIST = "contactItemList";
    private static final String CONTACT_ITEM_LIST_FULL = "contactItemListFull";
    private static final String CONTACT_DATABASE = "ContactDatabase";

    private ContactDatabase contactDatabase;
    private RecyclerView contactsRecyclerView;
    private ContactListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        connectContactDatabase();
        initContactsRecyclerView(savedInstanceState);
        setListeners();
        setToolBar();
    }

    private void connectContactDatabase() {
        contactDatabase = Room.databaseBuilder(this, ContactDatabase.class, CONTACT_DATABASE)
                .allowMainThreadQueries()
                .build();
    }

    private void initContactsRecyclerView(Bundle savedInstanceState) {
        contactsRecyclerView = findViewById(R.id.recyclerViewContacts);
        if (savedInstanceState != null) {
            contactsRecyclerView.setAdapter(new ContactListAdapter(
                    (ArrayList<Contact>) savedInstanceState.getSerializable(CONTACT_ITEM_LIST),
                    (ArrayList<Contact>) savedInstanceState.getSerializable(CONTACT_ITEM_LIST_FULL)
            ));
        } else {
            contactsRecyclerView.setAdapter(new ContactListAdapter(contactsFromDatabase()));
        }
        contactsRecyclerView.setLayoutManager(new GridLayoutManager(ContactsActivity.this,
                Integer.parseInt(contactsRecyclerView.getTag().toString())));
        adapter = (ContactListAdapter) contactsRecyclerView.getAdapter();

        if (adapter != null) {
            visibleSwitcher(adapter.getFullItemCount());
        }
    }

    private ArrayList<Contact> contactsFromDatabase() {
        ContactEntity[] contactEntities = contactDatabase.getContactDao().loadAddContacts();
        ArrayList<Contact> result = new ArrayList<>();
        for (ContactEntity contactEntity : contactEntities) {
            Contact contact;
            if (contactEntity.getType().equals(Contact.Type.EMAIL.name())) {
                contact = new Contact(Contact.Type.EMAIL, contactEntity.getName(), contactEntity.getData());
            } else {
                contact = new Contact(Contact.Type.PHONE, contactEntity.getName(), contactEntity.getData());
            }
            contact.setId(contactEntity.getId());
            result.add(contact);
        }
        return result;
    }

    private void setListeners() {
        setSearchViewListener();
        setFloatingActionButtonAddContactListener();
        setItemListenerWithData();
    }

    private void setSearchViewListener() {
        final TextView textViewHead = findViewById(R.id.textViewHead);
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewHead.setVisibility(View.INVISIBLE);
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                textViewHead.setVisibility(View.VISIBLE);
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void setFloatingActionButtonAddContactListener() {
        FloatingActionButton floatingActionButtonAddContact = findViewById(R.id.floatingActionButtonAddContact);
        floatingActionButtonAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(AddContactActivity
                        .newIntent(ContactsActivity.this), REQUEST_CODE_FOR_ADD);
            }
        });
    }

    private void setItemListenerWithData() {
        adapter.setItemListenerWithData(new ContactListAdapter.ItemListenerWithData() {
            @Override
            public void onClick(Contact oldContact) {
                startActivityForResult(
                        EditContactActivity.newIntent(ContactsActivity.this, oldContact),
                        REQUEST_CODE_FOR_EDIT
                );
            }
        });
    }

    private void setToolBar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolBar));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(CONTACT_ITEM_LIST, adapter.getContactItemList());
        outState.putSerializable(CONTACT_ITEM_LIST_FULL, adapter.getContactItemListFull());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data != null && resultCode == RESULT_OK) {
            if (REQUEST_CODE_FOR_ADD == requestCode) {
                Contact contact = (Contact) data.getSerializableExtra(AddContactActivity.EXTRA_CONTACT_FOR_ADD);
                if (contact != null) {
                    adapter.addContact(contact);
                    addContactToDatabase(contact);
                }
            } else if (REQUEST_CODE_FOR_EDIT == requestCode) {
                Contact newContact = (Contact) data.getSerializableExtra(EditContactActivity.EXTRA_NEW_CONTACT);
                Contact oldContact = (Contact) data.getSerializableExtra(EditContactActivity.EXTRA_OLD_CONTACT);
                if (newContact != null) {
                    adapter.editContact(newContact);
                    updateContactToDatabase(newContact);
                } else if (oldContact != null) {
                    adapter.removeContact(oldContact);
                    deleteContactFromDatabase(oldContact);
                }
            }
            visibleSwitcher(adapter.getFullItemCount());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void addContactToDatabase(Contact contact) {
        contactDatabase.getContactDao().insertContact(
                new ContactEntity.Builder(contact.getId())
                        .setType(contact.getType().name())
                        .setName(contact.getName())
                        .setData(contact.getData())
                        .setImageID(contact.getImageID())
                        .build()
        );
    }

    private void updateContactToDatabase(Contact contact) {
        contactDatabase.getContactDao().updateContact(
                new ContactEntity.Builder(contact.getId())
                        .setType(contact.getType().name())
                        .setName(contact.getName())
                        .setData(contact.getData())
                        .setImageID(contact.getImageID())
                        .build()
        );
    }

    private void deleteContactFromDatabase(Contact contact) {
        contactDatabase.getContactDao().deleteContact(new ContactEntity.Builder(contact.getId()).build());
    }

    private void visibleSwitcher(int fullItemCount) {
        if (fullItemCount > 0) {
            contactsRecyclerView.setVisibility(View.VISIBLE);
        } else {
            contactsRecyclerView.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        contactDatabase.close();
    }
}

