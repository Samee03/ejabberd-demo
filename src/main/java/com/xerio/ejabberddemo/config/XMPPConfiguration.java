package com.xerio.ejabberddemo.config;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class XMPPConfiguration {
    @Bean
    public XMPPTCPConnection xmppConnection() throws IOException {
        XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                .setXmppDomain("localhost")
                .setHost("127.0.0.1")
                .setPort(5222)
                .setDnssecMode(ConnectionConfiguration.DnssecMode.disabled)
                .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                .setConnectTimeout(5000)
                .build();

        return new XMPPTCPConnection(config);
    }

    @Bean
    public ChatManager chatManager(AbstractXMPPConnection xmppConnection) {
        return ChatManager.getInstanceFor(xmppConnection);
    }
}
