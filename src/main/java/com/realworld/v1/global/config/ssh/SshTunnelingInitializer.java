package com.realworld.v1.global.config.ssh;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import jakarta.annotation.PreDestroy;
import jakarta.validation.constraints.NotNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.Properties;

import static java.lang.System.exit;

@Slf4j
@Setter
@Component
@Validated
@ConfigurationProperties(prefix = "ssh")
public class SshTunnelingInitializer {

    @NotNull
    private String remoteJumpHost;

    @NotNull
    private String user;

    @NotNull
    private int sshPort;

    @NotNull
    private String privateKey;

//    @NotNull
//    private String databaseUrl;
//
//    @NotNull
//    private int databasePort;

    private Session session;

    @PreDestroy
    public void closeSSH() {
        if(session.isConnected()) {
            session.disconnect();
        }
    }

    public Integer buildSshConnection(String databaseUrl, int databasePort) {
        Integer forwardedPort = null;

        try{
            log.info("{}@{}:{}:{} with privateKey", user, remoteJumpHost, sshPort, databasePort);

            JSch jSch = new JSch();

            log.info("creating ssh tunneling..");
            jSch.addIdentity(privateKey);
            session = jSch.getSession(user, remoteJumpHost, sshPort);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            log.info("start creating ssh session");

            log.info("start connecting ssh connection");
            session.connect();
            log.info("success connecting ssh connection");

            log.info("start forwarding");
            forwardedPort = session.setPortForwardingL(0, databaseUrl, databasePort);
        } catch (Exception e) {
            log.info("fail to make ssh tunneling");
            this.closeSSH();
            e.printStackTrace();
            exit(1);
        }

        return forwardedPort;
    }

}
