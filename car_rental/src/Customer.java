public class Customer {
    private String first_name;
    private String last_name;
    private String sex;
    private String address;
    private String email;
    private String phone;
    public Customer(String first_name, String last_name, String sex, String address, String email, String phone){
        this.first_name = first_name;
        this.last_name = last_name;
        this.sex = sex;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    @Override
    public String toString(){
        return "Customer(First name: "+this.first_name+", Last name: "+this.last_name+", Sex: "+this.sex+", Address: "+this.address+", Email: "+this.email+", Phone: "+this.phone+")";
    }
}
