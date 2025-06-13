package temurasa.controllers;

import java.util.List;

import temurasa.database.OrderDao;
import temurasa.models.Order;
import temurasa.models.OrderItem;

public class OrderController implements IBaseController<Order> {
    private final OrderDao orderDao = new OrderDao();

    @Override
    public boolean tambah(Order order) {
        // insertOrder mengembalikan id, return true jika berhasil
        return orderDao.insertOrder(order) > 0;
    }

    @Override
    public List<Order> getAll() {
        return orderDao.getAllOrders();
    }

    public boolean tambahItemKeOrder(int orderId, List<OrderItem> items) {
        return orderDao.insertOrderItems(orderId, items);
    }

    public int tambahOrderReturnId(Order order) {
        return orderDao.insertOrder(order);
    }
}