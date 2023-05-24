package Utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBQuery {

    private static PreparedStatement ps;

    /**
     * Creates a prepares SQL statement.
     *
     * @param connection the database connection.
     * @param sql the SQL statement.
     * @throws SQLException from connection.
     */
    public static void setPreparedStatement(Connection connection, String sql) throws SQLException {

        ps = connection.prepareStatement(sql);
    }

    /**
     * Getter for the prepared statement.
     *
     * @return prepared statement.
     */
    public static PreparedStatement getPreparedStatement() {

        return ps;
    }
}
