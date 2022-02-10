/*
취약점 개요
응용 프로그램의 리소스에 액세스하기 위해 사용자에게 부여하거나 부여하지 않는 권한은 강력한 결정을 기반으로 해야 합니다. 
예를 들어, 사용자가 인증되었는지 여부를 확인하는 것은 올바른 역할/권한을 가지고 있습니다. 또한 사용자의 위치 또는 사용자가 액세스를 요청한 날짜, 시간에 따라 다를 수 있습니다.
*/

// Noncompliant Code Example
// 1. the vote method of an AccessDecisionVoter type is not compliant when it returns only an affirmative decision (ACCESS_GRANTED)
//     or abstains to make a decision (ACCESS_ABSTAIN)
public class WeakNightVoter implements AccessDecisionVoter {
    @Override
    public int vote(Authentication authentication, Object object, Collection collection) {  // Noncompliant

      Calendar calendar = Calendar.getInstance();

      int currentHour = calendar.get(Calendar.HOUR_OF_DAY);

      if(currentHour >= 8 && currentHour <= 19) {
        return ACCESS_GRANTED; // Noncompliant
      }

      // when users connect during the night, do not make decision
      return ACCESS_ABSTAIN; // Noncompliant
    }
}
// 2. the hasPermission method of a PermissionEvaluator type is not compliant when it doesn’t return false
public class MyPermissionEvaluator implements PermissionEvaluator {
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        //Getting subject
        Object user = authentication.getPrincipal();

        if(user.getRole().equals(permission)) {
              return true; // Noncompliant
        }

        return true;  // Noncompliant
    }
}



// Compliant Solution
// 1. he vote method of an AccessDecisionVoter type should return a negative decision (ACCESS_DENIED)
public class StrongNightVoter implements AccessDecisionVoter {
    @Override
    public int vote(Authentication authentication, Object object, Collection collection) {

      Calendar calendar = Calendar.getInstance();

      int currentHour = calendar.get(Calendar.HOUR_OF_DAY);

      if(currentHour >= 8 && currentHour <= 19) {
        return ACCESS_GRANTED;
      }

      // users are not allowed to connect during the night
      return ACCESS_DENIED; // Compliant
    }
}
// 2. the hasPermission method of a PermissionEvaluator type should return false
public class MyPermissionEvaluator implements PermissionEvaluator {
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        //Getting subject
        Object user = authentication.getPrincipal();

        if(user.getRole().equals(permission)) {
              return true;
        }

        return false; // Compliant
    }
}
