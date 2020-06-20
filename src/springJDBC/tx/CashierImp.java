package springJDBC.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Service("cashier")
public class CashierImp implements Cashier {

    @Autowired
    private Service service;

    @Transactional
    @Override
    public void checkout(String username, List<String> isbn) {
        for (String i : isbn) {
            service.purchase(username, i);
        }
    }
}
