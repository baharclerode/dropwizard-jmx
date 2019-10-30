package zone.dragon.dropwizard;

import java.util.List;

import javax.management.remote.JMXAuthenticator;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.google.common.collect.Lists;

import lombok.Data;
import zone.dragon.dropwizard.jmx.authenticators.SimpleUserPasswordAuthenticator;
import zone.dragon.dropwizard.jmx.connectors.JmxConnectorFactory;

/**
 * @author Bryan Harclerode
 * @date 2/8/2017
 */
@Data
public class JmxConfiguration {
    /**
     * Authenticator to use to secure access to the JMX connectors
     */
    @Valid
    @JsonTypeInfo(use = Id.NAME, property = "type")
    private JMXAuthenticator authenticator = new SimpleUserPasswordAuthenticator(null, null);

    /**
     * List of JXM connectors to instantiate and register when dropwizard starts
     */
    @NotNull
    @Valid
    private List<JmxConnectorFactory> connectors = Lists.newArrayList();
}
