package com.github.avchu;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.*;

import javax.validation.constraints.*;

public class WebRTCIPReplaceConfiguration extends Configuration {
    @JsonProperty("stunPort")
    Integer stunPort;

    @JsonProperty("stunHost")
    String stunHost;

    public void setStunHost(String stunHost) {
        this.stunHost = stunHost;
    }

    public String getStunHost() {
        return stunHost;
    }

    public Integer getStunPort() {
        return stunPort;
    }

    public void setStunPort(Integer stunPort) {
        this.stunPort = stunPort;
    }
}
