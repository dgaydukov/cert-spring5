package com.example.logic.ann.factorybean;

import org.springframework.beans.factory.FactoryBean;

public class UserFactory implements FactoryBean<User> {
    @Override
    public User getObject() {
        return new User();
    }

    @Override
    public Class<User> getObjectType() {
        return User.class;
    }

    public User createInstance(){
        return new User();
    }
}
