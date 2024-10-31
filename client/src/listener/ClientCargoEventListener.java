package listener;

import com.sun.xml.internal.ws.wsdl.writer.document.Port;
import enums.Protocol;
import eventListener.Listener;
import events.CargoEvent;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientCargoEventListener implements Listener<CargoEvent> {
    private Protocol protocol;
    private ObjectOutputStream objectOut;
    private DataOutputStream dataOut;
    private DatagramSocket datagramSocket;
    private InetAddress address;
    private int port;

    public ClientCargoEventListener(ObjectOutputStream objectOut, DataOutputStream dataOut) {
        this.protocol = Protocol.TCP;
        this.objectOut = objectOut;
        this.dataOut = dataOut;
    }

    public ClientCargoEventListener(DatagramSocket datagramSocket, InetAddress address, int port) {
        this.protocol = Protocol.UDP;
        this.datagramSocket = datagramSocket;
        this.address = address;
        this.port = port;
    }

    public void onEvent(CargoEvent event) {
        try {
            if (protocol.equals(Protocol.UDP)) {
                ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
                ObjectOutputStream objectOut = new ObjectOutputStream(byteArrayOut);
                objectOut.writeChar('c');
                objectOut.writeObject(event);
                objectOut.flush();
                byte[] sendData = byteArrayOut.toByteArray();
                DatagramPacket packetOut = new DatagramPacket(sendData, sendData.length, address, port);
                datagramSocket.send(packetOut);
                objectOut.close();
                byteArrayOut.close();
            } else {
                dataOut.writeChar('c');
                objectOut.writeObject(event);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
