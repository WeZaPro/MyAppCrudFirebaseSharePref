package example.com.myappcrudfirebase;

public class Data {
    private String ID;
    private String Name;
    private String Surname;

    public Data(){}

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    @Override
    public String toString(){
        return ID +" - " + Name + " " + Surname;
    }
}