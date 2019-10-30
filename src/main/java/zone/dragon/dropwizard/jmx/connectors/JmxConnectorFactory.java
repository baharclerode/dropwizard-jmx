package zone.dragon.dropwizard.jmx.connectors;

import javax.management.remote.JMXAuthenticator;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Environment;

/**
 * Factory interface for creating JMX connectors that expose the internal JMX server
 *
 * @author Bryan Harclerode
 * @date 2/8/2017
 */
@JsonTypeInfo(use = Id.NAME, property = "type")
public interface JmxConnectorFactory {
    /**
     * Attaches a connector to this dropwizard environment; If not a web-based connector, then this method should set up appropriate start
     * and stop hooks, such as using a {@link Managed}.
     *
     * @param environment
     *     Dropwizard environment to which the JXM interface should be attached
     * @param authenticator
     *     Authenticator for validating connections to this JXM connector
     *
     * @throws Exception
     *     If there was an error setting up and configuring this JXM connector
     */
    void applyConnector(Environment environment, JMXAuthenticator authenticator) throws Exception;
}
