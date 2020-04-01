package by.popkov.homework3;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class ContactsActivity extends AppCompatActivity {

    private RecyclerView contactsRecyclerView;
    private FloatingActionButton floatingActionButtonAddContact;

    private int requestCodeForAdd = 7777;
    private int requestCodeForEdit = 1111;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        contactsRecyclerView = findViewById(R.id.recyclerViewContacts);
        contactsRecyclerView.setAdapter(new ContactListAdapter());
        contactsRecyclerView.setLayoutManager(new LinearLayoutManager
                (this, RecyclerView.VERTICAL, false));


        floatingActionButtonAddContact = findViewById(R.id.floatingActionButtonAddContact);
        floatingActionButtonAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(AddContactActivity.newIntent(ContactsActivity.this), requestCodeForAdd);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (this.requestCodeForAdd == requestCode && resultCode == RESULT_OK && data != null) {
            Contact contact = (Contact) data.getSerializableExtra("Extra");
            if (contact != null) {
                ContactListAdapter adapter = (ContactListAdapter) contactsRecyclerView.getAdapter();
                if (adapter != null) {
                    adapter.setContext(ContactsActivity.this);
                    adapter.addContact(contact);
                    if (adapter.getItemCount() > 0) contactsRecyclerView
                            .setBackground(getDrawable(R.drawable.white_background));
                }
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    void startEditContact(Contact contact) {
        startActivityForResult(EditContactActivity
                .newIntent(ContactsActivity.this, contact), requestCodeForEdit);
    }
}

