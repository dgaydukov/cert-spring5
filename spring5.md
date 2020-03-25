# Content

1. [DI and IoC](#di-and-ioc)
* 1.1 [Dependency injection](#dependency-injection)
* 1.2 [Xml, Groovy, Properties example](#xml-groovy-properties-example)
2. [Miscellaneous](#miscellaneous)
* 2.1 [mvnw and mvnw.cmd](#mvnw-and-mvnwcmd)

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
package com.example.spring.app;

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
package com.example.spring.app;

public class SimplePrinter {
    public void print(String str){
        System.out.println("printer => " + str);
    }
}
```

And 3 configurations
File: `app.groovy`
```
import com.example.spring.app.*

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
simpleBean.(class)=com.example.spring.app.SimpleBean
simpleBean.name=goodBean
simpleBean.printer(ref)=simplePrinter

simplePrinter.(class)=com.example.spring.app.SimplePrinter
```
File: `app.xml`
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="simpleBean" class="com.example.spring.app.SimpleBean" init-method="init">
        <property name="name" value="goodBean"/>
        <property name="printer" ref="simplePrinter"/>
    </bean>
    <bean id="simplePrinter" class="com.example.spring.app.SimplePrinter"/>
</beans>
```
And main file
```java
package com.example.demo;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.groovy.GroovyBeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import com.example.spring.app.SimpleBean;


public class DemoApplication {
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
16:37:13.281 [main] DEBUG org.springframework.beans.factory.xml.XmlBeanDefinitionReader - Loaded 2 bean definitions from class path resource [app.xml]
16:37:13.285 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'simpleBean'
constructing SimpleBean...
16:37:13.357 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'simplePrinter'
initializing SimpleBean...
printer => I'm SimpleBean, my name is goodBean

Loading GROOVY
WARNING: An illegal reflective access operation has occurred
WARNING: Illegal reflective access by org.codehaus.groovy.reflection.CachedClass (file:/home/diman/.m2/repository/org/codehaus/groovy/groovy-all/2.4.12/groovy-all-2.4.12.jar) to method java.lang.Object.finalize()
WARNING: Please consider reporting this to the maintainers of org.codehaus.groovy.reflection.CachedClass
WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations
WARNING: All illegal access operations will be denied in a future release
16:37:13.872 [main] DEBUG org.springframework.beans.factory.groovy.GroovyBeanDefinitionReader - Loaded 2 bean definitions from class path resource [app.groovy]
16:37:13.872 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'simpleBean'
constructing SimpleBean...
16:37:13.872 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'simplePrinter'
initializing SimpleBean...
printer => I'm SimpleBean, my name is goodBean

Loading PROPERTIES
16:37:13.874 [main] DEBUG org.springframework.beans.factory.support.PropertiesBeanDefinitionReader - Loaded 2 bean definitions from class path resource [app.properties]
16:37:13.875 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'simpleBean'
constructing SimpleBean...
16:37:13.875 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'simplePrinter'
printer => I'm SimpleBean, my name is goodBean
```

Generally you should prefer `ApplicationContext` over `BeanFactory`, cause it adds bpp, bfpp, aop, i18n and so on do di.
Remember that you need to call `refresh()` on context.
```java
package com.example.demo;

import org.springframework.beans.factory.groovy.GroovyBeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import com.example.spring.app.SimpleBean;


public class DemoApplication {
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
package com.example.spring.app;

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
package com.example.demo;

import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericGroovyApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.example.spring.app.JavaConfig;
import com.example.spring.app.SimpleBean;


public class DemoApplication {
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

#### Miscellaneous

###### mvnw and mvnw.cmd
When you download [spring boot](https://start.spring.io/) you have 2 files `mvnw` and `mvnw.cmd`. These 2 files from [Maven Wrapper Plugin](https://github.com/takari/takari-maven-plugin) 
that allows to run app on systems where there is no mvn installed. `mvnv` - script for linux, `mvnw.cmd` - for windows. Generally you don't need them in your work, so you may delete them.


