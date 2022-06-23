# springtx-kotlin
[springtx](https://github.com/lds2292/springtx) 의 코틀린 버전

트랜잭션이 적용되는 로그를 확인 하려면 application.yml에 다음과 로깅설정을 한다
```yaml
logging:
  level:
    org.springframework.transaction.interceptor: trace
    org.springframework.jdbc.datasource.DataSourceTransactionManager: debug
    org.springframework.orm.jpa.JpaTransactionManager: debug
```
