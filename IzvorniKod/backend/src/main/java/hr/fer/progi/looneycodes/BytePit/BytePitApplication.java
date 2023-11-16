package hr.fer.progi.looneycodes.BytePit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;

@SpringBootApplication
public class BytePitApplication implements WebMvcConfigurer {


  @Bean
  public PasswordEncoder pswdEcoder(){
    return new BCryptPasswordEncoder();
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("index.html");
  }

  @Bean
  public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
      return factory -> {
          ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/index.html");
          factory.addErrorPages(error404Page);
      };
  }
  
   public static void main(String[] args) {
    SpringApplication.run(BytePitApplication.class, args);
  }

}
