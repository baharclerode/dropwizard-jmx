package zone.dragon.dropwizard.jmx.connectors;

import javax.management.remote.JMXAuthenticator;
import javax.servlet.ServletRegistration.Dynamic;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.jminix.console.servlet.MiniConsoleServlet;

import com.fasterxml.jackson.annotation.JsonTypeName;

import io.dropwizard.setup.Environment;
import lombok.Data;

/**
 * Factory that attaches a <a href="https://github.com/lbovet/jminix">JMiniX</a> servlet to the admin connector of the dropwizard
 * application
 *
 * @author Bryan Harclerode
 * @date 2/8/2017
 */
@Data
@JsonTypeName("jminix")
public class JMiniXConnectorFactory implements JmxConnectorFactory {
    /**
     * Subpath within the admin connector to which the JMiniX servlet should be bound; Defaults to {@code "jminix"}
     */
    @Pattern(regexp = "\\w+")
    @NotNull
    private String path = "jminix";

    @Override
    public void applyConnector(Environment environment, JMXAuthenticator authenticator) {
        Dynamic jmxServlet = environment.admin().addServlet("jminix", MiniConsoleServlet.class);
        jmxServlet.addMapping(String.format("/%s/*", getPath()));
    }
}
