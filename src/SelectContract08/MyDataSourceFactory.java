/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Home
 */
package SelectContract08;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;
import oracle.jdbc.pool.OracleDataSource;

public class MyDataSourceFactory {
    
    public static DataSource getOracleDataSource() throws SQLException {
        Properties props = new Properties();
        FileInputStream fis = null;
        OracleDataSource oracleDS = null;
        try {
            fis = new
            FileInputStream("/Users/Home/Desktop/ICS_Program/ICSFirstYear/ICS125/Lab08/SelectContract08/src/SelectContract08/db.properties");

            props.load(fis);
            oracleDS = new OracleDataSource();
            oracleDS.setURL(props.getProperty("ORACLE_DB_URL"));
            oracleDS.setUser(props.getProperty("ORACLE_DB_USERNAME"));
            oracleDS.setPassword(props.getProperty(
            "ORACLE_DB_PASSWORD"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return oracleDS;
    } // end getOracleDataSource
} // end class