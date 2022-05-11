package org.devgraft.sc.reactive;

import com.jcraft.jsch.JSchException;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Objects;

public class SshConnectionListener implements ApplicationListener<ApplicationPreparedEvent> {
    private boolean isFirst = true;
    @Override
    public void onApplicationEvent(ApplicationPreparedEvent event) {
        if (!isFirst) return;
        isFirst = false;
        SshSessionProperties sshSessionProperties = new SshSessionProperties();
        sshSessionProperties.setList(new ArrayList<>());
        int index = 0;
        while (StringUtils.hasText(event.getApplicationContext().getEnvironment().getProperty("ssh-connection.list[" + index + "].session-name"))) {
            String sessionName = event.getApplicationContext().getEnvironment().getProperty("ssh-connection.list[" + index + "].session-name");
            String connectionHost = event.getApplicationContext().getEnvironment().getProperty("ssh-connection.list[" + index + "].connection-host");
            Integer connectionPort = Integer.parseInt(Objects.toString(event.getApplicationContext().getEnvironment().getProperty("ssh-connection.list[" + index + "].connection-port"), "-1"));
            String host = event.getApplicationContext().getEnvironment().getProperty("ssh-connection.list[" + index + "].host");
            String userName = event.getApplicationContext().getEnvironment().getProperty("ssh-connection.list[" + index + "].user-name");
            String password = event.getApplicationContext().getEnvironment().getProperty("ssh-connection.list[" + index + "].password");
            Integer lport = Integer.parseInt(Objects.toString(event.getApplicationContext().getEnvironment().getProperty("ssh-connection.list[" + index + "].lport"), "-1"));
            Integer rport = Integer.parseInt(Objects.toString(event.getApplicationContext().getEnvironment().getProperty("ssh-connection.list[" + index + "].rport"), "-1"));
            sshSessionProperties.getList().add(new SshSessionProperty(sessionName, connectionHost, connectionPort, host, userName, password, lport, rport));
            index++;
        }

        System.out.println("SSH Context initialized...");
        for (SshSessionProperty sshSessionProperty : sshSessionProperties.getList()) {
            try {
                SshConnection.getInstance().connect(sshSessionProperty);
            } catch (JSchException e) {
                e.printStackTrace();
            }
        }
    }
}
