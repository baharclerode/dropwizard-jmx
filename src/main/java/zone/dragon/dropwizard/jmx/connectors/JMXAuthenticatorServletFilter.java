package zone.dragon.dropwizard.jmx.connectors;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.management.remote.JMXAuthenticator;
import javax.security.auth.Subject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * Servlet Filter that restricts access according to a {@link JMXAuthenticator}
 *
 * @author Bryan Harclerode
 * @date 10/30/2019
 */
@Slf4j
class JMXAuthenticatorServletFilter implements Filter {

    private final JMXAuthenticator authenticator;

    public JMXAuthenticatorServletFilter(@NonNull JMXAuthenticator authenticator) {
        this.authenticator = authenticator;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Don't need
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest && response instanceof HttpServletResponse)) {
            throw new IllegalArgumentException("This servlet filter only works on Http requests");
        }
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        try {
            String user = null;
            String password = null;
            String authorization = httpRequest.getHeader("Authorization");

            if (authorization != null) {
                if (!authorization.startsWith("Basic ")) {
                    throw new Exception("Unsupported authentication type");
                }

                String encodedUserPass = authorization.substring(6);
                String userPass = new String(Base64.getDecoder().decode(encodedUserPass), StandardCharsets.UTF_8);

                int sepIdx = userPass.indexOf(':');

                user = sepIdx > -1 ? userPass.substring(0, sepIdx) : userPass;
                password = sepIdx > -1 ? userPass.substring(sepIdx + 1) : null;
            }

            Subject result = authenticator.authenticate(new String[]{user, password});
            if (result == null) {
                throw new Exception("JMX Authenticator did not return a valid Subject");
            }
        } catch (Throwable t) {
            log.warn("Auth Failure", t);
            httpResponse.setHeader("WWW-Authenticate", "Basic");
            httpResponse.sendError(401, "Not Authenticated");
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Don't need
    }
}
