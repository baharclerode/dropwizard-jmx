package zone.dragon.dropwizard.jmx.connectors;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.dropwizard.setup.Environment;
import lombok.Data;
import org.jolokia.http.AgentServlet;

import javax.management.remote.JMXAuthenticator;
import javax.servlet.ServletRegistration.Dynamic;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author Darth Android
 * @date 2/8/2017
 */
@Data
@JsonTypeName("jolokia")
public class JolokiaConnectorFactory implements JmxConnectorFactory {
    @Valid
    @Pattern(regexp = "\\w+")
    @NotNull
    private String path = "jmx";

    @Override
    public void applyConnector(Environment environment, JMXAuthenticator authenticator) {
        Dynamic jmxServlet = environment.admin().addServlet("jolokia", AgentServlet.class);
        jmxServlet.addMapping(String.format("/%s/*", getPath()));
    }
}
