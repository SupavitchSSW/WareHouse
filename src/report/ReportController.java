package report;

import connectionDB.serviceDB;
import transaction.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ReportController {
    private connectionDB.serviceDB warehouse;

    public ReportController(connectionDB.serviceDB warehouse) {
        this.warehouse = warehouse;
    }

    public List<Transaction> getAllTransaction(){
//        return warehouse.getAllTransaction();
        return new ArrayList<>();
    }
}
