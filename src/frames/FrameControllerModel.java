package frames;

import data_base.DataBaseModel;
import instances.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import programm.FrameColor;
import programm.Programm;
import programm.texts.FrameButtonText;
import programm.texts.FrameLabelText;

import java.time.LocalDateTime;

/**
 * Created by Artem Siatchinov on 7/15/2016.
 */
public class FrameControllerModel {

    private Programm PROGRAMM;
    private String TYPE;
    private FrameModel FRAME_MODEL;
    private DataBaseModel DATA_BASE_MODEL;

    //Person Labels

    @FXML private Label LAST_NAME_LABEL;
    @FXML private Label FIRST_NAME_LABEL;
    @FXML private Label MIDDLE_NAME_LABEL;
    @FXML private Label DOB_LABEL;
    @FXML private Label PHONE_LABEL;
    @FXML private Label EMAIL_LABEL;


    //Person Fields

    @FXML private TextField LAST_NAME_FIELD;
    @FXML private TextField FIRST_NAME_FIELD;
    @FXML private TextField MIDDLE_NAME_FIELD;
    @FXML private DatePicker DOB_DATE_PICKER;
    @FXML private TextField PHONE_FIELD;
    @FXML private TextField EMAIL_FIELD;

    //Person Buttons
    @FXML private Button ADD_PERSON_BUTTON;
    @FXML private Button UPDATE_PERSON_BUTTON;
    @FXML private Button DELETE_PERSON_BUTTON;

    //Create Drop Table Labels and buttons
    @FXML private Label TABLE_IS_CREATED_LABEL;
    @FXML private Label TABLE_CREATE_STATUS_LABEL;
    @FXML private Button CREATE_TABLE_BUTTON;
    @FXML private Button DROP_TABLE_BUTTON;
    @FXML private Button CHECK_TABLE_BUTTON;

    //Frame Buttons
    @FXML private Button EXIT_BUTTON;

    //Text Area Log Info
    @FXML private TextArea LOG_TEXT_AREA;
    private String LOG_INFO = "";

    //Controller

    public FrameControllerModel(Programm PROGRAMM, String type, FrameModel frameModel, DataBaseModel dataBaseModel){
        this.PROGRAMM = PROGRAMM;
        this.TYPE = type;
        this.FRAME_MODEL = frameModel;
        this.DATA_BASE_MODEL = dataBaseModel;

    }




    //Prepares the frame for display

    /**
     * Sets the followding labels text
     * First name
     * Last name
     * Middle name
     *
     * Date of birth
     * Phone number
     * e-mail
     */
    public void setPersonLabelText() {

        LAST_NAME_LABEL.setText(FrameLabelText.getLastNameLabel());
        FIRST_NAME_LABEL.setText(FrameLabelText.getFirstNameLabel());
        MIDDLE_NAME_LABEL.setText(FrameLabelText.getMiddleNameLabel());

        DOB_LABEL.setText(FrameLabelText.getDobLabel());
        PHONE_LABEL.setText(FrameLabelText.getPhoneLabel());
        EMAIL_LABEL.setText(FrameLabelText.getEmailLabel());

    }



    /**
     * Set buttons text for the following buttons:
     *
     * Add instance
     * Remove instance
     * Update instance
     *
     */
    public void setPersonButtonsText(){

        ADD_PERSON_BUTTON.setText(FrameButtonText.getAddButtonText());
        UPDATE_PERSON_BUTTON.setText(FrameButtonText.getUpdateButtonText());
        DELETE_PERSON_BUTTON.setText(FrameButtonText.getRemoveButtonText());
    }

    /**
     * Sets the label and buttons text for the following:
     *
     * Create table button
     * Drop table button
     * Check if table is created button
     *
     * Label (is table created)
     */
    public void setTableLabelsButtons(){
        CREATE_TABLE_BUTTON.setText(FrameButtonText.getTableCreateButton());
        DROP_TABLE_BUTTON.setText(FrameButtonText.getTableDropButton());
        CHECK_TABLE_BUTTON.setText(FrameButtonText.getTableUpdateButton());

        TABLE_IS_CREATED_LABEL.setText(FrameLabelText.getTableStatusLabel());

    }

    /**
     * Sets the table is created status label
     *
     */
    public void setTableCreatedStatus(boolean status) {
        if (status){
            TABLE_CREATE_STATUS_LABEL.setText(FrameLabelText.getTableCreatedStatusPos());
            TABLE_CREATE_STATUS_LABEL.setTextFill(FrameColor.getColorSucess());
        }
        else {
            TABLE_CREATE_STATUS_LABEL.setText(FrameLabelText.getTableCreatedStatusNeg());
            TABLE_CREATE_STATUS_LABEL.setTextFill(FrameColor.getColorError());
        }
    }



    //Buttons Table

    @FXML void createTableBtnAction(ActionEvent event) {
/*        boolean status = DATA_BASE_MODEL.createTable();
        LOG_INFO = DATA_BASE_MODEL.getSTATUS() + "\n" + "\n" + LOG_INFO;

        setTableCreatedStatus(DATA_BASE_MODEL.isTABLE_CREATED());
        displayLog();*/

        boolean status = DATA_BASE_MODEL.createNewTable();
        LOG_INFO = DATA_BASE_MODEL.getSTATUS() + "\n" + "\n" + LOG_INFO;

        setTableCreatedStatus(DATA_BASE_MODEL.isTABLE_CREATED());
        displayLog();

    }

    @FXML void dropTableBtnAction(ActionEvent event) {
        boolean status = DATA_BASE_MODEL.dropTable();
        LOG_INFO = DATA_BASE_MODEL.getSTATUS() + "\n" + "\n" + LOG_INFO;

        setTableCreatedStatus(DATA_BASE_MODEL.isTABLE_CREATED());
        displayLog();
    }

    /**
     * Calls the DataBaseModel to check if table is created
     * All DataBase Instances inherit from DataBaseModel
     *
     */
    @FXML void checkTableBtnAction(ActionEvent event) {
        boolean status = DATA_BASE_MODEL.checkTable();
        LOG_INFO = DATA_BASE_MODEL.getSTATUS() + "\n" + "\n" + LOG_INFO;

        setTableCreatedStatus(DATA_BASE_MODEL.isTABLE_CREATED());
        displayLog();
    }

    private void displayLog() {

        LOG_TEXT_AREA.setText(LOG_INFO);
    }




    //Buttons Frame

    @FXML void exitBtnAction(ActionEvent event) {
        FRAME_MODEL.stop();
    }


    //Helper methods
    public Person createPerson(Person person) {

        person.setFIRST_NAME(FIRST_NAME_FIELD.getText());
        person.setMIDDLE_NAME(MIDDLE_NAME_FIELD.getText());
        person.setLAST_NAME(LAST_NAME_FIELD.getText());

        person.setDOB(DOB_DATE_PICKER.getValue());
        person.setPHONE(PHONE_FIELD.getText());
        person.setEMAIL(EMAIL_FIELD.getText());

        LocalDateTime created = LocalDateTime.now();
        person.setWHEN_CREATED(created);
        person.setDELETED(false);

        return person;
    }



    //Getters TextFields

    public TextField getLAST_NAME_FIELD() {
        return LAST_NAME_FIELD;
    }

    public TextField getFIRST_NAME_FIELD() {
        return FIRST_NAME_FIELD;
    }

    public TextField getMIDDLE_NAME_FIELD() {
        return MIDDLE_NAME_FIELD;
    }

    public DatePicker getDOB_DATE_PICKER() {
        return DOB_DATE_PICKER;
    }

    public TextField getPHONE_FIELD() {
        return PHONE_FIELD;
    }

    public TextField getEMAIL_FIELD() {
        return EMAIL_FIELD;
    }

}
