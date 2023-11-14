package hr.fer.progi.looneycodes.BytePit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class BytePitApplication implements WebMvcConfigurer {

  @Bean
  public PasswordEncoder pswdEcoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("index.html");
  }

  public static void main(String[] args) {
    SpringApplication.run(BytePitApplication.class, args);
  }

}
