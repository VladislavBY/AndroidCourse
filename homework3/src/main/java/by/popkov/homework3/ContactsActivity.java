package by.popkov.homework3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class ContactsActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButtonAddContact;
    private int requestCode = 7777;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        floatingActionButtonAddContact = findViewById(R.id.floatingActionButtonAddContact);
        floatingActionButtonAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(AddContact.newIntent(ContactsActivity.this), requestCode);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (this.requestCode == requestCode && resultCode == RESULT_OK && data != null) {
            Contact contact = (Contact) data.getSerializableExtra("Extra");
            if (contact != null) {
                String name = contact.getName() + contact.getData();
                Toast.makeText(ContactsActivity.this, name, Toast.LENGTH_LONG).show();
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}

