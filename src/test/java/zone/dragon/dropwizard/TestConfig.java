package zone.dragon.dropwizard;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.dropwizard.Configuration;
import lombok.Data;

/**
 * @author Bryan Harclerode
 * @date 9/23/2016
 */
@Data
public class TestConfig extends Configuration {

    @Valid
    @NotNull
    private JmxConfiguration jmx = new JmxConfiguration();

}
