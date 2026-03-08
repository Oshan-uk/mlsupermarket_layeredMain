package lk.ijse.mlsupermarket.bo.impl;

import lk.ijse.mlsupermarket.bo.custom.SalesBO;
import lk.ijse.mlsupermarket.dao.DAOFactory;
import lk.ijse.mlsupermarket.dao.custom.InventoryDAO;
import lk.ijse.mlsupermarket.dao.custom.ProductDAO;
import lk.ijse.mlsupermarket.dao.custom.SalesDAO;
import lk.ijse.mlsupermarket.dao.custom.SalesItemDAO;
import lk.ijse.mlsupermarket.db.DBConnection;
import lk.ijse.mlsupermarket.dto.SalesDTO;
import lk.ijse.mlsupermarket.dto.SaleItemDTO;
import lk.ijse.mlsupermarket.entity.Product;
import lk.ijse.mlsupermarket.entity.SaleItem;
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

    private final ProductDAO productDAO =
            (ProductDAO) DAOFactory.getInstance()
                    .getDAO(DAOFactory.DAOType.PRODUCT);

    private final InventoryDAO inventoryDAO =
            (InventoryDAO) DAOFactory.getInstance()
                    .getDAO(DAOFactory.DAOType.INVENTORY);


    @Override
    public boolean saveSale(SalesDTO saleDTO, List<SaleItemDTO> items) throws Exception {

        Connection conn = DBConnection.getInstance().getConnection();

        try {

            conn.setAutoCommit(false);

            salesDAO.saveSale(
                    new Sales(
                            saleDTO.getSaleId(),
                            saleDTO.getTotalAmount(),
                            saleDTO.getSaleDate()
                    )
            );

            for (SaleItemDTO dto : items) {

                salesItemDAO.saveSaleItem(
                        new SaleItem(
                                saleDTO.getSaleId(),
                                dto.getProductId(),
                                dto.getQuantity(),
                                dto.getUnitPrice(),
                                dto.getTotal()
                        )
                );

                productDAO.reduceQuantity(dto.getProductId(), dto.getQuantity());

                inventoryDAO.reduceStock(dto.getProductId(), dto.getQuantity());

            }

            conn.commit();

            return true;

        } catch (Exception e) {

            conn.rollback();

            throw e;

        } finally {

            conn.setAutoCommit(true);
        }
    }
    @Override
    public boolean returnSaleItem(String saleId, String productId,
                                  int returnQty, double unitPrice) throws Exception {
        return inventoryDAO.returnSaleItem(saleId, productId, returnQty, unitPrice);
    }

    @Override
    public List<SaleItemDTO> getAllSalesItems() throws Exception {
        return salesItemDAO.getAllSalesItems();
    }

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