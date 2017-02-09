package zone.dragon.dropwizard;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * @author Bryan Harclerode
 * @date 9/23/2016
 */
public class TestApplication extends Application<TestConfig> {

    @Override
    public void initialize(Bootstrap<TestConfig> bootstrap) {
        bootstrap.addBundle(new JmxBundle<>(TestConfig::getJmx));
    }

    @Override
    public void run(TestConfig testConfig, Environment environment) throws Exception {
    }

    public static void main(String[] args) throws Exception {
        TestApplication app = new TestApplication();
        app.run(args);
    }

}
