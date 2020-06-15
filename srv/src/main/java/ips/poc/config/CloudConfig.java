package ips.poc.config;

import org.springframework.cloud.config.java.ServiceScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.sap.xs2.security.commons.SAPPropertyPlaceholderConfigurer;

@Configuration
@ServiceScan
@Profile("cloud")
public class CloudConfig {

  @Bean
  public SAPPropertyPlaceholderConfigurer getPropertyHolder() {
    return new SAPPropertyPlaceholderConfigurer();
  }
}
