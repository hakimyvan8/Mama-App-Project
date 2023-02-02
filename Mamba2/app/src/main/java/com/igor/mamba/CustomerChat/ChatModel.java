package com.igor.mamba.CustomerChat;

public class ChatModel {
    public String id;
    public String msg_id;
    public String from_id;
    public String to_id;
    public String msgcont;
    public String sentat;

    public ChatModel(String id, String msg_id, String from_id, String to_id, String msgcont, String sentat) {
        this.id = id;
        this.msg_id = msg_id;
        this.from_id = from_id;
        this.to_id = to_id;
        this.msgcont = msgcont;
        this.sentat = sentat;
    }

    public String getId() {
        return id;
    }


    public String getMsg_id() {
        return msg_id;
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


}
