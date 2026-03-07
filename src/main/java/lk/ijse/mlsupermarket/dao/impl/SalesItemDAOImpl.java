package lk.ijse.mlsupermarket.dao.impl;

import lk.ijse.mlsupermarket.dao.CrudUtil;
import lk.ijse.mlsupermarket.dao.custom.SalesItemDAO;
import lk.ijse.mlsupermarket.db.DBConnection;
import lk.ijse.mlsupermarket.dto.SaleItemDTO;
import lk.ijse.mlsupermarket.entity.SaleItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalesItemDAOImpl implements SalesItemDAO {

    @Override
    public List<SaleItemDTO> getAllSalesItems() throws Exception {

        List<SaleItemDTO> list = new ArrayList<>();

        String sql = "SELECT * FROM sales_item WHERE quantity > 0 ORDER BY CAST(SUBSTRING(sale_id, 2) AS UNSIGNED) DESC";

        Connection conn = DBConnection.getInstance().getConnection();
        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            list.add(new SaleItemDTO(
                    rs.getString("sale_id"),
                    rs.getString("product_id"),
                    rs.getInt("quantity"),
                    rs.getDouble("unit_price"),
                    rs.getDouble("total_price")));
        }

        return list;
    }


    @Override
    public double getUnitPrice(String saleId, String productId) throws SQLException {

        String sql = "SELECT unit_price FROM sales_item WHERE sale_id = ? AND product_id = ?";

        Connection conn = DBConnection.getInstance().getConnection();
        PreparedStatement pst = conn.prepareStatement(sql);

        pst.setString(1, saleId);
        pst.setString(2, productId);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) return rs.getDouble("unit_price");
        return 0;
    }


    @Override
    public boolean saveSaleItem(SaleItem salesItem) throws SQLException {

        return CrudUtil.execute(
                "INSERT INTO sales_item (sale_id, product_id, quantity, unit_price, total_price) VALUES (?,?,?,?,?)",
                salesItem.getSaleId(),
                salesItem.getProductId(),
                salesItem.getQuantity(),
                salesItem.getUnitPrice(),
                salesItem.getTotalPrice()
        );
    }
}
