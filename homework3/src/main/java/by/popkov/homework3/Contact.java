package by.popkov.homework3;

import java.io.Serializable;
import java.util.UUID;

class Contact implements Serializable {
    enum Type {
        EMAIL, PHONE
    }

    final private Type type;
    final private String name;
    final private String data;
    private String id = UUID.randomUUID().toString();

    Contact(Type type, String name, String data) {
        this.type = type;
        this.name = name;
        this.data = data;
    }

    Type getType() {
        return type;
    }

    String getId() {
        return id;
    }

    void setId(String id) {
        this.id = id;
    }

    String getName() {
        return name;
    }

    String getData() {
        return data;
    }

    int getImageID() {
        if (type == Type.EMAIL) {
            return R.drawable.ic_contact_mail_pink_60dp;
        } else if (type == Type.PHONE) {
            return R.drawable.ic_contact_phone_blue_60dp;
        } else return 0;
    }

}
