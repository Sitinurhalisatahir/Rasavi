package temurasa.controllers;

import temurasa.models.Menu;
import temurasa.database.MenuDao;
import java.util.List;

public class MenuController implements IBaseController<Menu> {
    private final MenuDao menuDao = new MenuDao();

    @Override
    public boolean tambah(Menu menu) {
        return menuDao.insertMenu(menu);
    }

    @Override
    public List<Menu> getAll() {
        return menuDao.getAllMenu();
    }

    // Method khusus untuk update dan hapus
    public boolean updateMenu(int id, Menu menuBaru) {
        menuBaru.setId(id);
        return menuDao.updateMenu(menuBaru);
    }

    public boolean hapusMenu(int id) {
        return menuDao.deleteMenu(id);
    }
}