package programm.texts;

/**
 * Created by Artem Siatchinov on 7/24/2016.
 */
public class FrameLabelText {

    //Person Label Texts
    private static String LAST_NAME_LABEL = "Фамилия";
    private static String FIRST_NAME_LABEL = "Имя";
    private static String MIDDLE_NAME_LABEL = "Отчество";
    private static String DOB_LABEL = "Дата рождения";
    private static String PHONE_LABEL = "Телефон";
    private static String EMAIL_LABEL = "E-mail";
    private static String USER_NAME_LABEL = "Логин";
    private static String PASSWORD_LABEL = "Пароль";
    private static String PASSWORD_RE_LABEL = "Пароль";

    //Table Labels Texts
    private static String TABLE_STATUS_LABEL = "Статус таблицы: ";
    private static String TABLE_CREATED_STATUS_POS = "Таблица создана";
    private static String TABLE_CREATED_STATUS_NEG = "Таблица не создана";


    public static String getLastNameLabel() {
        return LAST_NAME_LABEL;
    }

    public static String getFirstNameLabel() {
        return FIRST_NAME_LABEL;
    }

    public static String getMiddleNameLabel() {
        return MIDDLE_NAME_LABEL;
    }

    public static String getDobLabel() {
        return DOB_LABEL;
    }

    public static String getPhoneLabel() {
        return PHONE_LABEL;
    }

    public static String getEmailLabel() {
        return EMAIL_LABEL;
    }

    public static String getUserNameLabel() {
        return USER_NAME_LABEL;
    }

    public static String getPasswordLabel() {
        return PASSWORD_LABEL;
    }

    public static String getPasswordReLabel() {
        return PASSWORD_RE_LABEL;
    }

    public static String getTableStatusLabel() {
        return TABLE_STATUS_LABEL;
    }

    public static String getTableCreatedStatusNeg() {
        return TABLE_CREATED_STATUS_NEG;
    }

    public static String getTableCreatedStatusPos() {
        return TABLE_CREATED_STATUS_POS;
    }
}
