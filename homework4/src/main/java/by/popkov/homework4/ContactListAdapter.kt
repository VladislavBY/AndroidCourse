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
    private val contactItemList: ArrayList<Contact> = ArrayList()
    private val contactItemListFull: ArrayList<Contact> = ArrayList()

    fun addContact(contact: Contact) {
        contactItemList.add(contact)
        contactItemListFull.add(contact)
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
                val filterPattern: String = constraint.toString().toLowerCase().trim()
                for (contact in contactItemListFull) {
                    if (contact.name.toLowerCase().contains(filterPattern)) {
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


    interface ItemListenerWithData {
        fun onClick(oldContact: Contact, positionFullList: Int, positionList: Int)

    }

    private lateinit var itemListenerWithData: ItemListenerWithData

    constructor(parcel: Parcel) : this() {
        val listFullNames = arrayOfNulls<String>(0)
        parcel.readStringArray(listFullNames)
        val listFullData = arrayOfNulls<String>(0)
        parcel.readStringArray(listFullData)
        val listFullImages = IntArray(0)
        parcel.readIntArray(listFullImages)

        val listNames = arrayOfNulls<String>(0)
        parcel.readStringArray(listNames)
        val listData = arrayOfNulls<String>(0)
        parcel.readStringArray(listData)
        val listImages = IntArray(0)
        parcel.readIntArray(listImages)

        for (i in listFullNames.indices){
            if (listFullImages[i] == Contact.IMAGE_ID_PHONE){
                contactItemListFull.add(ContactPhone(listFullNames[i]!!, listFullData[i]!!, Contact.IMAGE_ID_PHONE))
            }else if (listFullImages[i] == Contact.IMAGE_ID_EMAIL){
                contactItemListFull.add(ContactEmail(listFullNames[i]!!, listFullData[i]!!, Contact.IMAGE_ID_EMAIL))
            }
        }
        for (i in listNames.indices) {
            if (listImages[i] == Contact.IMAGE_ID_PHONE) {
                contactItemList.add(ContactPhone(listNames[i]!!, listData[i]!!, Contact.IMAGE_ID_PHONE))
            } else if (listImages[i] == Contact.IMAGE_ID_EMAIL) {
                contactItemList.add(ContactEmail(listNames[i]!!, listData[i]!!, Contact.IMAGE_ID_EMAIL))
            }
        }
    }

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

        fun bindData(contact: Contact) {
            contactImageView.setImageResource(contact.imageID)
            textViewName.text = contact.name
            textViewData.text = contact.data
        }

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        val listFullNames = arrayOfNulls<String>(contactItemListFull.size)
        for (i in listFullNames.indices) {
            listFullNames[i] = contactItemList[i].name
        }
        val listFullData = arrayOfNulls<String>(contactItemListFull.size)
        for (i in listFullData.indices) {
            listFullData[i] = contactItemListFull[i].data
        }
        val listFullImages = IntArray(contactItemListFull.size)
        for (i in listFullImages.indices) {
            listFullImages[i] = contactItemListFull[i].imageID
        }

        val listNames = arrayOfNulls<String>(contactItemList.size)
        for (i in listNames.indices) {
            listNames[i] = contactItemList[i].name
        }
        val listData = arrayOfNulls<String>(contactItemList.size)
        for (i in listData.indices) {
            listData[i] = contactItemList[i].data
        }
        val listImages = IntArray(contactItemList.size)
        for (i in listImages.indices) {
            listImages[i] = contactItemList[i].imageID
        }

        parcel.writeStringArray(listFullNames)
        parcel.writeStringArray(listFullData)
        parcel.writeIntArray(listFullImages)
        parcel.writeStringArray(listNames)
        parcel.writeStringArray(listData)
        parcel.writeIntArray(listImages)
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
}