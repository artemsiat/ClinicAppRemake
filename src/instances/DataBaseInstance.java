package instances;

import java.time.LocalDateTime;

/**
 * Created by Artem Siatchinov on 7/26/2016.
 */
public abstract class DataBaseInstance implements InstanceInterface{

    private int ID;
    private LocalDateTime WHEN_CREATED;
    private int CREATOR;
    private boolean DELETED;

    //Constructor

    public DataBaseInstance(){

        ID = 0;
        CREATOR = 0;
        DELETED = false;
    }

    //Getters


    public int getID() {
        return ID;
    }

    public LocalDateTime getWHEN_CREATED() {
        return WHEN_CREATED;
    }

    public int getCREATOR() {
        return CREATOR;
    }

    public boolean isDELETED() {
        return DELETED;
    }


    //Setters


    public void setID(int ID) {
        this.ID = ID;
    }

    public void setWHEN_CREATED(LocalDateTime WHEN_CREATED) {
        this.WHEN_CREATED = WHEN_CREATED;

    }

    public void setCREATOR(int CREATOR) {
        this.CREATOR = CREATOR;
    }

    public void setDELETED(boolean DELETED) {
        this.DELETED = DELETED;
    }
}
