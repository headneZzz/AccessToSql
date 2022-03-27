# AccessToSql
Консольная утилита для импорта информации о делах из MS Access в MS Sql Server.

Kotlin, Spring Boot 2, Spring Data, Spring Batch, UCanAccess, Mapstruct

### Перед запуском
Для корректной работы с SQL Server 12 нужно в файле (Java 17) путь_до_jdk\conf\security\java.security поменять эту строчку:
```
jdk.tls.disabledAlgorithms=SSLv3, TLSv1, TLSv1.1, RC4, DES, MD5withRSA, \
    DH keySize < 1024, EC keySize < 224, 3DES_EDE_CBC, anon, NULL
```
на эту:
```
jdk.tls.disabledAlgorithms=SSLv3, RC4, DES, MD5withRSA, \
    DH keySize < 1024, EC keySize < 224, 3DES_EDE_CBC, anon, NULL
```
