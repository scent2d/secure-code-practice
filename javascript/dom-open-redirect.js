/*
DOM 오픈 리디렉션 취약점은 document.location.hash 속성과 같은 사용자 제어 데이터가 리디렉션을 수행하는 데 직접 사용될 때 발생합니다.

사용자 제어 데이터는 DOM을 수정하는 데 사용되기 전에 항상 신뢰할 수 없는 것으로 간주되고 검증되어야 합니다.
*/


// Noncompliant Code Example
// Example of DOM open redirect vulnerability (http://vulnerable/page.html#https://www.attacker.com/):
document.location = document.location.hash.slice(1);


// Compliant Solution
// The URL can be validated with an allowlist:
function isValidUrl(url) {
    if(url.startsWith("https://www.example.com/")) {
      return true;
    }
  
    return false;
  }
  
  if(isValidUrl(document.location.hash.slice(1))) {
     document.location = document.location.hash.slice(1);
  }