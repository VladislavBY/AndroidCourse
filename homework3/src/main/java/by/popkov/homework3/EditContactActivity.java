package by.popkov.homework3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class EditContactActivity extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextPhoneNumberOrEmail;
    private Button buttonEdit;
    private Button buttonRemove;

    private Contact oldContact;
    private int fullListPosition;
    private int listPosition;

    public static Intent newIntent(Context context, Contact contact, int fullListPosition, int listPosition) {
        Intent intent = new Intent(context, EditContactActivity.class);
        intent.putExtra("contact", contact);
        intent.putExtra("fullListPosition", fullListPosition);
        intent.putExtra("listPosition", listPosition);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        init();
        setDataInFields();
        setListeners();
        setToolBar();
    }

    private void setToolBar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolBar));
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setListeners() {
        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditContactActivity.this, ContactsActivity.class);
                intent.putExtra("fullListPosition", fullListPosition);
                intent.putExtra("listPosition", listPosition);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditContactActivity.this, ContactsActivity.class);
                intent.putExtra("fullListPosition", fullListPosition);
                intent.putExtra("listPosition", listPosition);
                String name = editTextName.getText().toString().trim();
                String data = editTextPhoneNumberOrEmail.getText().toString().trim();
                Contact newContact;
                if (oldContact instanceof ContactEmail) {
                    newContact = new ContactEmail(name, data, Contact.IMAGE_ID_EMAIL);
                } else {
                    newContact = new ContactPhone(name, data, Contact.IMAGE_ID_PHONE);
                }
                intent.putExtra("newContact", newContact);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void setDataInFields() {
        if (oldContact != null) {
            if (oldContact instanceof ContactEmail) {
                editTextPhoneNumberOrEmail.setHint(R.string.email);
                editTextPhoneNumberOrEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            } else {
                editTextPhoneNumberOrEmail.setHint(R.string.phone_number);
                editTextPhoneNumberOrEmail.setInputType(InputType.TYPE_CLASS_PHONE);
            }
            editTextName.setText(oldContact.getName());
            editTextPhoneNumberOrEmail.setText(oldContact.getData());
        }


    }

    private void init() {
        editTextName = findViewById(R.id.editTextName);
        editTextPhoneNumberOrEmail = findViewById(R.id.editTextPhoneNumberOrEmail);
        buttonEdit = findViewById(R.id.buttonEdit);
        buttonRemove = findViewById(R.id.buttonRemove);
        Intent comeIntent = getIntent();
        oldContact = (Contact) comeIntent.getSerializableExtra("contact");
        fullListPosition = comeIntent.getIntExtra("fullListPosition", -404);
        listPosition = comeIntent.getIntExtra("listPosition", -777);
    }
}
