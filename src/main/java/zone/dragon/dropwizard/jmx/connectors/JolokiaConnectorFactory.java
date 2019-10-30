package zone.dragon.dropwizard.jmx.connectors;

import javax.management.remote.JMXAuthenticator;
import javax.servlet.ServletRegistration.Dynamic;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.jolokia.http.AgentServlet;

import com.fasterxml.jackson.annotation.JsonTypeName;

import io.dropwizard.setup.Environment;
import lombok.Data;

/**
 * Factory that attaches a <a href="https://jolokia.org/">Jolokia</a> servlet to the admin connector of Dropwizard
 *
 * @author Bryan Harclerode
 * @date 2/8/2017
 */
@Data
@JsonTypeName("jolokia")
public class JolokiaConnectorFactory implements JmxConnectorFactory {

    /**
     * Subpath within the admin connector to which the Jolokia servlet should be bound; Defaults to {@code "jolokia"}
     */
    @Pattern(regexp = "\\w+")
    @NotNull
    private String path = "jolokia";

    @Override
    public void applyConnector(Environment environment, JMXAuthenticator authenticator) {
        Dynamic jmxServlet = environment.admin().addServlet("jolokia", AgentServlet.class);
        jmxServlet.addMapping(String.format("/%s/*", getPath()));
    }
}
