/*
"HttpSecurity" URL patterns should be correctly ordered

메소드 에 구성된 URL 패턴 HttpSecurity.authorizeRequests()은 선언된 순서대로 고려됩니다. 실수를 하고 더 제한적인 구성보다 덜 제한적인 구성을 선언하기 쉽습니다. 따라서 "antMatchers" 선언의 순서를 검토해야 합니다. 선언 된 /**경우 하나가 마지막이어야 합니다.

이 규칙은 다음과 같은 경우 문제를 일으킵니다.

패턴은 로 끝나고 **시작이 같은 다른 패턴이 앞에 옵니다. 예: /page*-admin/db/**이후 /page*-admin/**
와일드카드 문자가 없는 패턴 앞에는 일치하는 다른 패턴이 옵니다. 예: /page-index/db이후/page*/**

/*

URL patterns configured on a HttpSecurity.authorizeRequests() method are considered in the order they were declared. It’s easy to do a mistake and to declare a less restrictive configuration before a more restrictive one. Therefore, it’s required to review the order of the "antMatchers" declarations. The /** one should be the last one if it is declared.

This rule raises an issue when:

A pattern is preceded by another that ends with ** and has the same beginning. E.g.: /page*-admin/db/** is after /page*-admin/**
A pattern without wildcard characters is preceded by another that matches. E.g.: /page-index/db is after /page*/**


/*
*/


// Noncompliant Code Example
protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
      .antMatchers("/resources/**", "/signup", "/about").permitAll() // Compliant
      .antMatchers("/admin/**").hasRole("ADMIN")
      .antMatchers("/admin/login").permitAll() // Noncompliant; the pattern "/admin/login" should occurs before "/admin/**"
      .antMatchers("/**", "/home").permitAll()
      .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')") // Noncompliant; the pattern "/db/**" should occurs before "/**"
      .and().formLogin().loginPage("/login").permitAll().and().logout().permitAll();
}


// Compliant Solution
protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
      .antMatchers("/resources/**", "/signup", "/about").permitAll() // Compliant
      .antMatchers("/admin/login").permitAll()
      .antMatchers("/admin/**").hasRole("ADMIN") // Compliant
      .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
      .antMatchers("/**", "/home").permitAll() // Compliant; "/**" is the last one
      .and().formLogin().loginPage("/login").permitAll().and().logout().permitAll();
}
