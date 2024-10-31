package listener;

import enums.Protocol;
import eventListener.Listener;
import events.OwnerEvent;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientCustomerEventListener implements Listener<OwnerEvent> {

    private Protocol protocol;
    private ObjectOutputStream objectOut;
    private DataOutputStream dataOut;
    private DatagramSocket datagramSocket;
    private InetAddress address;
    private int port;


    public ClientCustomerEventListener(ObjectOutputStream objectOut, DataOutputStream dataOut) {
        this.protocol = Protocol.TCP;
        this.objectOut = objectOut;
        this.dataOut = dataOut;
    }

    public ClientCustomerEventListener(DatagramSocket datagramSocket, InetAddress address, int port) {
        this.protocol = Protocol.UDP;
        this.datagramSocket = datagramSocket;
        this.address = address;
        this.port = port;
    }

    @Override
    public void onEvent(OwnerEvent event) {
        try {
            if (protocol.equals(Protocol.UDP)) {
                ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
                ObjectOutputStream objectOut = new ObjectOutputStream(byteArrayOut);
                DataOutputStream dataOut = new DataOutputStream(byteArrayOut);
                dataOut.writeChar('o');
                dataOut.flush();
                byte[] sendData = byteArrayOut.toByteArray();
                DatagramPacket packetOut = new DatagramPacket(sendData, sendData.length, address, port);
                datagramSocket.send(packetOut);
                dataOut.close();
                byteArrayOut.close();
                objectOut.writeObject(event);
                objectOut.flush();
                sendData = byteArrayOut.toByteArray();
                packetOut = new DatagramPacket(sendData, sendData.length, address, port);
                datagramSocket.send(packetOut);
                objectOut.close();
                byteArrayOut.close();
            } else {
                dataOut.writeChar('o');
                objectOut.writeObject(event);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
