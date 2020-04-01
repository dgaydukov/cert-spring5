package com.example.logic.ann.jdbc.spring;

import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;

public class MySqlExTranslator extends SQLErrorCodeSQLExceptionTranslator {
    static class SqlDeadException extends DataAccessException{
        public SqlDeadException(String msg, Exception ex) {
            super(msg, ex);
        }
    }
    @Override
    protected DataAccessException customTranslate(String task, String sql, SQLException ex){
        if(ex.getErrorCode() == 123456){
            return new SqlDeadException(task, ex);
        }
        return null;
    }
}
