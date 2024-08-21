package com.github.avchu.core;

import java.util.concurrent.ConcurrentHashMap;

public class IPBean {

  ConcurrentHashMap<String, String> ipHashMap = new ConcurrentHashMap<>();
  private String ip;

  private static IPBean ipBeanInstance = new IPBean();

  private IPBean() {
  }

  public static IPBean getIpBeanInstance() {
    return ipBeanInstance;
  }

  public String getIp(String realIp) {
    return this.ipHashMap.get(realIp);
  }

  public void setIp(String realId, String ip) {
    this.ipHashMap.put(realId, ip);
  }
}
