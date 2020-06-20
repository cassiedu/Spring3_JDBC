package springJDBC.tx;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;

public class TransactionTest {
    private ApplicationContext ctx = null;
    private Dao dao = null;
    private Service service = null;
    private Cashier cashier = null;

    {
        ctx = new ClassPathXmlApplicationContext("transaction.xml");
        dao = ctx.getBean(Dao.class);
        service = ctx.getBean(Service.class);
        cashier = ctx.getBean(Cashier.class);
    }

    @Test
    public void testCashierCheckout() {
        cashier.checkout("AA", Arrays.asList("1001", "1002"));
    }

    @Test
    public void testServicePurchase() {
        service.purchase("1001", "AA");
    }

    @Test
    public void testDaoUpdateUserAccount() {
        dao.updateUserAccount("AA", 20);
    }

    @Test
    public void testDaoUpdateBookStock() {
        dao.updateBookStock("1001");
    }

    @Test
    public void testDaoFindPriceByIsbn() {
        System.out.println(dao.findBookPriceByIsbn("1001"));
    }

}
