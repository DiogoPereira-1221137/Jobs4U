package lapr4.jobs4u.notification;

import java.io.Serializable;
import java.util.List;

public class CustomMessage implements Serializable {
    public int code;
//    public byte[] data1;
//    public byte[] data2;
    public List<byte[]> data;

    public CustomMessage(int code, List<byte[]> data/*byte[] data1, byte[] data2*/) {
        this.code = code;
//        this.data1 = data1;
//        this.data2 = data2;
        this.data = data;
    }
}
