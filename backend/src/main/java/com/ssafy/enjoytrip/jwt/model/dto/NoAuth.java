package com.ssafy.enjoytrip.jwt.model.dto;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) //어노테이션 레벨을 결정짓는다.
@Target({ElementType.TYPE,ElementType.METHOD})//선언된 어노테이션이 적용될수 있는 위치를 결정. TYPE-class,interface,enum에 적용.
public @interface NoAuth {
}