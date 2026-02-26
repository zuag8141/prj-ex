package prj301demo.dao;

import prj301demo.dto.StudentDTO;
import prj301demo.utils.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public List<StudentDTO> list(String keyword) {
        ArrayList<StudentDTO> list = new ArrayList<StudentDTO>();
        String sql = "SELECT id, firstname, lastname FROM student";

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql += " WHERE firstname LIKE ? OR lastname LIKE ?";
        }

        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBUtils.getConnection();
            if (con != null) {
                stm = con.prepareStatement(sql);
                if (keyword != null && !keyword.trim().isEmpty()) {
                    String searchPattern = "%" + keyword + "%";
                    stm.setString(1, searchPattern);
                    stm.setString(2, searchPattern);
                }
                rs = stm.executeQuery();
                while (rs.next()) {
                    list.add(new StudentDTO(
                        rs.getLong("id"),
                        rs.getString("firstname"),
                        rs.getString("lastname")
                    ));
                }
            }
        } catch (SQLException ex) {
            System.out.println("StudentDAO.list error: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stm != null) stm.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.out.println("StudentDAO.list close error: " + ex.getMessage());
            }
        }
        return list;
    }
}
