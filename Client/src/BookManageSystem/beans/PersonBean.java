package BookManageSystem.beans;

import java.io.Serializable;
import java.sql.Date;

public class PersonBean implements Serializable {
    private String name;
    private byte sex;
    private Date birthday;
    private String address;
    private String phone;
    private String user_name;
    private String password;


    public PersonBean(){

    }
    public PersonBean(String name, byte sex, Date birthday, String address, String phone, String user_name, String password){
        this.name=name;
        this.sex=sex;
        this.birthday=birthday;
        this.address=address;
        this.phone=phone;
        this.user_name=user_name;
        this.password=password;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public byte getSex() {
        return sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }



    public void setAddress(String address) {
        this.address = address;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSex(byte sex) {
        this.sex = sex;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
