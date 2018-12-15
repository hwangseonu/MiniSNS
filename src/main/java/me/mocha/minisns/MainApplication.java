package me.mocha.minisns;

import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class MainApplication {

    private static final int PORT = 8080;

    public static void main(String[] args) throws Exception {
        String appBase = ".";
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(PORT);
        tomcat.getHost().setAppBase(appBase);
        tomcat.addWebapp("", new File("build/libs/mini-sns-1.0-SNAPSHOT.war").getAbsolutePath());
        tomcat.start();
        tomcat.getServer().await();
    }

}
