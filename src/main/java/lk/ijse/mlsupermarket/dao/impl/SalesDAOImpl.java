package lk.ijse.mlsupermarket.dao.impl;

import lk.ijse.mlsupermarket.dao.CrudUtil;
import lk.ijse.mlsupermarket.dao.custom.SalesDAO;
import lk.ijse.mlsupermarket.db.DBConnection;
import lk.ijse.mlsupermarket.dto.SalesDTO;
import lk.ijse.mlsupermarket.dto.SaleItemDTO;
import lk.ijse.mlsupermarket.entity.Sales;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class SalesDAOImpl implements SalesDAO {

    @Override
    public boolean saveSale(Sales sales) throws SQLException{
        return CrudUtil.execute(
                "INSERT INTO sales (sale_id, total_amount, sale_date) VALUES (?, ?, ?)",
                    sales.getSaleId(),
                    sales.getTotalAmount(),
                    sales.getSaleDate()
                );

    }

    @Override
    public String generateNextSaleId() throws Exception {

        ResultSet rs = CrudUtil.execute(
                "SELECT sale_id FROM sales ORDER BY CAST(SUBSTRING(sale_id, 2) AS UNSIGNED) DESC LIMIT 1"
        );

        if (rs.next()) {
            String lastId = rs.getString("sale_id");
            int num = Integer.parseInt(lastId.substring(1));
            return "S" + (num + 1);
        }

        return "S1";
    }

    @Override
    public List<String> getAllSaleIds() throws SQLException {

        List<String> list = new ArrayList<>();

        ResultSet rs = CrudUtil.execute(
                "SELECT sale_id FROM sales ORDER BY CAST(SUBSTRING(sale_id, 2) AS UNSIGNED) DESC"
        );

        while (rs.next()) {
            list.add(rs.getString("sale_id"));
        }

        return list;
    }
}