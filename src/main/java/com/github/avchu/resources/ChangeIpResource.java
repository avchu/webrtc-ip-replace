package com.github.avchu.resources;

import com.codahale.metrics.annotation.Timed;
import com.github.avchu.core.IPBean;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/change")
public class ChangeIpResource {

  private IPBean ipBeanInstance;

  Logger logger = LoggerFactory.getLogger(ChangeIpResource.class);

  public ChangeIpResource(IPBean ipBeanInstance) {
    this.ipBeanInstance = ipBeanInstance;
  }

  @PATCH
  @Path("/{ip}")
  @Timed
  public Response changeIp(@PathParam("ip") String ip, @Context HttpServletRequest httpRequest) {
    logger.info("Setting IP: {}", ip);
    ipBeanInstance.setIp(httpRequest.getRemoteAddr(), ip);
    return Response.ok().build();
  }
}
