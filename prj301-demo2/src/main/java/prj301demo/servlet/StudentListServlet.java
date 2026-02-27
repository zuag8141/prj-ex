/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package prj301demo.servlet;

import prj301demo.dao.StudentDAO;
import prj301demo.dto.StudentDTO;
import prj301demo.dto.UserDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author DUNGHUYNH
 */
public class StudentListServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Check login
        HttpSession session = request.getSession(false);
        UserDTO loginUser = (session != null) ? (UserDTO) session.getAttribute("user") : null;
        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/login.html");
            return;
        }

        String keyword = request.getParameter("keyword");
        String sortCol = request.getParameter("sortCol");

        StudentDAO studentDAO = new StudentDAO();
        List<StudentDTO> students = studentDAO.list(keyword, sortCol);

        try ( PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Student List</title>");
            out.println("</head>");
            out.println("<body>");

            // Menu
            out.println("<nav>");
            out.println("<a href='" + request.getContextPath() + "/StudentList'>Student List</a> | ");
            out.println("<a href='" + request.getContextPath() + "/login.html'>Logout</a>");
            out.println("</nav>");
            out.println("<hr>");

            out.println("<p>Welcome, <strong>" + loginUser.getName() + "</strong> (" + loginUser.getUsername() + ")</p>");
            out.println("<h1>Student List</h1>");

            String kw = (keyword != null ? keyword : "");
            out.println("<form action=''>");
            out.println("<input name='keyword' type='text' placeholder='Search by first or last name' value='" + kw + "'>");
            out.println("<input type='submit' value='Search'>");
            out.println("</form>");

            String baseUrl = request.getContextPath() + "/StudentList?keyword=" + kw + "&sortCol=";
            out.println("<table border='1'>");
            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>First Name</th>");
            out.println("<th><a href='" + baseUrl + "lastname'>Last Name</a></th>");
            out.println("</tr>");

            for (StudentDTO s : students) {
                out.println("<tr>");
                out.println("<td>" + s.getId() + "</td>");
                out.println("<td>" + s.getFirstName() + "</td>");
                out.println("<td>" + s.getLastName() + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
