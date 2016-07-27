package frames.admin_frame;

import data_base.Administrators;
import data_base.DataBaseModel;
import frames.FrameControllerModel;
import frames.FrameModel;
import instances.Admin;
import instances.Person;
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


    //Controller

    public AdminFrameController(Programm programm, AdminFrame adminFrame) {

        super(programm, "admin", (FrameModel)adminFrame, (DataBaseModel)programm.getDATA_BASE().getADMINISTRATORS());

        this.PROGRAMM = programm;
        this.ADMIN_FRAME = adminFrame;
        this.ADMINISTRATORS = PROGRAMM.getDATA_BASE().getADMINISTRATORS();
        this.ADMINS_LIST = ADMINISTRATORS.getADMINISTRATORS();
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
    }

    //Table methods


    @FXML void tableClicked(Event event) {

        //Select Person
        if ((TABLE_ADMINS.getSelectionModel().getSelectedItem() != null)){

            ADMINS_LIST.get(TABLE_ADMINS.getSelectionModel().getSelectedIndex());

            //TODO TEST
            USER_NAME_FIELD.setText("Working Selection");
        }
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
        System.out.println("ADMINS Length: " + ADMINS_LIST.size());
        Admin admin = (Admin)createNewAdmin();

        admin.setUSER_NAME(USER_NAME_FIELD.getText());
        admin.setPASSWORD(PASSWORD_FIELD.getText());

        System.out.println(admin.toString());
        ADMINISTRATORS.addObject(admin);
        System.out.println("ADMINS Length: " + ADMINS_LIST.size());

    }


    @FXML void removeBtnAction(ActionEvent event) {

    }

    @FXML void updateBtnAction(ActionEvent event) {

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

        return admin;
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


}
