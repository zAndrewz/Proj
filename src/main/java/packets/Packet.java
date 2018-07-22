package packets;

import java.io.Serializable;

public class Packet <T extends Serializable> implements Serializable {
    public static final int COMMON_MESSAGE = 0;
    public static final int LIST_OF_CLIENTS = 1;
    private int type;
    private T message;

    public Packet(){}

    public Packet(int type, T message)
    {
        this.type = type;
        this.message = message;
    }

    public int getType() {return this.type;}

    public void setType(int type) {this.type = type;}

    public T getMessage() {return this.message;}

    public void setMessage(T message) { this.message = message;}

    @Override
    public String toString() {
        return "Packet[" +
                "type=" +type +
                ", message" + message +
                "]";
    }
}
