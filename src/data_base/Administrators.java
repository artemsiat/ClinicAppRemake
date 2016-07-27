package data_base;

import instances.Admin;
import instances.DataBaseInstance;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import programm.Programm;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * Created by Artem Siatchinov on 7/24/2016.
 */
public class Administrators extends DataBaseModel {
    //public class Administrators extends DataBaseModel implements ObjectDBInterface{

    private Programm PROGRAMM;
    private DataBase DATA_BASE;

    //ArrayList
    private ObservableList<Admin> ADMINISTRATORS;


    //Statements
    private String TABLE_NAME;
    private String CREATE_TABLE_STATEMENT ;
    private String DROP_TABLE_STATEMENT;
    private String LOAD_TABLE_STATEMENT;
    private String ADD_OBJECT_STATEMENT;


    public Administrators(DataBase dataBase, Programm programm){

        super(programm, dataBase);

        this.DATA_BASE = dataBase;
        this.PROGRAMM = programm;

        //Initializes new list of administrators
        ADMINISTRATORS = FXCollections.observableArrayList();

        //Statements
        TABLE_NAME = "ADMINS";

        CREATE_TABLE_STATEMENT ="CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("+
                "id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,"+
                "first_name varchar(25),"+
                "last_name varchar(25),"+
                "middle_name varchar(25),"+
                "dob date,"+
                "phone varchar(25),"+
                "email varchar(25),"+

                "user_name varchar(25),"+
                "password varchar(25),"+
                "creator int,"+
                "when_created timestamp,"+
                "deleted boolean)";

        DROP_TABLE_STATEMENT = "DROP TABLE IF EXISTS " + TABLE_NAME;

        LOAD_TABLE_STATEMENT = "select * from " + TABLE_NAME;

        ADD_OBJECT_STATEMENT = "insert into " + TABLE_NAME +
                "(first_name, last_name, middle_name, dob, phone, email, user_name, password, creator, when_created, deleted)" + //  11 variables
                " values(?,?,?,?,?,?,?,?,?,?,?)";

        initializer((DB_Instances_Interface)this);
    }


    //Table Methods
    public boolean createNewTable(){

        if (createTableOpertion(CREATE_TABLE_STATEMENT)) {
            renewList();
            return true;
        }
        return false;
    }

    //Table Methods

    @Override public String getTableCreateStatement() {
        return CREATE_TABLE_STATEMENT;
    }

    @Override public String getTableDropStatement() {
        return DROP_TABLE_STATEMENT;
    }

    @Override public String getTableName() {
        return TABLE_NAME;
    }




    //Insert operation

    @Override public String getAddPrepStatement() {
        return ADD_OBJECT_STATEMENT;
    }

    @Override public PreparedStatement prepareAddPrepStat(PreparedStatement preparedStatement, DataBaseInstance newAdmin) {

        Admin admin = (Admin)newAdmin;

        try {

            preparedStatement.setString(1,admin.getFIRST_NAME());
            preparedStatement.setString(2, admin.getLAST_NAME());
            preparedStatement.setString(3, admin.getMIDDLE_NAME());

            Date dob = null;
            if (admin.getDOB() != null){
                dob = Date.valueOf(admin.getDOB());
            }

            preparedStatement.setDate(4, dob);
            preparedStatement.setString(5, admin.getPHONE());
            preparedStatement.setString(6, admin.getEMAIL());

            preparedStatement.setString(7, admin.getUSER_NAME());
            preparedStatement.setString(8, admin.getPASSWORD());
            preparedStatement.setInt(9, admin.getCREATOR());

            Timestamp when_created = Timestamp.valueOf(LocalDateTime.now());
            preparedStatement.setTimestamp(10, when_created);

            preparedStatement.setBoolean(11, false);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return preparedStatement;
    }

    @Override public void addToListNewObject (DataBaseInstance dataBaseInstance) {
        Admin admin = (Admin)dataBaseInstance;
        //process to create Properties for Display
        admin.process();
        ADMINISTRATORS.add(admin);
    }



    //Load instances Operation

    @Override public String getLoadTableStatement() {
        return  LOAD_TABLE_STATEMENT;
    }

    @Override public void processLoadResultSet(ResultSet resultSet) throws SQLException {

        // overwrite existing ArrayList with new one
        renewList();

            while (resultSet.next()){
                //Create new Admin

                Admin admin = new Admin();

                admin.setID(resultSet.getInt("id"));
                admin.setFIRST_NAME(resultSet.getString("first_name"));
                admin.setLAST_NAME(resultSet.getString("last_name"));
                admin.setMIDDLE_NAME(resultSet.getString("middle_name"));

                admin.setDOB(resultSet.getDate("dob"));
                admin.setPHONE(resultSet.getString("phone"));
                admin.setEMAIL(resultSet.getString("email"));

                admin.setUSER_NAME(resultSet.getString("user_name"));
                admin.setPASSWORD(resultSet.getString("password"));
                admin.setCREATOR(resultSet.getInt("creator"));
                admin.setWHEN_CREATED(resultSet.getTimestamp("when_created").toLocalDateTime());
                admin.setDELETED(resultSet.getBoolean("deleted"));


                //will Format fields to be ready for display
                admin.process();

                System.out.println(admin.toString());
                //Add new object to the list

                ADMINISTRATORS.add(admin);

            }


    }




    @Override public ResultSet getAddObjectResultSet(Object object) {
        return null;
    }

    @Override public Statement getRemoveObjectStatement(Object object) {
        return null;
    }

    @Override public ResultSet getUpdateObjectStatement(Object object) {
        return null;
    }



    //Helper methods

    @Override public void renewList() {
        ADMINISTRATORS.clear();
    }



    //Getters

    public ObservableList<Admin> getADMINISTRATORS() {
        return ADMINISTRATORS;
    }
}
