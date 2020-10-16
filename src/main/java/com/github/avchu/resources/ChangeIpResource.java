package com.github.avchu.resources;

import com.codahale.metrics.annotation.Timed;
import com.github.avchu.core.IPBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

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
    public Response changeIp(@PathParam("ip") String ip) {
        logger.info("Setting IP: {}", ip);
        ipBeanInstance.setIp(ip);
        return Response.ok().build();
    }
}
