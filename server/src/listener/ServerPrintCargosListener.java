package listener;

import enums.Protocol;
import eventListener.Listener;
import events.PrintCargosEvent;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServerPrintCargosListener implements Listener<PrintCargosEvent> {
    private Protocol protocol;
    private ObjectOutputStream objectOut;
    private ByteArrayOutputStream byteArrayOut;
    private DatagramSocket datagramSocket;
    private InetAddress iAdress;
    private int port;

    public ServerPrintCargosListener(ObjectOutputStream objectOut) {
        this.protocol = Protocol.TCP;
        this.objectOut = objectOut;
    }

    public ServerPrintCargosListener(ObjectOutputStream objectOut, ByteArrayOutputStream byteArrayOut, DatagramSocket datagramSocket, InetAddress iAdress, int port) {
        this.protocol = Protocol.UDP;
        this.objectOut = objectOut;
        this.byteArrayOut = byteArrayOut;
        this.datagramSocket = datagramSocket;
        this.iAdress = iAdress;
        this.port = port;
    }

    @Override
    public void onEvent(PrintCargosEvent event) {
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
