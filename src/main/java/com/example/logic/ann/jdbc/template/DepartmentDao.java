package com.example.logic.ann.jdbc.template;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.logic.ann.jdbc.DepartmentModel;
import com.example.logic.ann.jdbc.MyDao;
import com.fasterxml.jackson.databind.ObjectMapper;

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


    /**
     * Example how we can pass mappers
     */
    public DepartmentModel getByIdSimpleMapper(int id) {
        return jdbcTemplate.queryForObject("select * from department where id=?", new Object[]{id}, this::mapRowToModel);
    }
    public DepartmentModel getByIdSimpleMapperReordered(int id) {
        return jdbcTemplate.queryForObject("select * from department where id=?", this::mapRowToModel, id);
    }

    /**
     * we can also use default mapper
     */
    public DepartmentModel getByIdSpringMapper(int id) {
        return jdbcTemplate.queryForObject("select * from department where id=?", new BeanPropertyRowMapper<>(DepartmentModel.class), id);
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

    /**
     * Example of simple save, no need to use PreparedStatementCreator and keyHolder
     */
    public DepartmentModel simpleSave(DepartmentModel model) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("department")
            .usingGeneratedKeyColumns("id");
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> values = mapper.convertValue(model, Map.class);
        int id = insert.executeAndReturnKey(values).intValue();
        model.setId(id);
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

    /**
     * We can define mapper like this
     */
    private DepartmentModel mapRowToModel(ResultSet rs, int rowNumber) throws SQLException {
        DepartmentModel model = new DepartmentModel();
        model.setId(rs.getInt("id"));
        model.setName(rs.getString("name"));
        model.setType(rs.getString("type"));
        return model;
    }
}