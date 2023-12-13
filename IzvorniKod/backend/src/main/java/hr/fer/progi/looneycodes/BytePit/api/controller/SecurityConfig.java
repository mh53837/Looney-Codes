package hr.fer.progi.looneycodes.BytePit.api.controller;

// spring imports
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

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
      http.httpBasic(withDefaults());
      http.csrf(AbstractHttpConfigurer::disable);
      return http.build();
  }
  /**
   * slozi login/logout rute za AuthenticationPrincipal
   * @see AuthenticationPrincipal
   */
  @Bean
  @Profile("form-security")
  public SecurityFilterChain spaFilterChain(HttpSecurity http) throws Exception {
      http.authorizeHttpRequests(authorize -> authorize
          .requestMatchers(new AntPathRequestMatcher("/login")).permitAll()
          .anyRequest().authenticated());
      http.formLogin(configurer -> {
              configurer.successHandler((request, response, authentication) ->
                      response.setStatus(HttpStatus.NO_CONTENT.value())
                  )
                  .failureHandler(new SimpleUrlAuthenticationFailureHandler());
          }
      );
      http.exceptionHandling(configurer -> {
          final RequestMatcher matcher = new NegatedRequestMatcher(
              new MediaTypeRequestMatcher(MediaType.TEXT_HTML));
          configurer
              .defaultAuthenticationEntryPointFor((request, response, authException) -> {
                  response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
              }, matcher);
      });
      http.logout(configurer -> configurer
          .logoutUrl("/logout")
          .logoutSuccessHandler((request, response, authentication) ->
              response.setStatus(HttpStatus.NO_CONTENT.value())));
      http.csrf(AbstractHttpConfigurer::disable);
      return http.build();
  }
}
