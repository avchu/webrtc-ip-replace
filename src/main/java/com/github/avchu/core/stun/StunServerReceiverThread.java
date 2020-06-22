package com.github.avchu.core.stun;

import com.github.avchu.core.IPBean;
import de.javawi.jstun.attribute.MappedAddress;
import de.javawi.jstun.attribute.MessageAttributeException;
import de.javawi.jstun.attribute.UnknownAttribute;
import de.javawi.jstun.attribute.UnknownMessageAttributeException;
import de.javawi.jstun.header.MessageHeader;
import de.javawi.jstun.header.MessageHeaderInterface;
import de.javawi.jstun.header.MessageHeaderParsingException;
import de.javawi.jstun.util.Address;
import de.javawi.jstun.util.UtilityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;

public class StunServerReceiverThread extends Thread {
    Logger logger = LoggerFactory.getLogger(StunServerReceiverThread.class);
    private Boolean doRun = true;

    private DatagramSocket receiverSocket;

    private IPBean ipBean;

    public StunServerReceiverThread(DatagramSocket receiverSocket, IPBean ipBeanInstance) {
        this.receiverSocket = receiverSocket;
        this.ipBean = ipBeanInstance;
    }

    public void run() {
        while (doRun) {
            try {
                DatagramPacket receive = new DatagramPacket(new byte[200], 200);
                receiverSocket.receive(receive);
                logger.debug(
                        receiverSocket.getLocalAddress().getHostAddress() +
                                ":" + receiverSocket.getLocalPort() +
                                " datagram received from " +
                                receive.getAddress().getHostAddress() + ":" +
                                receive.getPort());

                MessageHeader receiveMH = MessageHeader.parseHeader(receive.getData());
                try {
                    receiveMH.parseAttributes(receive.getData());
                    if (receiveMH.getType() == MessageHeaderInterface.MessageHeaderType.BindingRequest) {
                        MessageHeader sendMH = new MessageHeader(MessageHeaderInterface.MessageHeaderType.BindingResponse);
                        sendMH.setTransactionID(receiveMH.getTransactionID());
                        MappedAddress responseAddress = new MappedAddress();
                        responseAddress.setAddress(new Address(ipBean.getIp()));
                        responseAddress.setPort(12121);
                        sendMH.addMessageAttribute(responseAddress);
                        byte[] data = sendMH.getBytes();
                        DatagramPacket send = new DatagramPacket(data, data.length);
                        send.setPort(receive.getPort());
                        send.setAddress(receive.getAddress());
                        receiverSocket.send(send);
                    }
                } catch (UnknownMessageAttributeException umae) {
                    umae.printStackTrace();
                    MessageHeader sendMH = new MessageHeader(MessageHeaderInterface.MessageHeaderType.BindingErrorResponse);
                    sendMH.setTransactionID(receiveMH.getTransactionID());
                    UnknownAttribute ua = new UnknownAttribute();
                    ua.addAttribute(umae.getType());
                    sendMH.addMessageAttribute(ua);

                    byte[] data = sendMH.getBytes();
                    DatagramPacket send = new DatagramPacket(data, data.length);
                    send.setPort(receive.getPort());
                    send.setAddress(receive.getAddress());
                    receiverSocket.send(send);
                }
            } catch (SocketTimeoutException ioe) {
                // No data for SO_TIMEOUT milliseconds.
            } catch (IOException |
                    MessageHeaderParsingException |
                    UtilityException |
                    MessageAttributeException |
                    ArrayIndexOutOfBoundsException ioe) {
                ioe.printStackTrace();
            }
        }
        receiverSocket.close();
    }

    public void stopStunServer() {
        this.doRun = false;
    }
}
