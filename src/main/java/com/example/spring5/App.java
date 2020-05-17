package com.example.spring5;


/**
 * InjectProperty by variable name if property not set
 * Inject => rename to InjectType
 * Вынести логику настройки бина в отельных 2 интерфейса (до и после postconstruct) - в них можно передать appcontext
 * вынести в конфиг логику получения классво по интерфейсу из objectfactory
 * вынести логику кеширования в appcontext c методом getObject
 * вызов objectfactory.getObject - утечка инфрастуктуры в бизнес логику
 * DI - pass objects into classes. IoC - don't call us, we call you. Insert objects with Autowired/Inject
 * apprunner который связывает appcontext & objectfactory
 * init all singletons on startup
 * add cglib to proxy if no interfaces (or javassist)
 */