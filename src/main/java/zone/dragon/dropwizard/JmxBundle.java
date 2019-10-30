package zone.dragon.dropwizard;

import java.lang.management.ManagementFactory;
import java.util.function.Function;

import org.eclipse.jetty.jmx.MBeanContainer;

import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import zone.dragon.dropwizard.jmx.connectors.JmxConnectorFactory;

/**
 * Loads all configured JXM connectors upon application startup
 *
 * @author Bryan Harclerode
 * @date 9/23/2016
 */
@Slf4j
@RequiredArgsConstructor
public class JmxBundle<T> implements ConfiguredBundle<T> {

    /**
     * Configuration function, which extracts a {@link JmxConfiguration} from the application configuration
     */
    @NonNull
    private final Function<T, JmxConfiguration> configFn;

    @Override
    @SneakyThrows
    public void run(T configuration, Environment environment) {
        JmxConfiguration jmxConfig = configFn.apply(configuration);
        // Don't do anything if we don't have a config
        if (jmxConfig == null) {
            return;
        }
        // Enable Jetty-JMX if it's not already installed
        environment.lifecycle().addServerLifecycleListener(server -> {
            if (server.getBeans(MBeanContainer.class).isEmpty()) {
                MBeanContainer container = new MBeanContainer(ManagementFactory.getPlatformMBeanServer());
                server.addBean(container);
                server.addEventListener(container);
            }
        });
        // Initialize all the JXM connectors
        for (JmxConnectorFactory connectorFactory : jmxConfig.getConnectors()) {
            try {
                connectorFactory.applyConnector(environment, jmxConfig.getAuthenticator());
            } catch (Exception e) {
                log.error("Failed to initialize connector from {}", connectorFactory.getClass().getName(), e);
                throw e;
            }
        }
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) { }
}
