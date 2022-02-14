/*
취약점 개요
Spring @Component, @Controller, @Service및 @Repository클래스는 기본적으로 싱글톤입니다. 즉, 클래스의 하나의 인스턴스만 애플리케이션에서 인스턴스화됩니다.
일반적으로 이러한 클래스는 로거와 같은 소수의 정적 멤버를 가질 수 있지만 정적 멤버가 아닌 모든 멤버는 Spring에 의해 관리되어야 합니다.
즉, @Resource, @Inject, @Autowired 또는 @Value 주석 중 하나가 있어야 합니다.

이러한 클래스 중 하나에 주입되지 않은 멤버가 있으면 상태를 관리하려는 시도가 있을 수 있습니다. 싱글톤이기 때문에 이러한 시도는 결국 user1의 세션에서 user2로 데이터를 노출시킬 것이 거의 보장된다.

이 규칙은 @ConfigurationProperties가 없는 싱글톤 @Component, @Controller, @Service 또는 @Repository에 다음 중 하나로 주석이 달리지 않은 정적 멤버가 있을 때 문제가 발생합니다.
- org.springframework.beans.factory.annotation.Autowired
- org.springframework.beans.factory.annotation.Value
- javax.annotation.Inject
- javax.annotation.Resource
*/

// Noncompliant Code Example
@Controller
public class HelloWorld {

  private String name = null;

  @RequestMapping("/greet", method = GET)
  public String greet(String greetee) {

    if (greetee != null) {
      this.name = greetee;
    }

    return "Hello " + this.name;  // if greetee is null, you see the previous user's data
  }
}