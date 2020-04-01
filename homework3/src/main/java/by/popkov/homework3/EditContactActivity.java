package by.popkov.homework3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;



public class EditContactActivity extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextPhoneNumberOrEmail;
    private Button buttonEdit;
    private Button buttonRemove;
    private ImageButton buttonBack;
    private Intent comeIntent;
    private Contact oldContact;
    private int adapterPosition;

    public static Intent newIntent(Context context, Contact contact, int adapterPosition) {
        Intent intent = new Intent(context, EditContactActivity.class);
        intent.putExtra("contact", contact);
        intent.putExtra("adapterPosition", adapterPosition);
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
                intent.putExtra("adapterPosition", adapterPosition);
                String name = editTextName.getText().toString().trim();
                String data = editTextPhoneNumberOrEmail.getText().toString().trim();
                Contact newContact;
                if (data.contains("@")) {
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
        adapterPosition = comeIntent.getIntExtra("adapterPosition", -404);
    }
}
