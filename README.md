# AccessToSql
Консольная утилита для импорта информации о делах из MS Access в MS Sql Server.

Стек: Kotlin, Spring Boot 2, Spring Data, Spring Batch, UCanAccess, Mapstruct

### Перед запуском
Если при работе с SQL Server 12 появляется ошибка, связанная с TLS, то нужно в файле (Java 17) 
путь_до_jdk\conf\security\java.security поменять эту строчку:
```
jdk.tls.disabledAlgorithms=SSLv3, TLSv1, TLSv1.1, RC4, DES, MD5withRSA, \
    DH keySize < 1024, EC keySize < 224, 3DES_EDE_CBC, anon, NULL
```
на эту:
```
jdk.tls.disabledAlgorithms=SSLv3, RC4, DES, MD5withRSA, \
    DH keySize < 1024, EC keySize < 224, 3DES_EDE_CBC, anon, NULL
```

Номера фондов и описей должны быть в текстовой файле. Путь до файла указывается в `application.yml` в параметре 
`config.fileWithDocsPath`. Пример содержания файла:
```text
Р-137	1
Р-137	4
Р-489	1
Р-489	2
Р-1485	1
Р-1798	2
Р-1798	3
Р-2070	1
Р-3844	1
Р-3741	1
Р-3741	2
Р-3844	1
Р-4492	1
```

### Алгоритм работы
Из access файла создается временная sql таблица через библиотеку UCanAccess. Уже из нее с помощью Spring Batch данные 
переносятся в основную бд. После, с помощью sql скрипта в `TblUnitRepository`, агрегируем перенесенные данные со старыми 
и сохраняем полученный результат в основную бд.

Если не создавать временную sql таблицу, то вычитывание напрямую занимает намного дольше времени. Можно не переносить 
данные в основную бд, но тогда надо заново придумать алгоритм агрегации. Существующий sql скрипт был взят из старой 
программы на Visual Basic.
