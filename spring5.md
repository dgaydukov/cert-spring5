# Content

* [youtube spring basics](https://www.youtube.com/watch?v=3wBteulZaAs&list=PL6jg6AGdCNaWF-sUH2QDudBRXo54zuN1t)
* [spring-certification-5.0](https://github.com/vshemyako/spring-certification-5.0)
* [SpringCertification5.0](https://github.com/MrR0807/SpringCertification5.0)
* [Pivotal exam voucher](https://store.education.pivotal.io/confirm-course?courseid=EDU-1202)
* [Official study guide](https://www.amazon.com/Pivotal-Certified-Professional-Spring-Developer/dp/1484251350)
* [mock](http://itestjava.com/java-certification-practice-tests/product/enter.do?product=SPRING-CORE50)
* [guide](https://leanpub.com/corespring5certificationindetail)

1. [DI and IoC](#di-and-ioc)
* 1.1 [Dependency injection](#dependency-injection)
* 1.2 [Interface for every class](#interface-for-every-class)
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


###### Interface for every class
Use Interfaces for every class, at least for every Service class. The naming convention is `MyService` for interface and `MyServiceImpl` or `DefaultMyService` for implementation itself. The pros are:
- Interface clear defines contract. Who knows why developer made the method public (maybe he just forget to rename it to private). Classes generally unclear and cluttered to define public contract.
- interfaces allow di and mocking without use of reflection (don’t need to parse class implementation)
- JDK dynamic proxy can work only with interfaces (if class implement any it use it), otherwise java switch to CGLIB to create proxies


#### Miscellaneous

###### mvnw and mvnw.cmd
When you download [spring boot](https://start.spring.io/) you have 2 files `mvnw` and `mvnw.cmd`. These 2 files from [Maven Wrapper Plugin](https://github.com/takari/takari-maven-plugin) 
that allows to run app on systems where there is no mvn installed. `mvnv` - script for linux, `mvnw.cmd` - for windows. Generally you don't need them in your work, so you may delete them.


