package by.popkov.homework3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddContact extends AppCompatActivity {
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
