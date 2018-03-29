//: guru.springfamework.config.SecurityConfig.java


package guru.springfamework.config;


import guru.springfamework.domain.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final IUserService userService;

	@Autowired
	public SecurityConfig(IUserService userService) {
		this.userService = userService;
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws
			Exception {

		auth.userDetailsService(this.userService)
				.passwordEncoder(this.encoder());
	}

	/*
	 * Config how security is handled at the web level
	 * Requiring that certain security cinditions are met before allowing a
	 * request to be served
	 * Configuring a custom login page
	 * Enabling users to be able to log out of the app
	 * Configuring cross-site request forgery protection
	 */
	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {

		httpSecurity.httpBasic();

		httpSecurity
				.authorizeRequests()
						.antMatchers("/api/v1/**")
						.hasRole("USER")
				.and()
				.authorizeRequests()
						.antMatchers("/h2-console/**")
						.hasRole("ADMIN")
				.and()
				.authorizeRequests()
						.antMatchers("/", "/index", "/home")
						.permitAll()
		        .and()
				// Sets up a security filter that intercepts POST requests to
				//     "/logout"
				.logout()
						.logoutSuccessUrl("/index");

		// For access h2 database console
		httpSecurity.csrf().disable();
		httpSecurity.headers()
				.frameOptions()
				.disable();
	}

}///:~