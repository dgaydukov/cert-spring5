package com.example.logic.ann.jdbc.template;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.logic.ann.jdbc.DepartmentModel;

@Component
public class TemplateDepDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Inject oneself to imitate calling transactional method from non-transactional
     */
    @Autowired
    private TemplateDepDao templateDepDao;

    public List<DepartmentModel> getAll() {
        return templateDepDao.getAllTransactional();
    }

    /**
     * If we make this method public, it would be executed within transaction
     * otherwise not
     */
    @Transactional
    List<DepartmentModel> getAllTransactional() {
        System.out.println("getAll");
        return jdbcTemplate.query("select * from department", DepartmentDao::mapRowToModel);
    }
}

/**
 19:26:39.988 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'platformTransactionManager'
 19:26:40.008 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Creating new transaction with name [com.example.logic.ann.jdbc.template.TemplateDepDaoQuery.getAll]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT
 19:26:40.008 [main] DEBUG org.springframework.jdbc.datasource.SimpleDriverDataSource - Creating new JDBC Driver Connection to [jdbc:mysql://localhost:3306/spring5]
 19:26:40.010 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Acquired Connection [com.mysql.cj.jdbc.ConnectionImpl@606fc505] for JDBC transaction
 19:26:40.012 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Switching JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@606fc505] to manual commit
 getAll
 19:26:40.020 [main] DEBUG org.springframework.jdbc.core.JdbcTemplate - Executing SQL query [select * from department]
 19:26:40.038 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Initiating transaction commit
 19:26:40.038 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Committing JDBC transaction on Connection [com.mysql.cj.jdbc.ConnectionImpl@606fc505]
 19:26:40.039 [main] DEBUG org.springframework.jdbc.datasource.DataSourceTransactionManager - Releasing JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@606fc505] after transaction
 [DepartmentModel(id=1, name=Exchange, type=IT), DepartmentModel(id=2, name=Solution, type=IT), DepartmentModel(id=3, name=Markets, type=CP), DepartmentModel(id=4, name=New dep, type=cool), DepartmentModel(id=5, name=New dep, type=cool)]
 */