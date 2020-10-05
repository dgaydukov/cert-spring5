package com.example.spring5;

import lombok.Data;
import lombok.ToString;

/**
 *
 * @Data on classes that extends each other (cause fields from base class not in toString)
 * validate cors issue, compare simple vs non-simple requests, check how different headers like `Access-Control-Request-Method/Access-Control-Request-Headers` affect response
 * check how cors on spring , why return cors not found instead of desired cors.
 * withCredentials doesn't allow * as allowed_origin, test in firefox => it shows pre-flight OPTIONS request
 * https://docs.spring.io/spring-security/site/docs/4.2.x/reference/html/cors.html
 * if SqsMessageDeletionPolicy.NO_REDRIVE remove message on success (without manual message deletion)
 * spring & hibernate sharding (https://github.com/apache/shardingsphere)
 */


public class App{
    public static void main(String[] args) {
        User user = new User();
        user.setName("John");
        user.setAge(30);
        user.setEmail("jonh.doe@gmail.com");
        System.out.println("user => " + user);

        CustomUser customUser = new CustomUser();
        customUser.setName("John");
        customUser.setAge(30);
        customUser.setEmail("jonh.doe@gmail.com");
        System.out.println("customUser => " + user);
    }
}

@Data
class Person{
    String name;
    int age;
}
@Data
@ToString(callSuper = true)
class User extends Person{
    String email;
}
@Data
class CustomUser extends Person{
    String email;
    @Override
    public String toString() {
        return "User(name=" + name + ", age=" + age + ", email=" + email + ")";
    }
}
