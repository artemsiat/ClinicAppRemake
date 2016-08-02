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
public class Administrators extends DBSecondLayer {
    //public class Administrators extends DataBaseModel implements ObjectDBInterface{

    private Programm PROGRAMM;
    private DataBase DATA_BASE;

    //ArrayList
    private ObservableList<Admin> ADMINISTRATORS;
    private ObservableList<Admin> ADMINISTRATORS_REMOVED;


    //Statements
    private String TABLE_NAME;
    private String CREATE_TABLE_STATEMENT ;
    private String LOAD_TABLE_STATEMENT;
    private String ADD_OBJECT_STATEMENT;


    //Constructor
    public Administrators(DataBase dataBase, Programm programm){

        super(programm, dataBase);

        setStatements();

        this.DATA_BASE = dataBase;
        this.PROGRAMM = programm;

        //Initializes new list of administrators
        ADMINISTRATORS = FXCollections.observableArrayList();
        ADMINISTRATORS_REMOVED = FXCollections.observableArrayList();

        //Check table if created
        checkTable();
    }

    //Initialization

    private void setStatements(){
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


        //Todo to be moved to second layer reusable code
        LOAD_TABLE_STATEMENT = "select * from " + TABLE_NAME;



        ADD_OBJECT_STATEMENT = "insert into " + TABLE_NAME +
                "(first_name, last_name, middle_name, dob, phone, email, user_name, password, creator, when_created, deleted)" + //  11 variables
                " values(?,?,?,?,?,?,?,?,?,?,?)";
    }


    //Implementing Abstract Methods

    //Table Methods

    @Override protected String getCreateTableStatement() {
        return CREATE_TABLE_STATEMENT;
    }


    @Override protected String getTableName() {
        return TABLE_NAME;
    }

    //List Methods

    @Override protected void resetList() {
        ADMINISTRATORS.clear();
        ADMINISTRATORS_REMOVED.clear();
    }


    //Object Methods


    //Load Admins

    /**
     *
     * Receives Result Set from the First layer class for processing and obtaining new Instance of admin
     * if Admin is marked as Deleted then the method will not add that instance to the list
     *
     */
    @Override protected void processLoadResultSet(ResultSet resultSet) throws SQLException {

        resetList();

        //Todo Printing result
        System.out.println();
        System.out.println("Printing Administrators in data_base.Administrators.processLoadResultSet");

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
            if (admin.isDELETED()){
                System.out.print("REMOVED ");
                System.out.println(admin.toString());
                ADMINISTRATORS_REMOVED.add(admin);
                continue;
            }
            System.out.println(admin.toString());
            //Add new object to the list

            ADMINISTRATORS.add(admin);

        }

        //Todo Printing result
        System.out.println();


    }


    //Add Admin

    @Override protected String getAddNewObjectStatement(){
        return ADD_OBJECT_STATEMENT;
    }

    @Override protected PreparedStatement prepareAddPrepStat(PreparedStatement preparedStatement, DataBaseInstance newAdmin) {

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

    @Override protected void addNewObjectToList(DataBaseInstance dataBaseInstance){
        Admin admin = (Admin)dataBaseInstance;
        admin.process();
        ADMINISTRATORS.add(admin);
    }

    //Remove Admin

    @Override protected void removeObjectFromList(DataBaseInstance dataBaseInstance) {
        ADMINISTRATORS.remove((Admin)dataBaseInstance);
        ADMINISTRATORS_REMOVED.add((Admin)dataBaseInstance);
    }


    //Update Admin

    @Override protected String getUpdateObjectStatement(DataBaseInstance oldObject, DataBaseInstance newObject){

        Admin oldAdmin = (Admin)oldObject;
        Admin newAdmin = (Admin)newObject;

        //dob = 'date',
        //dob = null,


        String dob ="";
        if (newAdmin.getDOB() != null){
            dob = "dob = '" + Date.valueOf(newAdmin.getDOB())+ "', ";
        }
        else {
            dob = "dob = null, ";
        }

        String sqlStatement =  "UPDATE " + TABLE_NAME + " SET " +
                "first_name = '" + newAdmin.getFIRST_NAME() + "', " +
                "last_name = '" + newAdmin.getLAST_NAME() + "', " +
                "middle_name = '" + newAdmin.getMIDDLE_NAME() + "', " +
                dob +
                "phone = '" + newAdmin.getPHONE() + "', " +
                "email = '" + newAdmin.getEMAIL() + "', " +
                "user_name = '" + newAdmin.getUSER_NAME() + "', " +
                "password = '" + newAdmin.getPASSWORD() + "', " +
                "deleted = '" + newAdmin.isDELETED() + "' " +
                "WHERE id =" + oldObject.getID() + ";";

        return sqlStatement;

    }

    @Override protected void updateObjectsInList(DataBaseInstance oldObject, DataBaseInstance newObject) {
        newObject.setID(oldObject.getID());
        int index = ADMINISTRATORS.indexOf((Admin)oldObject);
        ADMINISTRATORS.set(index, (Admin)newObject);
    }


    //Restore Admin

    @Override
    protected void restoreObjectsList(DataBaseInstance dataBaseInstance) {
        ADMINISTRATORS.add((Admin)dataBaseInstance);
        ADMINISTRATORS_REMOVED.remove((Admin)dataBaseInstance);
    }


    //Getters

    public ObservableList<Admin> getADMINISTRATORS() {
        return ADMINISTRATORS;
    }

    public ObservableList<Admin> getADMINISTRATORS_REMOVED() {
        return ADMINISTRATORS_REMOVED;
    }
}
