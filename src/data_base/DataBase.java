package data_base;

import programm.Programm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Primary Data Base class
 * Will open Connection
 * Will close Connection
 * Will hold all instances
 * to load  , add remove , update data base
 * Created by Artem Siatchinov on 7/17/2016.
 */
public class DataBase {

    private Programm PROGRAMM;
    private Connection CONNECTION;

    private Administrators ADMINISTRATORS;

    private String URL = "jdbc:h2:./clinicDB;mv_store=false";
    private String DRIVER = "org.h2.Driver";

    public DataBase(Programm programm){
        this.PROGRAMM = programm;
        ADMINISTRATORS = new Administrators(this, PROGRAMM);
        ADMINISTRATORS.loadObjects();
    }

    //Load the rest of the Instances

    public void loadMainDB(){
        //Load Patients, Doctors, Working Days etc.
    }





    //Getters

    public Administrators getADMINISTRATORS() {
        return ADMINISTRATORS;
    }





    //Connection Methods

    public Connection getConnection(){

        try {
            if (CONNECTION == null || CONNECTION.isClosed()) {
                Class.forName(DRIVER);
                CONNECTION = DriverManager.getConnection(URL);
            }
            return CONNECTION;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

    public boolean closeConnection(){
        //if Connection is not closed
        try {
            if (!CONNECTION.isClosed()) {

                CONNECTION.close();
                return true;
            }
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


}
