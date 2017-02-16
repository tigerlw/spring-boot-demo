package com.ucloudlink.boot.service;

import java.lang.annotation.*;

import org.springframework.context.annotation.Import;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(EnableMyApplicationImportSelector.class)
public @interface EnableMyApplication {

}
