package springJDBC.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("dao")
public class DaoImp implements Dao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int findBookPriceByIsbn(String isbn) {
        String sql = "SELECT price FROM book WHERE isbn = ?";
        Integer num = jdbcTemplate.queryForObject(sql, Integer.class, isbn);
        return num;
    }

    @Override
    public void updateBookStock(String isbn) {
        //验证库存是否足够, 若不足, 则抛出异常
        String sql2 = "SELECT stock FROM bookstock WHERE isbn = ?";
        int stock = jdbcTemplate.queryForObject(sql2, Integer.class, isbn);
        if (stock == 0) {
            throw new BookStockException("the stock is not enough");
        }
        String sql = "UPDATE bookstock SET stock = stock -1 WHERE isbn = ?";
        jdbcTemplate.update(sql, isbn);
    }

    @Override
    public void updateUserAccount(String username, int price) {
        //验证余额是否足够, 若不足, 则抛出异常
        String sql2 = "SELECT balance FROM account WHERE username = ?";
        int balance = jdbcTemplate.queryForObject(sql2, Integer.class, username);
        if (balance < price) {
            throw new UserAccountException("the balance is not enough");
        }
        String sql = "UPDATE account SET balance = balance - ? WHERE username = ?";
        jdbcTemplate.update(sql, price, username);
    }
}
