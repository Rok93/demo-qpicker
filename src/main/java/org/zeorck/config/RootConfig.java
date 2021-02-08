package org.zeorck.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 'root-context.xml'을 대신하는 클래스로 XML로 설정된 내용은 RootConfig에서
 * @ComponentScan 어노테이션을 이용해서 처리할 수 있다.
 */
@Configuration
@ComponentScan(basePackages = {"org.zeorck.sample"})
public class RootConfig {

}
