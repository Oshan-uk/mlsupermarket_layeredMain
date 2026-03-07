package lk.ijse.mlsupermarket.dao.custom;

import lk.ijse.mlsupermarket.dao.SuperDAO;
import lk.ijse.mlsupermarket.dto.SaleItemDTO;

import java.sql.SQLException;
import java.util.List;

public interface SalesItemDAO extends SuperDAO {

    public boolean returnSaleItem(String saleId, String productId,
                                  int returnQty, double unitPrice) throws SQLException;

    public List<SaleItemDTO> getAllSalesItems() throws Exception;
    public double getUnitPrice(String saleId, String productId) throws SQLException;
}
