/*
취약점 개요

데이터 무결성 및 인증을 보장하기 위해 XML 문서에 서명할 수 있습니다. 서명이 안전한지 확인하고 검증해야 합니다. 
예를 들어, MD5와 같은 약한 암호 알고리즘을 기반으로 하는 서명은 거부되어야 하며, 
XML 문서에는 다수의 SignedInfo요소와 같이 서비스 거부 공격으로 이어질 수 있는 적대적인 구성이 포함되어서는 안 됩니다.
*/

// Noncompliant Code Example
// Java XML Digital Signature API는 기본적으로 강력한 서명 유효성 검사 모드를 사용하지 않습니다.
DOMValidateContext valContext = new DOMValidateContext(new KeyValueKeySelector(), nl.item(0)); // Noncompliant

// Compliant Solution
// Java XML Digital Signature API는 다양한 보안 문제 로부터 보호하기 위해 보안 유효성 검사 모드를 제공합니다.
DOMValidateContext valContext = new DOMValidateContext(new KeyValueKeySelector(), nl.item(0));
valContext.setProperty("org.jcp.xml.dsig.secureValidation", Boolean.TRUE); // 유효성 검사
