package listener;

import enums.Protocol;
import eventListener.Listener;
import events.PrintCustomersEvent;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServerPrintOwnersListener implements Listener<PrintCustomersEvent> {
    private Protocol protocol;
    private ObjectOutputStream objectOut;
    private ByteArrayOutputStream byteArrayOut;
    private DatagramSocket datagramSocket;
    private InetAddress iAdress;
    private int port;


    public ServerPrintOwnersListener(ObjectOutputStream objectOut) {
        this.protocol = Protocol.TCP;
        this.objectOut = objectOut;
    }

    public ServerPrintOwnersListener(ObjectOutputStream objectOut, ByteArrayOutputStream byteArrayOut, DatagramSocket datagramSocket, InetAddress iAdress, int port) {
        this.protocol = Protocol.UDP;
        this.objectOut = objectOut;
        this.byteArrayOut = byteArrayOut;
        this.datagramSocket = datagramSocket;
        this.iAdress = iAdress;
        this.port = port;
    }

    @Override
    public void onEvent(PrintCustomersEvent event) {
        try {
            if (protocol.equals(Protocol.UDP)) {
                objectOut.writeObject(event);
                byte[] responseData = byteArrayOut.toByteArray();
                DatagramPacket packetOut = new DatagramPacket(responseData, responseData.length, iAdress, port);
                datagramSocket.send(packetOut);
            } else {
                objectOut.writeObject(event);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
