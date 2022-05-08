package org.devgraft.sc;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.junit.jupiter.api.Test;

public class SshTest {
    //여기에 .pem 파일의 절대경로를 지정한다.
    private static String keyname = "/Users/pcloud/workspace/AWS_KeyPair/devgraft.pem";
    //여기에 EC2 instance 도메인 주소를 적는다.
    private static String publicDNS = "ec2-3-39-246-41.ap-northeast-2.compute.amazonaws.com";

    @Test
    void sshConnectionTest() throws Exception {
        try{
            JSch jsch=new JSch();

            String user = "ubuntu";
            String host = publicDNS;
            int port = 22;
            String privateKey = keyname;

            jsch.addIdentity(privateKey);
            System.out.println("identity added ");

            Session session = jsch.getSession(user, host, port);
            System.out.println("session created.");

            // disabling StrictHostKeyChecking may help to make connection but makes it insecure
            // see http://stackoverflow.com/questions/30178936/jsch-sftp-security-with-session-setconfigstricthostkeychecking-no
            //
            session.setConfig("StrictHostKeyChecking","no");
            session.setConfig("GSSAPIAuthentication","no");
            session.setServerAliveInterval(120 * 1000);
            session.setServerAliveCountMax(1000);
            session.setConfig("TCPKeepAlive","yes");

            session.connect();

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
