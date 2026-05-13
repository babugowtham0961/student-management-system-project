// StudentServlet.java

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/StudentServlet")
public class StudentServlet extends HttpServlet {

    // GET ALL STUDENTS
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");

        PrintWriter out = response.getWriter();

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM students";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            StringBuilder json =
                    new StringBuilder("[");

            boolean first = true;

            while (rs.next()) {

                if (!first) {
                    json.append(",");
                }

                json.append("{")
                        .append("\"id\":")
                        .append(rs.getInt("id"))
                        .append(",")

                        .append("\"name\":\"")
                        .append(rs.getString("name"))
                        .append("\",")

                        .append("\"age\":")
                        .append(rs.getInt("age"))
                        .append(",")

                        .append("\"course\":\"")
                        .append(rs.getString("course"))
                        .append("\"")
                        .append("}");

                first = false;
            }

            json.append("]");

            out.print(json);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // INSERT / UPDATE
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        String name = request.getParameter("name");
        int age = Integer.parseInt(
                request.getParameter("age"));
        String course =
                request.getParameter("course");

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps;

            if (id == null || id.isEmpty()) {

                String sql =
                        "INSERT INTO students(name, age, course) VALUES(?,?,?)";

                ps = con.prepareStatement(sql);

                ps.setString(1, name);
                ps.setInt(2, age);
                ps.setString(3, course);

                ps.executeUpdate();

                response.getWriter()
                        .print("Student added successfully!");

            } else {

                String sql =
                        "UPDATE students SET name=?, age=?, course=? WHERE id=?";

                ps = con.prepareStatement(sql);

                ps.setString(1, name);
                ps.setInt(2, age);
                ps.setString(3, course);
                ps.setInt(4, Integer.parseInt(id));

                ps.executeUpdate();

                response.getWriter()
                        .print("Student updated successfully!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // DELETE
    @Override
    protected void doDelete(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(
                request.getParameter("id"));

        try {

            Connection con =
                    DBConnection.getConnection();

            String sql =
                    "DELETE FROM students WHERE id=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, id);

            ps.executeUpdate();

            response.getWriter()
                    .print("Student deleted successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}