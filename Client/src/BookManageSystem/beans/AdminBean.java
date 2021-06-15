package BookManageSystem.beans;

import java.io.Serializable;
import java.sql.Date;

public class AdminBean extends PersonBean implements AdminBeanDao, Serializable {
    private int admin_id;

    public AdminBean(int admin_id, String name, byte sex, Date date, String address, String phone, String UserName, String password) {
        super(name,sex,date,address,phone,UserName,password);
        this.admin_id = admin_id;

    }

    public AdminBean() {
        super();
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    @Override
    public AdminBean login(String var1, String var2){
        return null;
    }

    @Override
    public boolean updateAdmin(AdminBean adminBean){
        return true;
    }






}
