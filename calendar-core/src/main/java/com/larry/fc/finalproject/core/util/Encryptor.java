package com.larry.fc.finalproject.core.util;

public interface Encryptor {
    //interface 를 자주 사용한다. 암호화모듈이 BCryptEncryptor 있을수도 있고
    //여러가지모듈이 있는데, 필요할때 손쉽게 바꿀수 있게 interaface 적용


    String encrypt(String origin);
    //origin 문자를 받아서 암호화한 해쉬문자열을 반환하는 메소드

    boolean isMatch(String origin, String hashed);
    //해쉬된 문자와 ,origin 과 매치해서 맞는가 확인하는 메소드
}
