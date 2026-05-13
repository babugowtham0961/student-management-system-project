// RegisterServlet.java

package com.teacher;

import java.io.*;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/register")
@MultipartConfig
public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");
        String dob = request.getParameter("dob");
        String password = request.getParameter("password");
        String subject = request.getParameter("subject");
        String qualification = request.getParameter("qualification");
        String experience = request.getParameter("experience");

        try {

            // MYSQL DRIVER
            Class.forName("com.mysql.cj.jdbc.Driver");

            // CONNECTION
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/teacherdb",
                    "root",
                    "root"
            );

            String query = "insert into teachers(fullname,email,mobile,dob,password,subject,qualification,experience) values(?,?,?,?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, fullname);
            ps.setString(2, email);
            ps.setString(3, mobile);
            ps.setString(4, dob);
            ps.setString(5, password);
            ps.setString(6, subject);
            ps.setString(7, qualification);
            ps.setString(8, experience);

            int i = ps.executeUpdate();

            if(i > 0){

                response.sendRedirect("home.html");

            }else{

                response.getWriter().println("Registration Failed");

            }

            con.close();

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
