package com.ibm.pmc.ticket;

import com.google.common.eventbus.EventBus;
import com.ibm.pmc.ticket.common.filter.AdminAuthenticationFilter;
import com.ibm.pmc.ticket.common.filter.ExternalLanguageFilter;
import com.ibm.pmc.ticket.common.filter.LocaleFilter;
import com.ibm.pmc.ticket.common.filter.UserAuthenticationFilter;
import com.ibm.pmc.ticket.common.mail.MailEventListener;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.lang.reflect.Method;

@EnableAsync
@SpringBootApplication
public class Application extends SpringBootServletInitializer {
    @Autowired
    private UserAuthenticationFilter userAuthenticationFilter;

    @Autowired
    private AdminAuthenticationFilter adminAuthenticationFilter;

    @Autowired
    private ExternalLanguageFilter externalLanguageFilter;

    @Autowired
    private LocaleFilter localeFilter;

    @Autowired
    private MailEventListener mailEventListener;

    /**
     * User authentication filter.
     *
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean localeFilterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new DelegatingFilterProxy(localeFilter));
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setName("localeFilter");
        filterRegistrationBean.setOrder(5);

        return filterRegistrationBean;
    }

    /**
     * User authentication filter.
     *
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean externalLanguageFilterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new DelegatingFilterProxy(externalLanguageFilter));
        filterRegistrationBean.addUrlPatterns("/api/users/*");
        filterRegistrationBean.setName("externalLanguageFilter");
        filterRegistrationBean.setOrder(4);

        return filterRegistrationBean;
    }

    /**
     * User authentication filter.
     *
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean userAuthenticationFilterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new DelegatingFilterProxy(userAuthenticationFilter));
        filterRegistrationBean.addUrlPatterns("/api/users/*");
        filterRegistrationBean.setName("userAuthenticationFilter");
        filterRegistrationBean.setOrder(2);

        return filterRegistrationBean;
    }

    /**
     * Admin authentication filter.
     *
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean adminAuthenticationFilterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new DelegatingFilterProxy(adminAuthenticationFilter));
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setName("adminAuthenticationFilter");
        filterRegistrationBean.setOrder(3);

        return filterRegistrationBean;
    }

    /**
     * EntityManager filter.
     *
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean openEntityManagerInViewFilterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new OpenEntityManagerInViewFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setName("openEntityManagerInViewFilter");
        filterRegistrationBean.setOrder(1);

        return filterRegistrationBean;
    }

    @Bean
    public EventBus eventBus() {
        EventBus eventBus = new EventBus();
        eventBus.register(mailEventListener);
        return eventBus;
    }

    @Bean
    @ExceptionHandler
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> logger.error(ex);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }


}
