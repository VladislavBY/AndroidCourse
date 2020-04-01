package by.popkov.homework3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddContact extends AppCompatActivity {
    private Contact contact;

    private int imageIDEmail = R.drawable.ic_contact_mail_pink_60dp;
    private int imageIDPhone = R.drawable.ic_contact_phone_blue_60dp;

    private ImageButton buttonBack;
    private ImageButton buttonAdd;

    private RadioButton radioButtonPhoneNumber;
    private RadioButton radioButtonEmail;

    private EditText editTextName;
    private EditText editTextPhoneNumberOrEmail;


    public static Intent newIntent(Context context) {
        return new Intent(context, AddContact.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        viewsInit();
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String phoneNumberOrEmail = editTextPhoneNumberOrEmail.getText().toString();
                if (!name.trim().equals("") && !phoneNumberOrEmail.equals("")) {
                    if (radioButtonEmail.isChecked()) contact =
                            new ContactEmail(name, phoneNumberOrEmail, imageIDEmail);
                    else if (radioButtonPhoneNumber.isChecked()) contact =
                            new ContactPhone(name, phoneNumberOrEmail, imageIDPhone);
                }
                Intent result = new Intent(AddContact.this, ContactsActivity.class);
                if (contact != null) {
                    result.putExtra("Extra", contact);
                    setResult(RESULT_OK, result);
                    finish();
                } else
                    Toast.makeText(AddContact.this, "Input data please", Toast.LENGTH_LONG).show();
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
