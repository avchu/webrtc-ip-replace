package com.github.avchu.core;

public class IPBean {
    private String ip;

    private static IPBean ipBeanInstance = new IPBean();

    private IPBean() {
    }

    public static IPBean getIpBeanInstance() {
        return ipBeanInstance;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
