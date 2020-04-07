package by.popkov.homework4

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

class EditContactActivity : AppCompatActivity() {
    companion object {
        fun newIntent(context: Context, contact: Contact, fullListPosition: Int, listPosition: Int): Intent {
            val intent = Intent(context, AppCompatActivity::class.java)
            intent.putExtra("contact", contact)
            intent.putExtra("fullListPosition", fullListPosition)
            intent.putExtra("listPosition", listPosition)
            return intent
        }
    }
}