package com.xerio.ejabberddemo.controller;

import com.xerio.ejabberddemo.service.XMPPService;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/chat")
public class XMPPController {
    private final ChatManager chatManager;
    private final XMPPService xmppService;

    public XMPPController(ChatManager chatManager, XMPPService xmppService) {
        this.chatManager = chatManager;
        this.xmppService = xmppService;
    }

    @PostMapping("/connect")
    public ResponseEntity<Map<String, Object>> connect(
            @RequestParam("username") String username,
            @RequestParam("password") String password)
            throws SmackException, IOException, XMPPException, InterruptedException {
        if (xmppService.connect(username, password)) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", HttpStatus.OK);
            responseBody.put("message", "Connected Successfully!");

            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        } else {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", HttpStatus.UNAUTHORIZED);
            responseBody.put("message", "Connection Failed!");

            return new ResponseEntity<>(responseBody, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/disconnect")
    public String disconnect() {
        return xmppService.disconnect();
    }

    @PostMapping("/send")
    public void sendMessage(@RequestParam String recipient, @RequestParam String messageText) throws Exception {
        EntityBareJid jid = JidCreate.entityBareFrom(recipient + "@localhost");
        Chat chat = chatManager.chatWith(jid);

        chat.send(messageText);
    }
}
