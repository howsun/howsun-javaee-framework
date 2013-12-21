package org.howsun.dcs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * 功能描述：
 *
 * @author 张纪豪(howsun.zhang@gmail.com)
 * @version 1.4
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface ContractService {
	String value() default "";
}
