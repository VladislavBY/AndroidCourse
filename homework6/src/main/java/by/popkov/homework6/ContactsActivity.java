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
    public static final String ADAPTER = "adapter";

    private MyDatabase myDatabase;
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
        myDatabase = Room.databaseBuilder(this, MyDatabase.class, "MyDatabase")
                .allowMainThreadQueries()
                .build();
    }


    @Override
    protected void onPause() {
        super.onPause();
        for (Contact contact : adapter.getContactItemListFull()) {
            ContactEntity contactEntity = new ContactEntity();
            contactEntity.id = contact.getId();
            contactEntity.name = contact.getName();
            contactEntity.data = contact.getData();
            contactEntity.imageID = contact.getImageID();
            contactEntity.type = contact.name();
            myDatabase.getContactDao().insertContact(contactEntity);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myDatabase.close();
    }

    private void initContactsRecyclerView(Bundle savedInstanceState) {
        contactsRecyclerView = findViewById(R.id.recyclerViewContacts);
        if (savedInstanceState != null) {
            contactsRecyclerView.setAdapter((ContactListAdapter) savedInstanceState
                    .getParcelable(ADAPTER));
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
        ContactEntity[] contactEntities = myDatabase.getContactDao().loadAddContacts();
        ArrayList<Contact> result = new ArrayList<>();
        for (ContactEntity contactEntity : contactEntities) {
            Contact contact;
            if (contactEntity.type.equals(Contact.EMAIL.name())) {
                contact = Contact.EMAIL;
            } else {
                contact = Contact.PHONE;
            }
            contact.setId(contactEntity.id);
            contact.setData(contactEntity.data);
            contact.setName(contactEntity.name);
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
            public void onClick(Contact oldContact, int positionFullList, int positionList) {
                startActivityForResult(EditContactActivity
                        .newIntent(
                                ContactsActivity.this,
                                oldContact,
                                positionFullList,
                                positionList
                        ), REQUEST_CODE_FOR_EDIT
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
        outState.putParcelable(ADAPTER, adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data != null && resultCode == RESULT_OK) {
            if (REQUEST_CODE_FOR_ADD == requestCode) {
                Contact contact = (Contact) data.getSerializableExtra(AddContactActivity.PUT_EXTRA);
                if (contact != null) {
                    adapter.addContact(contact);
                }
            } else if (REQUEST_CODE_FOR_EDIT == requestCode) {
                Contact newContact = (Contact) data.getSerializableExtra(EditContactActivity.EXTRA_NEW_CONTACT);
                int fullListPosition = data.getIntExtra(EditContactActivity.EXTRA_FULL_LIST_POS, -202);
                int listPosition = data.getIntExtra(EditContactActivity.EXTRA_LIST_POS, -204);
                if (newContact != null) {
                    adapter.editContact(newContact, fullListPosition, listPosition);
                } else {
                    adapter.removeContact(fullListPosition, listPosition);
                }
            }
            visibleSwitcher(adapter.getFullItemCount());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void visibleSwitcher(int fullItemCount) {
        if (fullItemCount > 0) {
            contactsRecyclerView.setVisibility(View.VISIBLE);
        } else {
            contactsRecyclerView.setVisibility(View.INVISIBLE);
        }

    }
}

