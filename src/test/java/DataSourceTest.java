import com.pyatakov.config.DataServiceConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertNotNull;

public class DataSourceTest {

    private GenericApplicationContext ctx;

    @Before
    public void setUp(){
        ctx = new AnnotationConfigApplicationContext(DataServiceConfig.class);
        assertNotNull(ctx);
    }

    @Test
    public void dataSourceTest() throws SQLException {
        DataSource dataSource = ctx.getBean(DataSource.class);
        Connection con = dataSource.getConnection();

        Statement stmt = con.createStatement();
        stmt.execute("select category from category");

        ResultSet rs = stmt.getResultSet();
        while (rs.next()) {
            System.out.println(rs.getString(1));
        }
        rs.close();
        stmt.close();
        con.close();
    }


    @After
    public void tearDown(){
        ctx.close();
    }

}
