package zone.dragon.dropwizard;

import java.util.Arrays;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.dropwizard.Configuration;
import lombok.Data;
import zone.dragon.dropwizard.jmx.authenticators.SimpleUserPasswordAuthenticator;
import zone.dragon.dropwizard.jmx.connectors.JMiniXConnectorFactory;
import zone.dragon.dropwizard.jmx.connectors.JolokiaConnectorFactory;

/**
 * @author Bryan Harclerode
 * @date 9/23/2016
 */
@Data
public class TestConfig extends Configuration {

    @Valid
    @NotNull
    private JmxConfiguration jmx = new JmxConfiguration();

    public TestConfig() {
        jmx.setAuthenticator(new SimpleUserPasswordAuthenticator("foo", "bar"));
        jmx.setConnectors(Arrays.asList(new JMiniXConnectorFactory(), new JolokiaConnectorFactory()));
    }
}
