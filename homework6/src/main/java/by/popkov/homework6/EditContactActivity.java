package by.popkov.homework6;

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
    public static final String EXTRA_NEW_CONTACT = "newContact";
    public static final String EXTRA_OLD_CONTACT = "contact";
    public static final String EXTRA_FULL_LIST_POS = "fullListPosition";
    public static final String EXTRA_LIST_POS = "listPosition";

    private EditText editTextName;
    private EditText editTextPhoneNumberOrEmail;
    private Button buttonEdit;
    private Button buttonRemove;

    private Contact oldContact;
    private int fullListPosition;
    private int listPosition;


    public static Intent newIntent(Context context, Contact contact, int fullListPosition, int listPosition) {
        Intent intent = new Intent(context, EditContactActivity.class);
        intent.putExtra(EXTRA_OLD_CONTACT, contact);
        intent.putExtra(EXTRA_FULL_LIST_POS, fullListPosition);
        intent.putExtra(EXTRA_LIST_POS, listPosition);
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
                intent.putExtra(EXTRA_FULL_LIST_POS, fullListPosition);
                intent.putExtra(EXTRA_LIST_POS, listPosition);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditContactActivity.this, ContactsActivity.class);
                intent.putExtra(EXTRA_FULL_LIST_POS, fullListPosition);
                intent.putExtra(EXTRA_LIST_POS, listPosition);
                String name = editTextName.getText().toString().trim();
                String data = editTextPhoneNumberOrEmail.getText().toString().trim();
                Contact newContact;
                if (oldContact == Contact.EMAIL) {
                    newContact = Contact.EMAIL
                            .setName(name)
                            .setData(data);
                } else {
                    newContact = Contact.PHONE
                            .setName(name)
                            .setData(data);
                }
                intent.putExtra(EXTRA_NEW_CONTACT, newContact);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void setDataInFields() {
        if (oldContact != null) {
            if (oldContact == Contact.EMAIL) {
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
        oldContact = (Contact) comeIntent.getSerializableExtra(EXTRA_OLD_CONTACT);
        fullListPosition = comeIntent.getIntExtra(EXTRA_FULL_LIST_POS, -404);
        listPosition = comeIntent.getIntExtra(EXTRA_LIST_POS, -777);
    }
}
