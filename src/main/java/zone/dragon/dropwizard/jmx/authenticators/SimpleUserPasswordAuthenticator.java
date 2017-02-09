package zone.dragon.dropwizard.jmx.authenticators;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import javax.management.remote.JMXAuthenticator;
import javax.security.auth.Subject;

/**
 * @author Darth Android
 * @date 2/9/2017
 */
@Slf4j
@JsonTypeName("userPassword")
@RequiredArgsConstructor
public class SimpleUserPasswordAuthenticator implements JMXAuthenticator {
    private final String username;
    private final String password;

    @Override
    public Subject authenticate(Object credentials) {
        if (username == null && password == null) {
            log.info("Anonymous user connected to JMX");
            return new Subject();
        }
        if (!(credentials instanceof String[])) {
            log.warn("Rejected JMX RMI connection with unknown credentials");
            throw new SecurityException("Username/Password combination was not supplied");
        }
        String clientUsername = ((String[]) credentials)[0];
        String clientPassword = ((String[]) credentials)[1];
        if ((username != null && !username.equals(clientUsername)) || (password != null && !password.equals(clientPassword))) {
            log.warn("Rejected JMX RMI connection from user '{}'", StringUtils.abbreviate(clientUsername, 50));
            throw new SecurityException("Invalid Username/Password");
        }
        return new Subject();
    }
}
