/*
URL 매개변수와 같은 사용자 제공 데이터는 항상 신뢰할 수 없고 오염된 것으로 간주되어야 합니다. 
오염된 데이터에서 직접 쿠키를 구성하면 공격자가 세션 식별자를 알려진 값으로 설정할 수 있으므로 공격자가 피해자와 세션을 공유할 수 있습니다. 
공격이 성공하면 예를 들어 피해자가 인증할 때 세션 식별자가 다시 생성되지 않는 경우 민감한 정보에 대한 무단 액세스가 발생할 수 있습니다.

일반적으로 이러한 유형의 공격을 방지하는 솔루션은 허용 목록의 영향을 받을 수 있는 쿠키를 제한하는 것입니다.

*/

// Noncompliant Code Example
module.exports.index = async function (req, res) {
    const value = req.query.value;
  
    res.setHeader("Set-Cookie", value);  // Noncompliant
    res.cookie("connect.sid", value);  // Noncompliant
  };

// Compliant Solution
module.exports.index = async function (req, res) {
    const value = req.query.value;
  
    res.setHeader("X-Data", value);
    res.cookie("data", value);
  };


