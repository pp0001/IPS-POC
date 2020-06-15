package ips.poc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) 
@Profile("dev")
public class LocalSecurityWebConfig extends WebSecurityConfigurerAdapter {
  
  @Override 
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/odata/v2/IPSService/**");
  }
  
}
