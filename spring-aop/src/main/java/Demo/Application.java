package Demo;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.activation.DataSource;
import java.sql.*;

@SpringBootApplication
public class Application implements CommandLineRunner{
    private Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    private HikariDataSource dataSourcessss;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        try (Connection conn = dataSourcessss.getConnection()) {
            // 这里，可以做点什么
            logger.info("[run][获得连接：{}]", conn);
            Statement statement = conn.createStatement();
            PreparedStatement preparedStatement = conn.prepareStatement("select * from account");
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println(resultSet);
            if(resultSet != null){ // 关闭记录集
                try{
                    resultSet.close() ;
                }catch(SQLException e){
                    e.printStackTrace() ;
                }

            }
            if(statement != null){ // 关闭声明
                try{
                    statement.close() ;
                }catch(SQLException e){
                    e.printStackTrace() ;
                }

            }
            if(conn != null){ // 关闭连接对象
                try{
                    conn.close() ;
                }catch(SQLException e){
                    e.printStackTrace() ;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
