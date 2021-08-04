package com.app.myapp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.SocketUtils;
import org.springframework.util.StringUtils;

public class SpringBootUtil {
    final static Logger log = LoggerFactory.getLogger(SpringBootUtil.class);

    public static void setRandomPort(int minPort, int maxPort) {
        try {
            String userDefinedPort = System.getProperty("server.port", System.getenv("SERVER_PORT"));
            if(StringUtils.isEmpty(userDefinedPort)) {
                int port = SocketUtils.findAvailableTcpPort(minPort, maxPort);
                System.setProperty("server.port", String.valueOf(port));
                log.info("Random Server Port is set to {}.", port);
            }
        } catch( IllegalStateException e) {
            log.warn("No port available in range 5000-5100. Default embedded server configuration will be used.");
        }
    }
}
