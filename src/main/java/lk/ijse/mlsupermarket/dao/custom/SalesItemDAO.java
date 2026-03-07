package lk.ijse.mlsupermarket.dao.custom;

import lk.ijse.mlsupermarket.dao.SuperDAO;
import lk.ijse.mlsupermarket.dto.SaleItemDTO;
import lk.ijse.mlsupermarket.entity.SaleItem;

import java.sql.SQLException;
import java.util.List;

public interface SalesItemDAO extends SuperDAO {

    public List<SaleItemDTO> getAllSalesItems() throws Exception;
    public double getUnitPrice(String saleId, String productId) throws SQLException;
    boolean saveSaleItem(SaleItem saleItem) throws SQLException;
}
