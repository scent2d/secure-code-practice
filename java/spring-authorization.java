/*
시큐어코드 가이드
각 메뉴 별, URL별 권한 검증 처리는 아래와 같이 어노테이션 처리하여 활성화 하거나, SecurityConfig 클래스의 configure 메서드를 오버라이드하여 처리할 수 있다.

*/

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록됨
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // secured 어노테이션 활성화, preAuthroize 어노테이션 활성화 (post도 활성화됨)
public class SecurityConfig exntends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
		  .antMatchers("/user/**").authenticated()
		  .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
		  .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
		  .anyRequest().permitAll()
		  .and()
		  .formLogin()
		  .loginPage("/login");
	}
}

@Secured("ROLE_ADMIN")
@GetMapping("/info")
public @ResponseBody String info() {
	return "info";
}

@PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
@GetMapping("/data")
public @ResponseBody String data() {
	return "data";
}

@GetMapping("/user")
public @ResponseBody String user() {
	return "user";
}