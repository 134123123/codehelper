package codehelper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 测试数据库连接是否正常
     */
    @Test
    void testDatabaseConnection() throws SQLException {
        // 尝试获取数据库连接
        try (Connection connection = dataSource.getConnection()) {
            // 验证连接是否有效
            assertTrue(connection.isValid(3000), "数据库连接无效");
            // 验证连接的数据库是否正确
            String databaseName = connection.getCatalog();
            assertEquals("codehelper_db", databaseName, "连接的数据库名称不正确");
        } catch (SQLException e) {
            fail("数据库连接失败: " + e.getMessage());
        }
    }

    /**
     * 测试数据库读取功能（如果已创建表可执行此测试）
     * 注意：需先确保数据库中存在对应表，否则会失败
     */
    @Test
    void testDatabaseRead() {
        try {
            // 执行简单查询测试读取功能（查询数据库版本）
            String sql = "SELECT VERSION()";
            String version = jdbcTemplate.queryForObject(sql, String.class);
            assertNotNull(version, "未能获取数据库版本信息");
            System.out.println("数据库版本: " + version);

            // 如果已创建code_record表，可取消以下注释进行测试

            sql = "SELECT COUNT(*) FROM code_record";
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
            assertNotNull(count, "查询表记录数失败");
            System.out.println("code_record表记录数: " + count);

        } catch (Exception e) {
            fail("数据库读取测试失败: " + e.getMessage());
        }
    }



}
