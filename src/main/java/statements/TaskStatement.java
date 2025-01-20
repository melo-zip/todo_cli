package statements;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TaskStatement {
    public PreparedStatement insertPreparedStatement(Connection conn, String name, String description) throws SQLException {
        String sql = "INSERT INTO tasks (name, description, done) VALUES ( ? , ? , ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, description);
        ps.setBoolean(3, false);
        return ps;
    }

    public PreparedStatement deletePreparedStatement(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM tasks WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        return ps;
    }

    public PreparedStatement selectPreparedStatement(Connection conn) throws SQLException {
        String sql = "SELECT * FROM tasks";
        return conn.prepareStatement(sql);
    }

    public PreparedStatement selectByNamePreparedStatement(Connection conn, String name) throws SQLException {
        String sql = "SELECT * FROM tasks WHERE name = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, name);
        return ps;
    }

    public PreparedStatement updatePreparedStatement(Connection conn, String newName, String description, String name) throws SQLException {
        String sql = "UPDATE tasks SET name = ?, description = ? WHERE name = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, newName);
        ps.setString(2, description);
        ps.setString(3, name);
        return ps;
    }

    public PreparedStatement toggleDoneStatusPreparedStatement(Connection conn, String name, boolean done) throws SQLException {
        String sql;
        sql = "UPDATE tasks SET done = ? WHERE name = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setBoolean(1, done);
        ps.setString(2, name);
        return ps;
    }
}
