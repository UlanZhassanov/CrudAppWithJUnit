package com.ulanzhasssanov.CrudAppWithJUnit.repository;

import com.ulanzhasssanov.CrudAppWithJUnit.enums.Status;
import com.ulanzhasssanov.CrudAppWithJUnit.model.Label;
import com.ulanzhasssanov.CrudAppWithJUnit.model.Post;
import com.ulanzhasssanov.CrudAppWithJUnit.enums.PostStatus;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class JdbcPostRepositoryImpl implements PostRepository {
    JdbcPostLabelRepositoryImpl postLabelRepository = new JdbcPostLabelRepositoryImpl();

    private static final String GET_POST_BY_ID = "SELECT * FROM posts WHERE id = ? AND status='ACTIVE'";
    private static final String GET_LABELS_BY_POST_ID = "SELECT l.* FROM post_label pl " +
            "JOIN labels l on pl.labelId = l.id " +
            "WHERE pl.postId = ?";
    private static final String GET_ALL_POSTS = "SELECT * FROM posts WHERE status='ACTIVE'";
    private static final String SAVE_POST = "INSERT INTO posts (content, created, updated, writerId, status) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_POST = "UPDATE posts SET content = ?, updated = ? WHERE id = ?";
    private static final String DELETE_POST = "UPDATE posts SET status = ? WHERE id = ?";
    private static final String SAVE_POST_LABEL = "INSERT INTO post_label (postId, labelId) VALUES (?, ?)";
    private static final String DELETE_POST_LABEL_BY_POST_ID = "DELETE FROM post_label WHERE postId = ?";
    private static final String GET_POST_BY_WRITER_ID = "SELECT * FROM posts WHERE writerId = ? AND status='ACTIVE'";


    @Override
    public Post getById(Integer id) {
        Post post = null;

        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_POST_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                post = new Post();
                post.setId(resultSet.getInt("id"));
                post.setContent(resultSet.getString("content"));
                post.setCreated(resultSet.getString("created"));
                post.setUpdated(resultSet.getString("updated"));
                post.setWriterId(resultSet.getInt("writerId"));
                post.setStatus(PostStatus.ACTIVE);

                List<Label> labels = getLabelsByPostId(id);
                post.setLabels(labels);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return post;
    }

    private List<Label> getLabelsByPostId(Integer postId) {
        List<Label> labels = new ArrayList<>();

        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement labelStatement = connection.prepareStatement(GET_LABELS_BY_POST_ID)) {
            labelStatement.setInt(1, postId);
            ResultSet labelResultSet = labelStatement.executeQuery();

            while (labelResultSet.next()) {
                Label label = new Label();
                label.setId(labelResultSet.getInt("id"));
                label.setName(labelResultSet.getString("name"));
                label.setStatus(Status.valueOf(labelResultSet.getString("status")));
                labels.add(label);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return labels;
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();

        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_POSTS);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Post post = new Post();
                post.setId(resultSet.getInt("id"));
                post.setContent(resultSet.getString("content"));
                post.setCreated(resultSet.getString("created"));
                post.setUpdated(resultSet.getString("updated"));
                post.setWriterId(resultSet.getInt("writerId"));
                post.setStatus(PostStatus.ACTIVE);

                List<Label> labels = getLabelsByPostId(post.getId());
                post.setLabels(labels);

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

        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement postStatement = connection.prepareStatement(SAVE_POST, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement postLabelStatement = connection.prepareStatement(SAVE_POST_LABEL)) {
            Timestamp currentTimestamp = Timestamp.from(Instant.now());

            postStatement.setString(1, post.getContent());
            postStatement.setTimestamp(2, currentTimestamp);
            postStatement.setTimestamp(3, currentTimestamp);
            postStatement.setInt(4, post.getWriterId());
            postStatement.setString(5, String.valueOf(Status.ACTIVE));
            postStatement.executeUpdate();

            generatedKeys = postStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                if (post.getLabels() != null) {
                    for (Label label : post.getLabels()) {
                        postLabelStatement.setInt(1, generatedKeys.getInt(1));
                        postLabelStatement.setInt(2, label.getId());
                        postLabelStatement.addBatch();
                    }
                    postLabelStatement.executeBatch();
                }

                resultPost = getById(generatedKeys.getInt(1));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultPost;
    }

    @Override
    public Post update(Post post) {
        Post resultPost = null;

        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_POST);
             PreparedStatement deletePostLabelStatement = connection.prepareStatement(DELETE_POST_LABEL_BY_POST_ID);
             PreparedStatement addPostLabelStatement = connection.prepareStatement(SAVE_POST_LABEL)) {
            Timestamp currentTimestamp = Timestamp.from(Instant.now());

            statement.setString(1, post.getContent());
            statement.setTimestamp(2, currentTimestamp);
            statement.setInt(3, post.getId());

            statement.executeUpdate();
            deletePostLabelStatement.setInt(1,post.getId());
            deletePostLabelStatement.executeUpdate();

            if (post.getLabels() != null) {
                for (Label label : post.getLabels()) {
                    addPostLabelStatement.setInt(1, post.getId());
                    addPostLabelStatement.setInt(2, label.getId());
                    addPostLabelStatement.addBatch();
                }
                addPostLabelStatement.executeBatch();
            }

            resultPost = getById(post.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultPost;
    }

    @Override
    public void deleteById(Integer id) {
        Post post = getById(id);

        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_POST)) {
            statement.setString(1, String.valueOf(Status.DELETED));
            statement.setInt(2, post.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Post> getByWriterId(Integer id) {
        List<Post> posts = new ArrayList<>();

        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_POST_BY_WRITER_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())  {
                Post post = new Post();
                post.setId(resultSet.getInt("id"));
                post.setContent(resultSet.getString("content"));
                post.setCreated(resultSet.getString("created"));
                post.setUpdated(resultSet.getString("updated"));
                post.setWriterId(resultSet.getInt("writerId"));
                post.setStatus(PostStatus.ACTIVE);

                List<Label> labels = getLabelsByPostId(post.getId());
                post.setLabels(labels);

                posts.add(post);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return posts;
    }

}
