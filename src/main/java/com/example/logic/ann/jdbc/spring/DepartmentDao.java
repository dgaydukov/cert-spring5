package com.example.logic.ann.jdbc.spring;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.logic.ann.jdbc.DepartmentModel;
import com.example.logic.ann.jdbc.MyDao;

@Repository
public class DepartmentDao implements MyDao<DepartmentModel> {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<DepartmentModel> getAll() {
        return jdbcTemplate.query("select * from department", new DepartmentModelMapper());
    }

    @Override
    public DepartmentModel getById(int id) {
        return jdbcTemplate.queryForObject("select * from department where id=?", new Object[]{id}, new DepartmentModelMapper());
    }

    @Override
    public boolean deleteById(int id) {
        return jdbcTemplate.update("delete from department where id=?", id) == 1;
    }

    @Override
    public DepartmentModel save(DepartmentModel model) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(conn-> {
                PreparedStatement ps = conn.prepareStatement("insert into department(name, type) values(?, ?)", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, model.getName());
                ps.setString(2, model.getType());
                return ps;
            },
            keyHolder
        );
        model.setId(keyHolder.getKey().intValue());
        return model;
    }

    private static class DepartmentModelMapper implements RowMapper<DepartmentModel> {
        @Override
        public DepartmentModel mapRow(ResultSet rs, int rowNumber) throws SQLException {
            DepartmentModel model = new DepartmentModel();
            model.setId(rs.getInt("id"));
            model.setName(rs.getString("name"));
            model.setType(rs.getString("type"));
            return model;
        }
    }
}
