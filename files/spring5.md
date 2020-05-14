# Content

1. [DI and IoC](#di-and-ioc)
* 1.1 [Dependency injection](#dependency-injection)
* 1.2 [Xml, Groovy, Properties example](#xml-groovy-properties-example)
* 1.3 [BFPP, BPP, ApplicationListener](#bfpp-bpp-applicationlistener)
* 1.4 [Prototype into Singleton](#prototype-into-singleton)
* 1.5 [PostConstruct and PreDestroy](#postconstruct-and-predestroy)
* 1.6 [BeanNameAware, BeanFactoryAware, ApplicationContextAware](#beannameaware-beanfactoryaware-applicationcontextaware)
* 1.7 [BeanFactory and FactoryBean<?>](#beanfactory-and-factorybean)
* 1.8 [Spring i18n](#spring-i18n)
* 1.9 [Resource interface](#resource-interface)
* 1.10 [Environment and PropertySource](#environment-and-propertysource)
* 1.11 [Profile, Primary, Qualifier, Order, Lazy](#profile-primary-qualifier-order-lazy)
* 1.12 [PropertySource and ConfigurationProperties](#propertysource-and-configurationproperties)
* 1.13 [Task scheduling](#task-scheduling)
* 1.14 [Remoting](#remoting)
* 1.15 [Conditional Annotation](#conditional-annotation)
2 [AOP](#aop)
* 2.1 [Aop basics](#aop-basics)
* 2.2 [Aop framework](#aop-framework)
* 2.3 [Native AspectJ](#native-aspectj)
3.[Spring MVC](#spring-mvc)
* 3.1 [DispatcherServlet](#dispatcherservlet)
    * 3.1.1 [Build .war file with pure java](#build-war-file-with-pure-java)
    * 3.1.2 [Build .war file with Spring](#build-war-file-with-spring)
    * 3.1.3 [Build .war file with Spring Boot](#build-war-file-with-spring-boot)
* 3.2 [Spring Boot](#spring-boot)
* 3.3 [Custom Filters](#custom-filters)
* 3.4 [Http security](#http-security)
* 3.5 [Aop security](#aop-security)
* 3.6 [WebSocket API](#websocket-api)
* 3.7 [Reactive WebFlux](#reactive-webflux)
* 3.8 [Data Validation](#data-validation)
* 3.9 [HATEOAS - Hypermedia as the Engine of Application State](#hateoas---hypermedia-as-the-engine-of-application-state)
* 3.10 [RestTemplate and WebClient](#resttemplate-and-webclient)
* 3.11 [Custom HttpMessageConverter](#custom-httpmessageconverter)
* 3.12 [Spring ViewResolver](#spring-viewresolver)
* 3.13 [HandlerMapping, HandlerAdapter, HttpRequestHandler](#handlermapping-handleradapter-httprequesthandler)
4. [DB](#db)
* 4.1 [Spring JDBC](#spring-jdbc)
* 4.2 [Hibernate](#hibernate)
* 4.3 [Spring Data](#spring-data)
* 4.4 [JTA - java transaction API](#jta---java-transaction-api)
* 4.5 [DataSource Interceptor and Sql query counter](#datasource-interceptor-and-sql-query-counter)
* 4.6 [@DynamicUpdate/@DynamicInsert and @NamedEntityGraph](#datasource-interceptor-and-sql-query-counter)
* 4.7 [Cascade types](#cascade-types)
5. [Spring Testing](#spring-testing)
* 5.1 [TestPropertySource and TestPropertyValues](#testpropertysource-and-testpropertyvalues)
* 5.2 [OutputCaptureRule](#outputcapturerule)
* 5.3 [TestExecutionListener](#testexecutionlistener)
6. [Spring Monitoring](#spring-monitoring)
* 6.1 [Jmx monitoring](#jmx-monitoring)
* 6.2 [Spring Boot Actuator](#spring-boot-actuator)
7. [Message Support](#message-support)
* 7.1 [JMS](#jms)
* 7.2 [AMQP (RabbitMQ)](#amqp-rabbitmq)
* 7.3 [Kafka](#kafka)
10. [Miscellaneous](#miscellaneous)
* 10.1 [mvnw and mvnw.cmd](#mvnw-and-mvnwcmd)
* 10.2 [Get param names](#get-param-names)
* 10.3 [Pom vs Bom](#pom-vs-bom)
* 10.4 [Spring Batch](#spring-batch)
* 10.5 [Spring Integration](#spring-integration)
* 10.6 [Spring XD](#spring-xd)
* 10.7 [Spring DevTools](#spring-devtools)
* 10.8 [JMS, AMQP, Kafka](#jms-amqp-kafka)
* 10.9 [YML Autocompletion](#yml-autocompletion)
* 10.10 [Spring Cloud](#spring-cloud)
* 10.11 [Spring Utils](#spring-utils)
* 10.12 [Spring Boot Logging](#spring-boot-logging)
* 10.13 [Controller's method params](#controllers-method-params)
* 10.14 [Spring Caching](#spring-caching)
* 10.15 [JavaBeans, POJO, Spring Beans](#javabeans-pojo-spring-beans)
* 10.16 [Maven scope](#maven-scope)
* 10.17 [Spring Boot Starter](#spring-boot-starter)
* 10.18 [Spring bean scopes (singleton vs. application)](#spring-bean-scopes-singleton-vs-application)
* 10.19 [Spring Context Indexer)](#spring-context-indexer)
* 10.20 [SPEL - Spring Expression Language](#spel---spring-expression-language)
* 10.21 [Custom Framework Impl](#custom-framework-impl)














#### DI and IoC
###### Dependency injection
Rewrite filed injection with `@Autowired` (performed by `AutowiredAnnotationBeanPostProcessor`), has one filed `boolean required default true`. If no dep found will throw `NoSuchBeanDefinitionException`, if set `required = false`, null will be injected.

Pros of constructor injection
- you can use `final` keyword with constructor injection, can’t be done with filed injection
- it’s easy to see when you break S in SOLID. If your class has more than 10 dependencies, your constructor would be bloated, and it’s easy to spot
- works with unit tests without di support (generally it's best practice to use app context in integration tests only, not in unit tests)

Generally `@Autowired` is a bad design. If you don’t want to write constructor code use lombok's `@AllArgsConstructor/@RequiredArgsConstructor` it will generate constructor based on all/final fields in your class and spring automatically will inject them.

Use Interfaces for every class, at least for every Service class. The naming convention is `MyService` for interface and `MyServiceImpl` or `DefaultMyService` for implementation itself.
Pros of using interfaces
- Interface clear defines contract. Who knows why developer made the method public (maybe he just forget to rename it to private). Classes generally unclear and cluttered to define public contract.
- interfaces allow di and mocking without use of reflection (don’t need to parse class implementation)
- JDK dynamic proxy can work only with interfaces (if class implement any it use it), otherwise java switch to CGLIB to create proxies
- now interfaces can have static, default and private methods, so you can use them as first class citizens.
- If you have a class and you add advice to it it would work fine. But if later you will add any interface with 1 method (like `AutoClosable`) you lookup by class name would fail.

Xml constructor injection (`value` - inject value directly, `ref` - inject other bean, in this case bean with such a name should exist in app context)
```
<bean id="" type="MyService">
<constructor-arg type="java.lang.String" name="arg1" index="0" value="1"/>
<constructor-arg type="java.lang.String" name="arg2" index="1" ref="myNewClass"/>
</bean>
```
* for name debug-mode should be enabled
* constructor modifier can be any (private/protected) spring will create it anyway
If we have a bean with private constructor and static method creation we can use `<bean id="" class="" factory-method=""/>`

Since `@Component` doesn't have a property for factory method, if you create factory bean with `@Component` it will create it with private constructor call.
If you have custom factory method, you should remove `@Component` and create bean with `@Bean`.
```java
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class App {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(App.class.getPackageName());
        context.getBean(MyStaticBean.class).sayHello();
    }
}

/**
 * We should disable @Component, cause it overrides @Bean
 */
class MyStaticBean {
    private MyStaticBean(){
        System.out.println("private constructor");
    }

    public static MyStaticBean getInstance(){
        System.out.println("getInstance");
        return new MyStaticBean();
    }

    public void sayHello(){
        System.out.println("hello");
    }
}

@Configuration
class JavaConfig {
    @Bean
    public MyStaticBean myStaticBean(){
        return MyStaticBean.getInstance();
    }
}
```
```
getInstance
private constructor
hello
```

In spring, the `parent` in bean configuration signifies `configuration inheritance` and not related to Java inheritance.
The `configuration inheritance` saves a lot of code as you do away with repeated XML code.
For example, you have following bean with attributes
```java
import lombok.Data;

@Data
class MyBean {
    private String attr1;
    private String attr2;
    private String attr3;
    private String attr4;
} 
```
Say one instance of bean say bean1 just needs attr1 and attr2 whereas another say bean2 instance needs all four the attributes.
```
<bean id="bean1" class="MyBean">
    <property name="attrib1" value="val1" />
    <property name="attrib2" value="val2" />
</bean>

<bean id="bean2" parent="bean1">
    <property name="attrib3" value="val3" />
    <property name="attrib4" value="val4" />
</bean>
```



If we want to init one bean only after other we can use `<bean id="" class="" depends-on="bean1, bean2"/>`. We can do the same with `@DependsOn({"bean1", "bean2"})`.

`@Autowired` => `@Inject`
`@Autowired + @Qualifier("myName")` => `@Resource("myName")` (resource - only on fields and setters)
`@Required` - used with xml config, and indicate that property is required, throws `BeanInitializationException` if property not set in xml (deprecated of spring 5).

If you put annotation into abstract methods => those annotations are stripped when you override these methods. But on `default` methods it would work fine.
```java
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

public class App{
    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(App.class.getPackageName());
    }
}

interface Printer {
    @PostConstruct
    void print();

    @PostConstruct
    default void print2(){
        System.out.println("print2");
    }
}

@Component
class MyPrinter implements Printer {
    @Override
    public void print() {
        System.out.println("print");
    }
}
```
```
print2
```

Bean id and name is same thing. When you create beans with java config with `@Bean`, by default bean name is function name, but you can override it by supplying array of names.
```java
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

public class App{
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(App.class.getPackage().getName());
        System.out.println(context.getBean("oldPrinter"));
        System.out.println(context.getBean("newPrinter"));
        System.out.println(context.getBean("printer"));
    }
}


class Printer{}
@Component
class JavaConfig{
    @Bean({"oldPrinter", "newPrinter"})
    public Printer printer(){
        return new Printer();
    }
}
```
```
com.example.spring5.Printer@4ec4f3a0
com.example.spring5.Printer@4ec4f3a0
Exception in thread "main" org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named 'printer' available
```
Since we override bean names, there is no name `printer`.


`@Configuration` - create proxy, so it can't be final, as well as `@Bean` methods can't be final. Even if your config class extends interface, spring still will use cglib.
The reason for the Spring container subclassing `@Configuration` classes is to control bean creation, for singleton beans, subsequent requests to the method creating the bean 
should return the same bean instance as created at the first invocation of the @Bean annotated method.
```java
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

public class App{
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(App.class.getPackage().getName());
        System.out.println(context.getBean("a").getClass().getName());
        System.out.println(context.getBean("b").getClass().getName());
    }
}


@Component
class A{}

@Configuration
class B{}
```
```
com.example.spring5.A
com.example.spring5.B$$EnhancerBySpringCGLIB$$a868d187
```

There are 2 ways to pass dependency in java config
1. directly call method `myController1`. Downside that injected bean should be in the same java config.
2. pass it as param into method `myController2`. In this case spring will find dependency and inject it into method. This approach is better cause injected bean can be in another java config, or xml file.
```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

class MyService{};
class MyController{
    public MyController(MyService service){}
}

@Configuration
class JavaConfig{
    @Bean
    public MyService myService(){
        return new MyService();
    }

    @Bean
    public MyController myController1(){
        return new MyController(myService());
    }
    @Bean
    public MyController myController2(MyService service){
        return new MyController(service);
    }
}
```



###### Xml, Groovy, Properties example
Before XML Spring used `DTD` - Document Type Definition. An example of dtd xml
```
<?xml version="1.0"?> 

<!DOCTYPE bookstore [ 
  <!ELEMENT bookstore (name,topic+)> 
  <!ELEMENT topic (name,book*)> 
  <!ELEMENT name (#PCDATA)> 
  <!ELEMENT book (title,author)> 
  <!ELEMENT title (#CDATA)> 
  <!ELEMENT author (#CDATA)> 
  <!ELEMENT isbn (#PCDATA)> 
  <!ATTLIST book isbn CDATA "0"> 
  ]> 

<bookstore> 
  <name>Mike's Store</name> 
  <topic> 
    <name>XML</name> 
    <book isbn="123-456-789"> 
      <title>Mike's Guide To DTD's and XML Schemas<</title> 
      <author>Mike Jervis</author> 
    </book> 
  </topic> 
</bookstore>
```

If we have same bean in xml, java config, and @Component => xml wins
Here is quick example to demonstrate how to use di in practice.
Notice, that `BeanDefinitionReader` takes `BeanDefinitionRegistry` instance, so our factory should be both `BeanFactory & BeanDefinitionRegistry`.
There are 3 ways to externalize your configs with `BeanDefinitionRegistry` interface
* groovy script
* xml
* properties files
We have 2 files
File: `SimpleBean.java`
```java
package com.example.logic.xml;

public class SimpleBean {
    private String name;
    private SimplePrinter printer;

    public SimpleBean(){
        System.out.println("constructing SimpleBean...");
    }

    public void init(){
        System.out.println("initializing SimpleBean...");
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPrinter(SimplePrinter printer){
        this.printer = printer;
    }

    public void print(){
        printer.print("I'm SimpleBean, my name is " + name);
    }
}
```
File: `SimplePrinter.java`
```java
package com.example.logic.xml;

public class SimplePrinter {
    public void print(String str){
        System.out.println("printer => " + str);
    }
}
```

And 3 configurations
File: `app.groovy`
```
import com.example.logic.xml.*

beans {
    simplePrinter(SimplePrinter)
    simpleBean(SimpleBean) { bean ->
        name = "goodBean"
        printer = simplePrinter
        bean.initMethod = 'init'
    }
}
```
File: `app.properties` (there is no way to add `init-method` to bean with this config type)
```
simpleBean.(class)=com.example.logic.xml.SimpleBean
simpleBean.name=goodBean
simpleBean.printer(ref)=simplePrinter

simplePrinter.(class)=com.example.logic.xml.SimplePrinter
```
File: `app.xml`
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="simpleBean" class="com.example.logic.xml.SimpleBean" init-method="init">
        <property name="name" value="goodBean"/>
        <property name="printer" ref="simplePrinter"/>
    </bean>
    <bean id="simplePrinter" class="com.example.logic.xml.SimplePrinter"/>
    <bean id="string1" class="java.lang.String"/>
    <bean class="java.lang.String"/>
</beans>
```
Pay attention that for first String bean name would be `string1`, but for second `java.lang.String#0`. So if we don't specify id in xml, spring will generate it by concat full name + number.

And main file
```java
package com.example.spring5;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.groovy.GroovyBeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import com.example.logic.xml.SimpleBean;


public class App {
	enum Type {XML, GROOVY, PROPERTIES}

	public static void main(String[] args) {
		for (Type type: Type.values()) {
			System.out.println("Loading " + type);
			BeanFactory factory = new DefaultListableBeanFactory();
			loadBeanDefinitions((BeanDefinitionRegistry) factory, "app", type);
			SimpleBean simpleBean = factory.getBean("simpleBean", SimpleBean.class);
			simpleBean.print();
			System.out.println();
		}
	}

	private static void loadBeanDefinitions(BeanDefinitionRegistry registry, String fileName, Type type){
		BeanDefinitionReader reader;
		switch (type) {
			case XML:
				reader = new XmlBeanDefinitionReader(registry);
				break;
			case GROOVY:
				reader = new GroovyBeanDefinitionReader(registry);
				break;
			case PROPERTIES:
				reader = new PropertiesBeanDefinitionReader(registry);
				break;
			default:
				throw new IllegalArgumentException("Unknown type: " + type);
		}
		reader.loadBeanDefinitions(fileName + "." + type.toString().toLowerCase());
	}
}
```
```
Loading XML
constructing SimpleBean...
initializing SimpleBean...
printer => I'm SimpleBean, my name is goodBean

Loading GROOVY
constructing SimpleBean...
initializing SimpleBean...
printer => I'm SimpleBean, my name is goodBean

Loading PROPERTIES
constructing SimpleBean...
printer => I'm SimpleBean, my name is goodBean
```




Generally you should prefer `ApplicationContext` over `BeanFactory`, cause it adds di, bpp, bfpp, aop, i18n and so on... Remember that you need to call `refresh()` of `ConfigurableApplicationContext` after context update.
```java
package com.example.spring5;

import org.springframework.beans.factory.groovy.GroovyBeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import com.example.logic.xml.SimpleBean;


public class App {
	enum Type {XML, GROOVY, PROPERTIES}

	public static void main(String[] args) {
		for (Type type: Type.values()) {
			System.out.println("Loading " + type);
			ApplicationContext context = new GenericApplicationContext();
			loadBeanDefinitions(context, "app", type);
			SimpleBean simpleBean = context.getBean("simpleBean", SimpleBean.class);
			simpleBean.print();
			System.out.println();
		}
	}

	private static void loadBeanDefinitions(ApplicationContext context, String fileName, Type type){
		BeanDefinitionRegistry registry = (BeanDefinitionRegistry) context;
		BeanDefinitionReader reader;
		switch (type) {
			case XML:
				reader = new XmlBeanDefinitionReader(registry);
				break;
			case GROOVY:
				reader = new GroovyBeanDefinitionReader(registry);
				break;
			case PROPERTIES:
				reader = new PropertiesBeanDefinitionReader(registry);
				break;
			default:
				throw new IllegalArgumentException("Unknown type: " + type);
		}
		reader.loadBeanDefinitions(fileName + "." + type.toString().toLowerCase());
		((ConfigurableApplicationContext) context).refresh();
	}
}
```

If we want to add annotation support to xml file we should add `<context:component-scan base-package="your.package"/>`. This will scan package `your.package` for all annotated with `@Component` and related to it.

By default there is no `BeanDefinitionReader` implementation for annotations. But we can use 2 other classes
* `AnnotatedBeanDefinitionReader` (compare to other readers, it not implements `BeanDefinitionReader`) - with method `register` - to register a list of java config classes
* `ClassPathBeanDefinitionScanner` - with method `scan` - to scan package with annotations like `@Component`.
If you register only config file and it has annotation `@ComponentScan("yourPackage")`, internally it uses `ClassPathBeanDefinitionScanner`.
```java
import com.example.logic.ann.beans.JavaConfig;
import com.example.logic.ann.beans.SimpleBean;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.support.GenericApplicationContext;

public class App {
    public static void main(String[] args) {
        var context = new GenericApplicationContext();
        var scanner = new ClassPathBeanDefinitionScanner(context);
        scanner.scan("com.example.logic.ann.beans");
        context.refresh();
        context.getBean(SimpleBean.class).sayHello();

        /**
         * recreate new context
         */
        context = new GenericApplicationContext();
        var reader = new AnnotatedBeanDefinitionReader(context);
        reader.register(JavaConfig.class);
        context.refresh();
        context.getBean(SimpleBean.class).sayHello();
    }
}
```
Notice that `AnnotationConfigApplicationContext` use these 2 classes inside, and you can call them like
```java
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App{
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext();
        context.register();
        context.scan();
    }
}
```

We can also use `GenericApplicationContext` to load data, cause it's both `ApplicationContext and BeanDefinitionRegistry`.
Notice that for xml and groovy we have specialized generic contexts with `load` method, where `reader.loadBeanDefinitions` logic is hidden.
Here we can also use fourth type with java config file.
config file
```java
package com.example.logic.xml;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaConfig {
    @Bean
    public SimpleBean simpleBean(){
        SimpleBean bean = new SimpleBean();
        bean.setName("goodBean");
        bean.setPrinter(simplePrinter());
        // explicitly call init here
        bean.init();
        return bean;
    }

    @Bean
    public SimplePrinter simplePrinter(){
        return new SimplePrinter();
    }
}

```
Context initialization
```java
package com.example.spring5;

import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericGroovyApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.example.logic.xml.JavaConfig;
import com.example.logic.xml.SimpleBean;


public class App {
	enum Type {XML, GROOVY, PROPERTIES, JAVA}

	public static void main(String[] args) {
		for (Type type: Type.values()) {
			System.out.println("Loading " + type);
			ApplicationContext context = buildContext("app", type);
			SimpleBean simpleBean = context.getBean("simpleBean", SimpleBean.class);
			simpleBean.print();
			System.out.println();
		}

	}

	private static ApplicationContext buildContext(String fileName, Type type) {
		fileName = fileName + "." + type.toString().toLowerCase();
		switch (type) {
			case XML:
				GenericXmlApplicationContext xmlContext = new GenericXmlApplicationContext();
				xmlContext.load(fileName);
				xmlContext.refresh();
				return xmlContext;
			case GROOVY:
				GenericGroovyApplicationContext groovyContext = new GenericGroovyApplicationContext();
				groovyContext.load(fileName);
				groovyContext.refresh();
				return groovyContext;
			case PROPERTIES:
				GenericApplicationContext propsContext = new GenericXmlApplicationContext();
				propsContext.refresh();
				BeanDefinitionReader reader = new PropertiesBeanDefinitionReader(propsContext);
				reader.loadBeanDefinitions(fileName);
				return propsContext;
			case JAVA:
				ApplicationContext javaContext = new AnnotationConfigApplicationContext(JavaConfig.class);
				return javaContext;
			default:
				throw new IllegalArgumentException("Unknown type: " + type);
		}
	}
}
```

Instead of using java config with manually creating beans with `@Bean` annotation, we can inject annotations directly into beans
File: `AnSimpleBean.java`
```java
package com.example.logic.ann;

import javax.annotation.PostConstruct;

import com.example.logic.ann.beans.SimplePrinter;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AnSimpleBean {
    @Value("goodBean")
    private String name;
    @Autowired
    private SimplePrinter printer;

    public AnSimpleBean(){
        System.out.println("constructing SimpleBean...");
    }

    @PostConstruct
    public void init(){
        System.out.println("initializing SimpleBean...");
    }

    public void print(){
        printer.print("I'm SimpleBean, my name is " + name);
    }
}
```

File: `AnSimplePrinter.java`
```java
package com.example.logic.ann;

import org.springframework.stereotype.Service;

@Service
public class AnSimplePrinter {
    public void print(String str){
        System.out.println("printer => " + str);
    }
}
```

Main method. By default bean id are like bean class name with first lowercase, in our example => anSimpleBean. But you can change it to whatever you want like this `@Service("myCoolBeanName")`
```java
package com.example.spring5;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.logic.ann.beans.SimpleBean;


public class App {
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext("com.example.logic.ann");
		SimpleBean simpleBean = context.getBean("anSimpleBean", SimpleBean.class);
		simpleBean.print();
	}
}
```
```
constructing SimpleBean...
initializing SimpleBean...
printer => I'm SimpleBean, my name is goodBean
```

We can have nested application contexts. `GenericApplicationContext` have `setParent` method, where you can pass parent context. And you can get all beans in child context from parent context
* `ClassPathXmlApplicationContext` - load context from xml file that is located in the class path
* `FileSystemXmlApplicationContext` - load context from xml file located anywhere in the file system
* `XmlWebApplicationContext` - load web context from xml file




###### BFPP, BPP, ApplicationListener
If you want to implement some custom logic during app lifecycle you should have classes that implement following interfaces
* `BeanFactoryPostProcessor` - fires when bean definitions has been loaded from xml/javaConfig/annotations, but none bean has been created
* `BeanPostProcessor` - fires when beans has been created, it has 2 methods
    * `postProcessBeforeInitialization` - fires before initialization, we can be sure that in this method we have original beans
    * `postProcessAfterInitialization` - fires after init, usually here we can substitute our bean with dynamic proxy
* `ApplicationListener<E extends ApplicationEvent>` - fires after bfpp and bpp, when we got some events

Since app context is also `ApplicationEventPublisher`, you can use context to publish `ApplicationEvent`. `start/stop` events can be called only from app context, spring will never issue them, inside they just called `publishEvent`.
```java
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

public class App{
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(App.class.getPackageName());
        context.stop();
        context.start();
        context.close();
    }
}

@Component
class MyAppListener implements ApplicationListener<ApplicationEvent> {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("event => " + event);
    }
}
```
```
event => org.springframework.context.event.ContextRefreshedEvent[source=org.springframework.context.annotation.AnnotationConfigApplicationContext@4cc0edeb, started on Tue May 12 16:56:08 HKT 2020]
event => org.springframework.context.event.ContextStoppedEvent[source=org.springframework.context.annotation.AnnotationConfigApplicationContext@4cc0edeb, started on Tue May 12 16:56:08 HKT 2020]
event => org.springframework.context.event.ContextStartedEvent[source=org.springframework.context.annotation.AnnotationConfigApplicationContext@4cc0edeb, started on Tue May 12 16:56:08 HKT 2020]
16:56:08.192 [main] DEBUG org.springframework.context.annotation.AnnotationConfigApplicationContext - Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@4cc0edeb, started on Tue May 12 16:56:08 HKT 2020
event => org.springframework.context.event.ContextClosedEvent[source=org.springframework.context.annotation.AnnotationConfigApplicationContext@4cc0edeb, started on Tue May 12 16:56:08 HKT 2020]
```

`@Order` - not working for BPP, if you want them ordered, they should implement `Ordered/PriorityOrdered`.

When you create a bean with java config and try to get class from BFPP you got null. The reason, is that spring can't determine method return type before executing methods.
```java
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

public class App{
    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(App.class.getPackageName());
    }
}

@Component
class MyService{}

class Printer{}

@Configuration
class JavaConfig {
    @Bean
    public Printer printer(){
        return new Printer();
    }
}

@Component
class MyBFPP implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) {
        for(String beanName: factory.getBeanDefinitionNames()){
            BeanDefinition beanDefinition = factory.getBeanDefinition(beanName);
            String className = beanDefinition.getBeanClassName();
            System.out.println(beanName + " => " + className);
        }
    }
}
```


When working with `BeanPostProcessor` or aspects the common problem is nested calls.
If you have logging through custom annotation @TimeLogging (that handles by custom BPP), and you have 2 methods annotated with it
if you call them separately both would be wrapped into time-logging. But if you call one from another, only one logging would be displayed To fix this you can do self-injection like `private @Autowired MyService proxy;`
And call one method from another, not with `this`, but with `proxy`. Or you can also create your own annotation `@SelfAutowired` and custom BPP to inject proxy.

To activate your BFPP/BPP you can either add `@Component` to it, or add it as static bean to java config file (bean should be static so it would be executed first, before config class is loaded), or you can do it manually by code.
`DefaultListableBeanFactory => ConfigurableListableBeanFactory => ConfigurableBeanFactory`
`ConfigurableBeanFactory.addBeanPostProcessor` - method to programatically add BPP
here is example
```java
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.logic.ann.postprocessors.LoggingWrapperBPP;

public class App {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext("com.example.logic.ann.postprocessors");
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        System.out.println("beanFactory => " + beanFactory.getClass().getName());
        System.out.println("beanFactory instanceof ConfigurableBeanFactory => " + (beanFactory instanceof ConfigurableBeanFactory));
        beanFactory.addBeanPostProcessor(new LoggingWrapperBPP());
    }
}
```
```
beanFactory => org.springframework.beans.factory.support.DefaultListableBeanFactory
beanFactory instanceof ConfigurableBeanFactory => true
```


###### Prototype into Singleton
You can create you custom scope by implementing `org.springframework.beans.factory.config.Scope`. To register your scope, you have to implement BFPP and register it there.
Below is example how to get scope from app context, and how to create your own scope.
```java
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


public class App{
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(App.class.getPackageName());
        System.out.println("MyService => " + getScopeByClass(context, MyService.class));
        System.out.println("MyPrinter => " + getScopeByClass(context, MyPrinter.class));
        System.out.println("MyBean => " + getScopeByClass(context, MyBean.class));
    }

    private static String getScopeByClass(ConfigurableApplicationContext context, Class<?> cls){
        ConfigurableListableBeanFactory factory = context.getBeanFactory();
        for(String name: factory.getBeanNamesForType(cls)){
            return factory.getBeanDefinition(name).getScope();
        }
        return null;
    }
}

@Component
class MyService{}

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class MyPrinter{}

@Component
@Scope(scopeName = GlobalScope.SCOPE_GLOBAL)
class MyBean{}

/**
 * Custom scope implementation
 */
class GlobalScope implements org.springframework.beans.factory.config.Scope {
    public static final String SCOPE_GLOBAL = "global";

    private Map<String, Object> objects = new ConcurrentHashMap<>();
    private Map<String, Runnable> destructionCallbacks = new ConcurrentHashMap<>();

    @Override
    public Object get(String s, ObjectFactory<?> objectFactory) {
        objects.putIfAbsent(s, objectFactory.getObject());
        return objects.get(s);
    }

    @Override
    public void registerDestructionCallback(String s, Runnable runnable) {
        destructionCallbacks.putIfAbsent(s, runnable);
    }

    @Override
    public Object remove(String s) {
        destructionCallbacks.remove(s);
        return objects.remove(s);
    }

    @Override
    public String getConversationId() {
        return SCOPE_GLOBAL;
    }

    @Override
    public Object resolveContextualObject(String s) {
        return null;
    }
}

/**
 * Registering custom scope
 */
@Component
class GlobalScopeBFPP implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
        factory.registerScope(GlobalScope.SCOPE_GLOBAL, new GlobalScope());
    }
}
```
```
MyService => singleton
MyPrinter => prototype
MyBean => global
```

If prototype bean is dependency of singleton, then it would be eagerly created once, and stay inside container until close.
To add ability for singleton to get every time new prototype we should add `@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)`
In this case proxy (not actual bean) is created and injected once. So only 1 instance of proxy would stay in container. Then for every method call, proxy create new instance of original object inside and call it's method.
TARGET_CLASS - use cglib proxy, INTERFACES - use jdk proxy.
```java
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.example.logic.ann.prototypeintosingleton.proxymode.SingletonBean;

public class App{
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext("com.example.logic.ann.prototypeintosingleton.proxymode");
        var bean = context.getBean(SingletonBean.class);
        bean.sayHello();
        bean.sayHello();
    }
}
```
```
PrototypePrinter constructor...
524 => I'm SingletonBean
PrototypePrinter constructor...
529 => I'm SingletonBean
```
As you see for every call new object is created inside proxy.

In case of xml configuration, we would need to make singleton abstract, and add abstract method to get prototype instance, and add it to xml like `<lookup-method name="getPrinter" bean="prototypePrinter"/>`.
You can also use `Supplier` or `Provider` interface.
```java
@Bean
public Supplier<MyService> getMyService(){
    return this::myService;
}
```


```java
import com.example.logic.ann.prototypeintosingleton.lookup.SingletonBean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class App {
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext("com.example.logic.ann");
		System.out.println();

		context.getBean("singletonBean", SingletonBean.class).sayHello();
		context.getBean("singletonBean", SingletonBean.class).sayHello();
	}
}
```
```
1 => I'm SingletonBean
5 => I'm SingletonBean
```

Alternatively you can declare your singleton as abstract class with 1 abstract method to get printer and override this method in config
```java
package com.example.logic.ann.prototypeintosingleton.abstractmethod;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PsJavaConfig {
    @Bean
    public SingletonBean singletonBean(){
        return new SingletonBean() {
            @Override
            public PrototypePrinter getPrinter() {
                var printer = new PrototypePrinter();
                printer.init();
                return printer;
            }
        };
    }
}
```
And then
```java
import com.example.logic.ann.prototypeintosingleton.abstractmethod.SingletonBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext("com.example.logic.ann.prototypeintosingleton.abstractmethod");
        context.getBean(SingletonBean.class).sayHello();
        context.getBean(SingletonBean.class).sayHello();
    }
}
```
```
963 => I'm SingletonBean
752 => I'm SingletonBean
```

If you don't want to manually create a bean and override abstract method, you can add `@Lookup` annotation on it, and spring will use cglib to create such a bean and will override your abstract method.
The same way you can declare your method as non-abstract and return null, but anyway when you add `@Lookup` spring will use cglib to override it.

If you declare BFPP with `@Bean`, you should make your method static
```java
@Bean
public static MyBFPP myBFPP(){
    return new MyBFPP();
}
```
Otherwise you will get error `@Bean method JavaConfig.myBFPP is non-static and returns an object assignable to Spring's BeanFactoryPostProcessor interface. This will result in a failure to process annotations such as @Autowired, @Resource and @PostConstruct within the method's declaring @Configuration class. Add the 'static' modifier to this method to avoid these container lifecycle issues; see @Bean javadoc for complete details.`
Because BFPP objects must be instantiated very early in the container lifecycle, they can interfere with processing of annotations such as `@Autowired`, `@Value`, and `@PostConstruct` within @Configuration classes. To avoid these lifecycle issues, mark BFPP-returning @Bean methods as static. 


If you want to use both annotation and xml config you can do
* In case of `AnnotationConfigApplicationContext`, add `@ImportResource("app.xml")` to your `@Configuration` class. This annotation also helpful if you have `@SpringBootApplication` and want to import xml config
* In case of `GenericXmlApplicationContext`, add `<context:annotation-config/>` to your xml file

If you have multiple configs annotated with `@Configuration` you can use `@Import` to import one into another. 
Since now we are using component scanning, we don't need to explicitly define it. But is still can be useful in testing where you can have multiple configs.
`@Import` - take list of classes, `@ImportResource` - take list of paths to xml or packages.



###### PostConstruct and PreDestroy

You have 4 options to hook to post construct event (when spring has set all properties)
* add `@PostConstruct` annotation above any method you would like to be called (in this case you can annotate as many methods as you want, in previous 2 works only with one method)
* Bean should implement `InitializingBean` interface with one method `afterPropertiesSet`, and put init logic there. (If we add `@PostConstruct` to this method, it would be called only once)
* Define init method in xml config `init-method=init`
* set `@Bean(initMethod = "init")` in java config file

In case of 1 and 3, you can add `private` to init method (spring still would be able to call it via reflection), but nobody outside will be able to call it second time.
But in case of implementing interface, `afterPropertiesSet` - is public, and can be called directly from your bean. More over in this case you are coupling your logic with spring.
If you set all 4 the order is this: 
`@PostConstruct` (registered with `CommonAnnotationBeanPostProcessor`) => `afterPropertiesSet` => `xml config init` => `@Bean config init`



The same way you can hook up to destroy event
* Add `@PreDestroy` annotation
* Implement `DisposableBean` interface with `destroy` method
* `destroy-method=destory` in xml config
* Add `@Bead(destoryMethod=destroy)` to java config
Destroy events are not fired automatically, you have to call `((ConfigurableApplicationContext)context).close();`. 
Method `destroy` in context is deprecated, and inside just make a call to `close`.


Order of execution: 
`PostConstruct => afterPropertiesSet => initMethod`
`PreDestroy => destroy => destroyMethod`
```java
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


public class App{
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(App.class.getPackageName());
        context.close();
    }
}

class MyBean implements InitializingBean, DisposableBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet");
    }

    public void initMethod(){
        System.out.println("initMethod");
    }
    public void destroyMethod(){
        System.out.println("destroyMethod");
    }

    @PostConstruct
    public void postConstruct(){
        System.out.println("PostConstruct");
    }
    @PreDestroy
    public void PreDestroy(){
        System.out.println("PreDestroy");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("destroy");
    }
}

@Configuration
class JavaConfig{
    @Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
    public MyBean myBean(){
        return new MyBean();
    }
}
```
```
PostConstruct
afterPropertiesSet
initMethod
17:11:03.733 [main] DEBUG org.springframework.context.annotation.AnnotationConfigApplicationContext - Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@482f8f11, started on Wed Apr 22 17:11:03 HKT 2020
PreDestroy
destroy
destroyMethod
```

`close` - close context right now, `registerShutdownHook` - close context when jvm exit. Both method in `ConfigurableApplicationContext`.
```java
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.example.logic.ann.beans.JavaConfig;

public class App {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(JavaConfig.class);
        /**
         * will gracefully close context later when jvm exit
         */
        context.registerShutdownHook();
        /**
         * will close context now
         */
        context.close();
    }
}
```

Overriding `@PostConstruct/@PreDestroy`. 
Since parent init is private, and child - public, it's not overriding. So when we create child bean parent init also called. 
If it was public, it would be valid overridning and it won't be called.
```java
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

public class Parent {
    @PostConstruct
    private void init(){
        System.out.println("Parent");
    }
}

@Component
class Child extends Parent {
    @PostConstruct
    public void init(){
        System.out.println("Child");
    }
}
```
```
Parent
Child
```


###### BeanNameAware, BeanFactoryAware, ApplicationContextAware
If you want to have bean name or app context to be injected into your bean you can implement this interfaces
```java
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

public class App{
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(App.class.getPackageName());
    }
}

@Component
class AwareBean implements BeanNameAware, BeanFactoryAware, ApplicationContextAware {

    @Override
    public void setBeanName(String beanName) {
        System.out.println("setBeanName => " + beanName);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("setBeanFactory => " + beanFactory);
    }

    @Override
    public void setApplicationContext(ApplicationContext context) {
        System.out.println("setApplicationContext => " + context);
    }
}
```
```
setBeanName => awareBean
setBeanFactory => org.springframework.beans.factory.support.DefaultListableBeanFactory@3a079870: defining beans [org.springframework.context.annotation.internalConfigurationAnnotationProcessor,org.springframework.context.annotation.internalAutowiredAnnotationProcessor,org.springframework.context.annotation.internalCommonAnnotationProcessor,org.springframework.context.annotation.internalPersistenceAnnotationProcessor,org.springframework.context.event.internalEventListenerProcessor,org.springframework.context.event.internalEventListenerFactory,awareBean]; root of factory hierarchy
setApplicationContext => org.springframework.context.annotation.AnnotationConfigApplicationContext@482f8f11, started on Tue Apr 28 15:21:55 HKT 2020
```


###### BeanFactory and FactoryBean<?>

Don't confuse the 2 interfaces
* `BeanFactory` - basic di interface, `ApplicationContext` is inherited from it
* `FactoryBean<?>` - helper interface to create factory objects (used when you need to implement factory pattern)

There are 3 ways we can use factory 
* Use any class, just add static method to get other class
* Use singleton (private constructor, static method to get oneself), add non-static method to get other class
* Implement `FactoryBean`. In this case we can omit `factory-method` in xml config

`factorybean.xml`
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans https://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="myUserFactory" class="com.example.spring5.MyUserFactory"/>
    <bean id="user1" factory-bean="myUserFactory" factory-method="getUser"/>

    <bean id="user2" class="com.example.spring5.MyUserFactory" factory-method="getStaticUser"/>

    <bean id="user3" class="com.example.spring5.SpringUserFactory"/>

</beans>
```
```java
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public class App{
    public static void main(String[] args) {
        var context = new ClassPathXmlApplicationContext("factorybean.xml");
        System.out.println("user1 => " + context.getBean("user1"));
        System.out.println("user2 => " + context.getBean("user2"));
        System.out.println("user3 => " + context.getBean("user3"));
    }
}

class User{}
class MyUserFactory{
    private static User user = new User();
    private static MyUserFactory instance = new MyUserFactory();
    private MyUserFactory(){}

    public static MyUserFactory getInstance(){
        return instance;
    }

    public User getUser(){
        return user;
    }

    public static User getStaticUser(){
        return user;
    }
}
class SpringUserFactory implements FactoryBean<User> {
    @Override
    public User getObject() {
        return new User();
    }

    @Override
    public Class<User> getObjectType() {
        return User.class;
    }
}
```


###### Spring i18n
Internationalization based on `MessageSource` interface. Since `ApplicationContext` extends this interface you can get i18n directly from context.
If we don't explicitly set message source, spring will create it's own empty `DelegatingMessageSource`
```java
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App{
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(App.class.getPackageName());
        var source = context.getBean(MessageSource.class);
        System.out.println(source.getClass().getName() + " => " + source);
    }
}
```
```
org.springframework.context.support.DelegatingMessageSource => Empty MessageSource
```

To be able to read messages, app context use bean with name `messageSource` and type `MessageSource`.
Suppose we have 2 bundles
```
resources/i18n/msg_en.properties
    name=English Resource
resources/i18n/msg_fr.properties
    name=French Resource
```
You need to inject `MessageSource` bean into context
```java
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

public class App{
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(App.class.getPackageName());
        System.out.println(context.getMessage("name", null, Locale.ENGLISH));
        System.out.println(context.getMessage("name", null, Locale.FRENCH));
    }
}

@Configuration
class JavaConfig {
    @Bean
    public MessageSource messageSource(){
        ResourceBundleMessageSource bundle = new ResourceBundleMessageSource();
        bundle.setBasename("i18n/msg");
        return bundle;
    }
}
```
```
English Resource
French Resource
```





###### Resource interface
Since `ApplicationContext` extends `ResourceLoader`, that has method `getResource`, you can load resources using context
```java
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.Resource;

public class App{
    public static void main(String[] args) {
        var context = new GenericApplicationContext();
        Resource resource = context.getResource("app.xml");
        System.out.println(resource);
    }
}
```
```
class path resource [app.xml]
```


###### Environment and PropertySource
Since `ApplicationContext` extends `EnvironmentCapable` interface with single method `getEnvironment()`, you can get env object from context.
```java
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

public class App{
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext();
        ConfigurableEnvironment env = context.getEnvironment();
        MutablePropertySources sources = env.getPropertySources();
        Map<String, Object> props = new HashMap<>();
        props.put("myKey", "myValue");
        sources.addLast(new MapPropertySource("props", props));
        System.out.println(env.getProperty("myKey"));
    }
}
```
```
myValue
```

`PropertySourcesPlaceholderConfigurer` (extends `PlaceholderConfigurerSupport`) is special BFPP that is equivalent to annotation `@PropertySource`
* resolves `${...}` placeholders within bean definition property values in xml config (like `<property name="username" value="${user.name}" />`)
* resolves `@Value` annotations against the current Spring `Environment` and its set of `PropertySources` (like `@Value("${user.name}")`).
```java
@Configuration
@EnableConfigurationProperties
public class PropsJavaConfig {
    /**
     * This bean is the same as adding @PropertySource("application.properties") to configuration class
     * since it BFPP is should be static
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
        PropertySourcesPlaceholderConfigurer propConfig = new PropertySourcesPlaceholderConfigurer();
        propConfig.setLocation(new ClassPathResource("application.properties"));
        return propConfig;
    }
}
```



###### Profile, Primary, Qualifier, Order, Lazy
We can have only 1 constructor with `@Aurowired` that is required. Or have many constructor with `@Aurowired(required=false)` - in this case spring will automatically determine which to use.

`@Primary` - if we have more than 1 bean implementing particular interface, you can use this annotation, so spring will inject exactly this bean
`@Qualifier("beanName")` - you can inject any bean you want. It's stronger than primary, so it injects bean by name.
We have spring qualifier and also `JSR-330` from `javax.inject` package. You cad add to your `pom.xml` this dependency
```
<dependency>
    <groupId>javax.inject</groupId>
    <artifactId>javax.inject</artifactId>
    <version>1</version>
</dependency>
```

`@Qualifier` - same as spring `@Qualifier`
Spring support both of them. Moreover you can create your own qualifiers based on any of these 2.

Another `JSR-330` annotation is `@Named` - same as spring `@Component`

If you inject list of interfaces, spring will try to find all beans that implement this interface, and will inject them in default order (sorted by names).
If you need custom order you can add `@Order(1)` on beans, and by this you will create custom order of injection.
If you have many beans, and some have this annotation, other not, those with annotation goes first, those without goes last with default sort.

If you have list of implementations and bean that return also list of implementations, when injected spring will collect list of impl, not your bean. To use your bean, you have to add `Qualifier`.
```java
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

public class App{
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(App.class.getPackageName());
        context.getBean(TestListInjection.class).test();
    }
}

interface Car{}
@Component
class SportCar implements Car{
    @Override
    public String toString(){
        return "SportCar";
    }
}
@Component
class SuvCar implements Car{
    @Override
    public String toString(){
        return "SuvCar";
    }
}

@Configuration
class JavaConfig{
    @Bean
    public List<Car> carList(){
        return new ArrayList<>(List.of(
            new Car() {
                @Override
                public String toString(){
                    return "car1";
                }
            },
            new Car() {
                @Override
                public String toString(){
                    return "car2";
                }
            },
            new Car() {
                @Override
                public String toString(){
                    return "car3";
                }
            }
        ));
    }
}

@Component
class TestListInjection{
    @Autowired
    private List<Car> cars;
    /**
     * @Resource - is short for @Autowired + @Qualifier("name")
     */
    @Resource(name = "carList")
    private List<Car> qualifierCars;

    public void test(){
        System.out.println("cars => " + cars);
        System.out.println("qualifierCars => " + qualifierCars);
    }
}
```
```
cars => [SuvCar, SportCar]
qualifierCars => [car1, car2, car3]
```
As you see without qualifier spring injects single beans that implement our interface, but with qualifier we can inject list bean itself.
* If no single beans found then list bean would be injected. 


When you use `@Autowired` and have 2 or more beans(and not Primary/Qualifier) it will try to inject it by variable name.
```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

public class App {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(App.class.getPackage().getName());
        context.getBean(SimpleBean.class).sayHello();
    }
}

interface Printer {
    void print(String str);
}
@Component
class OldPrinter implements Printer {
    @Override
    public void print(String str) {
        System.out.println("OldPrinter => " + str);
    }
}
@Component
class NewPrinter implements Printer {
    @Override
    public void print(String str) {
        System.out.println("NewPrinter => " + str);
    }
}
@Component
class SimpleBean {
    @Autowired
    public Printer newPrinter;

    public void sayHello(){
        newPrinter.print("hello");
    }
}
```
Since variable name is `newPrinter`, it injects it as `NewPrinter` bean. If you change it to `oldPrinter` then `OldPrinter` would be injected. If you change it anything else, you got `NoUniqueBeanDefinitionException`.

`@Qualifier` work as and. So if we have bean with multiple qualifiers only those that are include both would be injected
```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

public class App {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(App.class.getPackage().getName());
        context.getBean(MyQualifierTest.class).print();
    }
}

@Component
class MyQualifierTest{
    @Autowired
    @Suv
    @Sport
    private List<Car> cars;

    public void print(){
        System.out.println(cars);
    }
}

class Car {
    @Override
    public String toString(){
        return this.getClass().getSimpleName();
    }
}

@Suv
@Component
class SuvCar extends Car{
}

@Sport
@Component
class SportCar extends Car{}

@Sport
@Suv
@Component
class SportSuvCar extends Car{}

@Component
class AnyCar extends Car{}

@Retention(RetentionPolicy.RUNTIME)
@Qualifier
@interface Suv{}

@Retention(RetentionPolicy.RUNTIME)
@Qualifier
@interface Sport{}
```
```
[SportSuvCar]
```
First since qualifier - not repeatable it's better to create custom qualifiers and add them
Second - as you see only bean that satisfy both qualifier got injected.

If you need `or` qualifier - you have to write your custom BPP. Here is example
```java
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(App.class.getPackage().getName());
        context.getBean(MyQualifierTest.class).print();
    }
}

@Component
class OrQualifierBPP implements BeanPostProcessor{
    Map<Annotation, List<Object>> qualifiers = new HashMap<>();
    Map<Annotation, List<Field>> fields = new HashMap<>();
    Map<Field, Object> beans = new HashMap<>();
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> cls = bean.getClass();
        for(var ann: cls.getAnnotations()) {
            if(AnnotationUtils.findAnnotation(ann.getClass(), OrQualifier.class) != null){
                qualifiers.merge(ann, new ArrayList<>(List.of(bean)), (ls, v)->{
                    ls.addAll(v);
                    return ls;
                });
            }
        }
        for(var field: cls.getDeclaredFields()){
            if(field.getType() == List.class){
                beans.put(field, bean);
                for(var ann: field.getAnnotations()){
                    if(AnnotationUtils.findAnnotation(ann.getClass(), OrQualifier.class) != null){
                        fields.merge(ann, new ArrayList<>(List.of(field)), (ls, v)->{
                            ls.addAll(v);
                            return ls;
                        });
                    }
                }
            }
        }

        for(var ann: fields.keySet()){
            for(var field: fields.get(ann)){
                var obj = beans.get(field);
                field.setAccessible(true);
                try {
                    List list = (List)field.get(obj);
                    if (list == null){
                        list = new ArrayList<>();
                    }
                    var inject = qualifiers.get(ann);
                    if(inject != null){
                        for(var item: inject){
                            if(!list.contains(item)){
                                list.add(item);
                            }
                        }
                    }
                    field.set(obj, list);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return bean;
    }
}

@Component
class MyQualifierTest{
    @Suv
    @Sport
    private List<Car> cars;

    public void print(){
        System.out.println(cars);
    }
}

class Car {
    @Override
    public String toString(){
        return this.getClass().getSimpleName();
    }
}

@Suv
@Component
class SuvCar extends Car{
}

@Sport
@Component
class SportCar extends Car{}

@Sport
@Suv
@Component
class SportSuvCar extends Car{}

@Component
class AnyCar extends Car{}

@Retention(RetentionPolicy.RUNTIME)
@OrQualifier
@interface Suv{}

@Retention(RetentionPolicy.RUNTIME)
@OrQualifier
@interface Sport{}


@Retention(RetentionPolicy.RUNTIME)
@interface OrQualifier{}
```
```
[SportCar, SportSuvCar, SuvCar]
```
As you see it took all 3


`@Lazy` (same as xml `<bean id="demo" class="Demo" lazy-init="true"/>`, by default if you don't specify it `false`) works in 2 ways
* When it's declared on bean it would defer bean instantiation, until you first request it
* When declared inside constructor, it can help fix circular dependencies. 
```java
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

public class App {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(App.class.getPackage().getName());
        A a = context.getBean(A.class);
        a.print();
    }
}


@Component
class A{
    private final B b;
    public A(@Lazy B b){
        /**
         * Here b is empty proxy, if you try to call any method on it you will get BeanCurrentlyInCreationException
         * For this to work @Lazy should be inside constructor param. If you remove it and add to B, you still get exception
         */
        System.out.println("constructing A, b => " + b.getClass().getName());
        this.b = b;
    }
    public void print(){
        /**
         * Here b is fully featured proxy, you can call methods on it
         */
        System.out.println("b => " + b.getClass().getName());
        b.print();
    }
}

@Component
class B{
    private final A a;
    public B(A a){
        System.out.println("constructing B, a => " + a.getClass().getName());
        this.a = a;
    }
    public void print(){
        System.out.println("class B");
    }
}
```
```
21:26:37.212 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'a'
constructing A, b => com.example.spring5.B$$EnhancerBySpringCGLIB$$75dd6352
21:26:37.403 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'b'
21:26:37.417 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Autowiring by type from bean name 'b' via constructor to bean named 'a'
constructing B, a => com.example.spring5.A
b => com.example.spring5.B$$EnhancerBySpringCGLIB$$75dd6352
class B
```
As you see it first create instance of a with proxy, and later when you request it, it change proxy to fully-featured proxy.


###### PropertySource and ConfigurationProperties
We can load properties from `.properties/.yml` files.
`@PropertySource` - repeatable, so we can load several files. If keys are the same => those declared after will overwrite those declared before.
You can use `@Value("${propName}")` on every field, or use `@ConfigurationProperties` with public setter methods for every field (in this case spring would inject values with public setters).
You can also use this annotation on methods. In this case spring will call this methods and set all params to this value
```java
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

public class App{
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(App.class.getPackageName());
        context.getBean(MyService.class);
    }
}

@Component
class MyService{
    @Value("hello")
    public void set1(String s){
        System.out.println(s);
    }
    @Value("hello")
    public void set2(String s1, String s2){
        System.out.println(s1 + ", " + s2);
    }
    @Value("hello")
    public void set3(String s1, @Value("world") String s2){
        System.out.println(s1 + ", " + s2);
    }
}
```

`${expr} --> Immediate Evaluation`  `#{expr} --> Deferred Evaluation`
Immediate evaluation means that the expression is evaluated and the result returned as soon as the page is first rendered. Deferred evaluation means that the technology using the expression 
language can use its own machinery to evaluate the expression sometime later during the page’s lifecycle, whenever it is appropriate to do so.

`Person1.java`
```java
package com.example.logic.ann.props;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.ToString;

@Component
@ToString
public class Person1 {
    @Value("1")
    private int id;

    @Value("${name}")
    private String name;

    @Value("${age}")
    private int age;
}

```
`Person2.java`
```java
package com.example.logic.ann.props;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Setter;
import lombok.ToString;

/**
 * You need to add setters methods, cause they are binded with setters
 */
@Component
@ToString
@ConfigurationProperties
@Setter
public class Person2 {
    @Value("2")
    private int id;
    private String name;
    private int age;
}
```
`PropsJavaConfig.java`
```java
package com.example.logic.ann.props;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * ConfigurationProperties - enables by default in spring boot, but if you use other appContext you should enable them explicitly\
 */
@Configuration
@PropertySource("props/person.properties")
@EnableConfigurationProperties
public class PropsJavaConfig {
}
```
```java
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.logic.ann.props.Person1;
import com.example.logic.ann.props.Person2;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("com.example.logic.ann.props");
        System.out.println(context.getBean(Person1.class));
        System.out.println(context.getBean(Person2.class));
    }
}
```
```
Person1(id=1, name=John, age=20)
Person2(id=2, name=John, age=20)
```



###### Task scheduling
`java.util.concurrent` Provides a lot of standard classes to manage concurrent execution. Although you can use spring default classes (they are just wrappers around jdk classes) you either can use jdk classes directly.
In spring there are 2 useful annotations `@Scheduled` and `@Async`. To use them you should enable scheduling with `@EnableScheduling`.
```java
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableScheduling
@EnableAsync
public class App {
    @Bean
    public TaskScheduler scheduler() {
        return new ThreadPoolTaskScheduler();
    }

    @Scheduled(fixedDelay = 10000)
    public void run(){
        System.out.println("run job...");
    }

    @Async
    public void doWork(){
        sleep(3);
        System.out.println("doWork");
    }

    private void sleep(int s){
        try {
            Thread.sleep(s * 1000);
        } catch (InterruptedException ex){
            throw new RuntimeException(ex);
        }
    }



    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(App.class.getPackage().getName());
        var app = context.getBean(App.class);
        app.doWork();
        System.out.println("done");
    }
}
```
```
run job...
done
doWork
doWork
run job...
run job...
run job...
run job...
run job...
....
```


###### Remoting
There are a few options you can set up remote communication between 2 spring projects
Suppose you have 
```java
interface MyService{
    List<String> getNames();
}
```
On the web app side you create bean
```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;

@Configuration
public class WebAppConfig {
    @Autowired
    MyService myService;

    @Bean(name = "/httpInvoker/myService")
    public HttpInvokerServiceExporter httpInvokerServiceExporter() {
        var invokerService = new HttpInvokerServiceExporter();
        invokerService.setService(myService);
        invokerService.setServiceInterface(MyService.class);
        return invokerService;
    }
}
```

On the caller you create bean
```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

@Configuration
public class CallerConfig {
    @Bean
    public MyService myService() {
        var factoryBean = new HttpInvokerProxyFactoryBean();
        factoryBean.setServiceInterface(MyService.class);
        factoryBean.setServiceUrl("http://localhost:8080/invoker/httpInvoker/myService");
        factoryBean.afterPropertiesSet();
        return (MyService) factoryBean.getObject();
    }
}
```
With this settings you can call logic from caller app that will fetch all data from remote web app.
Of course you should share code for `MyService` between 2 apps.



###### Conditional Annotation
There are several annotations that can help you determine should you create a bean or not. 
`@Profile` - can be used to determine should bean be created for certain profile. Can be used on class and methods (in case method is a `@Bean`)
But there also class of `@Conditional` annotations.
`@ConditionalOnClass(MyService.class)` - bean would be created if bean of type MyService exists
`@ConditionalOnBean(name = "myService")` - bean would be created if bean with name myService exists
`@ConditionalOnMissingClass/@ConditionalOnMissingBean` - bean would be created if bean of type/name doesn't exist
`@ConditionalOnProperty(prefix = "my.starter", name = "show", matchIfMissing = true)` - bean would be created if value of `my.starter.show` not false, or it doesn't exist in configuration.
`@ConditionalOnResource(resources = "classpath:my.properties")` - bean would be created only if my.properties exist

We can create custom condition by extending `SpringBootCondition`
```java
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.stereotype.Component;


@Component
class MyCondition extends SpringBootCondition{
    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return null;
    }
}

@Conditional(MyCondition.class)
@Component
class MyService{
    
}
```

You can use expressions inside profile like:
`@Profile({"!dev"})` - any profile except dev
`@Profile({"!dev", "!qa"})` - if we have at least one profile that is not dev or qa (for example it will work if we have 2 profiles like `test & dev`)
`@Profile({"!dev & !qa"})` - If both profiles dev & qa are not present
`@Profile({"dev", "qa"})` - any profile either dev or qa
`@Profile({"dev & qa"})` - both profiles at the same time present
```java
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.stereotype.Component;

public class App{
    public static void main(String[] args) {
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "dev, qa");
        var context = new AnnotationConfigApplicationContext(App.class.getPackageName());
        System.out.println(context.getBean(MyService.class));
    }
}

@Profile({"dev & qa"})
@Component
class MyService{}
```






### AOP

* spring aspects fire before custom BPP
* if class implements at least 1 interface with 1 method, aspect will create dynamic proxy (until forced to use cglib by setting `proxyTargetClass` to true), otherwise will use cglib
* spring aop can work only with public methods for jdk proxies and with public/protected/package-private for cglib
```java
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

public class App{
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(App.class.getPackageName());
        context.getBean(MyBean.class).m1();
    }
}


@Component
class MyBean{
    @Autowired
    private MyBean myBean;

    public void m1(){
        System.out.println("m1");
        myBean.m2();
    }

    /**
     * m2 can be public/protected/package-private (no modifier) and it would be annotated
     * if it's private, aop won't be applied
     */
    protected void m2(){
        System.out.println("m2");
    }
}

@EnableAspectJAutoProxy
@Aspect
@Component
class MyAspect{
    @Before("execution (* m*())")
    private void before(JoinPoint jp){
        System.out.println("before => " + jp.getSignature().getName());
    }
}
```
```
before => m1
m1
before => m2
m2
```

###### Aop basics

Spring aop keywords
* `Join point` - well-defined point during code execution (e.g. method call, object instantiation). In Spring AOP it's always method call. 
* `Pointcut` - join point with applied advice
* `Advice` - piece of code that executes at particular join point
* `Aspect` - advice + pointcut
* `Weaving` - process of inserting aspect into code (3 types => compile(AspectJ), LTW(load-time weaving AspecJ, done during class loading), dynamic(Spring AOP)). Using compile or load-time weaving we can intercept internal calls and private methods.
* `Target` - original object whose flow is modified by aspect
* `AOP proxy` - an object created by the AOP framework in order to implement the aspect contracts (advise method executions and so on). In the Spring Framework, an AOP proxy will be a JDK dynamic proxy or a CGLIB proxy.
* `Introduction` - modification of code on the fly (e.g. add interface to a class)



Spring supports 6 types of advices
* `org.springframework.aop.MethodBeforeAdvice/@Before` - before method execution. has access to params. In case of exception, join point is not called
* `org.springframework.aop.AfterReturningAdvice/@AfterReturning` -  after method executed, has access to params & return value. If method execution throws exception, not called. If advice throws exception, code won't proceed further (so you can implement some after validation)
* `org.springframework.aop.AfterAdvice/@After` - cause would be executed no matter what
* `org.aopalliance.intercept.MethodInterceptor/@Around` - has full control over method execution
* `org.springframework.aop.ThrowsAdvice/@AfterThrowing` run if execution method throws exception
* `org.springframework.aop.IntroductionAdvisor/@DeclareParents` (no annotation, have to manually create such a bean with `ProxyFactoryBean`) add special logic to class

3 advices example (before, after, around). Notice that proxy is changed object, not original
```java
import java.lang.reflect.Method;
import java.util.Arrays;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;

public class App {
	public static void main(String[] args) {
		Person person = new Person();

		ProxyFactory pf = new ProxyFactory();
		pf.setTarget(person);

		/**
		 * void before
		 * We can change params, call method on target, & throw exception(in this case method won't proceed)
		 * Possible application: validate user access before execution
		 */
		pf.addAdvice((MethodBeforeAdvice)(Method method, Object[] params, Object target)->{
			System.out.println("beforeAdvice => " + method.getName() + ", " + Arrays.toString(params) + ", " + target);
			params[0] = "Mike";
		});

		/**
		 * void afterReturning
		 * You can't modify return value, all you can do is add some after-processing or throw exception
		 * Possible application: validate correct return value and throw ex if it's invalid
		 */
		pf.addAdvice((AfterReturningAdvice)(Object retVal, Method method, Object[] params, Object target)->{
			System.out.println("afterAdvice => " + retVal + ", " + method.getName() + ", " + Arrays.toString(params) + ", " + target);
		});

		/**
		 * Object invoke
		 * You can modify return value, prevent execution, and completely overwrite the method
		 */
		pf.addAdvice((MethodInterceptor)((MethodInvocation inv)->{
			System.out.println("aroundAdvice => " + inv.getMethod().getName() + ", " + Arrays.toString(inv.getArguments()) + ", " + inv.getThis());
			Object retVal = inv.proceed();
			System.out.println("aroundAdvice, retVal => " + retVal);
			return "NewName";
		}));

		Person proxy = (Person) pf.getProxy();
		System.out.println("target => " + person.getClass().getName());
		System.out.println("proxy => " + proxy.getClass().getName());
		String name = proxy.sayHello("Jack");
		System.out.println("name => " + name);
	}
}

class Person{
	public String sayHello(String name){
		System.out.println("I'm " + name);
		return name;
	}
}
```
```
target => com.example.spring5.Person
proxy => com.example.spring5.Person$$EnhancerBySpringCGLIB$$bb8bf2f6
beforeAdvice => sayHello, [Jack], com.example.spring5.Person@491cc5c9
aroundAdvice => sayHello, [Mike], com.example.spring5.Person@491cc5c9
I'm Mike
aroundAdvice, retVal => Mike
afterAdvice => NewName, sayHello, [Mike], com.example.spring5.Person@491cc5c9
name => NewName
```

With `ThrowsAdvice` you can do some processing and rethrow exception(if you don't rethrow, original exception would be thrown).
It awaits method with name `afterThrowing`, that takes exception. You can specify more exact exception to have more control
```java
import org.springframework.aop.ThrowsAdvice;
import org.springframework.aop.framework.ProxyFactory;

public class App {
	/**
	 * if we just put class outside, it's modifier won't be public and we got error:
	 * IllegalAccessException: class org.springframework.aop.framework.adapter.ThrowsAdviceInterceptor cannot access a member of class com.example.spring5.MyThrowsAdvice with modifiers "public"
	 * So we should either make it public like this, or put it into separate file
	 */
	public static class MyThrowsAdvice implements ThrowsAdvice {
		public void afterThrowing(Exception ex){
			System.out.println("throwAdvice => " + ex);
			throw new SecurityException(ex);
		}

		public void afterThrowing(RuntimeException ex){
			System.out.println("throwAdvice => " + ex);
			throw new SecurityException(ex);
		}
	}

	public static void main(String[] args){
		Person person = new Person();

		ProxyFactory pf = new ProxyFactory();
		pf.setTarget(person);
		pf.addAdvice(new MyThrowsAdvice());

		Person proxy = (Person) pf.getProxy();
		try {
			proxy.m1();
		} catch (Exception ex){
			ex.printStackTrace();
		}
		try {
			proxy.m2();
		} catch (Exception ex){
			ex.printStackTrace();
		}
	}
}
class Person{
	public void m1() throws Exception{
		throw new Exception("m1 failed");
	}

	public void m2() throws RuntimeException{
		throw new RuntimeException("m2 failed");
	}
}
```
```
throwAdvice => java.lang.Exception: m1 failed
java.lang.SecurityException: java.lang.Exception: m1 failed
Caused by: java.lang.Exception: m1 failed

throwAdvice => java.lang.RuntimeException: m2 failed
java.lang.SecurityException: java.lang.RuntimeException: m2 failed
Caused by: java.lang.RuntimeException: m2 failed
```


By default `addAdvice` add advice to all methods and all possible classes of `ProxyFactory`. If you want to limit classes as well as method you should use `Advisor` (aspect in spring aop terminology => advice+pointcut).
For method matching you can use 2 classes `StaticMethodMatcherPointcut` & `DynamicMethodMatcherPointcut`, the difference is that with dynamic you can also filter by a list of method arguments (like only apply advice if first argument is "Jack").
In both cases you have to implement method `matches`. It also recommended (yet not forced) to override method `getClassFilter` for better control.
```java
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Advisor;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.DynamicMethodMatcherPointcut;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

public class App{
	public static void main(String[] args) {
		MethodInterceptor advice = (MethodInvocation inv)->{
			System.out.println("calling => " + inv.getMethod().getName());
			Object retVal = inv.proceed();
			System.out.println("done");
			return retVal;
		};
		Pointcut pc = new MyStaticPointCut();

		Advisor advisor = new DefaultPointcutAdvisor(pc, advice);

		Person person = new Person();
		ProxyFactory pf = new ProxyFactory();
		pf.setTarget(person);
		pf.addAdvisor(advisor);
		pf.addAdvisor(new DefaultPointcutAdvisor(new MyDynamicPointCut(), advice));

		((Person) pf.getProxy()).sayHello("Jack");
	}
}

class Person{
	public String sayHello(String name){
		System.out.println("I'm " + name);
		return name;
	}
}

class MyStaticPointCut extends StaticMethodMatcherPointcut{
	@Override
	public boolean matches(Method method, Class<?> cls) {
		return "sayHello".equals(method.getName());
	}
	@Override
	public ClassFilter getClassFilter(){
		return cls -> cls == Person.class;
	}
}

class MyDynamicPointCut extends DynamicMethodMatcherPointcut{
	/**
	 * Although you are free not to implement this method and use both check in second matches
	 * like "sayHello".equals(method.getName()) && "Jack".equals(params[0])
	 * It's better to filter method names with static match, in this case this check run once,
	 * and all methods, that not satisfy it, never go to second match
	 */
	@Override
	public boolean matches(Method method, Class<?> cls) {
		return "sayHello".equals(method.getName());
	}
	@Override
	public boolean matches(Method method, Class<?> aClass, Object[] params) {
		return "Jack".equals(params[0]);
	}
	@Override
	public ClassFilter getClassFilter(){
		return cls -> cls == Person.class;
	}
}
```
```
calling => sayHello
calling => sayHello
I'm Jack
done
done
```



If we want filter only by method name (without signature), we can use `NameMatchMethodPointcut` or `JdkRegexpMethodPointcut`.
We can also use `AspectJExpressionPointcut` for native aspectJ patterns.
If we want to use specific aspectJ pointcuts we have to add to `pom.xml`. But if you are using spring-boot, these are already included into `spring-core`.
```
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjrt</artifactId>
    <version>1.9.5</version>
</dependency>
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.9.5</version>
</dependency>
```
Java code
```java
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.aop.support.NameMatchMethodPointcut;

public class App{
    public static void main(String[] args) {
        NameMatchMethodPointcut pc = new NameMatchMethodPointcut();
        pc.addMethodName("sayHello");

        JdkRegexpMethodPointcut regexPc = new JdkRegexpMethodPointcut();
        regexPc.setPattern(".*say.*");

        AspectJExpressionPointcut aspectJPc = new AspectJExpressionPointcut();
        aspectJPc.setExpression("execution(* sayHello*(..))");

        MethodInterceptor advice = (MethodInvocation inv)->{
            System.out.println("calling => " + inv.getMethod().getName());
            Object retVal = inv.proceed();
            System.out.println("done");
            return retVal;
        };

        ProxyFactory pf = new ProxyFactory();
        pf.setTarget(new Person());
        pf.addAdvisor(new DefaultPointcutAdvisor(pc, advice));
        pf.addAdvisor(new DefaultPointcutAdvisor(regexPc, advice));
        pf.addAdvisor(new DefaultPointcutAdvisor(aspectJPc, advice));

        ((Person) pf.getProxy()).sayHello("Jack");

    }
}

class Person{
    public String sayHello(String name){
        System.out.println("I'm " + name);
        return name;
    }
}
```
```
calling => sayHello
calling => sayHello
calling => sayHello
I'm Jack
done
done
done
```

If we need advisor based on annotations we can use `AnnotationMatchingPointcut`
```java
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;

public class App{
    public static void main(String[] args) {
        AnnotationMatchingPointcut pc = AnnotationMatchingPointcut.forMethodAnnotation(AddAroundAdvice.class);

        MethodInterceptor advice = (MethodInvocation inv)->{
            System.out.println("calling => " + inv.getMethod().getName());
            Object retVal = inv.proceed();
            System.out.println("done");
            return retVal;
        };

        ProxyFactory pf = new ProxyFactory();
        pf.setTarget(new Person());
        pf.addAdvisor(new DefaultPointcutAdvisor(pc, advice));

        ((Person) pf.getProxy()).sayHello("Jack");

    }
}

@Retention(RetentionPolicy.RUNTIME)
@interface AddAroundAdvice{

}

class Person{
    @AddAroundAdvice
    public String sayHello(String name){
        System.out.println("I'm " + name);
        return name;
    }
}
```
```
calling => sayHello
I'm Jack
done
```

Convenience classes like `NameMatchMethodPointcutAdvisor` to handle all logic there.
```java
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;

public class App{
    public static void main(String[] args) {

        MethodInterceptor advice = (MethodInvocation inv)->{
            System.out.println("calling => " + inv.getMethod().getName());
            Object retVal = inv.proceed();
            System.out.println("done");
            return retVal;
        };


        NameMatchMethodPointcutAdvisor advisor = new NameMatchMethodPointcutAdvisor(advice);
        advisor.setMappedName("sayHello");
        advisor.setClassFilter(cls -> cls == Person.class);

        ProxyFactory pf = new ProxyFactory();
        pf.setTarget(new Person());
        pf.addAdvisor(advisor);

        ((Person) pf.getProxy()).sayHello("Jack");
    }
}

class Person{
    public String sayHello(String name){
        System.out.println("I'm " + name);
        return name;
    }
}
```
```
calling => sayHello
I'm Jack
done
```

For more fine-grained control we can use `ControlFlowPointcut`.
```java
import java.lang.reflect.Method;
import java.util.Arrays;

import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.ControlFlowPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;

public class App{
    public static void main(String[] args) {
        MethodBeforeAdvice advice = (Method method, Object[] params, Object target)->{
            System.out.println("before => " + method.getName() + ", " + Arrays.toString(params) + ", " + target);
        };
        Pointcut pc = new ControlFlowPointcut(App.class, "print");

        ProxyFactory pf = new ProxyFactory();
        pf.setTarget(new Person());
        pf.addAdvisor(new DefaultPointcutAdvisor(pc, advice));

        Person proxy = (Person) pf.getProxy();
        proxy.sayHello();
        System.out.println();
        print(proxy);
    }
    public static void print(Person p){
        p.sayHello();
    }
}

class Person{
    public void sayHello(){
        System.out.println("Hello, I'm Person");
    }
}
```
```
Hello, I'm Person

before => sayHello, [], com.example.spring5.Person@3ecd23d9
Hello, I'm Person
```
As you see, first time aspect didn't apply, but second time it applied (cause our flow pointcut will apply aspect only when called from specific class and method).
You can also use `ComposablePointcut` with 2 methods `union` and `intersection` if you want to pass several `methodMatchers`.
If you need to combine only pointcuts you can use `PointCut` class, but if you want to combine pointcut/methodmatcher/classfilter you should use `ComposablePointcut`.

We also have `IntroductionAdvisor` by which we can add dynamically new implementations to object.
One possible application is to check if object data changed, and call save to db only in this case.
Below is simplified example, that determined is method with specific name has been called
```java
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.IntroductionAdvisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultIntroductionAdvisor;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;

public class App{
    public static void main(String[] args) {
        IntroductionAdvisor advisor = new IsMethodCalledAdvisor();

        ProxyFactory pf = new ProxyFactory();
        pf.setTarget(new Person());
        pf.addAdvisor(advisor);
        /**
        * force pf to use cglib, otherwise, when you try to cast object to Person you will got ClassCastException
        */
        pf.setOptimize(true);

        var proxy = (Person & IsMethodCalled) pf.getProxy();
        proxy.setName("sayHello");
        System.out.println(proxy.isCalled());
        proxy.sayHello();
        System.out.println(proxy.isCalled());

    }
}

class Person{
    public void sayHello(){
        System.out.println("Hello, I'm Person");
    }
}

interface IsMethodCalled{
    void setName(String name);
    boolean isCalled();
}

class IsMethodCalledAdvisor extends DefaultIntroductionAdvisor {
    public IsMethodCalledAdvisor() {
        super(new IsMethodCalledMixin());
    }
}

class IsMethodCalledMixin extends DelegatingIntroductionInterceptor implements IsMethodCalled {
    private String name;
    private boolean isCalled;

    @Override
    public boolean isCalled() {
        return isCalled;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Object invoke(MethodInvocation inv) throws Throwable {
        String name = inv.getMethod().getName();
        if (name.equals(this.name)) {
            isCalled = true;
        }
        return super.invoke(inv);
    }
}
```
```
false
Hello, I'm Person
true
```

Simple example of creating a bean from class and mixin other interface on the fly
```java
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


public class App{
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(App.class.getPackageName());
        var person = (Person & Printer) context.getBean("person");
        person.sayHello();
        person.print();
    }
}

@Configuration
class JavaConfig{
    @Bean
    public ProxyFactoryBean person() {
        class PrinterAdvice extends DelegatingIntroductionInterceptor implements Printer {
            @Override
            public void print(){
                System.out.println("printing...");
            }
        }
        
        ProxyFactoryBean pfb = new ProxyFactoryBean();
        pfb.setTarget(new Person());
        pfb.addAdvice(new PrinterAdvice());
        pfb.setOptimize(true);
        return pfb;
    }
}

class Person{
    public void sayHello(){
        System.out.println("Hello, I'm Person");
    }
}

interface Printer{
    void print();
}
```
```
Hello, I'm Person
printing...
```


We can rewrite it using `@DeclareParents`
```java
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;


public class App{
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(App.class.getPackageName());
        var person = (Person & Printer) context.getBean("person");
        person.sayHello();
        person.print();
    }
}

@Aspect
@Component
@EnableAspectJAutoProxy
class MyAspect{
    @DeclareParents(value="com.example.spring5.Person", defaultImpl=PrinterImpl.class)
    private Printer printer;
}

@Component
class Person{
    public void sayHello(){
        System.out.println("Hello, I'm Person");
    }
}


interface Printer{
    void print();
}

class PrinterImpl implements Printer{
    @Override
    public void print(){
        System.out.println("printing...");
    }
}
```



###### Aop framework
To grasp the full power of aop you can use spring framework support to use it in declarative way (compare to imperative where we inserted our proxy into code directly).
We have 3 files
`AopSimpleBean.java`
```java
package com.example.logic.ann.aop;

public class AopSimpleBean {
    public void sayHello(){
        System.out.println("I'm AopSimpleBean");
    }
}
```

`AopAroundAdvice.java`
```java
package com.example.logic.ann.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class AopAroundAdvice implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation inv) throws Throwable {
        System.out.println("aroundAdvice => " + inv.getMethod().getName());
        Object retVal = inv.proceed();
        System.out.println("aroundAdvice => " + retVal);
        return retVal;
    }
}
```

`AopJavaConfig.java`
```java
package com.example.logic.ann.aop;

import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AopJavaConfig {
    @Bean
    public AopSimpleBean originalAopSimpleBean(){
        return new AopSimpleBean();
    }

    @Bean
    public AopAroundAdvice aopBeforeAdvice(){
        return new AopAroundAdvice();
    }

    @Bean
    public ProxyFactoryBean aopSimpleBean(){
        ProxyFactoryBean pfb = new ProxyFactoryBean();
        pfb.setTarget(originalAopSimpleBean());
        pfb.setProxyTargetClass(true);
        pfb.addAdvice(aopBeforeAdvice());
        return pfb;
    }
}
```

And here run example
```java
package com.example.spring5;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.logic.ann.aop.AopSimpleBean;

public class App{
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("com.example.logic.ann");
        System.out.println();
        context.getBean("aopSimpleBean", AopSimpleBean.class).sayHello();
        System.out.println();
        context.getBean("originalAopSimpleBean", AopSimpleBean.class).sayHello();
    }
}
```
```
aroundAdvice => sayHello
I'm AopSimpleBean
aroundAdvice => null

I'm AopSimpleBean
```
As you see we have 2 beans of the same type, original (not adviced) and proxy (adviced).
If you want to have one bean, and you never need original you can remove it from java config, and inject it directly into `ProxyFactoryBean`

You can also use `@AspectJ` annotations, you should first enable them `@EnableAspectJAutoProxy(proxyTargetClass = true)` (setting proxyTargetClass force spring aop to use CGLIB)
`@AspectJ` refers to a style of declaring aspects as regular Java classes annotated with annotations.
The `@AspectJ` support can be enabled with XML or Java style configuration. In either case you will also need to ensure that AspectJ’s `aspectjweaver.jar` library is on the classpath of your application (version 1.6.8 or later)
If you are using `@SpringBootApplication` you don't need to explicitly include `@EnableAspectJAutoProxy`, cause it would be configured with `@EnableAutoConfiguration` which with the help of conditional annotations enables aop.


`AopAnnotatedAdvice.java`
```java
package com.example.logic.ann.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AopAnnotatedAdvice {
    @Pointcut("bean(original*)")
    public void isOriginalBean(){}

    @Pointcut("execution(* sayHello(..))")
    public void isSayHelloMethod(){}

    @Before("isOriginalBean() && isSayHelloMethod()")
    public void beforeAdvice(JoinPoint jp){
        System.out.println("beforeAdvice => " + jp.getSignature().getName());
    }
}
```

As you see we apply before advice only if bean name starts with `original` and method name is `sayHello`.
```java
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.logic.ann.aop.AopSimpleBean;

public class App{
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("com.example.logic.ann");
        System.out.println();
        context.getBean("originalAopSimpleBean", AopSimpleBean.class).sayHello();
        System.out.println();
        context.getBean("originalAopSimpleBean", AopSimpleBean.class).print();
    }
}
```
```
beforeAdvice => sayHello
I'm AopSimpleBean

printing...
```

Pointcut Designators

* `execution` - for matching method execution join points, this is the primary pointcut designator you will use when working with Spring AOP
* `within` - limits matching to join points within certain types (simply the execution of a method declared within a matching type when using Spring AOP)
* `this` - limits matching to join points (the execution of methods when using Spring AOP) where the bean reference (Spring AOP proxy) is an instance of the given type
* `target` - limits matching to join points (the execution of methods when using Spring AOP) where the target object (application object being proxied) is an instance of the given type
* `args` - limits matching to join points (the execution of methods when using Spring AOP) where the arguments are instances of the given types
* `@target` - limits matching to join points (the execution of methods when using Spring AOP) where the class of the executing object has an annotation of the given type
* `@args` - limits matching to join points (the execution of methods when using Spring AOP) where the runtime type of the actual arguments passed have annotations of the given type(s)
* `@within` - limits matching to join points within types that have the given annotation (the execution of methods declared in types with the given annotation when using Spring AOP)
* `@annotation` - limits matching to join points where the subject of the join point (method being executed in Spring AOP) has the given annotation
* `bean` - limits matching to join points (method being executed in Spring AOP) to beans with provided name

Example
```java
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

public class App{
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(App.class.getPackageName());
        MyBean bean = context.getBean(MyBean.class);
        bean.print();
        System.out.println();
        bean.print("test");
        System.out.println();
        bean.print(new MyClass());
    }
}

@Retention(RetentionPolicy.RUNTIME)
@interface MyService{}

@MyService
class MyClass{}

@Component
@Service
class MyBean {
    public void print(){
        System.out.println("print");
    }
    @MyService
    public void print(String str){
        System.out.println("print.str => " + str);
    }

    /**
     * would be intercepted by argsAnnotation() pointcut
     */
    public void print(Object obj){
        System.out.println("print.obj => " + obj);
    }
}

@Aspect
@Component
@EnableAspectJAutoProxy
class MyAdvice {
    /**
     * .. - means any number of params
     * empty - with no params
     * int, int - with 2 params of type int, int
     */
    @Pointcut("execution(* print(..))")
    public void executionPrint(){}

    @Pointcut("within(com.example.spring5..*)")
    public void withinPackage(){}

    @Pointcut("this(com.example.spring5.MyBean)")
    public void thisClass(){}

    @Pointcut("target(com.example.spring5.MyBean)")
    public void targetClass(){}

    @Pointcut("args(String)")
    public void argsString(){}

    @Pointcut("@target(org.springframework.stereotype.Service)")
    public void targetAnnotation(){}

    @Pointcut("@args(com.example.spring5.MyService)")
    public void argsAnnotation(){}

    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void withinAnnotation(){}

    /**
     * catch methods annotated with MyService
     */
    @Pointcut("@annotation(MyService)")
    public void annAnnotation(){}

    @Before("withinAnnotation()")
    public void beforeAdvice(JoinPoint jp){
        System.out.println("beforeAdvice => " + jp.getSignature().getName());
    }
}
```

`AopUtils/AopTestUtils` - can be useful for testing purpose. Here is small example
```java
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;


public class App{
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(App.class.getPackageName());
        var bean = context.getBean(Worker.class);
        System.out.println("class => " + bean.getClass().getName());
        System.out.println("getTargetClass => " + AopUtils.getTargetClass(bean).getName());
        System.out.println("isAopProxy => " + AopUtils.isAopProxy(bean));
        System.out.println("isCglibProxy => " + AopUtils.isCglibProxy(bean));
        System.out.println("isJdkDynamicProxy => " + AopUtils.isJdkDynamicProxy(bean));
    }
}

@Component
class MyBean implements Worker{
    public void print(){}
}

interface Worker {
    default void work(){}
}

/**
 * If we want to force it to use cglib we should set proxyTargetClass = true
 */
@EnableAspectJAutoProxy
@Aspect
@Component
class MyAspect{
    @Before("execution(* print())")
    private void beforeAdvice(JoinPoint jp){
        System.out.println("beforeAdvice => " + jp.getSignature().getName());
    }
}
```
```
class => com.example.spring5.$Proxy16
getTargetClass => com.example.spring5.MyBean
isAopProxy => true
isCglibProxy => false
isJdkDynamicProxy => true
```


###### Native AspectJ
You can also write your aspects in native aspectj language. For this you first need to add `AspectJ Weaver` plugin to your IDE. You can create a class with extension `*.aj`




### Spring MVC


`@RestController` - convenience annotation => `@Controller` + `@ResponseBody` (convert method return type into http response using `HttpMessageConverter` implementations)
`@RequestMapping(path = "/api")` - can add it to controller, so all methods would have this url as base
`@SessionAttributes` (class level only) - post request gets the same instance of the model attribute object that was placed into the get request.
`@CrossOrigin(origins="*")` - set origin to anybody, by default it same-host
`PUT` vs. `PATCH`. put - opposite to get, so it to replace whole object for url. Patch - is to replace some fields within the object.
`@RequestParam/@PathVariable` - have field required (default true), so if you don't pass param field you got exception. If you set it to false value would be null (if your value is primitive you got `IllegalStateException: Optional int parameter 'id' is present but cannot be translated into a null value due to being declared as a primitive type. Consider declaring it as object wrapper for the corresponding primitive type.`)
`ContextLoaderListener` (implements `ServletContextListener`) load root web app context.


You can also use `web.xml` to register your web app. Here you can register 
* root web app context
* dispatcher servlet
* config classes
* context loader listener
* filters (like `MultipartFilter`)
but not custom beans like `ViewResolver/MultipartResolver`. DispatcherServlet looks for an instance of `MultipartResolver` registered as a bean by the name of `DispatcherServlet.MULTIPART_RESOLVER_BEAN_NAME = "multipartResolver"`. By default it's `StandardServletMultipartResolver`.
```
<web-app>
    <!-- Configure ContextLoaderListener to use AnnotationConfigWebApplicationContext instead of the default XmlWebApplicationContext -->
    <context-param>
        <param-name>contextClass</param-name>
        <param-value>org.springframework.web.context.support.AnnotationConfigWebApplicationContext</param-value>
    </context-param>

    <!-- Configuration locations must consist of one or more comma- or space-delimited fully-qualified @Configuration classes. Fully-qualified packages may also be specified for component-scanning -->
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>com.acme.AppConfig</param-value>
    </context-param>

    <!-- Bootstrap the root application context as usual using ContextLoaderListener -->
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>

    <!-- Declare a Spring MVC DispatcherServlet as usual -->
    <servlet>
        <servlet-name>dispatcher</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!-- Configure DispatcherServlet to use AnnotationConfigWebApplicationContext instead of the default XmlWebApplicationContext -->
        <init-param>
            <param-name>contextClass</param-name>
            <param-value>org.springframework.web.context.support.AnnotationConfigWebApplicationContext</param-value>
        </init-param>
        <!-- Again, config locations must consist of one or more comma- or space-delimited and fully-qualified @Configuration classes. Beans declared here will override beans from root app context -->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>com.acme.web.MvcConfig</param-value>
        </init-param>
    </servlet>

    <!-- map all requests for /app/* to the dispatcher servlet -->
    <servlet-mapping>
        <servlet-name>dispatcher</servlet-name>
        <url-pattern>/app/*</url-pattern>
    </servlet-mapping>
</web-app>
```

As you see, we have 2 types of params `context-param` - common for whole app, and `init-param` - common for current servlet. So both `contextClass/contextConfigLocation` can be in both sections.


It's important to return correct value
```
curl http://localhost:8080/m1  # => javax.servlet.ServletException: Circular view path [m1]
curl http://localhost:8080/m2  # => m2
curl http://localhost:8080/m3  # => m3
```
```java
package com.example.logic.ann.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {
    @GetMapping("/m1")
    public String handleM1(){
        return "m1";
    }

    @GetMapping("/m2")
    @ResponseBody
    public String handleM2(){
        return "m2";
    }

    @GetMapping("/m3")
    public ResponseEntity<String> handleM3(){
        return new ResponseEntity<>("m3", HttpStatus.OK);
    }
}
```


In Spring Web MVC you can use any object as a command or form-backing object; you do not need to implement a framework-specific interface or base class
The type-level `@SessionAttributes` annotation declares session attributes used by a specific handler. This will typically list the names of model attributes or 
types of model attributes which should be transparently stored in the session or some conversational storage, serving as form-backing beans between subsequent requests




###### DispatcherServlet
`org.springframework.web.servlet.DispatcherServlet` - is entry point of every web app, it's main purpose to handle http requests (it extends in the end `HttpServlet` which itself in the end implements `Servlet`).
When you create web app, your context always an instance of `WebApplicationContext`, it extends `ApplicationContext`, and has a method `getServletContext`, to get `ServletContext`.
Each `DispatcherServlet` has its own `WebApplicationContext`, which inherits all the beans already defined in the root `WebApplicationContext`.
Beans inherited from root app context can be overridden in the servlet-specific scope, and you can define new scope-specific beans local to a given Servlet instance.
If you have a `<bean>` declaration with the same name or id in both the root context and the servlet context, the servlet context one will overwrite the root context one.




###### Build .war file with pure java
You can even build servlet app with pure [java](https://github.com/dgaydukov/cert-ocpjp11/blob/master/files/ocpjp11.md#java-servlet-webapp)



###### Build .war file with Spring
Before the advent of spring boot for building web app we were using `.war` files (web archive).
Inside we had web.xml were all configs are stores, then we put this file into `tomcat` directory, and when tomcat 
starts, it takes with file and run it. That's why we didn't have any `main` method inside web app for spring, tomcat itself build jar path and run our app.

To build web app you should implement `WebApplicationInitializer`, but usually you just extends some class like `AbstractAnnotationConfigDispatcherServletInitializer`.

`WebApplicationInitializer vs ServletContainerInitializer`
Tomcat 3.0+ search for `javax.servlet.ServletContainerInitializer` through the SPI and load that class.
Spring has default implementation `org.springframework.web.SpringServletContainerInitializer` with annotation `@HandlesTypes({WebApplicationInitializer.class})`.
So when you create a class from `WebApplicationInitializer` it get hooked by `SpringServletContainerInitializer` which in turn get hooked by tomcat and that's why you implement it, and not directly `ServletContainerInitializer`.


Example using `AbstractAnnotationConfigDispatcherServletInitializer`
```java
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class App extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{
            JavaConfig.class
        };
    }
    @Override
    protected Class<?>[] getServletConfigClasses()  {
        return new Class<?>[]{
            JavaConfig.class
        };
    }
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}

@EnableWebMvc
@Configuration
@ComponentScan
class JavaConfig implements WebMvcConfigurer {
}

@RestController("/")
class MyController {
    @GetMapping
    public String handleGet(){
        return "handleGet => " + ThreadLocalRandom.current().nextInt();
    }
}
```

You can also rewrite your app to implement just `WebApplicationInitializer`
```java
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class App implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        var context = new AnnotationConfigWebApplicationContext();
        context.register(JavaConfig.class);

        servletContext.addListener(new ContextLoaderListener(context));
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(context));

        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }
}
```


###### Build .war file with Spring Boot
If you have spring boot project, that you are going to build into war, it has a class `SpringBootServletInitializer` which implements `WebApplicationInitializer`, so you don't have to write your own implementation. You will have main entry point, and spring boot will do this under the hood.
If you choose `war` packaging spring boot will create 2 classes one is `ServletInitializer` another is `DemoApplication` with main method. You can rewrite them into one. 

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class App extends SpringBootServletInitializer {
    // for tomcat deployment
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(App.class);
	}

	// for local development
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
```

You can even remove `main` method and have pure war app. For this remove this plugin `spring-boot-maven-plugin`, otherwise `mvn clean install` won't work. And then just copy your war file to tomcat.
To run `.war` in tomcat do:
* Change project name to more readable
```
<build>
    <finalName>spring-boot-app</finalName>
</build>
```
* Run `mvn clean install`
* Download [tomcat](https://tomcat.apache.org/download-80.cgi) and extract in into `tomcat` directory
* Run `chmod +x ./bin/catalina.sh` and then just run `./bin/catalina.sh run`
* Copy your file `target/spring-boot-app.war` to `/tomcat/webapps`. This will force tomcat to extract `.war` file into folder
* Navigate to `http://localhost:8080/spring-boot-app/` - here your app.




###### Spring Boot
In spring boot you have 2 new lifecycle stages. You can register them in `resources/META-INF/spring.factories`. Just add these 2 lines
```
org.springframework.boot.env.EnvironmentPostProcessor=com.example.logic.ann.postprocessors.MyEPP
org.springframework.context.ApplicationContextInitializer=com.example.logic.ann.postprocessors.MyACI
```

###### Custom Filters
You can add many filter to filter your http request.
```java
package com.example.logic.ann.web.filters;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;


@Component
public class MyFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        System.out.println("doFilter");
        chain.doFilter(req, res);
    }
}
```
You should call in the end `chain.doFilter(req, res);`, otherwise filter will not proceed, and your query won't be executed.
By default this filter works for all urls. If you want to set filter for particular url you should remove `@Component` and create config bean.
```java
package com.example.logic.ann.web.filters;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class FilterJavaConfig {
    @Bean
    public FilterRegistrationBean<Filter> myFilter(){
        var filter = new FilterRegistrationBean<>();
        filter.setFilter(new MyFilter());
        filter.addUrlPatterns("/my");
        return filter;
    }
}
```




###### Http security

* `antMatcher(String antPattern)` - allows configuring the `HttpSecurity` to only be invoked when matching the provided ant pattern.
* `mvcMatcher(String mvcPattern)` - allows configuring the `HttpSecurity` to only be invoked when matching the provided Spring MVC pattern.

Generally mvcMatcher is more secure than an antMatcher. As an example:
`antMatchers("/secured")` matches only the exact `/secured` URL
`mvcMatchers("/secured")` matches `/secured` as well as `/secured/`, `/secured.html`, `/secured.xyz`

Servlet has a concept of filters, where each request first goes through a list of filters
One of the filter is `DelegatingFilterProxy` - builds a link between servlet lifecycle and app context, by including filters from context to servlet
Internally it uses `FilterChainProxy` that internally has a list of `SecurityFilterChain`.
The idea is that `FilterChainProxy` (spring security creates `javax.servlet.Filter` with name `springSecurityFilterChain`) can have a list of `SecurityFilterChain` each applied to unique url and each consisting of unique filters, so you can divide your logic to multiple layer security.
```java
import javax.annotation.Resource;
import javax.servlet.Filter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
    @Resource(name = "springSecurityFilterChain")
    private Filter filter;

    public static void main(String[] args) {
        var context = SpringApplication.run(App.class, args);
        var app = context.getBean(App.class);
        System.out.println("springSecurityFilterChain => " + app.filter);
    }
}
```
```
springSecurityFilterChain => FilterChainProxy[Filter Chains: [[ any request, [org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter@15ecde52, org.springframework.security.web.context.SecurityContextPersistenceFilter@770b5cd2, org.springframework.security.web.header.HeaderWriterFilter@6aed3675, org.springframework.security.web.csrf.CsrfFilter@e9c4c2d, org.springframework.security.web.authentication.logout.LogoutFilter@1175070a, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter@51d5c50f, org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter@2b023506, org.springframework.security.web.authentication.ui.DefaultLogoutPageGeneratingFilter@3d39d68, org.springframework.security.web.authentication.www.BasicAuthenticationFilter@148f6fd5, org.springframework.security.web.savedrequest.RequestCacheAwareFilter@295cb28f, org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter@34002c3d, org.springframework.security.web.authentication.AnonymousAuthenticationFilter@401d9586, org.springframework.security.web.session.SessionManagementFilter@29a62c48, org.springframework.security.web.access.ExceptionTranslationFilter@9d3851, org.springframework.security.web.access.intercept.FilterSecurityInterceptor@2954b177]]]]
```

There are 4 ways to specify user storage. For all of them you must create configuration class that implements `WebSecurityConfigurerAdapter`
1. In-memory storage
```java
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityJavaConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("user")
            .password("user")
            .authorities("ROLE_USER")
        .and()
            .withUser("admin")
            .password("admin")
            .authorities("ROLE_USER", "ROLE_ADMIN");
    }
}
```

2. Database storage
```java
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityJavaConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource ds;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        /**
         * If you don't want to store plain-text password in db you would better to
         * use password encoder
         */
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        /**
         * This expects you have table like this
         * users
         * username: string
         * password: string
         * enabled: boolean
         *
         * authorities
         * username
         * authority
         */
        auth.jdbcAuthentication().dataSource(ds).passwordEncoder(encoder);

        /**
         * You can also set your tables for this
         */
        auth.jdbcAuthentication()
            .dataSource(ds)
            .usersByUsernameQuery("select username, password, enabled from user_table where username=?")
            .authoritiesByUsernameQuery("select username, authority from auth_table where username=?")
            .passwordEncoder(encoder);

    }
}
```

3. LDAP server
```java
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityJavaConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        /**
         * If you want to compare password not in plain-text in ldap you should add password encoder
         * By default spring assume that ldap in localhost:33389
         * If you have custom ldap you should add .contextSource().url("your_ldap_url")
         */
        auth.ldapAuthentication()
            .userSearchFilter("user_id={0}")
            .groupSearchFilter("member_id={0}")
            .passwordCompare();
    }
}
```

4. Custom user service (it should be divided on 3 files, but I just give example in one file)
```java
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import lombok.Data;

@Data
@Entity
class UserEntity implements UserDetails {
    @Id
    @GeneratedValue
    private int id;
    private String username;
    private String password;
    private List<GrantedAuthority> authorities;
    private boolean enabled;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
}

interface UserRepository extends CrudRepository<UserEntity, Integer>{
    Optional<UserEntity> findByUsername(String username);
}

@Configuration
@EnableWebSecurity
public class SecurityJavaConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username->{
            var user = userRepository.findByUsername(username);
            if (user.isEmpty()) {
                throw new UsernameNotFoundException("Username `" + username + "` doesn't exist");
            }
            return user.get();
        })
        .passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
    }
}
```

For every controller you can inject current user like `@AuthenticationPrincipal UserEntity entity`


For servlet-based security we configure `HttpSecurity`, for reactive we configure `ServerHttpSecurity`.
```java
package com.example.logic.ann.reactive;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.server.SecurityWebFilterChain;

import reactor.core.publisher.Mono;

/**
 * Example for setting up security for webflux app
 * We don't need to extend from WebSecurityConfigurerAdapter and override any configure methods
 * We have 2 beans, one to configure http security and another to configure user details
 */
@Configuration
@EnableWebFluxSecurity
public class ReactiveJavaConfig {
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
            .authorizeExchange()
            .pathMatchers("/person").hasAuthority("USER")
            .anyExchange().permitAll()
            .and()
            .build();
    }

    @Bean
    public ReactiveUserDetailsService reactiveUserDetailsService(){
        return username -> Mono.just(new User(username, "password", List.of(()->"ROLE_USER")));
    }
}
```


###### Aop security
To work with aop security add following annotation to your config `@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)`
* `prePostEnabled` => pre/post annotations
* `securedEnabled` => determines if the `@Secured` annotation should be enabled
* `jsr250Enabled` =>  allows us to use the `@RoleAllowed` annotation

If we want to allow only certain roles to access some method, add this to any method `@Secured({"ROLE_USER", "ROLE_ADMIN"})` or `@RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})` from jsr250
`@PreAuthorize/@PostAuthorize` - can take `SPEL` expressions (first check before entering the method, second - check after method execution)
`@PreAuthorize("isAuthenticated()")` - check if user authenticated
`@PreFilter/@PostFilter` - can be used to filter requests

Old way to configure security was in xml files
```
security.xml:

<intercept-url pattern="/myrul" access="hasRole('ROLE_ADMIN')" method="POST" requires-channel="https" filters="none"/>

MyController.java:

@PostMapping("/myrul")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public void doWork(){}
```
`intercept-url` are read in order in which they are defined, once match if found, the search is stopped. It's recommended to set more specific rules at the top

`<intercept-url>` Attributes
* `access` Lists the access attributes which will be stored in the FilterInvocationSecurityMetadataSource for the defined URL pattern/method combination. This should be a comma-separated list of the security configuration attributes (such as role names) or `access="authenticated"`.
* `filters` Can only take the value "none". This will cause any matching request to bypass the Spring Security filter chain entirely. None of the rest of the <http> configuration will have any effect on the request and there will be no security context available for its duration. Access to secured methods during the request will fail.
* `method` The HTTP Method which will be used in combination with the pattern to match an incoming request. If omitted, any method will match. If an identical pattern is specified with and without a method, the method-specific match will take precedence.
* `pattern` The pattern which defines the URL path. The content will depend on the request-matcher attribute from the containing http element, so will default to ant path syntax.
* `requires-channel` Can be "http" or "https" depending on whether a particular URL pattern should be accessed over HTTP or HTTPS respectively. Alternatively the value "any" can be used when there is no preference. If this attribute is present on any <intercept-url> element, then a ChannelProcessingFilter will be added to the filter stack and its additional dependencies added to the application context.

There are couple of other security tags inside JSP
First you should enable them in jsp `<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>`
* `<sec:authorize access="hasRole('ROLE_ADMIN')">Hello Administator</sec:authorize>` - show content only for user with admin role
* `<sec:authentication property="principal.username" />` - return current username
* `<sec:accesscontrollist hasPermission="1,2" domainObject="${someObject}"></sec:accesscontrollist>` - only valid when used with Spring Security’s ACL module. It checks a comma-separated list of required permissions for a specified domain object

```java
package com.concretepage.service;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import com.concretepage.bean.Book;
public interface IBookService {
	@PreAuthorize ("hasRole('ROLE_WRITE')")
	public void addBook(Book book);

    /**
      * Once method executed we can get it's owner type and compare it to auth name
      * if the don't match error happen
      */
	@PostAuthorize ("returnObject.owner == authentication.name")
	public Book getBook();

	@PreAuthorize ("#book.owner == authentication.name")
	public void deleteBook(Book book);
} 
```

Basically these 3 annotations are the same
`@RolesAllowed({"ROLE_ADMIN"})` - can be checked only against role
`@Secured("ADMIN")` - can checked more than role, but doesn't support `SPEL`
`@PreAuthorize("hasRole('ADMIN')")` - the most versatile, can use `SPEL`

`@PreFilter` - filter a list as param to a function (if we have only 1 param as list, we can omit `filterTarget`)
`@PostFilter` - filter returned list

```java
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreFilter;

public class App{
    @Autowired
    private JpaRepository userRoleRepository;
    
    @PreFilter(value = "filterObject != authentication.principal.username", filterTarget = "usernames")
    public void joinUsernamesAndRoles(List<String> usernames, List<String> roles) {
    }

    @PostFilter("filterObject != authentication.principal.username")
    public List<String> getAllUsernamesExceptCurrent() {
        return userRoleRepository.findAll();
    }
}
```



###### WebSocket API
To work with spring websocket we should add to `pom.xml`. Since we most likely to work with json it's better to add jackson library too
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-websocket</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-json</artifactId>
    <version>2.2.6.RELEASE</version>
</dependency>
```

Working example. This file should be places in `main/resources/static/ws.html` and it would be accessible on `http://localhost:8080/ws.html`

`ws.html`
```
<html>
    <body>
        <h1>WebSocket Client</h1>
        <input id="server" size="40" value="ws://localhost:8080/socket"/>
        <br/>
        <button id="connect">Connect</button>
        <button id="disconnect">Disconnect</button>
        <br/>
        <br/>Message:
        <input id="message" value=""/>
        <button id="send">Send</button>
        <br/>
        <textarea id="chat" rows="10" cols="50"></textarea>
    </body>
</html>
<script type="text/javascript">
var socket;
document.getElementById("connect").addEventListener("click", (event) => {
    var url = document.getElementById("server").value;
    console.log(`connecting to ${url}...`);
    socket = new WebSocket(url);
    socket.onopen = event => {
        document.getElementById("chat").value = "CONNECTED";
        socket.onmessage = event => {
              console.log(event);
              document.getElementById("chat").value=document.getElementById("chat").value + "\n" + event.data;
            }
        };
});
document.getElementById("disconnect").addEventListener("click", (event) => {
    console.log("closing socket");
    socket.close();
});
document.getElementById("send").addEventListener("click", (event) => {
    socket.send(document.getElementById("message").value);
    document.getElementById("message").value="";
});
</script>
```

`WsJavaConfig.java`
```java
package com.example.logic.ann.ws;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Configuration
@EnableWebSocket
public class WsJavaConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        /**
         * By default only same url can access socket, if you want disable this add
         * setAllowedOrigins("*") to addHandler
         */
        registry.addHandler(new TextWebSocketHandler(){
            @Override
            protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
                System.out.println("handleTextMessage => " + message);
                session.sendMessage(new TextMessage("response => " + message.getPayload()));
            }
        }, "/socket");
    }
}
```

`App.java`
```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Since we have one pom.xml for all examples we exclude security so our socket
 * is open, and no security filter blocking us
 * otherwise we won't be able to open localhost:8080/ws.html (would be redirected to login page)
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class})
@ComponentScan("com.example.logic.ann.ws")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
```

If `WebSocket` is not supported by browser we call fallback to SockJs. For client take a [sockjs](https://github.com/sockjs/sockjs-client#getting-started).
For server add this `withSockJS()` to `registry.addHandler().`



###### Reactive WebFlux

For java reactive api there are 4 nested interfaces in  `java.util.concurrent.Flow` class. `Publisher` produces data that it sends to a `Subscriber` per a `Subscription`. And `Processor` is both publisher & subscriber to create pipes.
Since in java we have only interfaces of reactive, you should use either Reactor (spring use it) or RxJava
Here is short example to compare Reactor with Java Stream/Optional
```java
import java.util.Optional;
import java.util.stream.Stream;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class App {
    public static void main(String[] args) {
        String name = "mike";
        /**
         * Reactor Mono is equivalent to java Optional
         */
        Mono.just(name).map(String::toUpperCase).map(n->"Hello, "+n).subscribe(System.out::println);
        Optional.of(name).map(String::toUpperCase).map(n->"Hello, "+n).ifPresent(System.out::println);

        /**
         * Reactor Flux is equivalent to java Stream
         */
        Stream.of(1,2,3).filter(i->i%2==0).map(i->i*2).forEach(System.out::println);
        Flux.just(1,2,3).filter(i->i%2==0).map(i->i*2).subscribe(System.out::println);
    }
}
```

Useful functions:
* `fromArray/fromIterable/fromStream` to consume array/list/stream
* `range(to, from)` (from value is inclusive) to generate flow of data.
* `mergeWith` - combine flux with another (`concat` in stream api)
* `zip` - combine flux with another and produce tuples (where one element from first flux, another from second) - so you can handle both flux at the same time (no alternative in stream api)
* `skip` - skip first n elements (`skip` in stream api)
* `take` - take first n elements (`take` in stream api)
* `filter` - filter elements based on Predicate (`filter` in stream api)
* `distinct` - filter only distinct elements (`distinct` in stream api)
* `map/flatMap` - transform value (`map/flatMap` in stream api)
* `subscribe` - terminal operation (`forEach` in stream api)
* `subscribeOn` - how we should handle flow (takes `reactor.core.scheduler.Scheduler`, usually from `Schedulers` you call `single/immediate/parallel`)
* `buffer` - create buffers of size n `Flux<List<T>>` (kind of `collect` in stream api)
* `collectList` - create `Mono<List<T>>` (`collect(Collectors.toList())` in stream api)
* `collectMap` - create `Mono<Map<K, V>>` (`collect(Collectors.toMap())` in stream api)
* `all` - take predicate, verify that all elements comply to it (`allMatch` in stream api)
* `yan` - take predicate, verify that at least one element comply to it (`anyMatch` in stream api)



To use project Reactor you should add to your `pom.xml`
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
```

Basic syntax
```java
import java.util.ArrayList;
import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public class App {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(List.of(1,2,3));
        Flux<Integer> flux = Flux.just(1,2,3);
        Flux<Integer> flux2 = Flux.fromIterable(list);

        Mono<Integer> mono = Mono.just(1);
    }
}
```


If you want to work with reactive spring data, your repo should extends from `ReactiveCrudRepository` where all calls return `Flux/Mono`. Notice that only cassandra and mongodb support true reactive programming.
Controller stay the same, but it returns not values but also Flux/Mono, and media type should be `MediaType.TEXT_EVENT_STREAM_VALUE` (server will create a response with `Content-Type: text/event-stream`).
Relational databases don't support reactive, due to blocking nature of `JDBC`. Although you can use `ReactiveCrudRepository` for relational db, it just wrap all calls to `JDBC` into `Flux/Mono`.

To work with cassandra you should add to your `pom.xml`
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-cassandra-reactive</artifactId>
</dependency>
```
For both mongo & cassandra we have reactive as well as non-reactive starters.


You can also use functional controllers. The idea is not to use annotation, but instead create beans with type `RouterFunction` and return routes there.
For this to work you should remove `spring-boot-starter-web` from `pom.xml`, or exclude tomcat from it.
```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
public class MyFunctionalController {
    @Bean
    RouterFunction<ServerResponse> routes() {
        return route(GET("/employee"), req -> ok().body(Flux.just("a", "b"), String.class))
                .and(route(GET("/employee/{id}"), req -> ok().body(Mono.just("a"), String.class)));
    }
}
```

When you add `webflux` starter, it also add `Reactor IPC` to work with reactive servers like `ne.tty` (framework for asynchronous operations)
Comparing to tomcat, which for every request create new thread and block it until work is done netty has 1 process, when it get request it use ChannelOperation to pass request to `DispatcherHandler` which pass request to spring controllers.
When controller done his work, it calls publisher to `ChannelOperation` where subscribe is called and netty returns result.


###### Data Validation
`@Valid` vs. `@Validated`. They work the same, but `@Validated` can be useful when you have multiple step validation and in each step you want to validate some of fields of your model
```java
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class MyController {
    @PostMapping("/step1")
    public void stepOne(@Validated(Account.Step1.class) Account account) {}

    @PostMapping("/step2")
    public void stepTwo(@Validated(Account.Step2.class) Account account) {}
}

class Account {
    public static class Step1{}
    public static class Step2{}

    @NotBlank(groups = {Step1.class})
    private String username;

    @Email(groups = {Step1.class})
    @NotBlank(groups = {Step1.class})
    private String email;

    @NotBlank(groups = {Step2.class})
    @Min(value = 8, groups = {Step2.class})
    private String password;

    @NotBlank(groups = {Step2.class})
    private String confirmedPassword;
}
```

Example with controller, model and validator
`Employee.java`
```java
package com.example.logic.ann.validation;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.Data;

@Data
public class Employee {
    // NotNull - only for objects
    @NotNull(message = "Id can't be null")
    private String id;

    @NotBlank(message = "Name can't be empty")
    @Size(min = 3, max = 5, message = "Name should be between 3 and 5 letters")
    private String name;

    @CreditCardNumber(message = "Credit card should be correct")
    private String cardNumber;

    @Digits(integer = 3, fraction = 0, message = "Ccv should be exactly 3 digits")
    private String ccv;
}
```
`EmployeeValidator.java`
```java
package com.example.logic.ann.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class EmployeeValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return cls == Employee.class;
    }

    @Override
    public void validate(Object obj, Errors err) {
        Employee emp = (Employee) obj;
        if ("Jack".equals(emp.getName())) {
            err.reject("name", "`Jack` is a bad choice");
        }
    }
}
```
`MyController.java`
```java
package com.example.logic.ann.validation;

import javax.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/employee")
@RequiredArgsConstructor
public class MyController {
    private final EmployeeValidator employeeValidator;

    /**
     * If we don't specify model to which apply validation, it would be applied to all methods in controller
     */
    @InitBinder("employee")
    public void addValidation(WebDataBinder binder) {
        binder.addValidators(employeeValidator);
    }


    /**
     * If we don't put errors as second param - exception would be thrown in case validation failed
     */
    @PostMapping
    public ResponseEntity save(@RequestBody @Valid Employee emp, Errors err){
        System.out.println(err);
        if(err.hasErrors()) {
            List<String> errors = err.getAllErrors().stream().map(e->e.getDefaultMessage()).collect(Collectors.toList());
            return new ResponseEntity<>(Map.of("object", emp, "errors", errors), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(emp, HttpStatus.OK);
    }
}
```
Run
```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class})
@ComponentScan("com.example.logic.ann.validation")
public class App{
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
```


###### HATEOAS - Hypermedia as the Engine of Application State
HATEOAS (Hypermedia as the Engine of Application State) - when response return also other possible resources, and you don't need to hardcode them in client side. Add this to `pom.xml`
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifact`Id>spring-boot-starter-hateoas</artifactId>
</dependency>
```

Data model should be extended from `RepresentationModel`
```java
package com.example.logic.ann.hateoas;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Data;

@Data
@Relation(value="person", collectionRelation="people")
public class Person extends RepresentationModel<Person> {
    private int id;
    private String name;
    private String email;
    private String phone;
}
```
Controller
```java
package com.example.logic.ann.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class MyController {
    private List<Person> persons = new ArrayList<>();

    @PostConstruct
    public void init(){
        Person p = new Person();
        p.setId(1);
        p.setName("John");
        p.setEmail("john@mail.com");
        p.setPhone("+123456789");
        persons.add(p);
    }

    @GetMapping
    public ResponseEntity<List<Person>> getPersons(){
        persons.forEach(this::addLinks);
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Person> addPerson(@RequestBody Person person){
        persons.add(person);
        return new ResponseEntity<>(person, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable int id) {
        Optional<Person> person = persons.stream().filter(p->p.getId()==id).findFirst();
        person.ifPresent(this::addLinks);
        return new ResponseEntity<>(person.orElse(null), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable int id, @RequestBody Person person) {
        Optional<Person> current = persons.stream().filter(p->p.getId()==id).findFirst();
        if (current.isEmpty()) {
            return new ResponseEntity<>(person, HttpStatus.NOT_FOUND);
        }
        persons.removeIf(p->p.getId()==id);
        persons.add(person);
        return new ResponseEntity<>(person, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePerson(@PathVariable int id) {
        persons.removeIf(p->p.getId()==id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    private void addLinks(Person p){
        p.add(linkTo(MyController.class).withRel("persons"));
        p.add(linkTo(MyController.class).withRel("addPerson"));
        p.add(linkTo(MyController.class).slash(p.getId()).withRel("getPerson"));
        p.add(linkTo(MyController.class).slash(p.getId()).withRel("updatePerson"));
        p.add(linkTo(MyController.class).slash(p.getId()).withRel("deletePerson"));
    }
}
```
Response 
```
[
    {
        "id": 1,
        "name": "John",
        "email": "john@mail.com",
        "phone": "+123456789",
        "links": [
            {
                "rel": "persons",
                "href": "http://localhost:8080/person"
            },
            {
                "rel": "addPerson",
                "href": "http://localhost:8080/person"
            },
            {
                "rel": "getPerson",
                "href": "http://localhost:8080/person/1"
            },
            {
                "rel": "updatePerson",
                "href": "http://localhost:8080/person/1"
            },
            {
                "rel": "deletePerson",
                "href": "http://localhost:8080/person/1"
            }
        ]
    }
]
```


###### RestTemplate and WebClient
`org.springframework.web.client.RestTemplate` - synchronous web client to interact with REST (Representational state transfer) API
`org.springframework.web.reactive.function.client.WebClient` - reactive asynchronous web client to interact with REST API


`RestTemplate` just like `JdbcTemplate` frees you from low level http boiler-place (create client, send request, handle response ...).
It automatically encodes passed url so `http://test.com/my list` => would be encoded into `my%20list`. 
3 examples to get object. 
```java
package com.example.logic.ann.hateoas;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestApiCall {
    public void getForObject(){
        RestTemplate rt = new RestTemplate();
        int id = 1;
        Person p1 = rt.getForObject("http://localhost:8080/person/{id}", Person.class, id);
        Person p2 = rt.getForObject("http://localhost:8080/person/{id}", Person.class, Map.of("id", id));
        Person p3 = rt.getForObject("http://localhost:8080/person/1", Person.class);
    }
    
    public void getForEntity(){
        RestTemplate rt = new RestTemplate();
        int id = 1;
        ResponseEntity<Person> p1 = rt.getForEntity("http://localhost:8080/person/{id}", Person.class, id);
        ResponseEntity<Person> p2 = rt.getForEntity("http://localhost:8080/person/{id}", Person.class, Map.of("id", id));
        ResponseEntity<Person> p3 = rt.getForEntity("http://localhost:8080/person/1", Person.class);
    }
}
```

If you have api with hypermedia (hateoas) you can use [traverson](https://github.com/traverson/traverson)
```java
Traverson traverson = new Traverson(URI.create("http://localhost:8080/api"), MediaTypes.HAL_JSON);
```






###### Custom HttpMessageConverter

When we receive request with `Content-type` header, we must parse it according to this content-type.
And when we send response, we should send it according to `Accept` header. 
To convert between java objects and response/request body we must use `HttpMessageConverter`.
Here a quick example how to implement custom converter. We implement 2 methods - one for handle request payload, other to convert response body
```java
package com.example.logic.ann.web;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

public class MyHMC extends AbstractHttpMessageConverter<Person> {
    public MyHMC(){
        super(new MediaType("text", "person"));
    }

    @Override
    protected boolean supports(Class<?> cls) {
        return cls == Person.class;
    }

    @Override
    protected Person readInternal(Class<? extends Person> cls, HttpInputMessage input) throws IOException, HttpMessageNotReadableException {
        try(Scanner scanner = new Scanner(input.getBody())) {
            Person p = new Person();
            String[] arr = scanner.next().split("/");
            p.setAge(Integer.parseInt(arr[0]));
            p.setName(arr[1]);
            return p;
        }
    }

    @Override
    protected void writeInternal(Person person, HttpOutputMessage output) throws IOException, HttpMessageNotWritableException {
        try(OutputStream stream = output.getBody()){
            stream.write(person.toString().getBytes());
        }
    }
}
```

We should also register it as custom converter in config file
```java
package com.example.logic.ann.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebJavaConfig implements WebMvcConfigurer {
    /**
     * You can register new HttpMessageConverter by either addging a Bean
     * or overriding configureMessageConverters
     */
    @Bean
    public HttpMessageConverter<Person> httpMessageConverter(){
        return new MyHMC();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MyHMC());
    }
}
```

Simple controller example
```java
package com.example.logic.ann.web;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class MyController {
    @GetMapping
    public Person handleGet(){
        Person p = new Person();
        p.setAge(30);
        p.setName("Jack");
        return p;
    }

    @PostMapping
    public Person handlePost( Person p){
        System.out.println("handlePost => " + p);
        return p;
    }
}
```


```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class})
@ComponentScan("com.example.logic.ann.web")
public class App{
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
```
Now you can send 2 requests 
```
curl -H "Accept: text/person" http://localhost:8080/person
curl -H "Content-Type: text/person" -d "30/Jack" http://localhost:8080/person
```



###### Spring ViewResolver
`ViewResolver` - special interface to determine which view to show. There are several implementation:
* `InternalResourceViewResolver extends UrlBasedViewResolver` - allows to set prefix & suffix to view name
* `BeanNameViewResolver` - resolves views declared as beans



###### HandlerMapping, HandlerAdapter, HttpRequestHandler
* `HttpRequestHandler` - special interface to handle requests
* `HandlerMapping` - define a mapping between request and `HandlerAdapter`
* `HandlerAdapter` - handler that executed when some url called

```java
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.HttpRequestHandler;

@Configuration
class JavaConfig{
    @Bean(name = "/test")
    public HttpRequestHandler httpRequestHandler(){
        return (HttpServletRequest req, HttpServletResponse res) -> System.out.println("handle");
    }
}
```
You can go to `curl http://localhost:8080/test`.





#### DB

###### Spring JDBC
Before using spring jdbc, we can use standard jdk jdbc.
Add this to your `pom.xml`
```
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.18</version>
</dependency>
```
Print all departments
```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class App {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/spring5?user=root&password=";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("select * from department");
        ) {
            while (rs.next()) {
                System.out.print(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3));
                System.out.println();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
}
```
```
1 Exchange IT
2 Solution IT
3 Markets CP
```

But of course we don't want to work with raw data, we would like to work with models. So here example of custom model & dao based on jdk jdbc
```java
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.logic.ann.jdbc.DepartmentModel;
import com.example.logic.ann.jdbc.jdk.DepartmentDao;
import com.example.logic.ann.jdbc.jdk.JdkJavaConfig;

public class App{
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(JdkJavaConfig.class);
        var dao = context.getBean(DepartmentDao.class);

        System.out.println("getAll => " + dao.getAll());
        System.out.println("getById(1) => " + dao.getById(1));

        DepartmentModel model = new DepartmentModel();
        model.setName("New dep");
        model.setType("cool");
        var saved = dao.save(model);
        System.out.println("save => " + saved);
        dao.delete(saved);
    }
}
```
```
getAll => [DepartmentModel(id=1, name=Exchange, type=IT), DepartmentModel(id=2, name=Solution, type=IT), DepartmentModel(id=3, name=Markets, type=CP), DepartmentModel(id=4, name=New dep, type=cool)]
getById(1) => DepartmentModel(id=1, name=Exchange, type=IT)
save => DepartmentModel(id=5, name=New dep, type=cool)
```

If you take a look into package `com.example.logic.ann.jdbc.jdk` you will see a lot of boilerplate, so it's better to use spring jdbc to handle this
For spring jdbc you should add to your `pom.xml`. You also would like embedded db like `h2` for your testing, so it's better to add it too.
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <version>1.4.200</version>
</dependency>
```

`JdbcTemplate` has only `update` method to run `DML/DDL` (data manipulation(create, alter, drop)/definition(insert, update, delete) language). There is no `insert` method.
`JdbcTemplate` handles creation and release of resources, executes SQL queries, update statements and stored procedure calls, performs iteration over ResultSets (when you pass `RowMapper` you just pass mapping, but template do all iteration over `ResultSet`, but when you pass `RowCallbackHandler` you do iteration by yourself), 
and extraction of returned parameter values It simplify work with jdbc, we also have `HibernateTemplate` that simplify work with hibernate.
`update/query` methods may take a third param as array of `java.sql.Types`. Setting argument type provides correctness and optimisation (slight) for the underlying SQL statement.
When you test application code that manipulates the state of the Hibernate session, make sure to flush the underlying session within test methods that execute that code.

Spring nicely translate all checked sql exceptions into runtime exceptions with clear names. But you can also add logic there, by creating your own translator.
```java
package com.example.logic.ann.jdbc.template;

import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;

public class MySqlExTranslator extends SQLErrorCodeSQLExceptionTranslator {
    static class SqlDeadException extends DataAccessException{
        public SqlDeadException(String msg, Exception ex) {
            super(msg, ex);
        }
    }
    @Override
    protected DataAccessException customTranslate(String task, String sql, SQLException ex){
        if(ex.getErrorCode() == 123456){
            return new SqlDeadException(task, ex);
        }
        return null;
    }
}
```

Before use it, you should register it in your jdbc template
```java
@Bean
public JdbcTemplate jdbcTemplate(){
    DataSource ds = sql();
    JdbcTemplate template = new JdbcTemplate();
    MySqlExTranslator translator = new MySqlExTranslator();
    translator.setDataSource(ds);
    template.setDataSource(ds);
    template.setExceptionTranslator(translator);
    return template;
}
```

We can pass params in 2 ways: `Object[]` or `Map`. If you want to use map you should use `NamedParameterJdbcTemplate` and put all params into map.
```java
var r1 = jdbcTemplate.queryForObject("select * from department where id=?", new Object[]{id}, new DepartmentModelMapper());
NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate);
Map<String, Object> params = new HashMap<>();
params.put("id", id);
var r2 = template.queryForObject("select * from department where id=:id", params, new DepartmentModelMapper());
System.out.println("r1 => " + r1);
System.out.println("r2 => " + r2);
```
```
r1 => DepartmentModel(id=1, name=Exchange, type=IT)
r2 => DepartmentModel(id=1, name=Exchange, type=IT)
```

Here is example how to query data with `JdbcTemplate`
`DepartmentDao.java`
```java
package com.example.logic.ann.jdbc.template;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.logic.ann.jdbc.DepartmentModel;
import com.example.logic.ann.jdbc.MyDao;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class DepartmentDao implements MyDao<DepartmentModel> {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<DepartmentModel> getAll() {
        return jdbcTemplate.query("select * from department", new DepartmentModelMapper());
    }

    @Override
    public DepartmentModel getById(int id) {
        return jdbcTemplate.queryForObject("select * from department where id=?", new Object[]{id}, new DepartmentModelMapper());
    }


    /**
     * Example how we can pass mappers
     */
    public DepartmentModel getByIdSimpleMapper(int id) {
        return jdbcTemplate.queryForObject("select * from department where id=?", new Object[]{id}, this::mapRowToModel);
    }
    public DepartmentModel getByIdSimpleMapperReordered(int id) {
        return jdbcTemplate.queryForObject("select * from department where id=?", this::mapRowToModel, id);
    }


    @Override
    public boolean deleteById(int id) {
        return jdbcTemplate.update("delete from department where id=?", id) == 1;
    }

    @Override
    public DepartmentModel save(DepartmentModel model) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(conn-> {
                PreparedStatement ps = conn.prepareStatement("insert into department(name, type) values(?, ?)", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, model.getName());
                ps.setString(2, model.getType());
                return ps;
            },
            keyHolder
        );
        model.setId(keyHolder.getKey().intValue());
        return model;
    }

    /**
     * Example of simple save, no need to use PreparedStatementCreator and keyHolder
     */
    public DepartmentModel simpleSave(DepartmentModel model) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("department")
            .usingGeneratedKeyColumns("id");
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> values = mapper.convertValue(model, Map.class);
        int id = insert.executeAndReturnKey(values).intValue();
        model.setId(id);
        return model;
    }

    private static class DepartmentModelMapper implements RowMapper<DepartmentModel> {
        @Override
        public DepartmentModel mapRow(ResultSet rs, int rowNumber) throws SQLException {
            DepartmentModel model = new DepartmentModel();
            model.setId(rs.getInt("id"));
            model.setName(rs.getString("name"));
            model.setType(rs.getString("type"));
            return model;
        }
    }

    /**
     * We can define mapper like this
     */
    private DepartmentModel mapRowToModel(ResultSet rs, int rowNumber) throws SQLException {
        DepartmentModel model = new DepartmentModel();
        model.setId(rs.getInt("id"));
        model.setName(rs.getString("name"));
        model.setType(rs.getString("type"));
        return model;
    }
}
```
Dao example
```java
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.logic.ann.jdbc.DepartmentModel;
import com.example.logic.ann.jdbc.template.DepartmentDao;


public class App {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("com.example.logic.ann.jdbc.template");
        var dao = context.getBean(DepartmentDao.class);
        System.out.println("getAll => " + dao.getAll());
        var model = new DepartmentModel();
        model.setName("finance");
        model.setType("my");
        model = dao.save(model);
        System.out.println("save => " + model);
        int id = model.getId();
        System.out.println("getById("+id+") =>" + dao.getById(id));
        System.out.println("deleteById("+id+") => " + dao.deleteById(id));
    }
}
```
```
14:38:54.020 [main] DEBUG org.springframework.jdbc.core.JdbcTemplate - Executing SQL query [select * from department]
14:38:54.020 [main] DEBUG org.springframework.jdbc.datasource.DataSourceUtils - Fetching JDBC Connection from DataSource
14:38:54.020 [main] DEBUG org.springframework.jdbc.datasource.SimpleDriverDataSource - Creating new JDBC Driver Connection to [jdbc:mysql://localhost:3306/spring5]
getAll => [DepartmentModel(id=1, name=Exchange, type=IT), DepartmentModel(id=2, name=Solution, type=IT), DepartmentModel(id=3, name=Markets, type=CP)]
14:38:54.061 [main] DEBUG org.springframework.jdbc.core.JdbcTemplate - Executing SQL update and returning generated keys
14:38:54.062 [main] DEBUG org.springframework.jdbc.core.JdbcTemplate - Executing prepared SQL statement
14:38:54.062 [main] DEBUG org.springframework.jdbc.datasource.DataSourceUtils - Fetching JDBC Connection from DataSource
14:38:54.062 [main] DEBUG org.springframework.jdbc.datasource.SimpleDriverDataSource - Creating new JDBC Driver Connection to [jdbc:mysql://localhost:3306/spring5]
save => DepartmentModel(id=28, name=finance, type=my)
14:38:54.086 [main] DEBUG org.springframework.jdbc.core.JdbcTemplate - Executing prepared SQL query
14:38:54.087 [main] DEBUG org.springframework.jdbc.core.JdbcTemplate - Executing prepared SQL statement [select * from department where id=?]
14:38:54.087 [main] DEBUG org.springframework.jdbc.datasource.DataSourceUtils - Fetching JDBC Connection from DataSource
14:38:54.087 [main] DEBUG org.springframework.jdbc.datasource.SimpleDriverDataSource - Creating new JDBC Driver Connection to [jdbc:mysql://localhost:3306/spring5]
getById(28) =>DepartmentModel(id=28, name=finance, type=my)
14:38:54.103 [main] DEBUG org.springframework.jdbc.core.JdbcTemplate - Executing prepared SQL update
14:38:54.103 [main] DEBUG org.springframework.jdbc.core.JdbcTemplate - Executing prepared SQL statement [delete from department where id=?]
14:38:54.103 [main] DEBUG org.springframework.jdbc.datasource.DataSourceUtils - Fetching JDBC Connection from DataSource
14:38:54.103 [main] DEBUG org.springframework.jdbc.datasource.SimpleDriverDataSource - Creating new JDBC Driver Connection to [jdbc:mysql://localhost:3306/spring5]
deleteById(28) => true
```

There are several implementations of `DataSource`
* `SimpleDriverDataSource` (pooled, so you can use it only for testing purpose.) - Similar to DriverManagerDataSource except that it provides direct Driver usage which helps in resolving general class loading issues with the JDBC DriverManager within special class loading environments such as OSGi. We should set driver class with `setDriverClass`.
* `DriverManagerDataSource` - Simple implementation of the standard JDBC DataSource interface, configuring the plain old JDBC DriverManager via bean properties, and returning a new Connection from every getConnection call. We can set just driver class name with `setDriverClassName`.
* `SingleConnectionDataSource` - (extends `DriverManagerDataSource`, implement `SmartDataSource`) - use single connection without closing it

Although you can write your own implementation of `RowMapper` for each entity, if your db columns correspond to your model, you can
use one of default impl like `BeanPropertyRowMapper`, or if you need to get a map (key - columns, value- values) you can use `ColumnMapRowMapper`.

If you want to manipulate the whole result set you can use `ResultSetExtractor`. `RowCallbackHandler` can be used in `query`, but you have to manually regulate state.
```java
private DepartmentModel resultSetToModel(ResultSet rs) throws SQLException {
    DepartmentModel model = new DepartmentModel();
    model.setId(rs.getInt("id"));
    model.setName(rs.getString("name"));
    model.setType(rs.getString("type"));
    return model;
}

public List<DepartmentModel> getAllRCH() {
    List<DepartmentModel> list = new ArrayList<>();
    jdbcTemplate.query("select * from department", rs->{
        // add first row
        list.add(resultSetToModel(rs));
        // add all other rows
        while (rs.next()) {
            list.add(resultSetToModel(rs));
        }
    });
    return list;
}
```

By default spring boot searches for `schema.sql` and `data.sql` under `src/resources` and run these files on start to recreate db.
You can change file location with this configs.
```
spring.datasource.schema=db/schema.sql
spring.datasource.data=db/test-data.sql
```

spring jdbc also provides several classes
`MappingSqlQuery<T>` - allows you to create query+rowmapper into single class
`SqlUpdate` - to execute insert/update/delete
`BatchSqlUpdate` - execute batch insert/update/delete (multiple inserts in one db query)
`SqlFunction<T>` - work with functions and stored procedure
You usually create your class by extending one of these and implement one abstract method.



###### Hibernate
`JPA` (java persistence API) - specification how to write orm. `Hibernate/EclipseLink` - concrete examples of such orm based on JPA.
Jpa specification is inside `javax.persistence` package. To use hibernate you need to add these into your `pom.xml`
```
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-entitymanager</artifactId>
    <version>5.4.12.Final</version>
</dependency>
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-validator</artifactId>
    <version>6.1.2.Final</version>
</dependency>
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-core</artifactId>
    <version>5.4.12.Final</version>
</dependency>
```
To use `org.springframework.orm` package add this too 
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

If you don't want to persist field into db, but to have it in entity, you should add `@Transient`
If you have a image in db with big size and want to get it lazy (load from db only when you request it), you should add `@Basic(fetch = FetchType.LAZY)` to your column

`@Entity vs @Table` - both have name attribute. For entity - name is a name of entity that you can use in `JPQL/HQL` queries. By default it's class name. For table - it's actual name of table in database.

Simple query example with hibernate
`DepartmentEntity.java`
```java
package com.example.logic.ann.jdbc.hibernate.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import java.util.List;
import lombok.Data;

@Data
@Entity
@Table(name = "department")
@NamedQuery(name = "DepartmentEntity.GET_BY_ID", query = "SELECT d FROM DepartmentEntity d JOIN FETCH d.employees WHERE d.id=:id")
public class DepartmentEntity {
    public final static String GET_BY_ID = "DepartmentEntity.GET_BY_ID";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Version
    private int version;

    //@OneToMany(mappedBy = "department", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @OneToMany(mappedBy = "department")
    private List<EmployeeEntity> employees;
}
```

`EmployeeEntity.java`
```java
package com.example.logic.ann.jdbc.hibernate.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "employee")
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "salary")
    private int salary;

    @Version
    private int version;

    /**
     * We use ToString.Exclude to exclude this field from toString
     * otherwise, when we called department.toString for each department it will call employee.toString, which again will call department.toString
     * so we will end up with stackOverFlowError
     */
    @ManyToOne
    @JoinColumn(name = "department_id")
    @ToString.Exclude
    private DepartmentEntity department;
}
```

`DepartmentDao.java`
```java
package com.example.logic.ann.jdbc.hibernate;

import javax.transaction.Transactional;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.logic.ann.jdbc.hibernate.entities.DepartmentEntity;

@Transactional
@Repository
public class DepartmentDao implements HibernateDao<DepartmentEntity> {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<DepartmentEntity> getAll() {
        return sessionFactory.getCurrentSession().createQuery("from DepartmentEntity d").list();
    }

    @Override
    public DepartmentEntity getById(int id) {
        //return (DepartmentEntity) sessionFactory.getCurrentSession().getNamedQuery(DepartmentEntity.GET_BY_ID).setParameter("id", id).uniqueResult();
        return (DepartmentEntity) sessionFactory.getCurrentSession().createQuery("from DepartmentEntity d where d.id=:id").setParameter("id", id).uniqueResult();
    }

    @Override
    public void delete(DepartmentEntity model) {
        sessionFactory.getCurrentSession().delete(model);
    }

    @Override
    public DepartmentEntity save(DepartmentEntity model) {
        sessionFactory.getCurrentSession().saveOrUpdate(model);
        return model;
    }
}
```

`HibernateJavaConfig.java`
```java
package com.example.logic.ann.jdbc.hibernate;

import javax.sql.DataSource;

import java.io.IOException;
import java.sql.Driver;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource("jdbc.properties")
public class HibernateJavaConfig {
    @Value("${driverClassName}")
    private String driverClassName;
    @Value("${url}")
    private String url;
    /**
     * If you use `user/username` it would be the name of your machine (diman),
     * cause in spring env variables overwrite configs
     */
    @Value("${dbUser}")
    private String username;
    @Value("${password}")
    private String password;

    @Bean
    public DataSource simpleDs() {
        try{
            SimpleDriverDataSource ds = new SimpleDriverDataSource();
            ds.setDriverClass((Class<? extends Driver>) Class.forName(driverClassName));
            ds.setUrl(url);
            ds.setUsername(username);
            ds.setPassword(password);
            return ds;
        } catch (ClassNotFoundException ex){
            throw new RuntimeException(ex);
        }
    }

    private Properties propserties() {
        Properties props = new Properties();
        /**
         * this config allows to lazy access after session is closed
         * but you shouldn't use it cause it's antipattern
         */
        //props.put("hibernate.enable_lazy_load_no_trans", true);

        props.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        props.put("hibernate.format_sql", true);
        props.put("hibernate.use_sql_comments", true);
        props.put("hibernate.show_sql", true);
        return props;
    }

    @Bean
    public SessionFactory sessionFactory() {
        try{
            LocalSessionFactoryBean sessionBean = new LocalSessionFactoryBean();
            sessionBean.setDataSource(simpleDs());
            sessionBean.setHibernateProperties(propserties());
            sessionBean.setPackagesToScan("com.example.logic.ann.jdbc.hibernate.entities");
            sessionBean.afterPropertiesSet();
            return sessionBean.getObject();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager(){
        return new HibernateTransactionManager(sessionFactory());
    }
}
```

`App.java`
```java
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.logic.ann.jdbc.hibernate.HibernateDao;
import com.example.logic.ann.jdbc.hibernate.entities.DepartmentEntity;
import com.example.logic.ann.jdbc.hibernate.entities.EmployeeEntity;

public class App{
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("com.example.logic.ann.jdbc.hibernate");
        /**
         * If you try to run `context.getBean(DepartmentDao.class);`, you will get
         * NoSuchBeanDefinitionException: No qualifying bean of type 'com.example.logic.ann.jdbc.hibernate.DepartmentDao' available
         * the reason, is since we are using @Transactional, our object is changed with proxy, that's why we should use interfaces
         */
        HibernateDao<DepartmentEntity> dao = context.getBean(HibernateDao.class);
        System.out.println(dao.getAll());

        var dep = new DepartmentEntity();
        dep.setName("cool");
        dep.setType("my");

        var emp = new EmployeeEntity();
        emp.setName("Jack");
        emp.setSalary(200);
        emp.setDepartment(dep);

        dep.setEmployees(List.of(emp));

        dep = dao.save(dep);
        System.out.println("saved => " + dep);
        System.out.println("getById("+dep.getId()+") => " + dao.getById(dep.getId()));
        dao.delete(dep);
    }
}
```

Lazy loading example
```java
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.logic.ann.jdbc.hibernate.HibernateDao;
import com.example.logic.ann.jdbc.hibernate.entities.DepartmentEntity;

public class App {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext("com.example.logic.ann.jdbc.hibernate");
        /**
         * If you try to run `context.getBean(DepartmentDao.class);`, you will get
         * NoSuchBeanDefinitionException: No qualifying bean of type 'com.example.logic.ann.jdbc.hibernate.DepartmentDao' available
         * the reason, is since we are using @Transactional, our object is changed with proxy, that's why we should use interfaces
         */
        HibernateDao<DepartmentEntity> dao = context.getBean(HibernateDao.class);
        System.out.println("__RUN__");
        var e1 = (DepartmentEntity) dao.getById(1);
        System.out.println(e1.getName());
        Thread.sleep(1000);
        System.out.println(e1.getType());
        Thread.sleep(1000);
        System.out.println(e1.getEmployees());
    }
}
```


###### Spring Data
`SessionFactory` extends `EntityManagerFactory`. You can call `entityManagerFactory.createEntityManager()` to get current `EntityManager` on which you can run queries like `update/remove/save`
You can also inject it like that. Types should match exactly.
```java
@PersistenceUnit
EntityManagerFactory factory;
@PersistenceContext
EntityManager manager;
```

`@PersistenceUnit` - injects EntityManagerFactory (no need to use `@Autowired`)
`@PersistenceContext` takes care to create a unique EntityManager for every thread (no need to use `@Autowired`)

`@PersistenceUnit` injects an `EntityManagerFactory`, and `@PersistenceContext` injects an `EntityManager`. It's generally better to use `@PersistenceContext` unless you really need to manage the `EntityManager` lifecycle manually.
Although you can use `EntityManager` to manually create queries, it's better to use spring data repository pattern, that wrap entity manager inside and provide many default queries out of the box.

There are 2 interfaces `CrudRepository` & `JpaRepository` from which you can extend your repository interface (spring will generate class automatically) and have many default queries already implemented.
`JpaRepository` extends `PagingAndSortingRepository` which in turn extends `CrudRepository`.
Their main functions are:
* `CrudRepository` mainly provides CRUD functions. Ffor `findAll` return `Iterable`.
* `PagingAndSortingRepository` provides methods to do pagination and sorting records.
* `JpaRepository` provides some JPA-related methods such as flushing the persistence context and deleting records in a batch. For `findAll` return `List`.

You can also add `@RepositoryDefinition` to your interface, and spring will create crud repository implementation for you (no need to extends other interfaces), basically it the same as extending Repository.
To enable work with repository add to your config `@EnableJpaRepositories("com.example.logic.ann.jdbc.spring.repository")`.
But if you want some custom query (like find user by first and last name) you just need to add abstract method `findByFirstNameAndLastName(String firstName, String lastName);`
and it would work (spring automatically build query based on it's name). The valid names are: `read...By, get...By, find...By`
In case you want to have your own query for the method, just add `@Query("your query")`. By default it take `JPL`(java persistence language) query, but you can set `nativeQuery` to true and add pure sql query (in this case no support for sorting & pagination)
If we have a named query for repo `@NamedQuery(name = "MyEntity.myQuery", query="...")` and we have 
```java
public interface MyEntityRepository extends JpaRepository <MyEntity, Long> {
    List<MyEntity> myQuery();
}
```
This query will use named query automatically. But if we add `@Query` it will override named query.
It takes precedence over named queries, which are annotated with `@NamedQuery` or defined in an orm.xml file. For native queries pagination can be enabled by setting `countQuery` to some native sql.


Also spring data can build all api endpoint for your repos. For this just add following to your `pom.xml`
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-rest</artifactId>
</dependency>
```
Internally it create new controller with `@RepositoryRestController` for every repository. So if you need some customization you can create new controller with this annotation.
You can go `curl localhost:8080/api` and see all endpoints created. By default it will pluralize all names, so person would be persons. But if you want custom names you should add to your repository `@RestResource(rel="person", path="people")`

You can easily add auditing to any entity by adding `@EntityListeners(AuditingEntityListener.class)` - we are adding spring data auditor, you can also create your own auditors.
And then have 4 columns with following annotations `@CreatedDate/@CreatedBy/@LastModifiedBy/@LastModifiedDate`
You should add to your config `@EnableJpaAuditing(auditorAwareRef =  "auditorAwareBean")` and finally
```java
@Component
public class AuditorAwareBean implements AuditorAware<String>  {
    public Optional<String> getCurrentAuditor() {
        return Optional.of("admin");
    }
}
```

We can also add `Entity Versioning`, so whenever update/delete happens, old versions would be stored in history table.
To enable versioning on entity just add `@Audited`.

Generally you should prefer `EntityManager` over `Session`, cause it jpa standard while `Session` is hibernate. `Session` extends `EntityManager`. You can always get session like `Session current = (Session) entityManager.getDelegate();`.

By default `delete` return void, but you still can verify that row was deleted. If row doesn't exist, `EmptyResultDataAccessException` would be thrown. So you can catch it and do something.

If you want to subscribe on events for some repository you can extend `AbstractRepositoryEventListener<YourRepo>` and implements methods like `afterCreate` and have some custom logic there.



###### JTA - java transaction API

`@Transactional` use `TransactionInterceptor` internally to intercept and wrap methods into transactions.

`Propagation` => 
* `REQUIRED` - default value, if no transaction exists, create new, otherwise run in current
* `SUPPORTS` - if transaction exists, run in int, otherwise run as non-transactional
* `MANDATORY` - throw exception if method was called from non-transactional method
* `REQUIRES_NEW` - always create and run in new transaction
* `NOT_SUPPORTED` - stop current transaction, run as non-transactional
* `NEVER` - throw exception if there is active transaction
* `NESTED` - if a transaction exists, create a savepoint. If method throws an exception, then transaction rollbacks to this savepoint. If there's no active transaction, it works like REQUIRED.
             
`Isolation` =>
* `READ_UNCOMMITTED` - dirty reads, repeatable reads, phantom reads
* `READ_COMMITTED` - repeatable reads, phantom reads
* `REPEATABLE_READ` - phantom reads
* `SERIALIZABLE` - no reads available

By default all `RuntimeException` and `Error` rollback transaction whereas checked exceptions don't. This is an EJB legacy. You can configure this by using `rollbackFor()` and `noRollbackFor()` annotation parameters `@Transactional(rollbackFor=Exception.class)`
You can also pass array of strings for `rollbackForClassName/noRollbackForClassName`.
In test framework you have `@Rollback/@Commit`. Rollback - by default true, can set to false (same as set just `@Commit`). If you don't specify anything, for all integration tests all transactions would be rollbacked after test run.

Don't confuse 
* `javax.transaction.Transactional` - java EE7 annotation, but since spring3 also supported by spring framework, 
* `org.springframework.transaction.annotation.Transactional` - has more options and will always execute the right way.

If for some reason you don't want to use this annotation (for example you have a method that takes too long to run and if you put `@Transactional` on it, you will use all available connections), you can use programmatic transaction with `TransactionTemplate` (it's thread-safe)
```java
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

public class App{
    public static void main(String[] args) {
        PlatformTransactionManager manager = new JpaTransactionManager();
        TransactionTemplate template = new TransactionTemplate(manager);
        /**
         * with some return value, takes TransactionCallback functional interface with method doInTransaction
         */
        template.execute(status -> {
            // you can rollback by 
            status.setRollbackOnly();
            template.update("sql query");
            return "done";
        });
        /**
         * Without return value, or you can use lambda that return null
         */
        template.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                // you can rollback by 
                status.setRollbackOnly();
            }
        });
    }
}
```

You can even work without this template and use tx manager directly
```java
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class App{
    public static void main(String[] args) {
        PlatformTransactionManager manager = new JpaTransactionManager();
        JdbcTemplate template = new JdbcTemplate();

        DefaultTransactionDefinition params = new DefaultTransactionDefinition();
        // you can set tx name this way
        params.setName("myGoodTx");
        TransactionStatus tx = manager.getTransaction(params);

        try{
            template.update("sql query");
            manager.commit(tx);
        }catch (Exception e) {
            manager.rollback(tx);
        }
    }
}
```

There are 2 types of transactions:
* Local - work with single resource (single db) and either commit or rollback
* Global - work with many resources (like one mysql and one oracle db), and either all changes to all db commiter or rollbacked. Using `XA` protocol.
To work with global tx you should add to your `pom.xml`
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jta-atomikos</artifactId>
</dependency>
```


###### DataSource Interceptor and Sql query counter
You can add these 2 libraries to your `pom.xml` to get ability to count how many queries actually has been sent to db `SQLStatementCountValidator.assertSelectCount(1);` and intercept all queries.
```
<dependency>
    <groupId>com.vladmihalcea</groupId>
    <artifactId>db-util</artifactId>
    <version>1.0.4</version>
</dependency>
<dependency>
    <groupId>p6spy</groupId>
    <artifactId>p6spy</artifactId>
    <version>3.9.0</version>
</dependency>
```


###### @DynamicUpdate/@DynamicInsert and @NamedEntityGraph
By default when you update entity, even if you set 1 fields, orm will generate sql update with all fields in entity. If you want short update with only your field you should use dynamic update annotations. Same true for dynamic insert.
Entity graph - can be used to lazy/eager data loading.


###### Cascade types
When one entity A depends on entity B, we can either, first save B, and then A, or set `cascade` for Entity A, and just save A, and B would be saved automatically
```java
import lombok.Data;
import lombok.SneakyThrows;
import lombok.ToString;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class App{
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(App.class.getPackageName());
        context.getBean(MyRepository.class).save();
    }
}

@Component
@Transactional
class MyRepository{
    @Autowired
    private SessionFactory sessionFactory;

    public void save(){
        Session session = sessionFactory.getCurrentSession();
        User user = new User();
        user.setName("Jack");

        Account account = new Account();
        account.setCurrency("USD");
        account.setUser(user);

        user.setAccounts(List.of(account));
        session.save(account);

        User savedUser = (User) session.createQuery("FROM User u WHERE u.id=:id").setParameter("id", "1").uniqueResult();
        System.out.println("user => " + savedUser.getString());
        Account savedAccount = (Account) session.createQuery("FROM Account a WHERE a.id=:id").setParameter("id", "1").uniqueResult();
        System.out.println("account => " + savedAccount.getString());

        account.setUser(savedUser);
        account.setAmount(100);
        session.save(account);

        savedUser = (User) session.createQuery("FROM User u WHERE u.id=:id").setParameter("id", "1").uniqueResult();
        System.out.println("user => " + savedUser.getString());
        savedAccount = (Account) session.createQuery("FROM Account a WHERE a.id=:id").setParameter("id", "1").uniqueResult();
        System.out.println("account => " + savedAccount.getString());
    }
}

@Configuration
@EnableTransactionManagement
class JavaConfig{
    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.H2).build();
    }

    @Bean
    @SneakyThrows
    public SessionFactory sessionFactory() {
        LocalSessionFactoryBean sessionBean = new LocalSessionFactoryBean();
        sessionBean.setDataSource(dataSource());
        sessionBean.setPackagesToScan(App.class.getPackageName());
        var props = new Properties();
        // create tables in db from entities
        props.put("hibernate.hbm2ddl.auto", "create");
        sessionBean.setHibernateProperties(props);
        // initialize factory, if not called, `getObject` would return null
        sessionBean.afterPropertiesSet();
        return sessionBean.getObject();
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager(){
        return new HibernateTransactionManager(sessionFactory());
    }
}

@Entity
@Data
class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Account> accounts = new ArrayList<>();

    /**
     * Since user has a list of account and every account has an associated user, if we just stringify them, we would get StackOverFlow error
     * cause they would try to call toString from each other. That's why we exclude them from toString, and write another function to stringify
     */
    public String getString(){
        return "User[id=" + id + ", name=" + name + " accounts=[" + accounts.stream().map(Account::toString).collect(Collectors.joining(", ")) + "]]";
    }
}

@Entity
@Data
class Account{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String currency;
    private int amount;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    @ToString.Exclude
    private User user;

    public String getString(){
        return "Account=[id="+id+", currency="+currency+", amount="+amount+", user="+user+"]";
    }
}
```




#### Spring Testing
When you add spring test starter `junit` is added by default. `TestNG` is not supported. If you want to use `TestNG` instead of `junit` you have to manually add it to `pom.xml`.

###### TestPropertySource and TestPropertyValues
In testing you can add custom properties in 2 ways. `@TestPropertySource` - to pass test source. If you don't pass anything it will search for  default property file with name of your test class. 
If such filed doesn't exist you got `IllegalStateException: Could not detect default properties file for test class [com.example.spring5.JavaConfigTest]: class path resource [com/example/spring5/JavaConfigTest.properties] does not exist`.
You can also use `TestPropertyValues` to change properties on the fly.
```java
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.logic.ann.props.Person1;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = {JavaConfigTest.PropsACI.class}, classes = {JavaConfigTest.Config.class})
@TestPropertySource(properties = {"name = foo"})
public class JavaConfigTest {
    @ComponentScan("com.example.logic.ann.props")
    static class Config{}

    static class PropsACI implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext context) {
            TestPropertyValues.of("age=22").applyTo(context);
        }
    }

    @Autowired
    private ConfigurableApplicationContext context;

    @Test
    public void test(){
        System.out.println(context.getBean(Person1.class));
    }
}
```

You can also use `MockEnvironment` and `MockPropertySource`

###### OutputCaptureRule
This class helps to test what has been logged to console
```java
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.system.OutputCaptureRule;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.logic.ann.beans.JavaConfig;
import com.example.logic.ann.beans.Printer;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = JavaConfig.class)
public class MySimpleTest {
    @Autowired
    private Printer printer;

    @Rule
    public OutputCaptureRule capture = new OutputCaptureRule();

    @Test
    public void test(){
        printer.print("Hello World!");
        assertThat(capture.toString(), containsString("World"));
    }
}
```


###### TestExecutionListener
This is special interface that helps to manage test lifecycle. kind of `@BeforeEach, @AfterEach, @BeforeAll, and @AfterAll`
There are default impl, but you can create your own impl. To add it to your class use 
```java
@TestExecutionListeners(value = {
  CustomTestExecutionListener.class,
  DependencyInjectionTestExecutionListener.class
})
```
Pay attention that adding it like that remove all default listeners, that's why we explicitly add `DI` listener.
To preserve default listener add `mergeMode = MergeMode.MERGE_WITH_DEFAULTS`








First let's add junit to `pom.xml`
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

By default this starter come with junit4 and junit-vintage. If you want to use new junit5 jupiter, you should exclude these 2 and add jupiter explicitly
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
    <exclusions>
        <exclusion>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
        </exclusion>
        <exclusion>
            <groupId>org.junit.vintage</groupId>
            <artifactId>junit-vintage-engine</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-api</artifactId>
    <version>5.6.2</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-engine</artifactId>
    <version>5.6.2</version>
    <scope>test</scope>
</dependency>
```

RunWith vs ExtendWith. They both are `junit` annotations, not spring
If you are using Junit version < 5, so you have to use @RunWith(SpringRunner.class) or @RunWith(MockitoJUnitRunner.class) etc.
If you are using Junit version = 5, so you have to use @ExtendWith(SpringExtension.class) or @ExtendWith(MockitoExtension.class) etc.

If we want to load context or use `@Autowired` we should annotate test class with
```java
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JavaConfig.class)
```
But this 2 can be simplified to `@SpringJUnitConfig`. For web we should use `@SpringJUnitWebConfig`.
Here is full example
```java
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.example.logic.ann.beans.JavaConfig;
import com.example.logic.ann.beans.Printer;

@SpringJUnitConfig(classes = JavaConfig.class)
public class MySimpleTest {
    @Autowired
    private Printer printer;

    @Test
    public void getVersion(){
        String currentVersion = Test.class.getPackage().getImplementationVersion();
        System.out.println("currentVersion => " + currentVersion);
    }

    @Test
    public void doWork(){
        printer.print("hello");
    }
}
```

There are a few useful annotations you can use inside your test framework
`@Before/@After` - run some logic before all tests starts
`@ActiveProfiles("")"` - set up profiles for which tests would run

If we are using spring boot without profile it by default loads `application.properties/yml` files
If we set profile, it loads `application{you_profile}.properties/yaml`. Or you can have one config file divided by `---`, like
```
logging:
    level:
        tacos: DEBUG
---
spring:
    profiles: prod
logging:
    level:
    tacos: WARN
```
Order of property loading: `JVM system props`=>`OS env vars`=>`Command-line args`=>`application.properties`=>`application.yml`
If you set `server.port=0` app will start on any randomly selected port.

`@SpringBootTest` - if we don't pass anything it will create full web context. If we pass custom configuration
it will run only small part of total context. It also caching contexts for config.
So if you have one config for 10 tests, spring boot won't recreate context for all tests 10 time, instead it will create context
once and then will just use cached version. If we have several config file we should use `@ContextHierarchy`
* If you don't pass anything spring boot test will search for `@SpringBootConfiguration`
* If we want to clear context for some test or after it, we should use `@DirtiesContext`. You can add it to class/method, you can modify context as you want, but after execution, spring will reload context.
* If we want to have custom configuration that won't be used by spring boot test we should annotate it with `@TestConfiguration`
* If you want to test db you should use `@DataJpaTest` (loads all beans, but throw away everything except `@Repository`)
* If you want to test controllers you should use `@WebMvcTest` (loads all beans, but throw away everything except `@Controller`)
* If you need mock inside your test you should add `@MockBean` to property and use it later. But if you need mock only to build other objects, you can add them to class level like `@MockBean(MyService.class)`, it works since it repeatable.
* You can also test web flux with `WebTestClient`
* If you need web app context you can add class level annotation `@WebAppConfiguration`, or you can use `@SpringJUnitWebConfig` which is the same as `@SpringJUnitConfig + @WebAppConfiguration`



Simple example to test mvc
```java
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;import com.example.logic.ann.security.MyController;

@RunWith(SpringRunner.class)
@WebMvcTest(MyController.class)
public class ControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void test() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("myView"))
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("hello")));
    }
}
```

You can use `@ContextConfiguration(locations="classpath:config.xml" ,classes=Config.class)` and pass either list of xml configs or java configs.
To get access to shared context you can also extend your class from abstract `AbstractJUnit4SpringContextTests` or directly implement `ApplicationContextAware`.
This annotation is `@Inherited`, so you can create base test config class and extend all other classes from it to use same context.
If you don't specify any data to this annotation, it will load inner classes marked with `@Configuration`
If you use this annotation across multiple test, spring will automatically cache context, when you use the same `locations`.
This will enable to speed up your tests.
If you want to have separate contexts for different tests you should use. Order of configuration is important, if A depends on B, then B should go first.
```java
@ContextHierarchy({
    @ContextConfiguration("/parent-config.xml"),
    @ContextConfiguration("/child-config.xml")
})
```
If you are using `@SpringBootTest`, context automatically cached.

If you want to scan package you should add just `@ContextConfiguration` and add inner static config class
`@Mock` - create just proxy object and when you call methods, it doesn't execute them. Yet you can overwrite behavior with `when/then`.
`@Spy` - create real bean, but you still can overwrite behaviour with `when/then`.
`@InjectMocks` - create real bean, and inject all `@Mock/@Spy` beans declared within test class. If some undeclared, inject null.

`@Mock/@Spy` - plain mockito annotation to be used in unit tests
`@MockBean/@SpyBean` - spring boot test annotation used in integration tests (They add mock to spring application context, so every call to such bean within context would be replaced with mock bean)

```java
package com.example.spring5;

import com.example.logic.ann.beans.SimpleBean;
import com.example.logic.ann.beans.SimplePrinter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ContextConfiguration
public class BeansIntegrationTest {
    /**
     * In case you want to scan package you should add
     * inner static class (signature can't be private, otherwise ContextConfiguration can't load it)
     */
    @Configuration
    @ComponentScan("com.example.logic.ann.beans")
    public static class TestConfig{}

    @Mock
    private SimplePrinter simplePrinter;

    @InjectMocks
    private SimpleBean simpleBean;

    @Test
    public void testSayHello(){
        doAnswer(inv->{
            System.out.println("mock => " + inv.getArgument(0).toString());
            return null;
        }).when(simplePrinter).print(any(String.class));
        simpleBean.sayHello();
    }
}
```






#### Spring Monitoring
###### Jmx monitoring
If we want to import spring beans to jmx we would need to add. Spring will try to find running `MBeanServer`, and in case of web app it would be tomcat.
```java
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.MBeanExporter;

@Configuration
public class MyWebConfig {
    @Bean
    public MyService myService() {
        return new MyService() {
            @Override
            public void print() {
                System.out.println("hello");
            }
        };
    }
    @Bean
    public MBeanExporter jmxExporter() {
        MBeanExporter exporter = new MBeanExporter();
        Map<String, Object> beans = new HashMap<>();
        beans.put("bean:name=myService", myService());
        exporter.setBeans(beans);
        return exporter;
    }
}

interface MyService{
    void print();
}
```

You can add `@EnableMBeanExport` to config bean to enable support for registering mbeans with annotations.
You can also add `@ManagedResource(description = "JMX managed resource", objectName = "jmxDemo:name=myService")` to your bean directly.
And add `@ManagedOperation` to any method you want to be able to call from jmx console. If you want to expose property add `@ManagedAttribute(description = "myProp")`


You can also add hibernate to jmx console by adding following props
```java
props.put("hibernate.jmx.enabled", true);
props.put("hibernate.generate_statistics", true);
props.put("hibernate.session_factory_name", "sessionFactory");
```



###### Spring Boot Actuator
First you should add this dependency 
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```
Then these 2 would be available `/actuator/health` and `/actuator/info`

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class})
public class App{
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
```

By default all mapping are `/actuator/health` and so on, so base path is `actuator`. You can change that mapping to whatever you want, in your config file
```
management.endpoints.web.base-path = /
management.endpoints.web.path-mapping.health = apphealth
```
By default only `/health` & `/info` are enabled, cause actuator doesn't have any security. You can secure it with spring security, and enable other features by adding `management.endpoints.web.exposure.include = health,info,beans,conditions`, or set it to `*` to include all endpoints.
If you want to include all, except a few you can add `include=*` and `management.endpoints.web.exposure.exclude = info, beans`.

If you want to work with `jmx` rather than with web, that you should add to config like `management.endpoints.jmx.`

`/info` - empty by default, but you can add anything there, by adding properties with prefix `info`, like
```
info.my.v1=100
info.my.v2=200
```
Health by default just show status (`up/down/unknown/out_of_service`), but if you want more details you can add `management.endpoint.health.show-details=always`
If you want change the severity you should changed `management.health.status.order` props
If you want to add new status you should add `anagement.health.status.order=FATAL, DOWN, OUT_OF_SERVICE, UNKNOWN, UP` to your props file.
If you want to map health status to http status you should add `management.health.status.http-mapping.FATAL=422` (this would map FATAL status to http 422 unproccessible entity)

`/beans` - view context configuration
`/conditions` - view autoconfiguration rules (which beans has been autoconfigured)
`/env` - info about env variables. It's not only GET, but calling POST with valid json, you can create/update env vars on the fly (they would apply to current running instance, and would be lost once we restart app)
`/mappings` - info about current http endpoints
`/loggers` - view logging level for every component (like with env, you can change logging level with POST request)
`/heapdump` - download gzip heap dump file with info about heap, memory and so on (useful in debugging memory leaks or heap problems)
`/metrics` - view all metrics of running app

Customizing actuator
`/info` - you should implement `InfoContributor`
`/health` - you should implement `HealthIndicator`
`/metics` - you can inject `MeterRegistry` into any bean, and add info into it.

You can also create custom endpoint. For this annotate class with `@Endpoint`, method should be annotated with `@ReadOperation/@WriteOperation/@DeleteOperation`.
The reason actuator use it's own annotation instead of `@Controller`, is that actuator is not just controller, it's also expose data to JMX console. So that's why create only controller not enough.

To secure actuator you should use spring security. Since `/actuator` path can be changes from config file, it's better to use `EndpointRequest` like
```java
protected void configure(HttpSecurity http) throws Exception {
    http
    .requestMatcher(EndpointRequest.toAnyEndpoint().excluding("health", "info"))
    .authorizeRequests()
    .anyRequest().hasRole("ADMIN")
    .and()
    .httpBasic();
}
```

If you want to have nice ui to view actuator endpoints you should add spring boot admin to your `pom.xml`
```
<dependency>
    <groupId>de.codecentric</groupId>
    <artifactId>spring-boot-admin-starter-server</artifactId>
</dependency>
```
And add `@EnableAdminServer`.










#### Message Support

###### JMS
To work with jms you should add to `pom.xml`
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-artemis</artifactId>
</dependency>
```
artemis is next generation of activemq. By default artemis use port 61616. But you can configure it
```
spring.artemis.host=localhost
spring.artemis.port=61616
spring.artemis.user=user
spring.artemis.password=password
```
First you need to download [artemis](https://activemq.apache.org/components/artemis/download/)
After downloading you can create broker `./apache-artemis-2.11.0/bin/artemis create mybroker`. It will create mybroker folder, and you can run it `./mybroker/bin/artemis run`.
You can also access console from `http://localhost:8161/`

If we are using `@SpringBootApplication`, `JmsTemplate` would be automatically created (because of auto-configuration). If not we should explicitly declare such a bean.

To convert between your object and message you can use `org.springframework.jms.support.converter.MessageConverter`, you can implement it to crete your own, but there is no need, cause you can use `SimpleMessageConverter` or `MappingJackson2MessageConverter`.
Config file
```java
package com.example.logic.ann.message.jms;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;

import org.apache.activemq.artemis.jms.client.ActiveMQJMSConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;

@Configuration
public class MessageJavaConfig {
    @Bean
    public Destination destination(){
        return new ActiveMQQueue("localhost:61616");
    }

    @Bean
    public ConnectionFactory connectionFactory(){
        return new ActiveMQJMSConnectionFactory();
    }

    @Bean
    public MessageConverter messageConverter(){
        return new SimpleMessageConverter();
    }

    @Bean
    public JmsTemplate jmsTemplate(){
        JmsTemplate jms = new JmsTemplate(connectionFactory());
        jms.setDefaultDestination(destination());
        return jms;
    }

    /**
     * You can also implicitly set listener by creating component with method annotated @JmsListener
     */
    @Bean
    public MessageListenerContainer listenerContainer() {
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setDestination(destination());
        container.setMessageListener(new MyJmsListener(messageConverter()));
        return container;
    }
}
```
Sender
```java
package com.example.logic.ann.message.jms;

import javax.jms.Message;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class MyJmsSender {
    @Autowired
    private JmsTemplate jms;

    public void send(){
        jms.send(session -> session.createObjectMessage("time: " + LocalDateTime.now()));
    }

    /**
     * You can receive messages synchronously with this call
     */
    public Message receive(){
        return jms.receive();
    }
}
```

Receiver
```java
package com.example.logic.ann.message.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.jms.support.converter.MessageConverter;

public class MyJmsListener implements MessageListener {
    private MessageConverter converter;

    public MyJmsListener(MessageConverter converter){
        this.converter = converter;
    }

    @Override
    public void onMessage(Message message) {
        try {
            System.out.println("converted => " + converter.fromMessage(message) + ", message => " + message);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
```

Main code
```java
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.example.logic.ann.message.jms.MyJmsSender;

public class App{
    public static void main(String[] args) throws InterruptedException {
        var context = new AnnotationConfigApplicationContext("com.example.logic.ann.message.jms");
        MyJmsSender jms = context.getBean(MyJmsSender.class);
        jms.send();
        jms.send();
        System.out.println("done");
    }
}
```


###### AMQP (RabbitMQ)
Advanced Message Queuing Protocol
First add this dependency to your `pom.xml`
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```
Adding this starter to your build will trigger autoconfiguration that will create a AMQP connection factory and RabbitTemplate beans.
So if you don't use spring boot you will have to configure them manually.

You also need to install rabbitmq `sudo apt-get install rabbitmq-server`. You can start/stop and check status with following commnads
```shell script
sudo systemctl start rabbitmq-server.service
sudo systemctl enable rabbitmq-server.service
sudo rabbitmqctl status
```
To get web console access you need to add web console and add user. After you can access it from `http://localhost:15672/`
```shell script
sudo rabbitmq-plugins enable rabbitmq_management

# add user
sudo rabbitmqctl add_user user password
sudo rabbitmqctl set_user_tags user administrator
sudo rabbitmqctl set_permissions -p / user ".*" ".*" ".*"
```

Config file
```java
package com.example.logic.ann.message.amqp;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitJavaConfig {
    public final static String QUEUE_NAME = "my_queue";

    @Bean
    public ConnectionFactory connectionFactory(){
        return new CachingConnectionFactory();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(){
        return new RabbitTemplate(connectionFactory());
    }

    /**
     * Same can be achieved by using @RabbitListener
     */
    @Bean
    public MessageListenerContainer listenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setQueueNames(QUEUE_NAME);
        container.setMessageListener(new MyRabbitListener());
        return container;
    }
}
```

Sender
```java
package com.example.logic.ann.message.amqp;

import java.time.LocalDateTime;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyRabbitSender {
    @Autowired
    private RabbitTemplate rabbit;

    public void send() {
        MessageConverter converter = rabbit.getMessageConverter();
        MessageProperties properties = new MessageProperties();
        Message message = converter.toMessage("time: " + LocalDateTime.now(), properties);
        rabbit.send(RabbitJavaConfig.QUEUE_NAME, message);
    }
}
```

Receiver
```java
package com.example.logic.ann.message.amqp;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class MyRabbitListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println("message => " + message);
    }
}
```

Main code
```java
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.example.logic.ann.message.amqp.MyRabbitSender;

public class App{
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext("com.example.logic.ann.message.amqp");
        var sender = context.getBean(MyRabbitSender.class);
        sender.send();
        sender.send();
    }
}
```

###### Kafka
Kafka is new generation message system offers clustering out of the box. 
First you need to add to your `pom.xml`
```
<dependency>
    <groupId>org.springframework.kafka</groupId>
    <artifactId>spring-kafka</artifactId>
</dependency>
```
If you are using spring boot it will auto configure `KafkaTemplate`, otherwise you have to do it manually.

To install kafka you have to install zookeeper and kafka
```shell script
sudo apt-get install zookeeperd
# start/stop/status
systemctl start zookeeper
systemctl stop zookeeper
systemctl status zookeeper

# It's better to install kafka under separate user, but since it's our dev env, we won't create dedicated user
mkdir ~/kafka && cd ~/kafka
wget http://www.trieuvan.com/apache/kafka/2.4.1/kafka_2.13-2.4.1.tgz
tar -xvzf kafka_2.13-2.4.1.tgz --strip 1
rm kafka_2.13-2.4.1.tgz
./bin/kafka-server-start.sh config/server.properties
```

Config file
```java
package com.example.logic.ann.message.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListenerContainer;

@Configuration
public class KafkaJavaConfig {
    public final static String TOPIC_NAME = "my_topic";
    private final static String SERVER_URL = "localhost:9092";
    private final static String GROUP_ID = "my_group";

    @Bean
    public ProducerFactory<String, String> producerFactory(){
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVER_URL);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory(){
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVER_URL);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public MessageListenerContainer messageListenerContainer() {
        KafkaMessageListenerContainer<String, String> listener = new KafkaMessageListenerContainer<>(consumerFactory(), new ContainerProperties(TOPIC_NAME));
        listener.setupMessageListener(new MyKafkaListener());
        return listener;
    }

}
```

Sender
```java
package com.example.logic.ann.message.kafka;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MyKafkaSender {
    @Autowired
    private KafkaTemplate<String, String> kafka;

    public void send(){
        kafka.send(KafkaJavaConfig.TOPIC_NAME, "time: " + LocalDateTime.now());
    }
}
```

Listener
```java
package com.example.logic.ann.message.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;

public class MyKafkaListener implements MessageListener<String, String> {
    @Override
    public void onMessage(ConsumerRecord<String, String> record) {
        System.out.println("record => " + record);
    }
}
```


Main file
```java
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.example.logic.ann.message.kafka.MyKafkaSender;

public class App{
    public static void main(String[] args) throws InterruptedException {
        var context = new AnnotationConfigApplicationContext("com.example.logic.ann.message.kafka");
        var sender = context.getBean(MyKafkaSender.class);
        sender.send();
        sender.send();
    }
}
```







#### Miscellaneous
`@SpringBootApplication` - by default use component scanning of base package (where it was defined) and all subpackages. You can change this behavior by setting `scanBasePackages/scanBasePackageClasses` by setting other packages or java config classes.


###### mvnw and mvnw.cmd
When you download [spring boot](https://start.spring.io/) you have 2 files `mvnw` and `mvnw.cmd`. These 2 files from [Maven Wrapper Plugin](https://github.com/takari/takari-maven-plugin) 
that allows to run app on systems where there is no mvn installed. `mvnv` - script for linux, `mvnw.cmd` - for windows. Generally you don't need them in your work, so you may delete them.

###### Get param names
Starting from java9, you can [get names](#https://github.com/dgaydukov/cert-ocpjp11/blob/master/files/ocpjp11.md#get-param-names).
But spring was using it's own utility classes, that could get param names from debug info, by using asm
```java
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.util.Arrays;


public class App {
	public static void main(String[] args) {
		ParameterNameDiscoverer nameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
		String[] names = nameDiscoverer.getParameterNames(Person.class.getDeclaredConstructors()[0]);
		System.out.println(Arrays.toString(names));
	}
}

class Person{
	public Person(String name, int age){}
}
```
```
[name, age]
```


###### Pom vs Bom
POM - project object model. BOM - bill of materials.
Bom - special kind of pom, that helps control versions, and provide a central place to update these versions,
it usually incude block `<dependencyManagement/>` where all dependencies are stored. When maven try to build project
it first look for dependency in pom, and if can't find it goes to bom. 
There are 2 ways to use bom
1. Inheret is as parent.
```
<parent>
    <groupId>bomGroup</groupId>
    <artifactId>bomArtifact</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</parent>
```

In case you already have a parent, you can just import it
```
<dependency>
    <groupId>bomGroup</groupId>
    <artifactId>bomArtifact</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <type>pom</type>
    <scope>import</scope>
</dependency>
```

So bom allows you to not include version in your pom, cause it can be already in bom.


###### Spring Batch
Batch processing is using when you need to process a batch of data
First let's add starter to `pom.xml`
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-batch</artifactId>
</dependency>
```
By default all jobs starts automatically, but you can turn off them in `application.properties`, just add `spring.batch.job.enabled=false`.
```java
import java.time.LocalDateTime;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("com.example.logic.ann.batch")
public class App {
    public static void main(String[] args) throws Exception {
        var context = SpringApplication.run(App.class, args);
        System.out.println("context => " + context.getClass().getName());

        Job job = context.getBean(Job.class);
        JobLauncher jobLauncher = context.getBean(JobLauncher.class);
        JobParameters jobParameters = new JobParametersBuilder()
            .addString("date", LocalDateTime.now().toString())
            .toJobParameters();
        jobLauncher.run(job,  jobParameters);
    }
}
```
```
before => StepExecution: id=1, version=1, name=myStep, status=STARTED, exitStatus=EXECUTING, readCount=0, filterCount=0, writeCount=0 readSkipCount=0, writeSkipCount=0, processSkipCount=0, commitCount=0, rollbackCount=0, exitDescription=
processing => a
processing => bb
processing => ccc
write => [1, 2, 3]
after => StepExecution: id=1, version=2, name=myStep, status=COMPLETED, exitStatus=COMPLETED, readCount=3, filterCount=0, writeCount=3 readSkipCount=0, writeSkipCount=0, processSkipCount=0, commitCount=1, rollbackCount=0, exitDescription=
2020-04-07 20:13:45.730  INFO 5685 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [myStep] executed in 3s31ms
2020-04-07 20:13:45.736  INFO 5685 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=myJob]] completed with the following parameters: [{date=2020-04-07T20:13:42.503241}] and the following status: [COMPLETED] in 3s59ms
```


###### Spring Integration
First you need to add integration starter and message security to your `pom.xml`
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-integration</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.integration</groupId>
    <artifactId>spring-integration-security</artifactId>
    <version>5.2.5.RELEASE</version>
</dependency>
```
Here is simple example of sending message every 5 seconds through channel
```java
package com.example.spring5;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;


public class App{
    public static void main(String[] args) {
        /**
         * We need to set security context first and set it to shared-thread mode
         */
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        Authentication auth = new UsernamePasswordAuthenticationToken("user", "password", List.of(()->"ROLE_USER"));
        securityContext.setAuthentication(auth);
        SecurityContextHolder.setContext(securityContext);
        var context = new AnnotationConfigApplicationContext("com.example.logic.ann.integration");
    }
}
```
Here we using P2P channel, if we want to send message to many channels we should use `PublishSubscribeChannel`
`@BridgeFrom` - can connect PubSub channel with P2P

There are several building blocks of integration
* Channel (`PublishSubscribeChannel` or `FluxMessageChannel`) - pipes that connect all other parts
* Filter (Bean annotated with `@Filter`) - can be placed between channels to determine should message go further
* Transformer (Bean with `@Transformer` returning `GenericTransformer<S, T>`) - can transform data 
* Router (Bean with `@Router`, returning `AbstractMessageRouter`) - direct messages to different channels based on some criteria
* Splitter (Bean with `@Splitter`) - split message into several sub-messages. (You can combine it with router and split message from one channel and send 2 message to 2 other channels)
* Service activator (Bean with `@ServiceActivator` returning `MessageHandler`) - receive message from channel and handle it
* Gateway (interface with `@MessagingGateway`) - entry point to which third-party app send data for spring integration
* Channel Adapter (Bean with `@InboundChannelAdapter` returning `MessageSource<T>`) - entry point





###### Spring XD
Spring XD (eXtreme Data) - command line tool to set up jobs and execute them on distributed system in real-time.


###### Spring DevTools
They allowed
* hot reloading - when you change your code, app would restart. DevTools - has 2 classloaders, one with your classes, other with dependencies. When you change your code, only one classloader is reloaded and spring context is restarted. This helps to speed up reloading. 
Downside - if you change your dependencies, hot reloading won't help, you will need to manually reload your app. 
* browser refresh - if using template, browser would refresh once you reload your code
* out-of-the box work with h2 - you can go to `http://localhost:8080/h2-console` and play with your db while app is running
To use devtools add this to your `pom.xml`
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
</dependency>
```







###### YML Autocompletion

By default auto-completion work in ultimate ide for both `.yml` and `.properties`.
If you are using CE (community edition) you should download plugin `Spring Assistant`, it will enable auto-completion for `.yml` files.

You can also have auto-completion for your custom props, you should add this to your `pom.xml`
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
</dependency>
```
This dependency will generate file `/target/classes/META-INF/spring-configuration-metadata.json` with your props
Then have a any config file
```java
import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class App{
    public static void main(String[] args) {
        var context = SpringApplication.run(App.class, args);
        System.out.println(context.getBean(MyConf.class));

    }
}

@EnableConfigurationProperties
@Configuration
@ConfigurationProperties(prefix = "user")
@Data
class MyConf{
    int age;
    String name;
}
```
And if you go to `application.yml` and start to type you will see that user.name and user.age are auto-completed.

The same way, when you write your custom starter you can add to `/resources/META-INF/spring.factories`
this line `org.springframework.boot.autoconfigure.EnableAutoConfiguration=com.example.logic.ann.postprocessors.MyConfig`
java class
```java
package com.example.logic.ann.postprocessors;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "person")
public class MyConfig {
    private int age;
    private String name;
}
```

And then also use auto-complete to tune your starter from main project







###### Spring Cloud
Spring Cloud - allows easy production deployment. It consist of
* `Eureka` (service discovery from Netflix) - distinct service that store all services by name and provide them to other services
* `Ribbon` (client-side load balancer) - client library that help to work with eureka server
* `Config server` - distinct service that pull configuration from git or vault service and provide it to your microservices
* `Spring Cloud Bus` - automatic update of configuration in the Config Server, once they has been committed to git server
* `Spring Cloud Stream` - inter-service communication with rabbitmq or kafka (can be used to propagate changes from config server to all microservices)
* `Hystrix (Circuit Breaker)` - netflix library to handle bad responses from other microservices

To work with eureka add this to your `pom.xml`
```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```
To enable eureka add this to your configuration `@EnableEurekaServer`. And your discovery server is ready.
In client-side (microservice that would use eureka) you should add
```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```
This dependency add both eureka client and ribbon. You should add name of your sevice to `spring.application.name=myService`. And it would be available in eureka dashboard.
By default client will try to connect to eureka at localhost:8761. But you can change eureka url with property `eureka.client.service-url`
Once you enable client eureka you can create load-balanced `RestTemplate` or `WebClient` like
```java
@Bean
@LoadBalanced
public RestTemplate restTemplate() {
    return new RestTemplate();
}

@Bean
@LoadBalanced
public WebClient.Builder webClientBuilder() {
    return WebClient.builder();
}
```

You can also use feign client, that works like spring data, automatically generating rest code. First add this dependency to your `pom.xml`
```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```
First enable feign config
```java
@Configuration
@EnableFeignClients
public RestClientConfiguration {
}
```

Then create rest api like
```java
// employee-service - name of service by which you get actual url from eureka
@FeignClient("employee-service")
public interface EmployeeClient {
@GetMapping("/employee/{id}")
    Employee getEmployee(@PathVariable("id") String id);
}
```


To create config server, add this to your `pom.xml`
```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-config-server</artifactId>
</dependency>
```
To enable it add this `@EnableConfigServer`. 
You should also add git url, where properties are located `spring.cloud.config.server.git.uri`. You can also set port to 8888, cause it's default port to which clients will try to connect to get configuration.
After it's done you can access config server in `http://localhost:8888/{nameOfYourApp}/{profile}/{gitBranchName}`.
If git server has auth you should add to your props `spring.cloud.config.server.username` and `spring.cloud.config.server.password`
The best practice is first to deploy config server, and all microservices connect to it to get eureka server url as property param.

For client to consume properties from config server add this dependency
```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-config</artifactId>
</dependency>
```


Hystrix implemented as an aspect applied to a method that triggers a fallback method should the target method fail. Aspect also tracks how frequently the target method fails and then forwards all requests to the fallback if the failure rate exceeds some threshold.
To work with hystrix add it to your `pom.xml`
```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>
```
To start use it in your app, add this to your config `@EnableHystrix`. 
Then any method that runs some rest code (or db query) is annotated with `@HystrixCommand(fallbackMethod="youDefaultFallbackMethod")`. And in case of failure `youDefaultFallbackMethod` will be called.
If method takes more than 1 second, fallback method would be called, but you can tune execution time for every method.
You can monitor hystrix from actuator, or create new project with hystrix dashboard, and monitor it there.
```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix-dashboard</artifactId>
</dependency>
```
Then add to your config `@EnableHystrixDashboard`


###### Spring Utils
`AnnotationUtils` - can be useful when you want to find some annotation that you can't get by default. By default `getAnnotation` search only annotation of a class itself, so if we have Ann1=>Ann2=>OutClass, only Ann2 would be visible.
Of course we can write recursive function to retreive all annotations up to the top, but it's better to use `AnnotationUtils.findAnnotation`.
```java
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.core.annotation.AnnotationUtils;

public class App {
    public static void main(String[] args) {
        System.out.println(A.class.getAnnotation(Ann1.class));
        System.out.println(AnnotationUtils.findAnnotation(A.class, Ann1.class));
    }
}

@Retention(RetentionPolicy.RUNTIME)
@interface Ann1{}

@Ann1
@Retention(RetentionPolicy.RUNTIME)
@interface Ann2{}

@Ann2
@Retention(RetentionPolicy.RUNTIME)
@interface Ann3{}

@Ann3
class A{}
```
```
null
@com.example.spring5.Ann1()
```

###### Spring Boot Logging
There are a few types of looging in java
* java.util.logging - standard package for logging from jdk
* logback - custom logging implementation
* apache common loggign - custom logging from apache
* slf4j - simple loggig facade for java

By default spring using common logging for internal logging and slf4j(with logback) for external

```java
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.LoggerFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

public class App{
    public static void main(String[] args) {
        String className = App.class.getName();

        System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %4$s %2$s %5$s%6$s%n");
        Logger javaLog = Logger.getLogger(className);
        javaLog.info("hello");
        javaLog.log(Level.SEVERE, "my ex");

        org.slf4j.Logger log = LoggerFactory.getLogger(className);
        log.info("hello");
        log.error("my ex");

        Log commonLog = LogFactory.getLog(className);
        commonLog.info("hello");
        commonLog.error("my ex");


        ch.qos.logback.classic.Logger logback = (ch.qos.logback.classic.Logger)LoggerFactory.getLogger(className);
        logback.info("hello");
        logback.error("my ex");
    }
}
```
```
2020-04-18 17:21:15 INFO com.example.spring5.App main hello
2020-04-18 17:21:15 SEVERE com.example.spring5.App main my ex
17:21:15.396 [main] INFO com.example.spring5.App - hello
17:21:15.400 [main] ERROR com.example.spring5.App - my ex
17:21:15.409 [main] INFO com.example.spring5.App - hello
17:21:15.409 [main] ERROR com.example.spring5.App - my ex
17:21:15.409 [main] INFO com.example.spring5.App - hello
17:21:15.410 [main] ERROR com.example.spring5.App - my ex
```


###### Controller's method params
Method of controller can take following params
* HttpSession
* HttpServletRequest (or ServletRequest since it's super interface)
* HttpServletResponse (or ServletResponse since it's super interface)
```java
package com.example.logic.ann.misc;

import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/person")
public class MyController {
    @PostMapping
    @ResponseBody
    public Person postPerson(@RequestBody Person p, HttpServletRequest req, HttpServletResponse res, HttpSession session, WebRequest webReq){
        System.out.println(req);
        System.out.println(res);
        System.out.println(session);
        System.out.println(webReq);
        System.out.println(p);
        return p;
    }
}

@Data
class Person{
    private int age;
    private String name;
}
```
```
org.apache.catalina.connector.RequestFacade@3bf9b57e
org.apache.catalina.connector.ResponseFacade@141ae951
org.apache.catalina.session.StandardSessionFacade@28ebc7f6
ServletWebRequest: uri=/person;client=127.0.0.1;session=FEF5F030027934B5366BF585FD50342D
Person(age=30, name=Jack)
```
Send `curl -H "Content-type: application/json" -d 'ck"}' http://localhost:8080/person`

To set return type of controller or method you can use `@ResponseStatus`.
There are 2 ways to have map between your exceptions and http status codes. You can also add your own implementation of `HandlerExceptionResolver` or use one of the default to map exceptions to http statuses.
You can also directly throw `ResponseStatusException`
```java
package com.example.logic.ann.misc;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class MyController {
    @GetMapping("/a")
    public void handleGet(){
        System.out.println("handleGet");
        throw new MyException1();
    }
    @GetMapping("/b")
    public void handleGet2(){
        System.out.println("handleGet2");
        throw new MyException2();
    }
}



@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "oops 400")
class MyException1 extends RuntimeException{}

class MyException2 extends RuntimeException{}


@ControllerAdvice
class MyExceptionHandler{
    @ExceptionHandler(MyException2.class)
    public ResponseEntity<String> handleMyException2(){
        System.out.println("handleException");
        return new ResponseEntity<>("oops 400", HttpStatus.NOT_FOUND);
    }
}
```
`curl http://localhost:8080/a` => `{"timestamp":"2020-04-21T05:29:59.165+0000","status":404,"error":"Not Found","message":"oops 400","path":"/a"}`
`curl http://localhost:8080/b` => `oops 400`
* If you have `spring-boot-devtools` with `@ResponseStatus` or `ResponseStatusException` it will also include long trace into response

`@MatrixVariable` - get path variables of special format like `;a=1;b=2;c=3;`
```java
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;

@RestController
@RequestMapping("/")
public class MyController {
    @GetMapping("/a/{pathId}/{matrixId}")
    public void handleGet(@PathVariable String pathId, @RequestParam("id") String paramId, @MatrixVariable String matrixId){
        System.out.println("pathId => " + pathId + ", paramId => " + paramId + ", matrixId => " + matrixId);
    }
}


@Configuration
class WebConfig implements WebMvcConfigurer {
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        urlPathHelper.setRemoveSemicolonContent(false);
        configurer.setUrlPathHelper(urlPathHelper);
    }
}
```
If we hit ` curl http://localhost:8080/a/1/matrixId=2?id=3` we got `pathId => 1, paramId => 3, matrixId => 2`



###### Spring Caching
You should enable cache `@EnableCaching` 
```java
package com.example.logic.ann.misc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import lombok.SneakyThrows;

@RestController
@RequestMapping("/")
public class MyController {
    @Autowired
    private MyService service;
    
    /**
     * Since we are using spring boot, this bean would be autoconfigured
     */
    @Autowired
    private CacheManager cacheManager;

    @GetMapping
    public String handleGet(){
        System.out.println("handleGet");
        String name = service.getName();
        System.out.println("name => " + name);
        return name;
    }

    @PostMapping
    @CacheEvict(JavaConfig.CACHE_NAME)
    public void handlePost(){
        System.out.println("remove cache");
        /**
         * You can remove cache by annotation and by code
         */
        cacheManager.getCache(JavaConfig.CACHE_NAME).clear();
    }
}

@Service
class MyService{
    @SneakyThrows
    @Cacheable(JavaConfig.CACHE_NAME)
    public String getName(){
        Thread.sleep(3000);
        return "Jack";
    }
}

@Configuration
@EnableCaching
class JavaConfig{
    public static final String CACHE_NAME = "my_name";
}
```

Main code
```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class})
@ComponentScan("com.example.logic.ann.misc")
public class App{
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
```
First time you run ` curl http://localhost:8080/` you will wait for 3 sec, later you would get response immediately.
To clear cache call `curl -X POST http://localhost:8080/`

###### JavaBeans, POJO, Spring Beans
JavaBean - a bean with public no-arg constructor, private fields and public getter/setter. The best example is simple model
```java
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class User{
    int id;
    String name;
}
```
We are using lombok to generate getter/setter but you can also write them by hand


POJO - plain old java object (term inveted by Martin Fowler) - refers to any java object that's not coupled to any framework. 

Spring Bean - a java object managed by spring container. Spring bean can be javabean, or pojo, or just some class.



###### Maven scope
There are 2 types of dependencies
* Direct - directly included into project under `<dependency/>` tag
* Transitive - dependencies of your direct dependencies. Although you don't explicitly add them to project, they are still there.
We can list all of dependencies by running `mvn dependency:tree`

Scopes can help to limit transitivity and modify classpath for different built tasks. There are 6 scopes:
* `compile` - default scope if no other set. Dependencies with this scope available on the classpath for all builds. They are transitive.
* `provided` - dependency should be provided by jdk or container. So it only available during compile-time. At run-time they won't be available. For example `spring-starter-tomcat` for `.war` application.
* `runtime` - dependency only available at run-time not compile time. For example jdbc driver, since for compilation we are suing `DataSource` to obtain connection we don't need driver at compile time, be we definitely need it during runtime.
* `test` - means that junit types would be available only in testing scope, not inside main app. For example class `AopTestUtils` is available under test folder, but you can't import it into main code.
* `system` - similar to provided, but we should declare the exact path to jar file using `<systemPath/>` tag
* `import` - available for type `pom`, should be replaced with all respective dependencies from it's pom.

Dependencies with scopes provided and test will never be included in the main project.





###### Spring Boot Starter
Core starter, including auto-configuration support, logging and YAML is:
```
<dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
</dependency>
```
Although you should omit it, cause most starters (for example `spring-boot-starter-web`) include it as transitive dependency, but not all. If you want to use jpa you should include both `spring-boot-starter-data-jpa` and `spring-boot-starter`.







###### Spring bean scopes (singleton vs. application)
There are 6 scopes for spring beans, you can also create your own by implementing `Scope` interface.
2 are common for all apps
* `singleton` - one instance per app context
* `prototype` - new instance every time other bean call it
4 are for web only
* `request` - instance per http request
* `session` - instance per http session
* `application` - instance per `ServletContext`
* `websocket` - instance per web socket connection

`application` is somewhat similar to a Spring `singleton` bean but differs in two important ways: It is a singleton per ServletContext, not per Spring 'ApplicationContext' (or which there may be several in any given web application), 
and it is actually exposed and therefore visible as a ServletContext attribute



###### Spring Context Indexer
If you need to scan your project to search for some components, it may take some time. So you may add a dependency
```
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context-indexer</artifactId>
    <version>5.2.5.RELEASE</version>
</dependency>
```
And it will generate during compile time file `/META-INF/spring.components` in your jar with all your stereotype components.



###### SPEL - Spring Expression Language
It is separate language from `OGNL` (Object-Graph Navigation Language - open-source Expression Language for Java)
It supports regular expression
'hello world' - is valid `SPEL` expression






###### Custom Framework Impl
To better understand how spring works you should implement your own micro-framework, to get the idea, how it works and why it works this way and not another.

Why do we need di. Imagine that you create your dependencies with the `new` keyword inside your classes. Suppose you  have a code
```java
interface Paint{
    String getColor();
}

class BlackPaint implements Paint{
    @Override
    public String getColor() {
        return "black";
    }
}

interface Printer{
    void print(String msg);
}

class SimplePrinter implements Printer{
    private Paint paint = new BlackPaint();
    
    @Override
    public void print(String msg) {
        System.out.println("msg => " + msg + ", paint => " + paint.getColor());
    }
}
```
SimplePrinter - breaks `S` in `SOLID`. Cause it's not single responsibility class anymore. By creating dependencies this way we introduce 3 new responsibility:
* We should know which implementation of Paint to use. We can have multiple implementation and we tightly couple exact implementation.
* We should know how to create object (which constructor to use or use factory)
* We should know how to configure object (after we have created object maybe we need to configure it by setting some properties or run some init functions)

So `di` solves these 3 problems. To simplify work with reflections you can use [reflections](https://github.com/ronmamo/reflections) library. Add to your `pom.xml`
```
<dependency>
    <groupId>org.reflections</groupId>
    <artifactId>reflections</artifactId>
    <version>0.9.12</version>
</dependency>
```

Here is code
```java

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.reflections.Reflections;


import lombok.SneakyThrows;

public class App{
    @SneakyThrows
    public static void main(String[] args) {
        var context = new AppContext(App.class.getPackageName(), new ConcurrentHashMap<>());
        var printer = context.getObject(Printer.class);
        printer.print("hello");
    }
}

@Retention(RetentionPolicy.RUNTIME)
@interface Inject{}
@Retention(RetentionPolicy.RUNTIME)
@interface Init{}
@Retention(RetentionPolicy.RUNTIME)
@interface Singleton{}
@Retention(RetentionPolicy.RUNTIME)
@interface Logging{}

interface Paint{
    String getColor();
}

interface Printer{
    void print(String msg);
}
@Singleton
class BlackPaint implements Paint{
    // since class is not public, constructor also not public, that's why we should declare it as public
    public BlackPaint(){
        System.out.println("constructing BlackPaint...");}

    @Override
    public String getColor() {
        return "black";
    }
}
@Singleton
class SimplePrinter implements Printer{
    // since class is not public, constructor also not public, that's why we should declare it as public
    public SimplePrinter(){
        System.out.println("constructing SimplePrinter...");
    }

    @Inject
    private Paint paint;


    @Init
    public void init(){
        System.out.println("configuring printer...");
    }

    @Override
    @Logging
    public void print(String msg) {
        System.out.println("msg => " + msg + ", paint => " + paint.getColor());
    }
}


class AppContext{
    private Reflections reflections;
    private Map<Class, Object> beans = new ConcurrentHashMap<>();
    private Map<Class, Class> implementations;

    /**
     * We pass map here in case we want to have our custom implementation of some interface
     */
    public AppContext(String packageToScan, Map<Class, Class> implementations){
        reflections = new Reflections(packageToScan);
        this.implementations = implementations;
    }
    public Class getImpl(Class impl){
        if(impl.isInterface()){
            if(implementations.containsKey(impl)) {
                impl = implementations.get(impl);
            } else {
                var subtypes = reflections.getSubTypesOf(impl);
                int size = subtypes.size();
                if(size != 1) {
                    throw new RuntimeException("Found " + size + " implementations of " + impl);
                }
                Class exactImpl = (Class) subtypes.iterator().next();
                implementations.put(impl, exactImpl);
                impl = exactImpl;
            }
        }
        return impl;
    }
    @SneakyThrows
    public <T> T getObject(Class<T> impl){
        if (beans.containsKey(impl)){
            return (T) beans.get(impl);
        }
        impl = getImpl(impl);

        T obj = impl.getConstructor().newInstance();

        //TODO: rewrite pre & post init to Bean Initializers
        // pre init
        for(var field: impl.getDeclaredFields()){
            var ann = field.getAnnotation(Inject.class);
            if(ann != null){
                field.setAccessible(true);
                field.set(obj, getObject(field.getType()));
            }
        }

        for(var method: impl.getDeclaredMethods()){
            var ann = method.getAnnotation(Init.class);
            if(ann != null){
                method.invoke(obj);
            }
        }

        // post init
        for(var field: impl.getDeclaredMethods()){
            var ann = field.getAnnotation(Logging.class);
            if(ann != null){
                var target = obj;
                obj = (T) Proxy.newProxyInstance(impl.getClassLoader(), impl.getInterfaces(), (proxy, method, args) -> {
                    System.out.println("calling => " + method.getName());
                    Object retVal = method.invoke(target, args);
                    System.out.println("done => " + method.getName());
                    return retVal;
                });
            }
        }

        if (impl.getAnnotation(Singleton.class) != null) {
            beans.put(impl, obj);
        }
        return obj;
    }
}
```
```
constructing SimplePrinter...
constructing BlackPaint...
configuring printer...
calling => print
msg => hello, paint => black
done => print
```















