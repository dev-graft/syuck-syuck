package org.devgraft.sc.reactive.schedule;

import org.devgraft.sc.reactive.SshConnection;
import org.devgraft.sc.reactive.SshSessionProperties;
import org.devgraft.sc.reactive.SshSessionProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class SshKeepAliveJobBean extends QuartzJobBean {
    private final SshSessionProperties sshSessionProperties;
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("SshKeepAlive...");
        try {
            for (SshSessionProperty sshSessionProperty : sshSessionProperties.getList()) {
                SshConnection.getInstance().sendAlive(sshSessionProperty.getSessionName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
