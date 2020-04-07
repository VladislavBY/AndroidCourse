package by.popkov.homework4

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

class AddContactActivity: AppCompatActivity() {
    companion object {
        fun newIntent(context: Context): Intent = Intent(context, AppCompatActivity::class.java)
    }
}