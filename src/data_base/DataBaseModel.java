package data_base;

import data_base.data_base_layers.DBFirstLayer;
import instances.DataBaseInstance;
import programm.Programm;
import programm.error_texts.DataBaseErrorText;
import programm.texts.ConnectionText;
import programm.texts.DataBaseOperationText;

import java.sql.*;

/**
 * Created by Artem Siatchinov on 7/24/2016.
 */
public abstract class DataBaseModel extends DBFirstLayer {

    private Programm PROGRAMM;
    private DataBase DATA_BASE;
    private DB_Instances_Interface OBJECT_DB_INTERFACE;

    private String STATUS;
    private boolean TABLE_CREATED;

    //Constructor & Initializer

    public DataBaseModel(Programm programm, DataBase dataBase) {

        super(dataBase);
        this.PROGRAMM = programm;
        this.DATA_BASE = dataBase;


        STATUS = "";
    }

    /**
     * - Check if table is created when this method called
     */
    public void initializer(DB_Instances_Interface DBInstancesInterface) {

        this.OBJECT_DB_INTERFACE = DBInstancesInterface;

        TABLE_CREATED = false;

        checkTable();

    }

    //Table Methods
    //Create Table

    public boolean createTableOpertion(String sqlStatement) {

        if (performStatementOperation(sqlStatement, DataBaseOperationText.getCreateTableOperation())) {
            TABLE_CREATED = true;
            OBJECT_DB_INTERFACE.renewList();
            return true;
        }
        return false;
    }

    //Drop Table


    //Table Methods
    //Create Table
    public boolean createTable() {

        STATUS = DataBaseOperationText.getCreateTableOperation();

        //Get connection
        Connection connection = DATA_BASE.getConnection();
        if (connection == null) {
            STATUS += "\n" + ConnectionText.getOpenConnectionError();
            return false;
        }
        STATUS += "\n" + ConnectionText.getOpenConnectionSuccess();

        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(OBJECT_DB_INTERFACE.getTableCreateStatement());
            STATUS += "\n" + ConnectionText.getCreateTableSuccess();

        } catch (SQLException e) {
            e.printStackTrace();
            STATUS += "\n" + ConnectionText.getCreateTableError();
            return false;

        } finally {
            //Close Statement and connection
            if (statement != null) {
                try {
                    statement.close();
                    STATUS += "\n" + ConnectionText.getCloseStatementSuccess();
                } catch (SQLException e) {
                    e.printStackTrace();
                    STATUS += "\n" + ConnectionText.getCloseStatementError();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                    STATUS += "\n" + ConnectionText.getCloseConnectionSuccess();
                } catch (SQLException e) {
                    e.printStackTrace();
                    STATUS += "\n" + ConnectionText.getCloseConnectionError();
                }
            }

        }


        TABLE_CREATED = true;
        return true;
    }

    //Drop Table
    public boolean dropTable() {

        STATUS = DataBaseOperationText.getDropTableOperation();

        //Get connection
        Connection connection = DATA_BASE.getConnection();
        if (connection == null) {
            STATUS += "\n" + ConnectionText.getOpenConnectionError();
            return false;
        }
        STATUS += "\n" + ConnectionText.getOpenConnectionSuccess();

        Statement statement = null;

        try {
            statement = connection.createStatement();
            statement.executeUpdate(OBJECT_DB_INTERFACE.getTableDropStatement());
            STATUS += "\n" + ConnectionText.getDropTableSuccess();

        } catch (SQLException e) {
            e.printStackTrace();
            STATUS += "\n" + ConnectionText.getDropTableError();
            return false;

        } finally {
            //Close Statement and connection
            if (statement != null) {
                try {
                    statement.close();
                    STATUS += "\n" + ConnectionText.getCloseStatementSuccess();
                } catch (SQLException e) {
                    e.printStackTrace();
                    STATUS += "\n" + ConnectionText.getCloseStatementError();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                    STATUS += "\n" + ConnectionText.getCloseConnectionSuccess();
                } catch (SQLException e) {
                    e.printStackTrace();
                    STATUS += "\n" + ConnectionText.getCloseConnectionError();
                }
            }

        }
        TABLE_CREATED = false;
        OBJECT_DB_INTERFACE.renewList();
        return true;
    }

    //Check if Table exist
    public boolean checkTable() {
        TABLE_CREATED = false;
        STATUS = DataBaseOperationText.getCheckTableOperation();

        //Get connection
        Connection connection = DATA_BASE.getConnection();
        if (connection == null) {
            STATUS += "\n" + ConnectionText.getOpenConnectionError();
            return false;
        }
        STATUS += "\n" + ConnectionText.getOpenConnectionSuccess();


        ResultSet resultSet = null;
        try {
            resultSet = connection.getMetaData().getTables(null, null, OBJECT_DB_INTERFACE.getTableName(), null);

            if (resultSet.next()) {
                TABLE_CREATED = true;
            }
            STATUS += "\n" + ConnectionText.getCheckTableSuccess();

        } catch (SQLException e) {
            e.printStackTrace();
            STATUS += "\n" + ConnectionText.getCheckTableError();
            return false;
        } finally {
            //Close Result Set and connection;

            if (resultSet != null) {
                try {
                    resultSet.close();
                    STATUS += "\n" + ConnectionText.getCloseResultsSetSuccess();
                } catch (SQLException e) {
                    e.printStackTrace();
                    STATUS += "\n" + ConnectionText.getCloseResultSetError();
                }
            }

            try {
                connection.close();
                STATUS += "\n" + ConnectionText.getCloseConnectionSuccess();
            } catch (SQLException e) {
                e.printStackTrace();
                STATUS += "\n" + ConnectionText.getCloseConnectionError();
            }
        }

        return true;
    }



    //Object Methods
    //Insert object
    public boolean addObject(DataBaseInstance dataBaseInstance) {
        STATUS = DataBaseOperationText.getAddObjectOperation();

        if (!TABLE_CREATED) {
            STATUS += "\n" + DataBaseErrorText.getTableNotCreatedError();
            return false;
        }


        //Get connection
        Connection connection = DATA_BASE.getConnection();
        if (connection == null) {
            STATUS += "\n" + ConnectionText.getOpenConnectionError();
            return false;
        }
        STATUS += "\n" + ConnectionText.getOpenConnectionSuccess();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{

            //connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(OBJECT_DB_INTERFACE.getAddPrepStatement(), Statement.RETURN_GENERATED_KEYS);

            preparedStatement = OBJECT_DB_INTERFACE.prepareAddPrepStat(preparedStatement, dataBaseInstance);

            preparedStatement.executeUpdate();

            //get The id of the added doctor
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()){
                int newID = resultSet.getInt(1);
                //set ID to new object and add it to the list
                dataBaseInstance.setID(newID);
            }

            OBJECT_DB_INTERFACE.addToListNewObject(dataBaseInstance);

            STATUS += "\n" + ConnectionText.getAddObjectSuccess();

        } catch (SQLException e) {
            e.printStackTrace();
            STATUS += "\n" + ConnectionText.getAddObjectError();
            return false;

        }finally {
            //Close Result Set and connection;

            if (resultSet != null) {
                try {
                    resultSet.close();
                    STATUS += "\n" + ConnectionText.getCloseResultsSetSuccess();
                } catch (SQLException e) {
                    e.printStackTrace();
                    STATUS += "\n" + ConnectionText.getCloseResultSetError();
                }
            }

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                    STATUS += "\n" + ConnectionText.getCloseStatementSuccess();
                } catch (SQLException e) {
                    e.printStackTrace();
                    STATUS += "\n" + ConnectionText.getCloseStatementError();
                }
            }

            try {
                connection.close();
                STATUS += "\n" + ConnectionText.getCloseConnectionSuccess();
            } catch (SQLException e) {
                e.printStackTrace();
                STATUS += "\n" + ConnectionText.getCloseConnectionError();
            }
        }

        //Add new object to the list


        return true;
    }

    //Load objects
    public boolean loadObjects(){
        STATUS = DataBaseOperationText.getLoadObjectsOperation();

        if (!TABLE_CREATED) {
            STATUS += "\n" + DataBaseErrorText.getTableNotCreatedError();
            return false;
        }

        //Get connection
        Connection connection = DATA_BASE.getConnection();
        if (connection == null) {
            STATUS += "\n" + ConnectionText.getOpenConnectionError();
            return false;
        }
        STATUS += "\n" + ConnectionText.getOpenConnectionSuccess();

        //Creating Statement and Result Set
        Statement statement = null;
        ResultSet resultSet = null;

        try{
            statement = connection.createStatement();
            resultSet = statement.executeQuery(OBJECT_DB_INTERFACE.getLoadTableStatement());

            //pass result set to the subclass for creating new instances and adding them to the list

            OBJECT_DB_INTERFACE.processLoadResultSet(resultSet);
            STATUS += "\n" + ConnectionText.getLoadTableSuccess();

        } catch (SQLException e) {

            e.printStackTrace();
            STATUS += "\n" + ConnectionText.getLoadTableError();
            return false;
        }finally {
            //Close Result Set and connection;

            if (resultSet != null) {
                try {
                    resultSet.close();
                    STATUS += "\n" + ConnectionText.getCloseResultsSetSuccess();
                } catch (SQLException e) {
                    e.printStackTrace();
                    STATUS += "\n" + ConnectionText.getCloseResultSetError();
                }
            }

            if (statement != null) {
                try {
                    statement.close();
                    STATUS += "\n" + ConnectionText.getCloseStatementSuccess();
                } catch (SQLException e) {
                    e.printStackTrace();
                    STATUS += "\n" + ConnectionText.getCloseStatementError();
                }
            }

            try {
                connection.close();
                STATUS += "\n" + ConnectionText.getCloseConnectionSuccess();
            } catch (SQLException e) {
                e.printStackTrace();
                STATUS += "\n" + ConnectionText.getCloseConnectionError();
            }
        }

        return true;
    }

    //Update objects
    public boolean updateObject(DataBaseInstance dataBaseInstance){


        return true;
    }

    //Remove object




    //Getters

    public String getSTATUS() {
        return STATUS;
    }

    public boolean isTABLE_CREATED() {
        return TABLE_CREATED;
    }

}
