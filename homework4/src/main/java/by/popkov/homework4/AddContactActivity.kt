package by.popkov.homework4

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class AddContactActivity : AppCompatActivity() {
    private lateinit var contact: Contact
    private lateinit var buttonAdd: ImageButton
    private lateinit var radioButtonPhoneNumber: RadioButton
    private lateinit var radioButtonEmail: RadioButton
    private lateinit var editTextName: EditText
    private lateinit var editTextPhoneNumberOrEmail: EditText

    companion object {
        const val PUT_EXTRA: String = "Extra"
        fun newIntent(context: Context): Intent = Intent(context, AddContactActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)
        viewsInit()
        setListeners()
        setToolBar()
    }

    private fun viewsInit() {
        buttonAdd = findViewById(R.id.buttonAdd)
        radioButtonPhoneNumber = findViewById(R.id.radioButtonPhoneNumber)
        radioButtonEmail = findViewById(R.id.radioButtonEmail)
        editTextName = findViewById(R.id.editTextName)
        editTextPhoneNumberOrEmail = findViewById(R.id.editTextPhoneNumberOrEmail)
    }

    private fun setListeners() {
        radioButtonEmail.setOnClickListener {
            editTextPhoneNumberOrEmail.setHint(R.string.email)
            editTextPhoneNumberOrEmail.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        }
        radioButtonPhoneNumber.setOnClickListener {
            editTextPhoneNumberOrEmail.setHint(R.string.phone_number)
            editTextPhoneNumberOrEmail.inputType = InputType.TYPE_CLASS_PHONE
        }
        buttonAdd.setOnClickListener {
            val name = editTextName.text.toString()
            val phoneNumberOrEmail = editTextPhoneNumberOrEmail.text.toString()
            if (name.trim().isNotEmpty() && phoneNumberOrEmail.trim().isNotEmpty()) {
                if (radioButtonEmail.isChecked) contact =
                        ContactEmail(name, phoneNumberOrEmail, Contact.IMAGE_ID_EMAIL)
                else if (radioButtonPhoneNumber.isChecked) contact =
                        ContactPhone(name, phoneNumberOrEmail, Contact.IMAGE_ID_PHONE)
                val result = Intent(this@AddContactActivity, ContactsActivity::class.java)
                result.putExtra(PUT_EXTRA, contact)
                setResult(Activity.RESULT_OK, result)
                finish()
            } else {
                val root: View = findViewById(R.id.rootLayout)
                Snackbar.make(root, R.string.input_data_please, Snackbar.LENGTH_LONG).show()
            }
        }

    }

    private fun setToolBar() {
        setSupportActionBar(findViewById(R.id.toolBar))
        val supportActionBar = supportActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
