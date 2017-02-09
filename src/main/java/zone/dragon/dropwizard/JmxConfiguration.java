package zone.dragon.dropwizard;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.google.common.collect.Lists;
import lombok.Data;
import zone.dragon.dropwizard.jmx.authenticators.SimpleUserPasswordAuthenticator;
import zone.dragon.dropwizard.jmx.connectors.JmxConnectorFactory;

import javax.management.remote.JMXAuthenticator;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Darth Android
 * @date 2/8/2017
 */
@Data
public class JmxConfiguration {
    @Valid
    @JsonTypeInfo(use = Id.NAME, property = "type")
    private JMXAuthenticator          authenticator = new SimpleUserPasswordAuthenticator(null, null);
    @NotNull
    @Valid
    private List<JmxConnectorFactory> connectors    = Lists.newArrayList();
}
