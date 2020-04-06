package by.popkov.homework4

import java.io.Serializable

interface Contact : Serializable {
    companion object {
        val IMAGE_ID_EMAIL: Int
            get() = R.drawable.ic_contact_mail_pink_60dp
        val IMAGE_ID_PHONE: Int
            get() = R.drawable.ic_contact_phone_blue_60dp
    }

    val name: String
    val data: String
    val imageID: Int
}
