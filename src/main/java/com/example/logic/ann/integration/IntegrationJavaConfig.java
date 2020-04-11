package com.example.logic.ann.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.security.channel.ChannelSecurityInterceptor;
import org.springframework.integration.security.channel.SecuredChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
@EnableIntegration
public class IntegrationJavaConfig {
    @Autowired
    @Bean
    public ChannelSecurityInterceptor interceptor(
            AuthenticationManager authenticationManager,
            AccessDecisionManager accessDecisionManager){
        var interceptor = new ChannelSecurityInterceptor();
        interceptor.setAuthenticationManager(authenticationManager);
        interceptor.setAccessDecisionManager(accessDecisionManager);
        return interceptor;
    }

    @Bean
    @SecuredChannel(interceptor = "interceptor", sendAccess = {"ROLE_USER"})
    public MessageChannel messageChannel(){
        return new DirectChannel();
    }

    /**
     * You can't name bean just messageSource, cause it will confuse it with bean from app context resource bundle
     * responsible for i18n, so you get exception
     * Bean named 'messageSource' is expected to be of type 'org.springframework.context.MessageSource'
     */
    @Bean
    @InboundChannelAdapter(value = "messageChannel", poller = @Poller(fixedDelay = "5000"))
    public MessageSource<String> stringMessageSource() {
        return () -> new GenericMessage<>("hello");
    }

    @Bean
    @ServiceActivator(inputChannel= "messageChannel")
    public MessageHandler messageHandler() {
        return message -> {
            System.out.println("messageHandler => " + message);
        };
    }
}
