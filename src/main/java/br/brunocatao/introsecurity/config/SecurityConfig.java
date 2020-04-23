package br.brunocatao.introsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private static final int UM_DIA = (int)TimeUnit.HOURS.toSeconds(1);
  private static final String CHAVE_SECRETA = "ChaveUnicaParaGerarTokens";

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
          .antMatchers("/", "/home").permitAll()
          .antMatchers("/admin").hasAuthority("ADMIN")
          .anyRequest().authenticated()
        .and()
        .formLogin()
          .loginPage("/login")
          .permitAll()
        .and()
          .logout()
          .deleteCookies("JSESSIONID")
          .permitAll()
        .and()
          .rememberMe().key(CHAVE_SECRETA).tokenValiditySeconds(UM_DIA)
        .and()
          .exceptionHandling().accessDeniedPage("/nao_autorizado");
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
        .passwordEncoder(passwordEncoder())
          .withUser("admin")
            .password(passwordEncoder().encode("admin"))
            .authorities("USER", "ADMIN").and()
          .withUser("user")
            .password(passwordEncoder().encode("user"))
            .authorities("USER");
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
