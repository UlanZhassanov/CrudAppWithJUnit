package com.ulanzhasssanov.CrudAppWithJUnit.repository;

import com.ulanzhasssanov.CrudAppWithJUnit.enums.Status;
import com.ulanzhasssanov.CrudAppWithJUnit.model.Post;
import com.ulanzhasssanov.CrudAppWithJUnit.model.Writer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcWriterRepositoryImpl implements WriterRepository {
    JdbcPostRepositoryImpl postRepository = new JdbcPostRepositoryImpl();

    @Override
    public Writer getById(Integer id) {
        Writer writer = null;
        String query = "SELECT * FROM writers WHERE id = ? AND status='ACTIVE'";

        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                writer = new Writer();
                writer.setId(resultSet.getInt("id"));
                writer.setFirstName(resultSet.getString("firstname"));
                writer.setLastName(resultSet.getString("lastname"));
                writer.setStatus(Status.ACTIVE);
            }

            List<Post> posts = postRepository.getByWriterId(writer.getId());
            writer.setPosts(posts);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return writer;
    }

    @Override
    public List<Writer> getAll() {

        List<Writer> writers = new ArrayList<>();
        String query = "SELECT * FROM writers WHERE status='ACTIVE'";

        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Writer writer = new Writer();
                writer.setId(resultSet.getInt("id"));
                writer.setFirstName(resultSet.getString("firstname"));
                writer.setLastName(resultSet.getString("lastname"));
                writer.setStatus(Status.ACTIVE);

                List<Post> posts = postRepository.getByWriterId(writer.getId());
                writer.setPosts(posts);

                writers.add(writer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return writers;
    }

    @Override
    public Writer save(Writer writer) {
        Writer resultWriter = null;
        ResultSet generatedKeys;
        String query = "INSERT INTO writers (firstname, lastname, status) VALUES (?, ?, ?)";

        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, writer.getFirstName());
            statement.setString(2, writer.getLastName());
            statement.setString(3, String.valueOf(Status.ACTIVE));
            statement.executeUpdate();

            generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                resultWriter = getById(generatedKeys.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultWriter;
    }

    @Override
    public Writer update(Writer writer) {
        Writer resultWriter = null;
        String query = "UPDATE writers SET firstname = ?, lastname = ? WHERE id = ?";

        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, writer.getFirstName());
            statement.setString(2, writer.getLastName());
            statement.setInt(3, writer.getId());

            statement.executeUpdate();

            resultWriter = getById(writer.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultWriter;
    }

    @Override
    public void deleteById(Integer id) {
        Writer writer = getById(id);
        String query = "UPDATE writers SET status = ? WHERE id = ?";

        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, String.valueOf(Status.DELETED));
            statement.setInt(2, writer.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
