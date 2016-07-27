package data_base;

import instances.DataBaseInstance;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Artem Siatchinov on 7/25/2016.
 */
public interface DB_Instances_Interface {

    public String getTableCreateStatement();
    public String getTableDropStatement();
    public String getTableName();

    //Insert operation
    public String getAddPrepStatement();
    public PreparedStatement prepareAddPrepStat(PreparedStatement preparedStatement, DataBaseInstance addObject);
    public void addToListNewObject(DataBaseInstance dataBaseInstance);

    //Load operation
    public String getLoadTableStatement();
    public void processLoadResultSet(ResultSet resultSet) throws SQLException;



    //Helper methods
    public void renewList();

    //Table Methods
    public boolean createNewTable();



    public ResultSet getAddObjectResultSet(Object object);
    public Statement getRemoveObjectStatement(Object object);
    public ResultSet getUpdateObjectStatement(Object object);


}
