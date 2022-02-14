/*
취약점 개요
동적으로 로드된 클래스에는 정적 클래스 이니셜라이저에 의해 실행되는 악성 코드가 포함될 수 있습니다. 
IE에서는 공격에 취약하기 위해 이러한 클래스에서 메서드를 인스턴스화하거나 명시적으로 호출할 필요조차 없습니다.
*/

// Noncompliant Code Example
String className = System.getProperty("messageClassName");
Class clazz = Class.forName(className);  // Noncompliant
