package zone.dragon.dropwizard;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Bryan Harclerode
 * @date 10/29/2019
 */
@ExtendWith(DropwizardExtensionsSupport.class)
public class JmxBundleTest {

    private DropwizardAppExtension<TestConfig> app = new DropwizardAppExtension<>(TestApplication.class, new TestConfig());


    @Test
    void testAppStartup() {
        assertThat(app.getEnvironment()).isNotNull();
    }

}
