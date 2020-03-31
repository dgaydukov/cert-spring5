package com.example.spring5;

import com.example.logic.ann.jdbc.jdk.DepartmentDao;
import com.example.logic.ann.jdbc.jdk.DepartmentModel;
import com.example.logic.ann.jdbc.jdk.MyConnection;
import com.example.logic.ann.jdbc.jdk.MyDao;

public class App {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/spring5?user=root&password=";
        MyConnection conn = new MyConnection(url);
        MyDao<DepartmentModel> dao = new DepartmentDao(conn.getConnection());

        System.out.println("getAll => " + dao.getAll());
        System.out.println("getById(1) => " + dao.getById(1));

        DepartmentModel model = new DepartmentModel();
        model.setName("New dep");
        model.setType("cool");
        var saved = dao.save(model);
        int id = saved.getId();
        System.out.println("save => " + saved);
        System.out.println("deleteById(" + id + ") => " + dao.deleteById(id));
    }
}