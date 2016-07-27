package programm;


import data_base.DataBase;


/**
 * Main programm class that hold important info
 *
 * 1. DataBase
 * 2. Who is Current Administrator
 *
 *
 * Created by Artem Siatchinov on 7/14/2016.
 */
public class Programm {

    private DataBase DATA_BASE;



    public Programm(){
        DATA_BASE = new DataBase(this);
    }


    public DataBase getDATA_BASE() {
        return DATA_BASE;
    }
}
