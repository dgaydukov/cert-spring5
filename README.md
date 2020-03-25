# Spring5 certification

### Description
Here is my experience for taking [spring5](https://store.education.pivotal.io/confirm-course?courseid=EDU-1202).

### Why do you need it.
There are 2 main reasons to get it.
1. During preparation you will learn a lot of new stuff
2. It may help your career

### Contents
* [Spring Tips](https://github.com/dgaydukov/cert-spring5/blob/master/spring5.md)


### Useful links
* [Pivotal exam voucher](https://store.education.pivotal.io/confirm-course?courseid=EDU-1202)
* [spring-certification-5.0](https://github.com/vshemyako/spring-certification-5.0)
* [SpringCertification5.0](https://github.com/MrR0807/SpringCertification5.0)
* [mock](http://itestjava.com/java-certification-practice-tests/product/enter.do?product=SPRING-CORE50)
* [Official study guide](https://www.amazon.com/Pivotal-Certified-Professional-Spring-Developer/dp/1484251350)



### TODO
1. beanfactorypostprocessor (with non static method from javaconfig) vs beanpostprocessor
4. create custom context (load data from .properties files or json context) with PropertiesBeanDefinitionReader and with custom reader
5. how to update prototype inside singleton (proxymode=targetclass)
6. create custom scope (like singleton/prototype)
7. custom beanpostprocessor vs aspect (cause bpp works better than aspect)
8. postconstruct in xml and javaconfig
9. applicationlistener & contextlistener & eventlistener
10. Before java8 `-parameters` Spring used `ParameterNameDiscoverer nameDiscoverer = new LocalVariableTableParameterNameDiscoverer();` which in turn used asm ClassReader.
11. `@Order(int n)` - allow to set order when you use `@Autowired` on list, in which they would be injected into this list
12. classpathbeandefinitionscanner
13. bpp (before - we have original class), after - we can substitute class with proxy
14. create custom spring boot starter (spring.factories) with app.yml autoconfig of values
15. applicationcontextinitializer (what logic can we have here), when it fired?
16. environmentpostprocessor
17. configurationproperites autocomplete
18. config applicationlistener
19. spring web.xml