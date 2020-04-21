package by.popkov.homework6;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddContactActivity extends AppCompatActivity {
    public static final String PUT_EXTRA = "Extra";
    private Contact contact;

    private ImageButton buttonAdd;
    private RadioButton radioButtonPhoneNumber;
    private RadioButton radioButtonEmail;
    private EditText editTextName;
    private EditText editTextPhoneNumberOrEmail;


    public static Intent newIntent(Context context) {
        return new Intent(context, AddContactActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        viewsInit();
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
        radioButtonEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextPhoneNumberOrEmail.setHint(R.string.email);
                editTextPhoneNumberOrEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

            }
        });
        radioButtonPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextPhoneNumberOrEmail.setHint(R.string.phone_number);
                editTextPhoneNumberOrEmail.setInputType(InputType.TYPE_CLASS_PHONE);
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String phoneNumberOrEmail = editTextPhoneNumberOrEmail.getText().toString();
                if (!name.trim().isEmpty() && !phoneNumberOrEmail.trim().isEmpty()) {
                    if (radioButtonEmail.isChecked()) {
                        contact = new ContactEmail(name, phoneNumberOrEmail, Contact.IMAGE_ID_EMAIL);
                    } else if (radioButtonPhoneNumber.isChecked()) {
                        contact = new ContactPhone(name, phoneNumberOrEmail, Contact.IMAGE_ID_PHONE);
                    }
                    Intent result = new Intent(AddContactActivity.this, ContactsActivity.class);
                    if (contact != null) {
                        result.putExtra(PUT_EXTRA, contact);
                        setResult(RESULT_OK, result);
                        finish();
                    }
                } else {
                    Toast.makeText(
                            AddContactActivity.this,
                            R.string.input_data_please,
                            Toast.LENGTH_LONG
                    ).show();
                }
            }
        });
    }

    private void viewsInit() {
        buttonAdd = findViewById(R.id.buttonAdd);
        radioButtonPhoneNumber = findViewById(R.id.radioButtonPhoneNumber);
        radioButtonEmail = findViewById(R.id.radioButtonEmail);
        editTextName = findViewById(R.id.editTextName);
        editTextPhoneNumberOrEmail = findViewById(R.id.editTextPhoneNumberOrEmail);
    }
}
