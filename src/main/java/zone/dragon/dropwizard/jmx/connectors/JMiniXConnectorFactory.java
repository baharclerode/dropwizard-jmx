package zone.dragon.dropwizard.jmx.connectors;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.dropwizard.setup.Environment;
import lombok.Data;
import org.jminix.console.servlet.MiniConsoleServlet;

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
@JsonTypeName("jminix")
public class JMiniXConnectorFactory implements JmxConnectorFactory {
    @Valid
    @Pattern(regexp = "\\w+")
    @NotNull
    private String path = "jmx";

    @Override
    public void applyConnector(Environment environment, JMXAuthenticator authenticator) {
        Dynamic jmxServlet = environment.admin().addServlet("jminix", MiniConsoleServlet.class);
        jmxServlet.addMapping(String.format("/%s/*", getPath()));
    }
}
