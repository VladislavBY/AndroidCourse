package by.popkov.homework3;

import java.io.Serializable;

interface Contact extends Serializable {
    int IMAGE_ID_EMAIL = R.drawable.ic_contact_mail_pink_60dp;
    int IMAGE_ID_PHONE = R.drawable.ic_contact_phone_blue_60dp;


    String getName();

    String getData();

    int getImageID();

    void setName(String name);

    void setData(String data);

    void setImageID(int imageID);
}
