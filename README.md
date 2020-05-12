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



когда создаём класс напрямую без di
1. мы должны знать какую имплементацию выбрать (у интерфейса может быть много имплементаций)
2. мы должны знать через какой конструктор создать (сегодня пустой, завтра с двумя параметрами)
3. мы должны знать как настроить (какие сеттеры вызвать после создания)
т.о. на каждую созданную через new зависимость наш класс приобретает 3 responsibilities

пример фреймворка с
1. созданием обьекта
2. настройкой конфигурации (inject) (pre configurator - object-configurator)
3. postconstruct
4. замена на прокси (post configurator - proxy-configurator with jdk proxy & cglib)