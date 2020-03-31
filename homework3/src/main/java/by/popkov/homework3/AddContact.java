package by.popkov.homework3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddContact extends AppCompatActivity {
    private Button buttonBack;
    private Button buttonAdd;

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
        setResult(RESULT_OK);
    }
}
