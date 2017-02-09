package zone.dragon.dropwizard;

import io.dropwizard.Configuration;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Bryan Harclerode
 * @date 9/23/2016
 */
@Data
public class TestConfig extends Configuration {

    @Valid
    @NotNull
    private JmxConfiguration jmx;

}
