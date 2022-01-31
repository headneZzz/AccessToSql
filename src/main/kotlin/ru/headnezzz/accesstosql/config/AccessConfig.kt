package ru.headnezzz.accesstosql.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
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
    entityManagerFactoryRef = "accessEntityManagerFactory",
    transactionManagerRef = "accessTransactionManager",
    basePackages = ["ru.headnezzz.accesstosql.repository.access"]
)
@ConditionalOnProperty(prefix = "spring", name = ["access-enabled"], havingValue = "true", matchIfMissing = false)
class AccessConfig {

    @Bean("accessDataSource")
    @ConfigurationProperties(prefix = "spring.access-datasource")
    fun accessDataSource(): DataSource {
        return DataSourceBuilder.create().build()
    }

    @Bean("accessEntityManagerFactory")
    fun accessEntityManagerFactory(
        builder: EntityManagerFactoryBuilder,
        @Qualifier("accessDataSource") dataSource: DataSource
    ): LocalContainerEntityManagerFactoryBean {
        return builder
            .dataSource(dataSource)
            .packages("ru.headnezzz.accesstosql.model.entity.access")
            .persistenceUnit("access")
            .build()
    }

    @Bean("accessTransactionManager")
    fun accessTransactionManager(
        @Qualifier("accessEntityManagerFactory") personalEntityManagerFactory: EntityManagerFactory
    ): PlatformTransactionManager {
        return JpaTransactionManager(personalEntityManagerFactory)
    }
}
