package temurasa.controllers;

import temurasa.models.Menu;
import temurasa.database.MenuDao;
import java.util.List;

public class MenuController {
    private MenuDao menuDao = new MenuDao();

    public boolean tambahMenu(Menu menu) {
        return menuDao.insertMenu(menu);
    }

    public boolean updateMenu(int id, Menu menuBaru) {
        menuBaru.setId(id);
        return menuDao.updateMenu(menuBaru);
    }

    public boolean hapusMenu(int id) {
        return menuDao.deleteMenu(id);
    }

    public List<Menu> getAllMenu() {
        return menuDao.getAllMenu();
    }

    public Menu getMenuById(int id) {
        return menuDao.getMenuById(id);
    }
}


