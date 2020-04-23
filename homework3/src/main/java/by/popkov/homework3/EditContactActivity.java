package by.popkov.homework3;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class EditContactActivity extends AppCompatActivity {
    static final String EXTRA_NEW_CONTACT = "newContact";
    static final String EXTRA_OLD_CONTACT = "contact";

    private EditText editTextName;
    private EditText editTextPhoneNumberOrEmail;
    private Button buttonEdit;
    private Button buttonRemove;
    private Dialog dialog;

    private Contact oldContact;

    public static Intent newIntent(Context context, Contact contact) {
        Intent intent = new Intent(context, EditContactActivity.class);
        intent.putExtra(EXTRA_OLD_CONTACT, contact);
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
        setDialog();
    }

    private void init() {
        editTextName = findViewById(R.id.editTextName);
        editTextPhoneNumberOrEmail = findViewById(R.id.editTextPhoneNumberOrEmail);
        buttonEdit = findViewById(R.id.buttonEdit);
        buttonRemove = findViewById(R.id.buttonRemove);
        Intent comeIntent = getIntent();
        oldContact = (Contact) comeIntent.getSerializableExtra(EXTRA_OLD_CONTACT);
    }

    private void setDataInFields() {
        if (oldContact != null) {
            if (oldContact.getType() == Contact.Type.EMAIL) {
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

    private void setListeners() {
        setButtonRemoveListener();
        setButtonEditListener();
    }

    private void setButtonRemoveListener() {
        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null) {
                    dialog.show();
                }
            }
        });
    }

    private void setButtonEditListener() {
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditContactActivity.this, ContactsActivity.class);
                intent.putExtra(EXTRA_OLD_CONTACT, oldContact);
                String name = editTextName.getText().toString().trim();
                String data = editTextPhoneNumberOrEmail.getText().toString().trim();
                Contact newContact;
                if (oldContact.getType() == Contact.Type.EMAIL) {
                    newContact = new Contact(Contact.Type.EMAIL, name, data);
                } else {
                    newContact = new Contact(Contact.Type.PHONE, name, data);
                }
                newContact.setId(oldContact.getId());
                intent.putExtra(EXTRA_NEW_CONTACT, newContact);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
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

    private void setDialog() {
        dialog = new AlertDialog.Builder(this)
                .setPositiveButton(R.string.edit_contact_dialog_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(EditContactActivity.this, ContactsActivity.class);
                        intent.putExtra(EXTRA_OLD_CONTACT, oldContact);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                })
                .setNegativeButton(R.string.edit_contact_dialog_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setMessage(R.string.edit_contact_dialog_massage)
                .create();
    }
}
