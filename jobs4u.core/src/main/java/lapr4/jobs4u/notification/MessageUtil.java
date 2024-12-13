package lapr4.jobs4u.notification;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessageUtil {

    public static void writeMessage(DataOutputStream out, int code, List<byte[]> dataList /*byte[] data1, byte[] data2*/) throws IOException {
        out.writeByte(1); // Version 1
        out.writeByte(code); // Code
        // Write DATA1 array
        for (byte[] data: dataList) {
            out.writeByte(data.length & 0xFF);
            out.writeByte((data.length >> 8) & 0xFF);
            out.write(data);
        }
        // Write DATA1 length
//        if (data1 != null) {
//            out.writeByte(data1.length & 0xFF);
//            out.writeByte((data1.length >> 8) & 0xFF);
//            out.write(data1);
//        } else {
//            out.writeByte(0);
//            out.writeByte(0);
//        }
//        // Write DATA2 length
//        if (data2 != null) {
//            out.writeByte(data2.length & 0xFF);
//            out.writeByte((data2.length >> 8) & 0xFF);
//            out.write(data2);
//        } else {
//            out.writeByte(0);
//            out.writeByte(0);
//        }
        // End of message
        out.writeByte(0);
        out.writeByte(0);
    }

    public static CustomMessage readMessage(DataInputStream in) throws IOException {

        int version = in.readByte();
        if (version != 1) {
            throw new IOException("Unsupported message version: " + version);
        }
        int code = in.readByte();
        List<byte[]> data = new ArrayList<>();
        int dataLength = -1;
        int endByte1 = -1;
        int endByte2 = -1;
        while (dataLength != 0) {
            endByte1 = in.readByte();
            endByte2 = in.readByte();
            dataLength = (endByte1 & 0xFF) + ((endByte2 & 0xFF) << 8); // Ensure correct byte order
            byte[] dataP = null;
            if (dataLength > 0) {
                dataP = new byte[dataLength];
                in.readFully(dataP);
                data.add(dataP);
            }
        }
//        int data1Length = (in.readByte() & 0xFF) + ((in.readByte() & 0xFF) << 8); // Ensure correct byte order
//        byte[] data1 = null;
//        if (data1Length > 0) {
//            data1 = new byte[data1Length];
//            in.readFully(data1);
//        }
//        int data2Length = (in.readByte() & 0xFF) + ((in.readByte() & 0xFF) << 8); // Ensure correct byte order
//        byte[] data2 = null;
//        if (data2Length > 0) {
//            data2 = new byte[data2Length];
//            in.readFully(data2);
//        }
        if (endByte1 != 0 || endByte2 != 0) {
            throw new IOException("Invalid end of message");
        }
//        return new CustomMessage(code, data1, data2);
        return new CustomMessage(code, data);
    }

}
