package database;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;


public final class DBHelper {

    private static BasicDataSource ds = new BasicDataSource();

    static {
        ds = new BasicDataSource();
        try {
            InputStream input = new FileInputStream("config.properties");
            Properties prop = new Properties();

            // load a properties file
            prop.load(input);
            ds.setUsername(prop.getProperty("db.user"));
            ds.setPassword(prop.getProperty("db.password"));
            ds.setUrl(prop.getProperty("db.url"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private DBHelper(){ }
}
