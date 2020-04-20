package by.popkov.homework4

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class EditContactActivity : AppCompatActivity() {
    private lateinit var editTextName: EditText
    private lateinit var editTextPhoneNumberOrEmail: EditText
    private lateinit var buttonEdit: Button
    private lateinit var buttonRemove: Button

    private lateinit var oldContact: Contact
    private var fullListPosition: Int = -100
    private var listPosition: Int = -100

    companion object {
        const val EXTRA_NEW_CONTACT = "newContact"
        const val EXTRA_OLD_CONTACT = "contact"
        const val EXTRA_FULL_LIST_POS = "fullListPosition"
        const val EXTRA_LIST_POS = "listPosition"

        fun newIntent(context: Context, contact: Contact, fullListPosition: Int, listPosition: Int): Intent {
            val intent = Intent(context, EditContactActivity::class.java)
            intent.putExtra(EXTRA_OLD_CONTACT, contact)
            intent.putExtra(EXTRA_FULL_LIST_POS, fullListPosition)
            intent.putExtra(EXTRA_LIST_POS, listPosition)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_contact)
        init()
        setDataInFields()
        setListeners()
        setToolBar()
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

    private fun setListeners() {
        buttonRemove.setOnClickListener {
            val intent = Intent(this@EditContactActivity, ContactsActivity::class.java)
            intent.putExtra(EXTRA_FULL_LIST_POS, fullListPosition)
            intent.putExtra(EXTRA_LIST_POS, listPosition)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        buttonEdit.setOnClickListener {
            val intent = Intent(this@EditContactActivity, ContactsActivity::class.java)
            intent.putExtra(EXTRA_FULL_LIST_POS, fullListPosition)
            intent.putExtra(EXTRA_LIST_POS, listPosition)
            val name = editTextName.text.toString().trim()
            val data = editTextPhoneNumberOrEmail.text.toString().trim()
            val newContact: Contact
            newContact = if (oldContact is ContactEmail) ContactEmail(name, data, Contact.IMAGE_ID_EMAIL)
            else ContactPhone(name, data, Contact.IMAGE_ID_PHONE)
            intent.putExtra(EXTRA_NEW_CONTACT, newContact)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    private fun setDataInFields() {
        if (oldContact is ContactEmail) {
            editTextPhoneNumberOrEmail.setHint(R.string.email)
            editTextPhoneNumberOrEmail.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        } else {
            editTextPhoneNumberOrEmail.setHint(R.string.phone_number)
            editTextPhoneNumberOrEmail.inputType = InputType.TYPE_CLASS_PHONE
        }
        editTextName.setText(oldContact.name)
        editTextPhoneNumberOrEmail.setText(oldContact.data)
    }

    private fun init() {
        editTextName = findViewById(R.id.editTextName)
        editTextPhoneNumberOrEmail = findViewById(R.id.editTextPhoneNumberOrEmail)
        buttonEdit = findViewById(R.id.buttonEdit)
        buttonRemove = findViewById(R.id.buttonRemove)
        val comeIntent = intent
        oldContact = comeIntent.getSerializableExtra(EXTRA_OLD_CONTACT) as Contact
        fullListPosition = comeIntent.getIntExtra(EXTRA_FULL_LIST_POS, -404)
        listPosition = comeIntent.getIntExtra(EXTRA_LIST_POS, -777)
    }
}