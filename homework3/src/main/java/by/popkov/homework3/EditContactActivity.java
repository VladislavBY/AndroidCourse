package by.popkov.homework3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

public class EditContactActivity extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextPhoneNumberOrEmail;
    private Button buttonEdit;
    private Button buttonRemove;
    private ImageButton buttonBack;
    private Intent comeIntent;
    private Contact oldContact;

    public static Intent newIntent(Context context, Contact contact) {
        Intent intent = new Intent(context, AddContactActivity.class);
        intent.putExtra("contact", contact);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        init();
        setDataInFields();

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditContactActivity.this, ContactsActivity.class);
                intent.putExtra("oldContact", oldContact);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditContactActivity.this, ContactsActivity.class);
                intent.putExtra("oldContact", oldContact);
                String name = editTextName.getText().toString().trim();
                String data = editTextPhoneNumberOrEmail.getText().toString().trim();
                Contact newContact;
                if (data.contains("@")) {
                    newContact = new ContactEmail(name, data, R.drawable.ic_contact_mail_pink_60dp);
                } else {
                    newContact = new ContactPhone(name, data, R.drawable.ic_contact_phone_blue_60dp);
                }
                intent.putExtra("newContact", newContact);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void setDataInFields() {
        if (oldContact != null) {
            editTextName.setText(oldContact.getName());
            editTextPhoneNumberOrEmail.setText(oldContact.getData());
        }

    }

    private void init() {
        comeIntent = getIntent();
        editTextName = findViewById(R.id.editTextName);
        editTextPhoneNumberOrEmail = findViewById(R.id.editTextPhoneNumberOrEmail);
        buttonEdit = findViewById(R.id.buttonEdit);
        buttonRemove = findViewById(R.id.buttonRemove);
        buttonBack = findViewById(R.id.buttonBack);
        oldContact = (Contact) comeIntent.getSerializableExtra("contact");
    }
}
