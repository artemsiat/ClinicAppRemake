package frames.admin_frame;

import data_base.Administrators;
import data_base.DBSecondLayer;
import frames.FrameControllerModel;
import frames.FrameModel;
import instances.Admin;
import instances.Person;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import programm.Programm;
import programm.texts.FrameLabelText;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Artem Siatchinov on 7/15/2016.
 */
public class AdminFrameController extends FrameControllerModel implements Initializable  {

    private Programm PROGRAMM;
    private AdminFrame ADMIN_FRAME;
    private Administrators ADMINISTRATORS;

    private ObservableList<Admin> ADMINS_LIST;

    private Admin SELECTED_ADMIN;

    //Admin fields

    @FXML private TextField USER_NAME_FIELD;
    @FXML private PasswordField PASSWORD_FIELD;
    @FXML private PasswordField PASSWORD_RE_FIELD;

    //Admin Lables

    @FXML private Label USER_NAME_LABEL;
    @FXML private Label PASSWORD_LABEL;
    @FXML private Label PASSWORD_RE_LABEL;


    //Table Admins
    @FXML private TableView<Admin> TABLE_ADMINS;
    @FXML private TableColumn<Admin, String> FULL_NAME_COL;
    @FXML private TableColumn<Admin, String> PHONE_COL;
    @FXML private TableColumn<Admin, String> DOB_COL;
    @FXML private TableColumn<Admin, String> EMAIL_COL;

    //Restore
    @FXML private CheckBox SHOW_REMOVED_CHECKBOX;
    @FXML private Button RESTORE_PERSON_BUTTON;

    //Controller

    public AdminFrameController(Programm programm, AdminFrame adminFrame) {

        super(programm, "admin", (FrameModel)adminFrame, (DBSecondLayer)programm.getDATA_BASE().getADMINISTRATORS());

        this.PROGRAMM = programm;
        this.ADMIN_FRAME = adminFrame;
        this.ADMINISTRATORS = PROGRAMM.getDATA_BASE().getADMINISTRATORS();
        this.ADMINS_LIST = ADMINISTRATORS.getADMINISTRATORS();
        this.SELECTED_ADMIN = null;
    }

    // Initialization

    @Override public void initialize(URL location, ResourceBundle resources) {

        createAdminTable();

        setPersonLabelText();
        setAuthLabelsText();
        setPersonButtonsText();
        setTableLabelsButtons();

        //Set the status of the tableIsCreated
        setTableCreatedStatus();
        setCheckBoxListener();
    }

    //Table methods


    @FXML void tableClicked(Event event) {

        //Select Person
        if ((TABLE_ADMINS.getSelectionModel().getSelectedItem() != null)){

            SELECTED_ADMIN = ADMINS_LIST.get(TABLE_ADMINS.getSelectionModel().getSelectedIndex());

            setSelectedPersonFields(SELECTED_ADMIN);
            setSelectedAdminFields();
        }
    }

    private void setSelectedAdminFields() {

        USER_NAME_FIELD.setText(SELECTED_ADMIN.getUSER_NAME());
        PASSWORD_FIELD.setText(SELECTED_ADMIN.getPASSWORD());
        PASSWORD_RE_FIELD.setText(SELECTED_ADMIN.getPASSWORD());

    }

    private void createAdminTable() {

        FULL_NAME_COL.setCellValueFactory(new PropertyValueFactory<Admin, String>("FULL_NAME_PROPERTY"));
        PHONE_COL.setCellValueFactory(new PropertyValueFactory<Admin, String>("PHONE_NUMBER_PROPERTY"));
        DOB_COL.setCellValueFactory(new PropertyValueFactory<Admin, String >("DATE_OF_BIRTH_PROPERTY"));
        EMAIL_COL.setCellValueFactory(new PropertyValueFactory<Admin, String>("EMAIL_PROPERTY"));

        TABLE_ADMINS.setItems(ADMINS_LIST);

    }

    private void setTableCreatedStatus() {
        setTableCreatedStatus(ADMINISTRATORS.isTABLE_CREATED());
    }



    //Buttons Admins

    @FXML void addBtnAction(ActionEvent event) {

        //TODO check that all inputs are ok

        //TODO check that similar admin has not been created

        //TODO check that login is free to take

        //TODO check that two password fields match

        //create new person type admins
        Admin admin = (Admin)createNewAdmin();



        ADMINISTRATORS.addObject(admin);

    }


    @FXML void removeBtnAction(ActionEvent event) {
        if (SELECTED_ADMIN != null){
            if (ADMINISTRATORS.removeObject(SELECTED_ADMIN)){
                diselectAdmin();
            }
        }
    }

    @FXML void updateBtnAction(ActionEvent event) {

        if (SELECTED_ADMIN != null){
            if (ADMINISTRATORS.updateObject(SELECTED_ADMIN, createNewAdmin())){

                diselectAdmin();
            }
        }

    }
    @FXML void restoreBtnAction(ActionEvent event) {

        if (SELECTED_ADMIN != null){
            if (ADMINISTRATORS.restoreObject(SELECTED_ADMIN)){

                diselectAdmin();
            }
        }
    }

    private void diselectAdmin() {
        SELECTED_ADMIN = null;
        TABLE_ADMINS.getSelectionModel().select(null);
        clearAdminFields();

    }

    private void clearAdminFields() {

        clearPersonFields();

        USER_NAME_FIELD.setText("");
        PASSWORD_FIELD.setText("");
        PASSWORD_RE_FIELD.setText("");
    }


    //Initialization

    /**
     * Sets authentication labels:
     *
     * user name
     * password
     * re-type password
     */
    public void setAuthLabelsText(){

        USER_NAME_LABEL.setText(FrameLabelText.getUserNameLabel());
        PASSWORD_LABEL.setText(FrameLabelText.getPasswordLabel());
        PASSWORD_RE_LABEL.setText(FrameLabelText.getPasswordReLabel());

    }



    //Helper methods
    private Admin createNewAdmin(){

        Person person = new Admin();
        createPerson(person);

        Admin admin = (Admin)person;

        admin.setUSER_NAME(USER_NAME_FIELD.getText());
        admin.setPASSWORD(PASSWORD_FIELD.getText());

        return admin;
    }

    private void setSelectedFields(){

    }

    public boolean loginPassChecker(){
        String login = USER_NAME_FIELD.getText();
        String password = PASSWORD_FIELD.getText();
        String passwordRetype = PASSWORD_RE_FIELD.getText();

        //Check the length of user name and password
        if (login.length() < 3 ){

        }

        //check if passwords match

        return true;
    }

    private void setCheckBoxListener(){
        SHOW_REMOVED_CHECKBOX.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                System.out.println("New value : " + newValue);
                if (newValue){
                    ADMINS_LIST = ADMINISTRATORS.getADMINISTRATORS_REMOVED();
                }
                else {
                    ADMINS_LIST = ADMINISTRATORS.getADMINISTRATORS();
                }
                TABLE_ADMINS.setItems(ADMINS_LIST);
            }
        });
    }

}
