package by.popkov.homework3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddContactActivity extends AppCompatActivity {
    private Contact contact;

    private ImageButton buttonBack;
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
                    if (radioButtonEmail.isChecked()) contact =
                            new ContactEmail(name, phoneNumberOrEmail, Contact.IMAGE_ID_EMAIL);
                    else if (radioButtonPhoneNumber.isChecked()) contact =
                            new ContactPhone(name, phoneNumberOrEmail, Contact.IMAGE_ID_PHONE);
                    Intent result = new Intent(AddContactActivity.this, ContactsActivity.class);
                    if (contact != null) {
                        result.putExtra("Extra", contact);
                        setResult(RESULT_OK, result);
                        finish();
                    }
                } else
                    Toast.makeText(AddContactActivity.this, R.string.input_data_please, Toast.LENGTH_LONG).show();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    private void viewsInit() {
        buttonBack = findViewById(R.id.buttonBack);
        buttonAdd = findViewById(R.id.buttonAdd);
        radioButtonPhoneNumber = findViewById(R.id.radioButtonPhoneNumber);
        radioButtonEmail = findViewById(R.id.radioButtonEmail);
        editTextName = findViewById(R.id.editTextName);
        editTextPhoneNumberOrEmail = findViewById(R.id.editTextPhoneNumberOrEmail);
    }
}
