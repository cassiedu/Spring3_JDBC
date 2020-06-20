package springJDBC.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestJDBC {
    private ApplicationContext ctx = null;
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    {
        ctx = new ClassPathXmlApplicationContext("c3p0-sever.xml");
        jdbcTemplate = (JdbcTemplate) ctx.getBean("jdbcTemplate");
        namedParameterJdbcTemplate = ctx.getBean(NamedParameterJdbcTemplate.class);
    }

    //SQL的值要和employee类中属性的值一致
    @Test
    public void testNamedParameterJdbcTemplate2() {
        String sql = "INSERT INTO employee(last_name, email, dept_id) VALUES(:last_name,:email,:dept_id)";

        Employee employee = new Employee();
        employee.setLast_name("XYZ");
        employee.setEmail("xyz@sina.com");
        employee.setDept_id(3);

        SqlParameterSource paramSource = new BeanPropertySqlParameterSource(employee);
        namedParameterJdbcTemplate.update(sql, paramSource);
    }

    @Test
    public void testNamedParameterJdbcTemplate() {
        String sql = "INSERT INTO employee(last_name, email, dept_id) VALUES(:ln,:email,:deptid)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("ln", "FF");
        paramMap.put("email", "ff@atguigu.com");
        paramMap.put("deptid", 2);

        namedParameterJdbcTemplate.update(sql, paramMap);
    }

    @Test
    public void testQueryForObject2() {
        String sql = "select count(id) from employee";
        Long count = jdbcTemplate.queryForObject(sql, long.class);
        System.out.println(count);
    }

    @Test
    public void testQueryForList() {
        String sql = "select id, last_name LastName, email from employee where id = ?";
        RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
        List<Employee> employee = jdbcTemplate.query(sql, rowMapper, 3);
        System.out.println(employee);

    }

    @Test
    public void testQueryForObject() {
        String sql = "select id, last_name LastName, email from employee where id = ?";
        RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
        Employee employee = jdbcTemplate.queryForObject(sql, rowMapper, 1);
        System.out.println(employee);

    }


    @Test
    public void testBatchUpdate() {
        String sql = "insert into employee(last_name, email, dept_id) values(?,?,?)";
        List<Object[]> batchArgs = new ArrayList<>();
        batchArgs.add(new Object[]{"AA", "AA@atguigu.com", 1});
        batchArgs.add(new Object[]{"BB", "BB@atguigu.com", 2});
        batchArgs.add(new Object[]{"CC", "CC@atguigu.com", 3});
        batchArgs.add(new Object[]{"DD", "DD@atguigu.com", 4});
        jdbcTemplate.batchUpdate(sql, batchArgs);
    }

    @Test
    public void testDataSource() throws SQLException {
        DataSource dataSource = ctx.getBean(DataSource.class);
        System.out.println(dataSource.getConnection());
    }
}