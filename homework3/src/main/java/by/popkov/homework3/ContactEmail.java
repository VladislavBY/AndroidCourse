package by.popkov.homework3;

public class ContactEmail implements Contact {
    private String name;
    private String date;
    private int imageID;

    public ContactEmail(String name, String date, int imageID) {
        this.name = name;
        this.date = date;
        this.imageID = imageID;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getDate() {
        return null;
    }

    @Override
    public String getImageID() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public void setDate(String date) {

    }

    @Override
    public void setImageID(int imageID) {

    }
}
