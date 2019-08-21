package io.github.oxnz.Ingrid

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.{EnableWebSecurity, WebSecurityConfigurerAdapter}

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  override def configure(http: HttpSecurity): Unit = {
    http.authorizeRequests().antMatchers("/avatars", "/avatar/*").permitAll()
    http.csrf().disable()
    http.headers().frameOptions().disable()
    http.httpBasic()
  }

  @Autowired
  def configureGlobal(auth: AuthenticationManagerBuilder): Unit = {
    auth
      .inMemoryAuthentication.withUser("user")
      .password("pass")
      .roles("user")
  }
}
