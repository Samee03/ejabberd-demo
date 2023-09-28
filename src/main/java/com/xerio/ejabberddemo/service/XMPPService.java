package com.xerio.ejabberddemo.service;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jxmpp.jid.EntityBareJid;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class XMPPService {
    private final XMPPTCPConnection xmppConnection;
    private final ChatManager chatManager;

    public XMPPService(XMPPTCPConnection xmppConnection, ChatManager chatManager) {
        this.xmppConnection = xmppConnection;
        this.chatManager = chatManager;
    }

    public boolean connect(String username, String password) throws SmackException, IOException, XMPPException, InterruptedException {
        if (xmppConnection.isAuthenticated()) {
            subscribe();
            return true;
        } else if (!xmppConnection.isConnected()){
            xmppConnection.connect().login(username, password);
            subscribe();
            return true;
        }
        return false;
    }

    public String disconnect() {
        xmppConnection.disconnect();
        if (xmppConnection.isConnected())
            return "Disconnected Successfully!";
        return "Disconnect Failed!";
    }

    public void subscribe() {
        chatManager.addIncomingListener(new IncomingChatMessageListener() {
            @Override
            public void newIncomingMessage(EntityBareJid from, Message message, Chat chat) {
                System.out.println("New message from " + from + ": " + message.getBody());
            }
        });
    }
}
