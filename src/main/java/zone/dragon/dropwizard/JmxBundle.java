package zone.dragon.dropwizard;

import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.jmx.MBeanContainer;
import zone.dragon.dropwizard.jmx.connectors.JmxConnectorFactory;

import java.lang.management.ManagementFactory;
import java.util.function.Function;

/**
 * Provides integration between DropWizard and HK2, allowing
 *
 * @author Bryan Harclerode
 * @date 9/23/2016
 */
@Slf4j
@RequiredArgsConstructor
public class JmxBundle<T> implements ConfiguredBundle<T> {



    @NonNull
    private final Function<T, JmxConfiguration> configFn;

    @Override
    @SneakyThrows
    public void run(T configuration, Environment environment) {
        JmxConfiguration jmxConfig = configFn.apply(configuration);



        // Enable Jetty-JMX
        environment.lifecycle().addServerLifecycleListener(server -> {
            MBeanContainer container = new MBeanContainer(ManagementFactory.getPlatformMBeanServer());
            server.addBean(container);
            server.addEventListener(container);
        });
        for (JmxConnectorFactory connectorFactory : jmxConfig.getConnectors()) {
            try {
                connectorFactory.applyConnector(environment, jmxConfig.getAuthenticator());
            } catch (Exception e) {
                log.error("Failed to initialize connector from {}", connectorFactory.getClass().getName(), e );
                throw e;
            }
        }
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) { }
}
