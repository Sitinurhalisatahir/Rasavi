package temurasa.controllers;
import temurasa.database.AdminDao;
import temurasa.models.User;
import temurasa.util.PasswordUtils;

public class UserController {
    private AdminDao adminDao = new AdminDao();

    public boolean tambahUser(User user) {
        if (adminDao.isAdminExist(user.getUsername())) {
            return false;
        }
        String hashedPassword = PasswordUtils.hashPassword(user.getPassword());
        return adminDao.insertAdmin(user.getUsername(), hashedPassword);
    }

    public boolean authenticate(String username, String password) {
        String hashedPassword = PasswordUtils.hashPassword(password);
        return adminDao.authenticate(username, hashedPassword);
    }
}
