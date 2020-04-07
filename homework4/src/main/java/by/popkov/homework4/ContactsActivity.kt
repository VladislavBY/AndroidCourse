package by.popkov.homework4

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ContactsActivity : AppCompatActivity() {
    private lateinit var contactsRecyclerView: RecyclerView
    private lateinit var adapter: ContactListAdapter

    private val requestCodeForAdd: Int = 7777
    private val requestCodeForEdit: Int = 1111

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)
        initContactsRecyclerView(savedInstanceState)
        setListeners()
        setToolBar()

    }

    private fun initContactsRecyclerView(savedInstanceState: Bundle?) {

    }

    private fun setToolBar() {
        setSupportActionBar(findViewById(R.id.toolBar))
    }

    private fun setListeners() {
        val textViewHead: TextView = findViewById(R.id.textViewHead)
        val searchView: SearchView = findViewById(R.id.searchView)
        searchView.setOnSearchClickListener { textViewHead.visibility = View.INVISIBLE }
        searchView.setOnCloseListener {
            textViewHead.visibility = View.VISIBLE
            false
        }
        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter.getFilter().filter(newText)
                return false
            }
        })
        val floatingActionButtonAddContact = findViewById<FloatingActionButton>(R.id.floatingActionButtonAddContact)
        floatingActionButtonAddContact.setOnClickListener {
            startActivityForResult(AddContactActivity
                    .newIntent(this@ContactsActivity), requestCodeForAdd)
        }

    }
}