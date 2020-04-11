package by.popkov.homework4

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ContactsActivity : AppCompatActivity() {
    private lateinit var contactsRecyclerView: RecyclerView
    private lateinit var adapter: ContactListAdapter

    private val requestCodeForAdd: Int = 7777
    private val requestCodeForEdit: Int = 1111

    companion object{
        const val ADAPTER = "adapter"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)
        initContactsRecyclerView(savedInstanceState)
        setListeners()
        setToolBar()

    }

    private fun initContactsRecyclerView(savedInstanceState: Bundle?) {
        contactsRecyclerView = findViewById(R.id.recyclerViewContacts)
        if (savedInstanceState != null) contactsRecyclerView.adapter = savedInstanceState
                .getParcelable<ContactListAdapter>(ADAPTER)
        else contactsRecyclerView.adapter = ContactListAdapter()
        contactsRecyclerView.layoutManager = GridLayoutManager((this@ContactsActivity),
                contactsRecyclerView.tag.toString().toInt(), RecyclerView.VERTICAL, false)
        adapter = contactsRecyclerView.adapter as ContactListAdapter
        visibleSwitcher(adapter.getFullItemCount())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(ADAPTER, adapter)
    }

    private fun setToolBar() {
        setSupportActionBar(findViewById(R.id.toolBar))
    }

    private fun setListeners() {
        val textViewHead: TextView = findViewById(R.id.textViewHead)
        val searchView: SearchView = findViewById(R.id.searchView)
        searchView.setOnSearchClickListener { textViewHead.visibility = View.INVISIBLE }
        searchView.setOnCloseListener(object : SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                textViewHead.visibility = View.VISIBLE
                return false
            }
        })
        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
        val floatingActionButtonAddContact: FloatingActionButton = findViewById(R.id.floatingActionButtonAddContact)
        floatingActionButtonAddContact.setOnClickListener {
            startActivityForResult(AddContactActivity
                    .newIntent(this@ContactsActivity), requestCodeForAdd)
        }
        adapter.setItemListenerWithData(object : ContactListAdapter.ItemListenerWithData {
            override fun onClick(oldContact: Contact, positionFullList: Int, positionList: Int) {
                startActivityForResult(EditContactActivity.newIntent(this@ContactsActivity,
                        oldContact, positionFullList, positionList), requestCodeForEdit)
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data != null) {
            if (requestCodeForAdd == requestCode && resultCode == Activity.RESULT_OK) {
                val contact: Contact? = data.getSerializableExtra(AddContactActivity.PUT_EXTRA) as Contact
                if (contact != null) {
                    adapter.addContact(contact)
                }
            } else if (requestCodeForEdit == requestCode && resultCode == Activity.RESULT_OK) {
                val newContact: Contact? = data.getSerializableExtra(EditContactActivity.EXTRA_NEW_CONTACT) as? Contact
                val fullListPosition = data.getIntExtra(EditContactActivity.EXTRA_FULL_LIST_POS, -202)
                val listPosition = data.getIntExtra(EditContactActivity.EXTRA_LIST_POS, -204)
                if (newContact != null) {
                    adapter.editContact(newContact, fullListPosition, listPosition)
                } else {
                    adapter.removeContact(fullListPosition, listPosition)
                }
            }
            visibleSwitcher(adapter.getFullItemCount())
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun visibleSwitcher(fullItemCount: Int) {
        if (fullItemCount > 0) contactsRecyclerView.visibility = View.VISIBLE
        else contactsRecyclerView.visibility = View.INVISIBLE

    }
}