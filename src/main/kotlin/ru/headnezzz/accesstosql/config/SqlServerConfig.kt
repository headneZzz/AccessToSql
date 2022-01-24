package ru.headnezzz.accesstosql.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "sqlServerEntityManagerFactory",
    transactionManagerRef = "sqlServerTransactionManager",
    basePackages = ["ru.headnezzz.accesstosql.repository.sqlserver"]
)
class SqlServerConfig {

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    fun sqlServerDataSource(): DataSource {
        return DataSourceBuilder.create().build()
    }

    @Primary
    @Bean
    fun sqlServerEntityManagerFactory(
        builder: EntityManagerFactoryBuilder,
        @Qualifier("sqlServerDataSource") dataSource: DataSource
    ): LocalContainerEntityManagerFactoryBean {
        return builder
            .dataSource(dataSource)
            .packages("ru.headnezzz.accesstosql.model.entity.sqlserver")
            .persistenceUnit("sqlserver")
            .build()
    }

    @Primary
    @Bean
    fun sqlServerTransactionManager(sqlServerEntityManagerFactory: EntityManagerFactory): PlatformTransactionManager {
        return JpaTransactionManager(sqlServerEntityManagerFactory)
    }
}
