package org.zeorck.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * web.xml을 대신해서 이 역할을 하는 클래스
 * AbstractAnnotationConfigDispatcherServletInitializer 추상 클래스를 상속받는다
 * 3개의 추상 메서드를 오버라이드 하여야 한다.
 */
public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * 'root-context.xml' 을 대신하는 클래스를 지정한다. 이 예제에서는 'WebConfig' 클래스를 사용한다.
     * @return
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{RootConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    @Override
    protected String[] getServletMappings() {
        return null;
    }
}
