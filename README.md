# Многомодульный maven. Многопоточность. XML. Веб сервисы. Удаленное взаимодействие
## <a href="http://javawebinar.ru/masterjava">Регистрация</a>
## [Программа проекта](#Программа-проекта)

### _Разработка полнофункционального многомодульного Maven проекта_
- веб приложение (Tomcat, Thymleaf, jQuery)
- модуль экспорта из XML (JAXB, StAX)
- многопоточный почтовый сервис (JavaMail, java.util.concurrent.*)
- связь модулей через веб-сервисы (SOAP, JAX-WS) и по REST (JAX-RS)
- сохранение данных в RMDBS (postgresql)
- библиотеки Guava, StreamEx, Lombook, Typesafe config, jDBI

### Требование к участникам
Опыт программирования на Java. Базовые знания Maven.

### Необходимое ПО
-  <a href="http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html">JDK8</a>
-  <a href="http://git-scm.com/downloads">Git</a>
-  <a href="http://www.jetbrains.com/idea/download/index.html">IntelliJ IDEA</a>

> Выбирать Ultimate, 30 days trial (работа с JavaScript, Tomcat, JSP). Персональный ключ к Ultimate (на 6 месяцев) выдается на первом занятии.

# Первое занятие: многопоточность.

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 1. <a href="https://www.youtube.com/watch?v=whONxvrM2Fc">Вступление. Многопоточность и параллельность.</a>
![Concurrent vs Parallel](https://joearms.github.io/images/con_and_par.jpg)

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 2. <a href="https://www.youtube.com/watch?v=qpV0KRadPj8">Структура памяти Java. Ленивая инициализация.</a>
> В видео в `LazySingleton` ошибка: должно быть как в коде проекта `instance == null`

### Структура памяти: куча, стек, permanent/metaspace
  - <a href="http://www.slideshare.net/kslisenko/jvm-35760825">JVM изнутри - оптимизация и профилирование</a>.
  - <a href="http://stackoverflow.com/questions/79923/what-and-where-are-the-stack-and-heap#24171266">Stack and Heap</a>
  - Дополнительно:
    - <a href="http://habrahabr.ru/post/117274/">Из каких частей состоит память java процесса</a>.
    - <a href="http://www.javaspecialist.ru/2011/04/permanent.html">Permanent область памяти</a>
    - <a href="http://www.javaspecialist.ru/2011/04/java-thread-stack.html">Java thread stack </a>
    - <a href="http://habrahabr.ru/post/134102/">Размер Java объектов</a>

### Ленивая инициализация
- <a href="https://habrahabr.ru/post/27108/">Реализация Singleton в JAVA</a>
- <a href="https://ru.wikipedia.org/wiki/Double_checked_locking">Double checked locking</a>
- <a href="https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom">Initialization-on-demand holder idiom</a>
- <a href="https://tproger.ru/translations/singleton-pitfalls/">Подводные камни Singleton</a>

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 3. <a href="https://www.youtube.com/watch?v=8bFF-5r_Kig">Реализация многопоточности в Java</a>
- <a href="https://ru.wikipedia.org/wiki/Параллелизм_в_Java">Параллелизм в Java</a>
- <a href="https://ru.wikipedia.org/wiki/Монитор_(синхронизация)">Монитор (синхронизация)</a>
- <a href="https://en.wikipedia.org/wiki/Compare-and-swap">Compare-and-swap</a>
- <a href="http://www.javaspecialist.ru/2011/06/java-memory-model.html">Java Memory Model</a>
- <a href="http://www.skipy.ru/technics/synchronization.html">Синхронизация потоков</a>
- <a href="https://habrahabr.ru/company/luxoft/blog/157273">Обзор java.util.concurrent.*</a>
- <a href="https://habrahabr.ru/post/132884/">Как работает ConcurrentHashMap</a>
- <a href="https://habrahabr.ru/post/277669/"> Справочник по синхронизаторам java.util.concurrent.*</a>
- <a href="http://articles.javatalks.ru/articles/17">Использование ThreadLocal переменных</a>
- <a href="https://www.youtube.com/watch?v=8piqauDj2yo">Николай Алименков — Прикладная многопоточность</a>
- <a href="http://stackoverflow.com/questions/20163056/in-java-can-thread-switching-happen-in-the-synchronized-block">Can thread switching happen in the synchronized block?</a>

#### Tproger: Многопоточное программирование в Java 8
- <a href="https://tproger.ru/translations/java8-concurrency-tutorial-1/">1. Параллельное выполнение кода с помощью потоков</a>
- <a href="https://tproger.ru/translations/java8-concurrency-tutorial-2/">2. Синхронизация доступа к изменяемым объектам</a>
- <a href="https://tproger.ru/translations/java8-concurrency-tutorial-3/">3. Атомарные переменные и конкурентные таблицы</a>

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 4. <a href="https://www.youtube.com/watch?v=AEhIh2qd-FM">Реализация многопоточной отправки писем. Execution Framework</a>
> правка к видео: `22:   completionService.submit(..)`

*Все изменения в проекте будут делаться на основе патчей: скачайте [1_1_MailService.patch](https://drive.google.com/open?id=0B9Ye2auQ_NsFTE5ZV3pzWElxTWM), положите его в проект, правой мышкой на нем сделайте Apply Patch ...*

----------------------------

### Ресурсы (основы)
- Intuit, <a href="http://www.intuit.ru/studies/courses/16/16/lecture/27127">Потоки выполнения. Синхронизация</a>
- Алексей Владыкин, <a href="https://www.youtube.com/watch?v=zxZ0BXlTys0&list=PLlb7e2G7aSpRSBWi5jbGjIe-v_CjRO_Ug">Основы многопоточность в Java</a>
- Виталий Чибриков, <a href="https://www.youtube.com/watch?v=dLDhB6SRXzw&list=PLrCZzMib1e9qkzxEuU_huxtSAxrW1t9NZ">Java. Многопоточность</a>
- Computer Science Center, курс <a href="https://compscicenter.ru/courses/hp-course/2016-spring">Параллельное программирование</a>
- Юрий Ткач, курс <a href="https://www.youtube.com/playlist?list=PL6jg6AGdCNaXo06LjCBmRao-qJdf38oKp">Advanced Java - Concurrency</a>
- Головач, курс <a href="https://www.youtube.com/playlist?list=PLoij6udfBncgVRq487Me6yQa1kqtxobZS">Java Multithreading</a>

---
## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Задание первого занятия

Вычекать этот проект:
```git clone  https://github.com/JavaOPs/masterjava.git```

- Применить <a href="https://habrahabr.ru/post/114797/">оптимизацию</a> к MatrixUtil.singleThreadMultiply
- Реализовать метод `MatrixUtil.concurrentMultiply`, позволяющий многопоточно <a href="https://ru.wikipedia.org/wiki/Умножение_матриц">перемножать квадратные матрицы N*N</a>.
- Количество дочерних потоков ограничено `MainMatrix.THREAD_NUMBER`.
- Добиться того, чтобы на матрице 1000*1000 многопоточная реализация была быстрее однопоточной

-----

# Программа проекта
> **возможны изменения, окончательная программа будет перед стартом курса**

## Занятие 2
- Разбор ДЗ (многопоточная реализация умножения матриц)
- <a href="http://openjdk.java.net/projects/code-tools/jmh/">Java Microbenchmark JMH</a> (от Алексея Шипилева)
- Обзор <a href="https://github.com/google/guava">Guava</a>
- Формат XML. Создание схемы XSD.
- Работа с XML в Java
  - JAXB, JAXP
  - StAX
  - XPath
  - XSLT

## Занятие 3
- Разбор ДЗ (работа с XML)
- Обзор <a href="https://github.com/amaembo/streamex">StreamEx</a> (от Тагира Валеева)
- Монады. flatMap
- SOA и Микросервисы
- Многомодульный Maven проект

## Занятие 4
- Разбор ДЗ (реализация структуры проекта, загрузка и разбор xml)
- Thymleaf
- Maven. Поиск и разрешение конфликтов зависимостей
- Логирование
- Выбор lightweight JDBC helper library. <a href="http://jdbi.org/">JDBI</a>
- Tomcat Class Loader. Memory Leeks

## Занятие 5
- Разбор ДЗ (реализуем модули persist, export и web)
- Конфигурирование приложения (<a href="https://github.com/typesafehub/config">Typesafe config</a>)
- Lombook

## Занятие 6
- Разбор ДЗ (доработка модели и модуля export)
- Миграция DB
- Веб-сервисы (REST/SOAP)
  - Java реализации SOAP
  - Имплементируем Mail Service
  
## Занятие 7
- Разбор ДЗ (реализация MailSender, сохранение результатов отправки)
- Стили WSDL. Кастомизация WSDL
- Публикация кастомизированного WSDL. Автогенерация.
- Деплой в Tomcat
- Создание клиента почтового сервиса

## Занятие 8
- Разбор ДЗ (отправка почты через Executor из модуля web)
- Доступ к переменным maven в приложении
- SOAP Exception. Выделение общей части схемы
- Передача двоичных данных в веб-сервисах. MTOM

## Занятие 9
- Разбор ДЗ (реализация загрузки и отправки вложений по почте)
- JAX-WS Message Context
- JAX-WS Handlers (логирование SOAP)

## Занятие 10 (предварительно)
- Разбор ДЗ (реализация авторизации и статистики)
- JavaEE
  - JAX-RS. Интеграция с Jersey
  - EJB
  - JMS
 
## Занятие 11 (предварительно)
- Асинхронные сервлеты 3.x в Tomcat
- Maven Groovy cкрптинг (groovy-maven-plugin)
- AKKA
- Redis
