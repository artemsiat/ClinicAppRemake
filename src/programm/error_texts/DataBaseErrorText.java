package programm.error_texts;

/**
 * Created by Artem Siatchinov on 7/25/2016.
 */
public class DataBaseErrorText {
    private static String TABLE_NOT_CREATED_ERROR = "Ошибка: таблица не создана. ";

    public static String getTableNotCreatedError() {
        return TABLE_NOT_CREATED_ERROR;
    }
}
