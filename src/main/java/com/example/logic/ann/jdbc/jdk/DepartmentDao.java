package com.example.logic.ann.jdbc.jdk;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.logic.ann.jdbc.DepartmentModel;
import com.example.logic.ann.jdbc.MyDao;

public class DepartmentDao implements MyDao<DepartmentModel> {
    private Connection conn;

    public DepartmentDao(Connection conn){
        this.conn = conn;
    }

    @Override
    public List<DepartmentModel> getAll() {
        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from department");){
            List<DepartmentModel> list = new ArrayList<>();
            while (rs.next()) {
                DepartmentModel model = new DepartmentModel();
                model.setId(rs.getInt(1));
                model.setName(rs.getString(2));
                model.setType(rs.getString(3));
                list.add(model);
            }
            return list;
        } catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public DepartmentModel getById(int id) {
        try(PreparedStatement stmt = conn.prepareStatement("select * from department where id=?");){
            stmt.setInt(1, id);
            try(ResultSet rs = stmt.executeQuery();){
                while (rs.next()) {
                    DepartmentModel model = new DepartmentModel();
                    model.setId(rs.getInt(1));
                    model.setName(rs.getString(2));
                    model.setType(rs.getString(3));
                    return model;
                }
                return null;
            }
        } catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }


    @Override
    public boolean deleteById(int id) {
        try(PreparedStatement stmt = conn.prepareStatement("delete from department where id=?");){
            stmt.setInt(1, id);
            return stmt.executeUpdate() == 1;
        } catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public DepartmentModel save(DepartmentModel model) {
        try(PreparedStatement stmt = conn.prepareStatement("insert into department(name, type) values(?, ?)", Statement.RETURN_GENERATED_KEYS);){
            stmt.setString(1, model.getName());
            stmt.setString(2, model.getType());
            stmt.execute();
            try(ResultSet rs = stmt.getGeneratedKeys();){
                if(rs.next()){
                    model.setId(rs.getInt(1));
                    return model;
                }
                throw new RuntimeException("Can't insert model="+model);
            }
        } catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }
}
