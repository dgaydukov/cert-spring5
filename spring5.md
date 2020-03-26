# Content

1. [DI and IoC](#di-and-ioc)
* 1.1 [Dependency injection](#dependency-injection)
* 1.2 [Xml, Groovy, Properties example](#xml-groovy-properties-example)
* 1.3 [BFPP, BPP, ApplicationListener](#bfpp-bpp-applicationlistener)
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


public class DemoApplication {
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

#### Miscellaneous

###### mvnw and mvnw.cmd
When you download [spring boot](https://start.spring.io/) you have 2 files `mvnw` and `mvnw.cmd`. These 2 files from [Maven Wrapper Plugin](https://github.com/takari/takari-maven-plugin) 
that allows to run app on systems where there is no mvn installed. `mvnv` - script for linux, `mvnw.cmd` - for windows. Generally you don't need them in your work, so you may delete them.


