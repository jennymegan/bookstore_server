public class Customer
{
    private int id;
    private String phone;
    private String name;
    private String address;

    public String printInfo(){
        return "ID: " + this.id + ", Name: " + this.name + ", Address: " + this.address + ", Phone No.: " + this.phone + ", Address: " + this.address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
