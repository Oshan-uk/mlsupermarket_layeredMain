package lk.ijse.mlsupermarket.dao.custom;

import lk.ijse.mlsupermarket.dto.SalesDTO;
import lk.ijse.mlsupermarket.dto.SaleItemDTO;
import lk.ijse.mlsupermarket.dao.SuperDAO;
import lk.ijse.mlsupermarket.entity.Sales;

import java.sql.SQLException;
import java.util.List;

public interface SalesDAO extends SuperDAO {

    boolean saveSale(Sales sale) throws SQLException;

    String generateNextSaleId() throws Exception;

    List<String> getAllSaleIds() throws SQLException;

}