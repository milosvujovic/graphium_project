package uk.ac.cardiff.team5.graphium.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.*;

@Controller
public class TestController {
    @GetMapping("/")
    public String home(Model model) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "comsc");
        Statement stmt = conn.createStatement();
        stmt.execute("use test;");
        stmt.execute("select * from Test");

        ResultSet rs = stmt.getResultSet();

        rs.next();

        model.addAttribute("testData", rs.getString("testName"));
        return "test-template";
    }
}
