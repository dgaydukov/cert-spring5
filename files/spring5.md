# Content

1. [DI and IoC](#di-and-ioc)
* 1.1 [Dependency injection](#dependency-injection)
* 1.2 [Xml, Groovy, Properties example](#xml-groovy-properties-example)
* 1.3 [BFPP, BPP, ApplicationListener](#bfpp-bpp-applicationlistener)
* 1.4 [Prototype into Singleton](#bfpp-bpp-applicationlistener)
* 1.5 [PostConstruct and PreDestroy](#postconstruct-and-predestroy)
* 1.6 [BeanNameAware and ApplicationContextAware](#beannameaware-and-applicationcontextaware)
* 1.7 [BeanFactory and FactoryBean<?>](#beanfactory-and-factorybean)
* 1.8 [Spring i18n](#spring-i18n)
* 1.9 [Resource interface](#resource-interface)
* 1.10 [Environment and PropertySource](#environment-and-propertysource)
* 1.11 [Profile, Primary, Qualifier, Order](#profile-primary-qualifier-order)
* 1.12 [PropertySource and ConfigurationProperties](#propertysource-and-configurationproperties)
2 [AOP](#aop)
* 2.1 [Aop basics](#aop-basics)
* 2.2 [Aop framework](#aop-framework)
3.[Spring MVC](#spring-mvc)
* 3.1 [DispatcherServlet](#dispatcherservlet)
* 3.2 [Spring Boot](#spring-boot)
* 3.3 [Custom Filters](#custom-filters)
* 3.4 [Spring Security](#spring-security)
4. [DB](#db)
* 4.1 [Spring JDBC](#spring-jdbc)
* 4.2 [Hibernate](#hibernate)
* 4.3 [Spring Data](#spring-data)
* 4.4 [JTA - java transaction API](#jta---java-transaction-api)
10. [Miscellaneous](#miscellaneous)
* 10.1 [mvnw and mvnw.cmd](#mvnw-and-mvnwcmd)
* 10.2 [Get param names](#get-param-names)



#### DI and IoC
###### Dependency injection
Rewrite filed injection (with `@Autowired`) to constructor injection. The pros are
- you can use `final` keyword with constructor injection, can’t be done with filed injection
- it’s easy to see when you break S in SOLID. If your class has more than 10 dependencies, your constructor would be bloated, and it’s easily to spot
- works with unit tests (without di support) without problems`
Generally `@Autowired` is a bad design. If you don’t want to write constructor code use lobmok `@AllArgsConstructor` it will generate constructor based on all private fields in your class and spring automatically will inject them.
- now interfaces can have static, default and private methods, so you can use them as first class citizens.
Use Interfaces for every class, at least for every Service class. The naming convention is `MyService` for interface and `MyServiceImpl` or `DefaultMyService` for implementation itself. The pros are:
- Interface clear defines contract. Who knows why developer made the method public (maybe he just forget to rename it to private). Classes generally unclear and cluttered to define public contract.
- interfaces allow di and mocking without use of reflection (don’t need to parse class implementation)
- JDK dynamic proxy can work only with interfaces (if class implement any it use it), otherwise java switch to CGLIB to create proxies

###### Xml, Groovy, Properties example
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
</beans>
```
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

Generally you should prefer `ApplicationContext` over `BeanFactory`, cause it adds bpp, bfpp, aop, i18n and so on do di.
Remember that you need to call `refresh()` on context.
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
		ConfigurableApplicationContext ctx = (ConfigurableApplicationContext) context;
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
		ctx.refresh();
	}
}
```

If we want to add annotation support to xml file we should add `<context:component-scan base-package="your.package"/>`. This will scan package `your.package` for all annotated with `@Component` and related to it.

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

import org.springframework.beans.factory.annotation.Autowired;
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

import com.example.logic.ann.SimpleBean;


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

We can have nested application contexts. `GenericApplicationContext` have `setParent` method, where you can pass parent context. And you can get all beans in child context from parent context.


###### BFPP, BPP, ApplicationListener
If you want to implement some custom logic during app lifecycle you should have classes that implement following interfaces
* `BeanFactoryPostProcessor` - fires when no bean definitions has been accumulated from xml or annotations, but none bean has been created
* `BeanPostProcessor` - fires when beans has been created, it has 2 methods
    * `postProcessBeforeInitialization` - fires before initialization, we can be sure that in this method we have original beans
    * `postProcessAfterInitialization` - fires after init, usually here we can substitute our bean with dynamic proxy
* `ApplicationListener<E extends ApplicationEvent>` - fires after bfpp and bpp, when we got some events

###### Prototype into Singleton
You can use following code to get scope of any class in your app context
```java
private static String getScope(ApplicationContext context, Class<?> cls){
    ConfigurableListableBeanFactory factory = ((ConfigurableApplicationContext)context).getBeanFactory();
    for(String name: factory.getBeanNamesForType(cls)){
        return factory.getBeanDefinition(name).getScope();
    }
    return null;
}
```
To add ability for singleton to get every time new prototype we should add `@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)`
In case of xml configuration, we would need to make singleton abstract, and add abstract method to get prototype instance, and add it to xml like `<lookup-method name="getPrinter" bean="prototypePrinter"/>`
```java
import com.example.logic.ann.prototypeintosingleton.SingletonBean;

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

If you declare BFPP with `@Bean`, you should make your method static
```java
@Bean
public static MyBFPP myBFPP(){
    return new MyBFPP();
}
```
Otherwise you will get error `@Bean method JavaConfig.myBFPP is non-static and returns an object assignable to Spring's BeanFactoryPostProcessor interface. This will result in a failure to process annotations such as @Autowired, @Resource and @PostConstruct within the method's declaring @Configuration class. Add the 'static' modifier to this method to avoid these container lifecycle issues; see @Bean javadoc for complete details.`
BeanFactoryPostProcessor-returning @Bean methods
Special consideration must be taken for @Bean methods that return Spring BeanFactoryPostProcessor (BFPP) types. 
Because BFPP objects must be instantiated very early in the container lifecycle, they can interfere with processing of annotations such as @Autowired, @Value, and @PostConstruct within @Configuration classes. To avoid these lifecycle issues, mark BFPP-returning @Bean methods as static. 


If you want to use both annotation and xml config you can do
* In case of `AnnotationConfigApplicationContext`, add `@ImportResource("app.xml")` to your `@Configuration` class
* In case of `GenericXmlApplicationContext`, add `<context:annotation-config/>` to your xml file

###### PostConstruct and PreDestroy
You have 4 options to hook to post construct event (when spring has set all properties)
* 1. Define init method in xml config `init-method=init`
* 2. Bean should implement `InitializingBean` interface with one method `afterPropertiesSet`, and put init logic there
* 3. add `@PostConstruct` annotation above any method you would like to be called (in this case you can annotate as many methods as you want, in previous 2 works only with one method)
* 4. set `@Bean(initMethod = "init")` in java config file
In case of 1 and 3, you can add `private` to init method (spring still would be able to call it via reflection), but nobody outside will be able to call it second time.
But in case of implementing interface, `afterPropertiesSet` - is public, and can be called directly from your bean. More over in this case you are coupling your logic with spring.
If you set all 4 the order is this: 
`@PostConstruct` (registred with `CommonAnnotationBeanPostProcessor`)'=>`afterPropertiesSet`=>`xml config init` => `Bean config init`

The same way you can hook up to destroy event
* `destroy-method=destory` in xml config
* Implement `DisposableBean` interface with `destroy` method
* Add `@PreDestroy` annotation
* Add `@Bead(destoryMethod=destroy)` to java config
Destroy events are not fired automatically, you have to call `((AbstractApplicationContext)context).close();`. 
Method `destroy` in context is deprecated, and inside just make a call to `close`.


###### BeanNameAware and ApplicationContextAware
If you want to have bean name or app context to be injected into your bean you can implement this interfaces
```java
package com.example.logic.ann;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class AwareBean implements BeanNameAware, ApplicationContextAware {
    private String beanName;
    private ApplicationContext context;

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public void setApplicationContext(ApplicationContext context) {
        this.context = context;
    }

    public void doWork(){
        System.out.println("beanName => " + beanName + ", context => " + context);
    }
}
```
If you call from your main `context.getBean(AwareBean.class).doWork();` you will get `beanName => awareBean, context => org.springframework.context.annotation.AnnotationConfigApplicationContext@66d33a, started on Fri Mar 27 15:50:43 HKT 2020`.
You should use this interfaces, when you bean is not business logic, but some spring specific event.


###### BeanFactory and FactoryBean<?>
Don't confuse the 2 interfaces
* `BeanFactory` - basic di interface, `ApplicationContext` is inhereted from it
* `FactoryBean<?>` - helper interface to create factory objects (used when you need to implement factory pattern)
```java
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
```
In case you don't want to use `FactoryBean`, and you have custom factory with method name `createInstance`, you can configure your factory in xml like `<bean id="myUser" factory-bean="UserFactory" factory-method="createInstance"/>`.


###### Spring i18n
Internationalization based on `MessageSource` interface. Since `ApplicationContext` extends this interface you can get i18n directly from context.
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
@Bean
public MessageSource messageSource(){
    ResourceBundleMessageSource bundle = new ResourceBundleMessageSource();
    bundle.setBasename("i18n/msg");
    return bundle;
}
```
And then you can just call
```java
System.out.println(context.getMessage("name", null, Locale.ENGLISH));
System.out.println(context.getMessage("name", null, Locale.FRENCH));
```
```
English Resource
French Resource
```

###### Resource interface
Since `ApplicationContext` extends `ResourceLoader`, that has method `getResource`, you can load resources using context
```java
Resource resource = context.getResource("app.xml");
System.out.println(resource);
```
```
class path resource [app.xml]
```


###### Environment and PropertySource
Since `ApplicationContext` extends `EnvironmentCapable` interface with 1 method `getEnvironment()`, you can get env object from context.
```java
ConfigurableEnvironment env = (ConfigurableEnvironment) context.getEnvironment();
MutablePropertySources sources = env.getPropertySources();
Map<String, Object> props = new HashMap<>();
props.put("myKey", "myValue");
sources.addLast(new MapPropertySource("props", props));
System.out.println(env.getProperty("myKey"));
```
```
myValue
```


###### Profile, Primary, Qualifier, Order
`@Primary` - if we have more than 1 bean implementing particular interface, you can use this annotation, so spring will inject exactly this bean
`@Qualifier("beanName")` - you can inject any bean you want. It's stronger than primary, so it autowired bean by name.

If you inject list of interfaces, spring will try to find all beans that implement this interface, and will inject them in default order (sorted by names).
If you need custom order you can add `@Order(1)` on beans, and by this you will create custom order of injection.
If you have many beans, and some have this annotation, other not, those with annotation goes first. 
Those without it goes last with default sort.

If you have list of implementation and bean that return also list of implementations, when injected spring will collect list of impl, not your bean.
To use your bean, you have to add `Qualifier`


###### PropertySource and ConfigurationProperties
We can load properties from `.properties/.yml` files.
`@PropertySource` - repetable, so we can load several files. If keys are the same => those declared after will overwrite those declared before.
You can use `@Value("${propName}")` on every field, or use `ConfigurationProperties` with public setter methods for every field.
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

### AOP

###### Aop basics

Spring aop keywords
* Jointpoint - well-defined point during code execution (e.g. method call, object instantiation). In Spring AOP it's always method call. 
* Advice - piece of code that executes at particular jointpoint
* Pointcut - jointpoint with applied advice
* Aspect - advice + pointcut
* Weaving - process of inserting aspect into code (2 types => compile(AspectJ) & dynamic(Spring AOP))
* Target - object whose flow is modified by aspect
* Introduction - modification of code on the fly (e.g. add interface to a class)


Spring supports 6 types of advices
* `org.springframework.aop.MethodBeforeAdvice` - before method execution. has access to params. In case of exception, jointpoint is not called
* `org.springframework.aop.AfterReturningAdvice` -  after method executed, has access to params & return value. If method execution throws exception, not called
* `org.springframework.aop.AfterAdvice` (after-finally) - cause would be executed no matter what
* `org.aopalliance.intercept.MethodInterceptor` (around) - has full control over method execution
* `org.springframework.aop.ThrowsAdvice` - run if execution method throws exception
* `org.springframework.aop.IntroductionAdvisor` - add special logic to class

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
If we want to use specific aspectJ pointcuts we have to add to `pom.xml`
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
As you see, first time aspect didin't apply, but second time it applied (cause our flow pointct will apply aspect only when called from specific class and method).
You can also use `ComposablePointcut` with 2 methods `union` and `intersection` if you want to pass several `methodMatchers`.
If you need to combine only pointcuts you can use `PointCut` class, but if you want to combine pointcut/methodmatcher/classfilter you should use `ComposablePointcut`.

We also have `IntroductionAdvisor` by which we can add dynamically new implementations to object.
One possible application is to check if object data chaned, and call save to db only in this case.
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
    public static void print(Person p){
        p.sayHello();
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
As you see we have 2 beans of the same type, original - not adviced and adviced.
If you want to have one bean, and you never need original you can remove it from javaconfig, and inject it directly into `ProxyFactoryBean`

You can also use aspecj annotations, you should first enable them `@EnableAspectJAutoProxy(proxyTargetClass = true)`;
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
As you see we apply before advice only if bean name starts with `original` and methodname is `sayHello`.
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

### Spring MVC
###### DispatcherServlet
It's entry point of every web app, it's main purpose to handle http requests.
When you create web app, your context always an instance of `WebApplicationContext`, it extends `ApplicationContext`,
and has a method `getServletContext`, to get `ServletContext`.

Before the advent of spring boot for building web app we were using `.war` files (web archive).
Inside we had web.xml were all configs are stores, then we put this file into `tomcat` directory, and when tomcat 
starts, it takes with file and run it. That's why we didn't have any `main` method inside web app for spring.

###### Spring Boot
In spring boot you have 2 new events. You can register them in `resources/META-INF/spring.factories`. Just add these 2 lines
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




###### Spring Security
It creates `javax.servlet.Filter` with name `springSecurityFilterChain`.













#### DB
###### Spring JDBC
Before using spring jdbc, we can use standarc jdk jdbc.
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
import com.example.logic.ann.jdbc.jdk.DepartmentDao;
import com.example.logic.ann.jdbc.DepartmentModel;
import com.example.logic.ann.jdbc.jdk.MyConnection;
import com.example.logic.ann.jdbc.MyDao;

public class App {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/spring5?user=root&password=";
        MyConnection conn = new MyConnection(url);
        MyDao<DepartmentModel> dao = new DepartmentDao(conn.getConnection());

        System.out.println("getAll => " + dao.getAll());
        System.out.println("getById(1) => " + dao.getById(1));

        DepartmentModel model = new DepartmentModel();
        model.setName("New dep");
        model.setType("cool");
        var saved = dao.save(model);
        int id = saved.getId();
        System.out.println("save => " + saved);
        System.out.println("deleteById(" + id + ") => " + dao.deleteById(id));
    }
}
```
```
getAll => [DepartmentModel(id=1, name=Exchange, type=IT), DepartmentModel(id=2, name=Solution, type=IT), DepartmentModel(id=3, name=Markets, type=CP), DepartmentModel(id=4, name=New dep, type=cool)]
getById(1) => DepartmentModel(id=1, name=Exchange, type=IT)
save => DepartmentModel(id=16, name=New dep, type=cool)
deleteById(16) => true
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

Spring nicely translate all checked sql exeptions into runtime execptions with clear names.
But you can also add logic there, by creating your own translator
```java
package com.example.logic.ann.jdbc.spring;

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

We can pass params in 2 ways: `Object[]` or `Map`. If you want to use map you should use `NamedParameterJdbcTemplate` and put all params into hashmap.
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
package com.example.logic.ann.jdbc.spring;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.logic.ann.jdbc.DepartmentModel;
import com.example.logic.ann.jdbc.MyDao;

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
}

```
Dao example
```java
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.logic.ann.jdbc.DepartmentModel;
import com.example.logic.ann.jdbc.spring.DepartmentDao;


public class App {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("com.example.logic.ann.jdbc.spring");
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

`SimpleDriverDataSource` - is not pooled, so you can use it only for testing purpose. 

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
`JPA` (java persistence API) - specification how to write orm. `Hibernate` - concrete example of such orm based on JPA.
Jpa specification is inside `javax.persistence` package.
To use hibernate you need to add these into your `pom.xml`
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

In hibernate we will show how to work with persistance objects
`DepartmentEntity.java`
```java
package com.example.logic.ann.jdbc.hibernate.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import java.util.List;

import lombok.Data;

@Data
@Entity
@Table(name = "department")
public class DepartmentEntity {
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

    /**
     * We are using FetchType.EAGER to fetch join queries eagerly, generally it's not a good practice
     * and you should use lazy loading in prod
     */
    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
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
public class DepartmentDao implements MyDao<DepartmentEntity> {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<DepartmentEntity> getAll() {
        return sessionFactory.getCurrentSession().createQuery("from DepartmentEntity d").list();
    }

    @Override
    public DepartmentEntity getById(int id) {
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

import com.example.logic.ann.jdbc.hibernate.MyDao;
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
        MyDao<DepartmentEntity> dao = context.getBean(MyDao.class);
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

import com.example.logic.ann.jdbc.hibernate.MyDao;
import com.example.logic.ann.jdbc.hibernate.entities.DepartmentEntity;

public class App {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext("com.example.logic.ann.jdbc.hibernate");
        /**
         * If you try to run `context.getBean(DepartmentDao.class);`, you will get
         * NoSuchBeanDefinitionException: No qualifying bean of type 'com.example.logic.ann.jdbc.hibernate.DepartmentDao' available
         * the reason, is since we are using @Transactional, our object is changed with proxy, that's why we should use interfaces
         */
        MyDao<DepartmentEntity> dao = context.getBean(MyDao.class);
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
Jpa `EntityManagerFactory` resembles `SessionFactory`. You can call `entityManagerFactory.createEntityManager()` to get current `EntityManager` on which you can run queries like `update/remove/save`
Although you can use `EntityManager` to manually create queries, it's better to use spring data repository pattern, that wrap entity manager inside and provide many default queries out of the box.
There are 2 interfaces `CrudRepository` & `JpaRepository` from which you can extend your repository interface (spring will create class automatically) and have many default queries already implemented.
To enable work with repository add this to your config `@EnableJpaRepositories("com.example.logic.ann.jdbc.jpa.repository")`.
But if you want some custom query (like fine user by first and last name) you just need to add abstract method `findByFirstNameAndLastName(String firstName, String lastName)` and it would work (spring automatically build query based on it's name).
In case you want to have your own query for the method, just add `@Query("your query")`.

You can easily add auditing to any entity by adding `@EntityListeners(AuditingEntityListener.class)`
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

###### JTA - java transaction API
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

#### Miscellaneous
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
