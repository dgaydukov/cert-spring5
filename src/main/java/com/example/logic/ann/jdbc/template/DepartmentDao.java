package com.example.logic.ann.jdbc.template;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
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

    private DepartmentModel resultSetToModel(ResultSet rs) throws SQLException {
        DepartmentModel model = new DepartmentModel();
        model.setId(rs.getInt("id"));
        model.setName(rs.getString("name"));
        model.setType(rs.getString("type"));
        return model;
    }

    public List<DepartmentModel> getAllRCH() {
        List<DepartmentModel> list = new ArrayList<>();
        jdbcTemplate.query("select * from department", rs->{
            // add first row
            list.add(resultSetToModel(rs));
            // add all other rows
            while (rs.next()) {
                list.add(resultSetToModel(rs));
            }
        });
        return list;
    }



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
    public void delete(DepartmentModel model) {
        jdbcTemplate.update("delete from department where id=?", model.getId());
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

    /**
     * Here we have access to one row at a time
     */
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
     * Here we have access to whole ResultSet, so we can manipulate it whatever we want (like creating a map with name as key and type as list)
     */
    private static class DepartmentResultSetExtractor implements ResultSetExtractor<Map<String, List<String>>> {
        @Override
        public Map<String, List<String>> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<String, List<String>> map = new HashMap<>();
            while (rs.next()){
                map.merge(rs.getString("name"), new ArrayList<>(List.of(rs.getString("type"))), (acc, v)->{
                    acc.addAll(v);
                    return acc;
                });
            }
            return map;
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