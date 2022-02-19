'''
취약점 개요
URL 매개변수 및 POST 본문 콘텐츠와 같은 사용자 제공 데이터는 항상 신뢰할 수 없고 오염된 것으로 간주되어야 합니다.
오염된 데이터를 기반으로 NoSQL 작업을 수행하는 애플리케이션은 일반 SQL 주입 버그와 유사하게 악용될 수 있습니다. 
코드에 따라 SQL 주입과 동일한 위험이 존재합니다. 공격자는 민감한 정보에 액세스하거나 데이터 무결성을 손상시키는 것을 목표로 합니다. 
공격에는 쿼리 연산자, JavaScript 코드 또는 문자열 작업의 삽입이 포함될 수 있습니다.
이 문제는 ODM(Object Document Mapper) 라이브러리를 사용하거나 크기 또는 허용된 문자를 기반으로 사용자 제공 데이터의 유효성을 검사하여 완화할 수 있습니다.
'''

# Noncompliant Code Example
# DynamoDB의 경우 FilterExpression또는 ProjectionExpression매개 KeyConditionExpression변수가 사용자 제어 값의 영향을 받는 경우 예기치 않은 NoSQL 작업이 실행될 수 있습니다.
DYNAMO_CLIENT = boto3.client('dynamodb', config=config)

DYNAMO_CLIENT.scan(
    FilterExpression= username + " = :u AND password = :p", # username is user-controlled
    ExpressionAttributeValues={
        ":u": { 'S': username },
        ":p": { 'S': password }
     },
    ProjectionExpression="username, password",
    TableName="users"
) # Noncompliant

# Compliant Solution
# DynamoDB의 경우 , FilterExpression매개 변수는 사용자 제어 값의 영향을 받지 않아야 합니다
DYNAMO_CLIENT = boto3.client('dynamodb', config=config)

DYNAMO_CLIENT.scan(
    FilterExpression= "username = :u AND password = :p",
    ExpressionAttributeValues={
        ":u": { 'S': username },
        ":p": { 'S': password }
     },
    ProjectionExpression="username, password",
    TableName="users"
)