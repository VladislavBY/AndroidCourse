package by.popkov.homework4

import java.io.Serializable

class ContactPhone(override val name: String, override val data: String, override val imageID: Int)
    : Contact, Serializable