package teacher;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/timetable")
public class TimeTableServlet extends HttpServlet {

 protected void doPost(HttpServletRequest request,
                       HttpServletResponse response)
         throws ServletException, IOException {

     String classname = request.getParameter("classname");
     String section = request.getParameter("section");
     String date = request.getParameter("date");
     String note = request.getParameter("note");

     try{

         Class.forName("com.mysql.cj.jdbc.Driver");

         Connection con = DriverManager.getConnection(
                 "jdbc:mysql://localhost:3306/schooldb",
                 "root",
                 "root"
         );

         String query =
                 "insert into timetable(classname,section,effectivedate,note) values(?,?,?,?)";

         PreparedStatement ps = con.prepareStatement(query);

         ps.setString(1, classname);
         ps.setString(2, section);
         ps.setString(3, date);
         ps.setString(4, note);

         int i = ps.executeUpdate();

         if(i > 0){

             response.sendRedirect("successfull.html");

         }else{

             response.getWriter().println("Failed");

         }

         con.close();

     }
     catch(Exception e){

         e.printStackTrace();

     }

 }
}
