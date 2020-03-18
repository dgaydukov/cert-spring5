# Spring Tips


### Final Exam

https://spring.io/projects/spring-boot



* [youtube spring basics](https://www.youtube.com/watch?v=3wBteulZaAs&list=PL6jg6AGdCNaWF-sUH2QDudBRXo54zuN1t)

* [javarevisited](https://javarevisited.blogspot.com/search/label/Spring%20certification?updated-max=2019-06-17T19:00:00-07:00&max-results=3&start=3&by-date=false)
* [How I became a Pivotal Spring Professional Certified 5.0](https://medium.com/@raphaelrodrigues_74842/how-i-became-a-pivotal-spring-professional-certified-5-0-c6348da5f80b)
* [spring-certification-5.0](https://github.com/vshemyako/spring-certification-5.0)
* [SpringCertification5.0](https://github.com/MrR0807/SpringCertification5.0)
* [coderanch post](https://coderanch.com/t/706033/spring-professional-certification/certification/Passed-Spring-Professional-certification)

* [Pivotal exam voucher](https://store.education.pivotal.io/confirm-course?courseid=EDU-1202)
* [Official study guide](https://www.amazon.com/Pivotal-Certified-Professional-Spring-Developer/dp/1484251350)


### Tips

`1.` mvnw vs mvnw.cmd - these 2 files from [Maven Wrapper Plugin](https://github.com/takari/takari-maven-plugin) that allows to run app on systems where there is no mvn installed.
mvnv - script for linux, mvnw.cmd - for windows. Generally you don't need them in your work, so you may delete them.


`2.` DI - dependency injection
Rewrite filed injection (with `@Autowired`) to constructor injection. The pros are
- you can use `final` keyword with constructor injection, can’t be done with filed injection
- it’s easy to see when you break S in SOLID. If your class has more than 10 dependencies, your constructor would be bloated, and it’s easily to spot
- works with unit tests (without di support) without problems`
Generally `@Autowired` is a bad design. If you don’t want to write constructor code use lobmok `@AllArgsConstructor` it will generate constructor based on all private fields in your class and spring automatically will inject them.
- now interfaces can have static, default and private methods, so you can use them as first class citizens.


`3.` Interfaces for every class
Use Interfaces for every class, at least for every Service class. The naming convention is `MyService` for interface and `MyServiceImpl` or `DefaultMyService` for implementation itself. The pros are:
- Interface clear defines contract. Who knows why developer made the method public (maybe he just forget to rename it to private). Classes generally unclear and cluttered to define public contract.
- interfaces allow di and mocking without use of reflection (don’t need to parse class implementation)
- JDK dynamic proxy can work only with interfaces (if class implement any it use it), otherwise java switch to CGLIB to create proxies

3. Use Spring cloud stream for messaging support
4. Use lombok `@Slf4j` to automatically inject logger
5. When you return `Object`, and there is no `Object`, return `null`. When you return `Collection` and thre is nothing to return, return empty collection.
6. Difference between appcontext & genericappcontext (and all others)
7. Difference between: jdbc, spring jdbc, jpa, hibernate, spring data
8. a


--
1. контекст загружать по имени интерфейса