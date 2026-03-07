package lk.ijse.mlsupermarket.bo.impl;

import lk.ijse.mlsupermarket.bo.custom.SalesBO;
import lk.ijse.mlsupermarket.dao.DAOFactory;
import lk.ijse.mlsupermarket.dao.custom.SalesDAO;
import lk.ijse.mlsupermarket.dao.custom.SalesItemDAO;
import lk.ijse.mlsupermarket.db.DBConnection;
import lk.ijse.mlsupermarket.dto.SalesDTO;
import lk.ijse.mlsupermarket.dto.SaleItemDTO;
import lk.ijse.mlsupermarket.entity.Sales;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

public class SalesBOImpl implements SalesBO {

    private final SalesDAO salesDAO =
            (SalesDAO) DAOFactory.getInstance()
                    .getDAO(DAOFactory.DAOType.SALES);

    private final SalesItemDAO salesItemDAO =
            (SalesItemDAO) DAOFactory.getInstance()
                    .getDAO(DAOFactory.DAOType.SALE_ITEM);

    @Override
    public boolean saveSale(SalesDTO sale) throws Exception {
        return salesDAO.saveSale(
                new Sales(
                        sale.getSaleId(),
                        sale.getTotalAmount(),
                        sale.getSaleDate())
        );
    }

    @Override
    public boolean returnSaleItem(String saleId, String productId,
                                  int returnQty, double unitPrice) throws Exception {
        return salesItemDAO.returnSaleItem(saleId, productId, returnQty, unitPrice);
    }

    @Override
    public List<SaleItemDTO> getAllSalesItems() throws Exception {
        return salesItemDAO.getAllSalesItems();
    }

//    @Override
//    public void printStockReport() throws Exception {
//        salesDAO.printStockReport();
//    }

    @Override
    public String generateNextSaleId() throws Exception {
        return salesDAO.generateNextSaleId();
    }

    @Override
    public double getUnitPrice(String saleId, String productId) throws Exception {
        return salesItemDAO.getUnitPrice(saleId, productId);
    }

    @Override
    public List<String> getAllSaleIds() throws Exception {
        return salesDAO.getAllSaleIds();
    }

    @Override
    public void printStockReport() throws Exception {

        Connection conn = DBConnection.getInstance().getConnection();

        InputStream inputStream =
                getClass().getResourceAsStream("/lk/ijse/mlsupermarket/reports/invoice_report.jasper");

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(inputStream, new HashMap<>(), conn);

        JasperViewer.viewReport(jasperPrint, false);
    }
}