package lk.ijse.mlsupermarket.dao.custom;

import lk.ijse.mlsupermarket.dao.CrudDAO;
import lk.ijse.mlsupermarket.entity.Product;

import java.sql.SQLException;

public interface ProductDAO extends CrudDAO<Product> {
    String generateNextId() throws Exception;
    boolean reduceQuantity(String productId, int qty) throws SQLException;
}
