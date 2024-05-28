package com.ulanzhasssanov.CrudAppWithJUnit.repository;

import java.sql.*;
import java.util.List;

public class JdbcPostLabelRepositoryImpl {
    private static final String SAVE_POST_LABEL = "INSERT INTO post_label (postId, labelId) VALUES (?, ?)";
    private static final String DELETE_POST_LABEL_BY_POST_ID = "DELETE FROM post_label WHERE postId = ?";

    public void save(Integer postId, Integer labelId) {

        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_POST_LABEL)) {

            statement.setInt(1, postId);
            statement.setInt(2, labelId);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteByPostId(Integer postId) {
        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_POST_LABEL_BY_POST_ID)) {

            statement.setInt(1, postId);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
