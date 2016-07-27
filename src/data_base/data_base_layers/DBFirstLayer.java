package data_base.data_base_layers;

import data_base.DB_Instances_Interface;
import data_base.DataBase;
import programm.texts.ConnectionText;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * First Data Base Layer
 * Created by Artem Siatchinov on 7/27/2016.
 */
public abstract class DBFirstLayer implements DB_Instances_Interface {

    private DataBase DATA_BASE;
    private String STATUS;

    //Constructor
    public DBFirstLayer(DataBase dataBase){
        this.DATA_BASE = dataBase;
    }

    public boolean performStatementOperation(String sqlStatement, String status){
        STATUS = "";

        //Get connection
        Connection connection = DATA_BASE.getConnection();
        if (connection == null) {
            STATUS += "\n" + status + "  " +  ConnectionText.getOpenConnectionError();
            return false;
        }
        STATUS += "\n" + status + "  " + ConnectionText.getOpenConnectionSuccess();

        Statement statement = null;

        try {
            statement = connection.createStatement();
            statement.executeUpdate(sqlStatement);
            STATUS += "\n" + status + "  " + ConnectionText.getExecuteStatementSuccess();

        } catch (SQLException e) {
            e.printStackTrace();
            STATUS += "\n" + status + "  " + ConnectionText.getExecuteStatementError();
            return false;

        } finally {
            //Close Statement and connection
            if (statement != null) {
                try {
                    statement.close();
                    STATUS += "\n" + status + "  " + ConnectionText.getCloseStatementSuccess();
                } catch (SQLException e) {
                    e.printStackTrace();
                    STATUS += "\n" + status + "  " + ConnectionText.getCloseStatementError();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                    STATUS += "\n" + status + "  " + ConnectionText.getCloseConnectionSuccess();
                } catch (SQLException e) {
                    e.printStackTrace();
                    STATUS += "\n" + status + "  " + ConnectionText.getCloseConnectionError();
                }
            }

        }
        return true;
    }

    //Getters

    public String getSTATUS() {
        return STATUS;
    }
}
