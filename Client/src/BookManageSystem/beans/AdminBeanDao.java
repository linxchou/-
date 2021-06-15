package BookManageSystem.beans;

public interface AdminBeanDao {
    AdminBean login(String var1, String var2);

    boolean updateAdmin(AdminBean adminBean);
}
