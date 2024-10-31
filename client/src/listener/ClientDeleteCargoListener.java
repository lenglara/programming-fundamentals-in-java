package listener;

import enums.Protocol;
import eventListener.Listener;
import events.IntegerEvent;
import events.OwnerEvent;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientDeleteCargoListener implements Listener<IntegerEvent> {

        private Protocol protocol;
        private ObjectOutputStream objectOut;
        private DataOutputStream dataOut;
        private DatagramSocket datagramSocket;
        private InetAddress address;
        private int port;


        public ClientDeleteCargoListener(ObjectOutputStream objectOut, DataOutputStream dataOut) {
            this.protocol = Protocol.TCP;
            this.objectOut = objectOut;
            this.dataOut = dataOut;
        }

        public ClientDeleteCargoListener(DatagramSocket datagramSocket, InetAddress address, int port) {
            this.protocol = Protocol.UDP;
            this.datagramSocket = datagramSocket;
            this.address = address;
            this.port = port;
        }

        @Override
        public void onEvent(IntegerEvent event) {
            try {
                if (protocol.equals(Protocol.UDP)) {
                    ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
                    ObjectOutputStream objectOut = new ObjectOutputStream(byteArrayOut);
                    objectOut.writeChar('d');
                    objectOut.writeObject(event);
                    objectOut.flush();
                    byte[] sendData = byteArrayOut.toByteArray();
                    DatagramPacket packetOut = new DatagramPacket(sendData, sendData.length, address, port);
                    datagramSocket.send(packetOut);
                    objectOut.close();
                    byteArrayOut.close();
                } else {
                    dataOut.writeChar('d');
                    objectOut.writeObject(event);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}
