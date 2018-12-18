package Logic;

import Storage.WarehouseSystem;
import Storage.Transaction;

import java.util.List;

public class ReportController {
    private WarehouseSystem warehouse;

    public ReportController(WarehouseSystem warehouse) {
        this.warehouse = warehouse;
    }

    public List<Transaction> getAllTransaction(){
        return warehouse.getAllTransaction();
    }
}
