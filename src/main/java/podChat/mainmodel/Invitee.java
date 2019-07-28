package podChat.mainmodel;

import java.io.Serializable;

public class Invitee implements Serializable {

    private long id;
    private int idType;

    public Invitee(int id, int idType) {
        this.id = id;
        this.idType = idType;
    }

    public Invitee(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }
}
