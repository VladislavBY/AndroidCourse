package by.popkov.homework3;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class ContactsActivity extends AppCompatActivity {

    private RecyclerView contactsRecyclerView;
    private ContactListAdapter adapter;

    private int requestCodeForAdd = 7777;
    private int requestCodeForEdit = 1111;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        initContactsRecyclerView();
        setListeners();
    }

    private void initContactsRecyclerView() {
        contactsRecyclerView = findViewById(R.id.recyclerViewContacts);
        contactsRecyclerView.setAdapter(new ContactListAdapter());
        contactsRecyclerView.setLayoutManager(new LinearLayoutManager
                (this, RecyclerView.VERTICAL, false));
        adapter = (ContactListAdapter) contactsRecyclerView.getAdapter();
        if (adapter != null) adapter.setContactsActivity(this);
    }

    private void setListeners() {
        SearchView searchView = findViewById(R.id.searchView);
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
        FloatingActionButton floatingActionButtonAddContact = findViewById(R.id.floatingActionButtonAddContact);
        floatingActionButtonAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(AddContactActivity
                        .newIntent(ContactsActivity.this), requestCodeForAdd);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data != null) {
            if (this.requestCodeForAdd == requestCode && resultCode == RESULT_OK) {
                Contact contact = (Contact) data.getSerializableExtra("Extra");
                if (contact != null) {
                    adapter.addContact(contact);
                }
            } else if (this.requestCodeForEdit == requestCode && resultCode == RESULT_OK) {
                Contact newContact = (Contact) data.getSerializableExtra("newContact");
                int fullListPosition = data.getIntExtra("fullListPosition", -202);
                if (newContact != null) {
                    adapter.editContact(newContact, fullListPosition);
                } else {
                    adapter.removeContact(fullListPosition);
                }
            }
            visibleSwitcher(adapter.getFullItemCount());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void visibleSwitcher(int fullItemCount) {
        if (fullItemCount > 0) {
            contactsRecyclerView.setVisibility(View.VISIBLE);
        } else
            contactsRecyclerView.setVisibility(View.INVISIBLE);
    }

    void startEditContact(Contact contact, int fullListPosition) {
        startActivityForResult(EditContactActivity
                .newIntent(ContactsActivity.this, contact, fullListPosition), requestCodeForEdit);
    }
}

