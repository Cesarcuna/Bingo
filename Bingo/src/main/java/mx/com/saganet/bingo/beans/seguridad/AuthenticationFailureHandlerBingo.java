package mx.com.saganet.bingo.beans.seguridad;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

public class AuthenticationFailureHandlerBingo extends SimpleUrlAuthenticationFailureHandler {
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationFailureHandlerBingo.class);

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		if (exception.getClass().isAssignableFrom(BadCredentialsException.class)) {
			setDefaultFailureUrl("/app/login?error=bce");
		} else if (exception.getClass().isAssignableFrom(DisabledException.class)) {
			setDefaultFailureUrl("/app/login?error=de");
		} else if (exception.getClass().isAssignableFrom(SessionAuthenticationException.class)) {
			setDefaultFailureUrl("/app/login?error=sae");
		} else {
			setDefaultFailureUrl("/app/login?error=" + exception.getClass().getSimpleName());
		}

		logger.debug("Exception: {} -> {}", exception.getClass().getName(), exception.getMessage());

		super.onAuthenticationFailure(request, response, exception);
	}

}
