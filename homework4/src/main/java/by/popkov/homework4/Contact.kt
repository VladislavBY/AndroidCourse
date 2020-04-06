package by.popkov.homework4

import java.io.Serializable

interface Contact : Serializable {
    companion object {
        const val IMAGE_ID_EMAIL: Int = R.drawable.ic_contact_mail_pink_60dp
        const val IMAGE_ID_PHONE: Int  = R.drawable.ic_contact_phone_blue_60dp
    }

    val name: String
    val data: String
    val imageID: Int
}
