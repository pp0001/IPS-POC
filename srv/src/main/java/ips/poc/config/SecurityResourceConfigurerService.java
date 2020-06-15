package ips.poc.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import com.sap.xs2.security.commons.SAPPropertyPlaceholderConfigurer;

@Configuration
@EnableWebSecurity
@EnableResourceServer
public class SecurityResourceConfigurerService extends ResourceServerConfigurerAdapter {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void configure(HttpSecurity http) throws Exception {

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);

		http.authorizeRequests() // configure authorization of requests
				.accessDecisionManager(accessDecisionManager()) // enable OAuth2 checks

				.anyRequest().permitAll(); // Require authentication for any other request not covered above
	}

	@Bean
	protected UnanimousBased accessDecisionManager() {
		List<AccessDecisionVoter<? extends Object>> voterList = new ArrayList<>();
		WebExpressionVoter expressionVoter = new WebExpressionVoter();
		expressionVoter.setExpressionHandler(new OAuth2WebSecurityExpressionHandler());
		voterList.add(expressionVoter);
		voterList.add(new AuthenticatedVoter());
		return new UnanimousBased(voterList);
	}

	private String getXSAppName(String plan) {
		SAPPropertyPlaceholderConfigurer propertyHolder = new SAPPropertyPlaceholderConfigurer();
		String xsAppName = propertyHolder.readVcapServices("xs.appname", plan);
		if (xsAppName == null || xsAppName.isEmpty()) {
			String m = "Could not get property xs.appname from environment for plan <" + plan + ">.";
			logger.error(m);
			// this result is only for tests
			return "local-xsapp";
		}
		logger.info("GET xsAppName: {}", xsAppName);
		return xsAppName;
	}

}