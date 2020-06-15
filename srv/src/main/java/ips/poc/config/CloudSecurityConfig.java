package ips.poc.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

import com.sap.xs2.security.commons.SAPOfflineTokenServicesCloud;
import com.sap.xs2.security.container.SecurityContext;
import com.sap.xs2.security.container.UserInfo;
import com.sap.xs2.security.container.UserInfoException;

@Profile("cloud")
@Configuration
public class CloudSecurityConfig {
	  private final Logger logger = LoggerFactory.getLogger(this.getClass());

	  @Bean
	  protected SAPOfflineTokenServicesCloud sapOfflineTokenServices() {
	    return new SAPOfflineTokenServicesCloud();
	  }

	  /**
	   * UserInfo is provided as a Request Scoped Bean rather than pulling it directly from the
	   * SecurityContext.
	   * The advantage of this is being able to override it when run in the default profile locally,
	   * this way we can
	   * override the xsappname with a local testing default value.
	   */
	  @Bean
	  @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
	  public UserInfo userInfoBean() {
	    try {
	      return SecurityContext.getUserInfo();
	    } catch (UserInfoException e) {
	      logger.error("Error creating userInfoBean");
	      return null;
	    }
	  }
}
