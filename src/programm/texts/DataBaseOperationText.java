package programm.texts;

/**
 * Created by Artem Siatchinov on 7/25/2016.
 */
public class DataBaseOperationText {

    private static String CHECK_TABLE_OPERATION = "Операция: проверка статуса таблицы. ";
    private static String CREATE_TABLE_OPERATION = "Операция: создание новой таблицы. ";
    private static String DROP_TABLE_OPERATION = "Операция: удаление существующей таблицы. ";
    private static String ADD_OBJECT_OPERATION = "Операция: добавление нового объекта в базу данных. ";
    private static String LOAD_OBJECTS_OPERATION = "Операция: загрузка базы данных. ";

    public static String getCheckTableOperation() {
        return CHECK_TABLE_OPERATION;
    }

    public static String getCreateTableOperation() {
        return CREATE_TABLE_OPERATION;
    }

    public static String getDropTableOperation() {
        return DROP_TABLE_OPERATION;
    }

    public static String getAddObjectOperation() {
        return ADD_OBJECT_OPERATION;
    }

    public static String getLoadObjectsOperation() {
        return LOAD_OBJECTS_OPERATION;
    }
}
