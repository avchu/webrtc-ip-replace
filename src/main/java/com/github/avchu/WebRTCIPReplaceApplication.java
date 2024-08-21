package com.github.avchu;

import com.github.avchu.core.IPBean;
import com.github.avchu.core.stun.StunServerReceiverThread;
import com.github.avchu.resources.ChangeIpResource;
import io.dropwizard.Application;
import io.dropwizard.health.conf.HealthConfiguration;
import io.dropwizard.health.core.HealthCheckBundle;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class WebRTCIPReplaceApplication extends Application<WebRTCIPReplaceConfiguration> {

  public static void main(final String[] args) throws Exception {
    new WebRTCIPReplaceApplication().run(args);
  }

  @Override
  public String getName() {
    return "WebRTCIPReplace";
  }

  @Override
  public void initialize(final Bootstrap<WebRTCIPReplaceConfiguration> bootstrap) {
    // TODO: application initialization
    bootstrap.addBundle(new HealthCheckBundle<WebRTCIPReplaceConfiguration>() {
      @Override
      protected HealthConfiguration getHealthConfiguration(
          final WebRTCIPReplaceConfiguration configuration) {
        return configuration.getHealthConfiguration();
      }
    });
  }

  @Override
  public void run(final WebRTCIPReplaceConfiguration configuration,
      final Environment environment) throws UnknownHostException, SocketException {
    DatagramSocket datagramSocket = new DatagramSocket(
        configuration.getStunPort(),
        InetAddress.getByName(configuration.getStunHost())
    );
    StunServerReceiverThread stunServerReceiverThread = new StunServerReceiverThread(
        datagramSocket, IPBean.getIpBeanInstance()
    );
    environment.lifecycle().manage(new Managed() {
      @Override
      public void start() throws Exception {
        stunServerReceiverThread.start();
      }

      @Override
      public void stop() throws Exception {
        stunServerReceiverThread.stopStunServer();
      }
    });
    environment.jersey().register(new ChangeIpResource(IPBean.getIpBeanInstance()));
  }

}
