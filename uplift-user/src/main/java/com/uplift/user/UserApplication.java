package com.uplift.user;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 用户中心服务启动类
 */
@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.uplift")
@MapperScan("com.uplift.user.mapper")
public class UserApplication {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(UserApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path", "");
        String applicationName = env.getProperty("spring.application.name");
        String nacosAddr = env.getProperty("spring.cloud.nacos.discovery.server-addr");
        String nacosNamespace = env.getProperty("spring.cloud.nacos.discovery.namespace");

        log.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local: \t\thttp://localhost:{}{}\n\t" +
                        "External: \thttp://{}:{}{}\n\t" +
                        "Nacos Addr: \t{}\n\t" +
                        "Nacos Namespace: {}\n" +
                        "----------------------------------------------------------",
                applicationName, port, path, ip, port, path, nacosAddr, nacosNamespace);
    }
}
