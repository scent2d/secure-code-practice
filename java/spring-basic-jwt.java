/*
스프링 JWT


*/


@Configuration
@EnableWebSecurity
@RequireArgsConstructor
public class SecurityConfig exntends WebSecurityConfigurerAdapter {

	private final CorsFilter corsFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 세션을 사용하지 않음
		.and()
		.addFilter(corsFilter) // @CrossOrigin(인증X), 시큐리티 필터에 등록 인증(O)
		.formLogin().disable()
		.httpBasic().disable()
		.authorizeRequests()
		.antMatchers("/api/v1/user/**")
		.access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
		.antMatchers("/api/v1/manager/**")
		.access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
		.antMatchers("/api/v1/admin/**")
		.access("hasRole('ROLE_ADMIN')")
		.anyRequest().permitAll();

	}
}

@Configuration
public class CorsConfig {

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true); // 내 서버가 응답을 할 때 json을 자바스크립트에서 처리할 수 있게 할지를 설정함
		config.addAllowedOrigin("*"); // 모든 IP에 응답을 허용함
		config.addAllowedHeader("*"); // 모든 header에 응답을 허용함
		config.addAllowedMethod("*"); // 모든 메서드에 요청을 허용함
		source.registerCorsConfiguration("/api/**", config);
		return new CorsFilter(source);
	}
}