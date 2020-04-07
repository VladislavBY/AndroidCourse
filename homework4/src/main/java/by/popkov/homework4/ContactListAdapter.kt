package by.popkov.homework4

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class ContactListAdapter : RecyclerView.Adapter<ContactListAdapter.ItemViewHolder>() {
    val contactItemList: List<Contact> = ArrayList()
    val contactItemListFull: List<Contact> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    interface ItemListenerWithData {
        fun onClick(oldContact: Contact, positionFullList: Int, positionList: Int)
    }

    private lateinit var itemListenerWithData: ItemListenerWithData

    fun setItemListenerWithData(itemListenerWithData: ItemListenerWithData) {
        this.itemListenerWithData = itemListenerWithData
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val contactImageView: ImageView = itemView.findViewById(R.id.contactImageView)
        private val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        private val textViewData: TextView = itemView.findViewById(R.id.textViewData)

        init {
            itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    val clickedContact: Contact = contactItemList[adapterPosition]
                    itemListenerWithData.onClick(clickedContact,
                            contactItemListFull.indexOf(clickedContact),
                            contactItemList.indexOf(clickedContact))
                }
            })
        }
        fun bindData(contact: Contact){
            contactImageView.setImageResource(contact.imageID)
            textViewName.text = contact.name
            textViewData.text = contact.data
        }
    }
}