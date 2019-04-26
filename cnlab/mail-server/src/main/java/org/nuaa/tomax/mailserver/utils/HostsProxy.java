package org.nuaa.tomax.mailserver.utils;

/**
 * @Name: HostsProxy
 * @Description: TODO
 * @Author: tomax
 * @Date: 2019-04-25 23:42
 * @Version: 1.0
 */
public class HostsProxy {
    public static int queryLocalhostPort(String host) {
        switch (host) {
            case "tomax.xin": return 8081;
            case "toyer.xyz": return 8082;
            default: return 25;
        }
    }

    public static String queryLocalhostAddress(String host) {
        return "127.0.0.1";
    }
}
