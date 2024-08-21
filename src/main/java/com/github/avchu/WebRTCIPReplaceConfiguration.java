package com.github.avchu;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.health.conf.HealthConfiguration;
import javax.validation.Valid;
import org.hibernate.validator.constraints.*;

import javax.validation.constraints.*;

public class WebRTCIPReplaceConfiguration extends Configuration {
    @JsonProperty("stunPort")
    Integer stunPort;

    @JsonProperty("stunHost")
    String stunHost;

    @Valid
    @NotNull
    @JsonProperty("health")
    private HealthConfiguration healthConfiguration = new HealthConfiguration();

    public HealthConfiguration getHealthConfiguration() {
        return healthConfiguration;
    }

    public void setHealthConfiguration(final HealthConfiguration healthConfiguration) {
        this.healthConfiguration = healthConfiguration;
    }

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
