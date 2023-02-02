package com.igor.mamba.DriverChat;
import java.io.Serializable;

public class ChatDriverModel {
    public String id;
    public String msg_id;
    public String receiverName;
    public String senderName;
    public String from_id;
    public String to_id;
    public String msgcont;
    public String sentat;
    public String receivernumber;
    public String sendernumber;

    public ChatDriverModel(String id, String msg_id, String receiverName,String senderName, String from_id, String to_id, String msgcont, String sentat, String receivernumber, String sendernumber) {
        this.id = id;
        this.msg_id = msg_id;
        this.receiverName = receiverName;
        this.senderName = senderName;
        this.from_id = from_id;
        this.to_id = to_id;
        this.msgcont = msgcont;
        this.sentat = sentat;
        this.receivernumber = receivernumber;
        this.sendernumber = sendernumber;
    }

    public String getId() {
        return id;
    }


    public String getMsg_id() {
        return msg_id;
    }


    public String getReceiverName() {
        return receiverName;
    }


    public String getSenderName() {
        return senderName;
    }


    public String getFrom_id() {
        return from_id;
    }


    public String getTo_id() {
        return to_id;
    }


    public String getMsgcont() {
        return msgcont;
    }


    public String getSentat() {
        return sentat;
    }

    public String getReceivernumber() {
        return receivernumber;
    }


    public String getSendernumber() {
        return sendernumber;
    }


}
