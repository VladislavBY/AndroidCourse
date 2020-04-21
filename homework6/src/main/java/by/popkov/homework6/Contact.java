package by.popkov.homework6;

import java.io.Serializable;
import java.util.UUID;

enum Contact implements Serializable {
    EMAIL, PHONE;

    private String name;
    private String data;
    private String id = UUID.randomUUID().toString();

    public String getName() {
        return name;
    }

    public Contact setName(String name) {
        this.name = name;
        return this;
    }

    public String getData() {
        return data;
    }

    public Contact setData(String data) {
        this.data = data;
        return this;
    }

    public int getImageID() {
        if (this == EMAIL) {
            return R.drawable.ic_contact_mail_pink_60dp;
        } else if (this == PHONE) {
            return R.drawable.ic_contact_phone_blue_60dp;
        } else return 0;
    }
}
