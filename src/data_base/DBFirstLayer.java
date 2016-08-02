package data_base;

import instances.DataBaseInstance;
import programm.texts.ConnectionText;
import programm.texts.DataBaseOperationText;

import java.sql.*;

/**
 * First Data Base Layer
 * Created by Artem Siatchinov on 7/27/2016.
 */
public abstract class DBFirstLayer {

    private DataBase DATA_BASE;

    private String STATUS;

    private Connection CONNECTION;
    private Statement STATEMENT;
    private ResultSet RESULT_SET;
    private PreparedStatement PREPARED_STATEMENT;


    //Constructor
    public DBFirstLayer(DataBase dataBase){
        CONNECTION = null;
        STATEMENT = null;
        RESULT_SET = null;

        this.DATA_BASE = dataBase;
    }



    //Private inner Methods

    /**
     *
     * Method created connection with Data Base and returns a boolean
     * to indicate if the operation was successful or not
     *
     */
    private boolean getConnection(String operationStatus){
        STATUS = "";

        CONNECTION = DATA_BASE.getConnection();
        if (CONNECTION == null){
            STATUS += "\n" + operationStatus + "  " +  ConnectionText.getOpenConnectionError();
            return false;
        }

        STATUS += "\n" + operationStatus + "  " + ConnectionText.getOpenConnectionSuccess();
        return true;
    }

    private void closeStatement(String operationName){
        if (STATEMENT != null) {
            try {
                STATEMENT.close();
                STATUS += "\n" + operationName + "  " + ConnectionText.getCloseStatementSuccess();
            } catch (SQLException e) {
                e.printStackTrace();
                STATUS += "\n" + operationName + "  " + ConnectionText.getCloseStatementError();
            }
        }
    }

    private void closeConnection(String operationName){

        if (CONNECTION != null) {
            try {
                CONNECTION.close();
                STATUS += "\n" + operationName + "  " + ConnectionText.getCloseConnectionSuccess();
            } catch (SQLException e) {
                e.printStackTrace();
                STATUS += "\n" + operationName + "  " + ConnectionText.getCloseConnectionError();
            }
        }
    }

    private void closeResultSet(String operationName){
        if (RESULT_SET != null) {
            try {
                RESULT_SET.close();
                STATUS += "\n" + ConnectionText.getCloseResultsSetSuccess();
            } catch (SQLException e) {
                e.printStackTrace();
                STATUS += "\n" + ConnectionText.getCloseResultSetError();
            }
        }
    }

    private void closePreparedStatemnt(String operationName){
        if (PREPARED_STATEMENT != null) {
            try {
                PREPARED_STATEMENT.close();
                STATUS += "\n" + ConnectionText.getCloseStatementSuccess();
            } catch (SQLException e) {
                e.printStackTrace();
                STATUS += "\n" + ConnectionText.getCloseStatementError();
            }
        }
    }



    //Protect Package methods

    /**
     *Performs Statement operation
     * Create table, drop table,
     */
    protected boolean performStatementOperation(String sqlStatement, String operationName){

        if (!getConnection(operationName)){
            return false;
        }

        STATEMENT = null;


        try {
            STATEMENT = CONNECTION.createStatement();
            STATEMENT.executeUpdate(sqlStatement);
            STATUS += "\n" + operationName + "  " + ConnectionText.getExecuteStatementSuccess();

        } catch (SQLException e) {
            e.printStackTrace();
            STATUS += "\n" + operationName + "  " + ConnectionText.getExecuteStatementError();
            return false;

        } finally {
            closeStatement(operationName);
            closeConnection(operationName);
        }

        return true;
    }

    //Check if Table exist
    /**
     * Performs table check operation
     * Returns boolean to indicate if table exist
     * This Method throws an exception to indicate that the operation couldn't be performed
     */
    protected boolean performTableCheckOperation(String tableName) throws Exception {

        boolean tableCreated = false;
        Exception checkTableException = new Exception();
        String operationName = DataBaseOperationText.getCheckTableOperation();

        if (!getConnection(operationName)){
            throw checkTableException;
        }

        RESULT_SET = null;

        try {
            RESULT_SET = CONNECTION.getMetaData().getTables(null, null, tableName, null);

            if (RESULT_SET.next()) {
                tableCreated = true;
            }

            STATUS += "\n" + operationName + "  " + ConnectionText.getCheckTableSuccess();

        } catch (SQLException e) {

            e.printStackTrace();
            STATUS += "\n" + operationName + "  " + ConnectionText.getCheckTableError();
            throw checkTableException;

        } finally {

            closeResultSet(operationName);
            closeConnection(operationName);

            return tableCreated;
        }
    }


    protected boolean addObjectOperation(String sqlStatement, DataBaseInstance dataBaseInstance) {

        String operationName = DataBaseOperationText.getAddObjectOperation();
        int objectsId = 0;

        if (!getConnection(operationName)){
            return false;
        }

        PREPARED_STATEMENT = null;
        RESULT_SET = null;

        try{

            PREPARED_STATEMENT = CONNECTION.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            PREPARED_STATEMENT = prepareAddPrepStat(PREPARED_STATEMENT, dataBaseInstance);
            PREPARED_STATEMENT.executeUpdate();

            RESULT_SET = PREPARED_STATEMENT.getGeneratedKeys();

            if (RESULT_SET.next()){
                objectsId = RESULT_SET.getInt(1);
            }

            dataBaseInstance.setID(objectsId);

            STATUS += "\n" + operationName + "  " + ConnectionText.getAddObjectSuccess();

        } catch (SQLException e) {
            e.printStackTrace();
            STATUS += "\n" + operationName + "  " + ConnectionText.getAddObjectError();
            return false;

        }finally {
            //Close Result Set , prepared statement and connection;

            closeResultSet(operationName);
            closePreparedStatemnt(operationName);
            closeConnection(operationName);
        }

        return true;
    }

    protected boolean loadObjectsOperation(String sqlStatement, String operationName){

        if (!getConnection(operationName)){
            return false;
        }

        //Creating Statement and Result Set
        STATEMENT = null;
        RESULT_SET = null;

        try{
            STATEMENT = CONNECTION.createStatement();

            RESULT_SET = STATEMENT.executeQuery(sqlStatement);

            //pass result set to the subclass for creating new instances and adding them to the list
            processLoadResultSet(RESULT_SET);
            STATUS += "\n" + ConnectionText.getLoadTableSuccess();

        } catch (SQLException e) {

            e.printStackTrace();
            STATUS += "\n" + ConnectionText.getLoadTableError();
            return false;

        }finally {

            //Close Result Set, statement and connection;
            closeResultSet(operationName);
            closeStatement(operationName);
            closeConnection(operationName);

        }

        return true;

    }


    //Status
    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    //Abstract methods
    protected abstract void processLoadResultSet(ResultSet resultSet)throws SQLException;
    protected abstract PreparedStatement prepareAddPrepStat(PreparedStatement preparedStatement, DataBaseInstance newAdmin);
    public abstract boolean isTABLE_CREATED();
}
