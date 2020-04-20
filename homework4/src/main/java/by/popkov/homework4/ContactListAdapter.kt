package by.popkov.homework4

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class ContactListAdapter() :
        RecyclerView.Adapter<ContactListAdapter.ItemViewHolder>(), Filterable, Parcelable {
    private var contactItemList: ArrayList<Contact> = ArrayList()
    private var contactItemListFull: ArrayList<Contact> = ArrayList()

    fun addContact(contact: Contact) {
        contactItemListFull.add(contact)
        contactItemList.add(contact)
        notifyDataSetChanged()
    }

    fun removeContact(fullListPosition: Int, listPosition: Int) {
        contactItemListFull.removeAt(fullListPosition)
        contactItemList.removeAt(listPosition)
        notifyDataSetChanged()
    }

    fun editContact(newContact: Contact, fullListPosition: Int, listPosition: Int) {
        contactItemListFull.removeAt(fullListPosition)
        contactItemListFull.add(fullListPosition, newContact)
        contactItemList.removeAt(listPosition)
        contactItemList.add(listPosition, newContact)
        notifyDataSetChanged()
    }

    fun getFullItemCount(): Int = contactItemListFull.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflate = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_contact, parent, false)
        return ItemViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindData(contactItemList[position])
    }

    override fun getItemCount(): Int = contactItemList.size

    private val contactFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredContactItemList: ArrayList<Contact> = ArrayList()
            if (constraint == null || constraint.isEmpty()) {
                filteredContactItemList.addAll(contactItemListFull)
            } else {
                val filterPattern: String = constraint.toString().toLowerCase(Locale.ROOT).trim()
                for (contact in contactItemListFull) {
                    if (contact.name.toLowerCase(Locale.ROOT).contains(filterPattern)) {
                        filteredContactItemList.add(contact)
                    }
                }
            }
            val filterResults = FilterResults()
            filterResults.values = filteredContactItemList
            return filterResults
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            contactItemList.clear()
            if (results != null) {
                contactItemList.addAll(results.values as Collection<Contact>)
                notifyDataSetChanged()
            }
        }
    }

    override fun getFilter(): Filter = contactFilter

    private constructor(parcel: Parcel) : this() {
        contactItemListFull = parcel.readSerializable() as ArrayList<Contact>
        contactItemList = parcel.readSerializable() as ArrayList<Contact>
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
       parcel.writeSerializable(contactItemListFull)
       parcel.writeSerializable(contactItemList)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ContactListAdapter> {
        override fun createFromParcel(parcel: Parcel): ContactListAdapter {
            return ContactListAdapter(parcel)
        }

        override fun newArray(size: Int): Array<ContactListAdapter?> {
            return arrayOfNulls(size)
        }
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
            itemView.setOnClickListener {
                val clickedContact: Contact = contactItemList[adapterPosition]
                itemListenerWithData.onClick(clickedContact,
                        contactItemListFull.indexOf(clickedContact),
                        contactItemList.indexOf(clickedContact))
            }
        }

        fun bindData(contact: Contact) {
            contactImageView.setImageResource(contact.imageID)
            textViewName.text = contact.name
            textViewData.text = contact.data
        }

    }
}