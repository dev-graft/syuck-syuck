package org.devgraft.sc.reactive.schedule;

import org.devgraft.sc.reactive.SshConnection;
import org.devgraft.sc.reactive.SshSessionProperties;
import org.devgraft.sc.reactive.SshSessionProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Slf4j
@RequiredArgsConstructor
@Component
public class SshInitListener {
    private final SshSessionProperties sshSessionProperties;

    @PreDestroy
    public void onDestroy() {
        log.info("SSH Context Close...");
        for (SshSessionProperty sshSessionProperty : sshSessionProperties.getList()) {
            SshConnection.getInstance().close(sshSessionProperty.getSessionName());
        }
    }
}
