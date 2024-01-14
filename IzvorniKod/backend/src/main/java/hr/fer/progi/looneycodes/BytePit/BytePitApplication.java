package hr.fer.progi.looneycodes.BytePit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;

@SpringBootApplication
//@EnableScheduling
public class BytePitApplication {

  public static void main(String[] args) {
    SpringApplication.run(BytePitApplication.class, args);
  }

  @Bean
  public PasswordEncoder pswdEcoder(){
    return new BCryptPasswordEncoder();
  }
  
  @Bean
  public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
      return factory -> {
          ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/index.html");
          factory.addErrorPages(error404Page);
      };
  }
  @Bean
  public TaskScheduler taskScheduler() {
      return new ThreadPoolTaskScheduler();
  }

}
