package zone.dragon.dropwizard.jmx.authenticators;

import javax.management.remote.JMXAuthenticator;
import javax.security.auth.Subject;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JMX authenticator that authenticates against a static username and password; The connector must pass a string array with two elements,
 * the first being the username and the second being the password.
 *
 * @author Bryan Harclerode
 * @date 2/9/2017
 */
@Slf4j
@Data
@JsonTypeName("userPassword")
@RequiredArgsConstructor
public class SimpleUserPasswordAuthenticator implements JMXAuthenticator {
    /**
     * The required username, or {@code null} if any username is allowed
     */
    private final String username;

    /**
     * The required password, or {@code null} if any password is allowed
     */
    private final String password;

    @Override
    public Subject authenticate(Object credentials) {
        if (getUsername() == null && getPassword() == null) {
            log.info("Anonymous user connected to JMX");
            return new Subject();
        }
        if (!(credentials instanceof String[])) {
            log.warn("Rejected JMX RMI connection with unknown credentials");
            throw new SecurityException("Username/Password combination was not supplied");
        }
        String clientUsername = ((String[]) credentials)[0];
        String clientPassword = ((String[]) credentials)[1];
        if ((getUsername() != null && !getUsername().equals(clientUsername)) || (getPassword() != null && !getPassword().equals(
            clientPassword))) {
            log.warn("Rejected JMX RMI connection from user '{}'", StringUtils.abbreviate(clientUsername, 50));
            throw new SecurityException("Invalid Username/Password");
        }
        return new Subject();
    }
}
