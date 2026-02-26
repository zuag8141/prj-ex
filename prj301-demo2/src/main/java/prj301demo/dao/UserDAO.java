package prj301demo.dao;

import prj301demo.dto.UserDTO;
import prj301demo.utils.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public UserDTO login(String username, String password) {
        String sql = "SELECT username, name, password FROM users WHERE username = ? AND password = ?";
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBUtils.getConnection();
            if (con != null) {
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, password);
                rs = stm.executeQuery();
                if (rs.next()) {
                    return new UserDTO(
                        rs.getString("username"),
                        rs.getString("name"),
                        rs.getString("password")
                    );
                }
            }
        } catch (SQLException ex) {
            System.out.println("UserDAO.login error: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stm != null) stm.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.out.println("UserDAO.login close error: " + ex.getMessage());
            }
        }
        return null;
    }
}
