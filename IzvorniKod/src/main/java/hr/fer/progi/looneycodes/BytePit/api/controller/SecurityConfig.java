package hr.fer.progi.looneycodes.BytePit.api.controller;

// spring imports
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


// local imports
import hr.fer.progi.looneycodes.BytePit.service.KorisnikDetailsService;

/**
 * Klasa sluzi za konfiguraciju security profila da mozemo koristiti Secured anotacije u kontrolerima.
 * @see Secured
 * @see KorisnikDetailsService
 */
@Configuration
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = false)
public class SecurityConfig {
  /**
   * Dozvoli sve osim onoga za kaj smo oznacili da je Secured
   */
  @Bean
  @Profile("basic-security")
  public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
      http.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());
      http.formLogin(withDefaults());
      http.httpBasic(withDefaults());
      http.csrf(AbstractHttpConfigurer::disable);
      return http.build();
  }
}
