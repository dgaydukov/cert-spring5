# Spring5 certification

### Description
Here is my experience for taking [spring5](https://store.education.pivotal.io/confirm-course?courseid=EDU-1202) certification.

### Why do you need it.
There are 2 main reasons to get it.
1. During preparation you will learn a lot of new stuff
2. It may help your career

### Contents
* [Spring Tips](https://github.com/dgaydukov/cert-spring5/blob/master/files/spring5.md)


### Preparation Sources
* [github/pdf](https://github.com/MrR0807/SpringCertification5.0)
* [github/java project](https://github.com/vshemyako/spring-certification-5.0)
* [mock/250/25](http://itestjava.com/java-certification-practice-tests/product/enter.do?product=SPRING-CORE50)
* [mock/280/80](https://www.certification-questions.com/spring-exam/professional-dumps.html)
* [mobile mock](https://play.google.com/store/apps/details?id=com.springqcm)
* [Official study guide](https://www.amazon.com/Pivotal-Certified-Professional-Spring-Developer/dp/1484251350)


1. spring boot war project without App.main class (only ServletInitializer with springbotapp annotation)
2. pure spring war project with MyWAI class, and with class that just implements WebApplicationInitializer
3. use [wrk](https://github.com/wg/wrk) to test health checkpoint. Copmare it to [test-cli](https://github.com/gorelikov/cards-hub-evolution)
4. hibernate [datasourceProxy](https://github.com/p6spy/p6spy) [assert-sql-count](https://github.com/vladmihalcea/db-util)
onetotmany, fetch(fetchmode.subselect) - run 2 selects
@namedentitygraph
dynamicinsert/dynamicupdate - изменять только те поля, которые были изменены (по умочанию хибернет обновляет все поля даже если изменено 1)

best practice
interface for every class
composition over inheritance

5. spring context indexer
Senior Solutions Architect

hazelcast
zookeeper

6. multiple autowire constructor with @required
7. try to inject persistenceunit/context into object
8. what is testNG
9. application vs singleton

