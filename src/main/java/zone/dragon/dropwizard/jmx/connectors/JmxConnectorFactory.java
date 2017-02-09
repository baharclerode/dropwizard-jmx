package zone.dragon.dropwizard.jmx.connectors;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import io.dropwizard.setup.Environment;

import javax.management.remote.JMXAuthenticator;

/**
 * @author Darth Android
 * @date 2/8/2017
 */
@JsonTypeInfo(use = Id.NAME, property = "type")
public interface JmxConnectorFactory {
    void applyConnector(Environment environment, JMXAuthenticator authenticator) throws Exception;
}
