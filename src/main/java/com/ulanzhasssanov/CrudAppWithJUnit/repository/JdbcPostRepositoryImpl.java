package com.ulanzhasssanov.CrudAppWithJUnit.repository;

import com.ulanzhasssanov.CrudAppWithJUnit.enums.Status;
import com.ulanzhasssanov.CrudAppWithJUnit.model.Post;
import com.ulanzhasssanov.CrudAppWithJUnit.enums.PostStatus;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.*;
import java.text.DateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JdbcPostRepositoryImpl implements PostRepository {


    @Override
    public Post getById(Integer id) {
        Post post = null;
        String query = "SELECT * FROM posts WHERE id = ? AND status='ACTIVE'";

        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    post = new Post();
                    post.setId(resultSet.getInt("id"));
                    post.setContent(resultSet.getString("content"));
                    post.setCreated(resultSet.getString("created"));
                    post.setUpdated(resultSet.getString("updated"));
                    post.setLabelId(resultSet.getInt("labelId"));
                    post.setWriterId(resultSet.getInt("writerId"));
                    post.setStatus(PostStatus.ACTIVE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return post;
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        String query = "SELECT * FROM posts WHERE status='ACTIVE'";

        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()){
                Post post = new Post();
                post.setId(resultSet.getInt("id"));
                post.setContent(resultSet.getString("content"));
                post.setCreated(resultSet.getString("created"));
                post.setUpdated(resultSet.getString("updated"));
                post.setLabelId(resultSet.getInt("labelId"));
                post.setWriterId(resultSet.getInt("writerId"));
                post.setStatus(PostStatus.ACTIVE);

                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return posts;
    }

    @Override
    public Post save(Post post) {
        Post resultPost = null;
        ResultSet generatedKeys;
        String query = "INSERT INTO posts (content, created, updated, labelId, writerId, status) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            Timestamp currentTimestamp = Timestamp.from(Instant.now());

            statement.setString(1, post.getContent());
            statement.setTimestamp(2, currentTimestamp);
            statement.setTimestamp(3, currentTimestamp);
            statement.setInt(4, post.getLabelId());
            statement.setInt(5, post.getWriterId());
            statement.setString(6, String.valueOf(Status.ACTIVE));

            generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()){
                resultPost = getById(generatedKeys.getInt(1));
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultPost;
    }

    @Override
    public Post update(Post post) {
        Post resultPost = null;
        String query = "UPDATE posts SET content = ?, labelId = ?, updated = ? WHERE id = ?";

        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)){
            Timestamp currentTimestamp = Timestamp.from(Instant.now());

            statement.setString(1, post.getContent());
            statement.setInt(2, post.getLabelId());
            statement.setTimestamp(3, currentTimestamp);
            statement.setInt(4, post.getId());

            statement.executeUpdate();

            resultPost = getById(post.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultPost;
    }

    @Override
    public void deleteById(Integer id) {
        Post post = getById(id);
        String query = "UPDATE posts SET status = ? WHERE id = ?";

        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, String.valueOf(Status.DELETED));
            statement.setInt(2, post.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
