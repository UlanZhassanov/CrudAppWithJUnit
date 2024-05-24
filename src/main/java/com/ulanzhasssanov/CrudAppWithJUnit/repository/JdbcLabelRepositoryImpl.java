package com.ulanzhasssanov.CrudAppWithJUnit.repository;

import com.ulanzhasssanov.CrudAppWithJUnit.enums.PostStatus;
import com.ulanzhasssanov.CrudAppWithJUnit.enums.Status;
import com.ulanzhasssanov.CrudAppWithJUnit.model.Label;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcLabelRepositoryImpl implements LabelRepository {

    @Override
    public Label getById(Integer id) {
        Label label = null;
        String query = "SELECT * FROM labels WHERE id = ? AND status = 'ACTIVE'";

        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    label = new Label();
                    label.setId(resultSet.getInt("id"));
                    label.setName(resultSet.getString("name"));
                    label.setStatus(Status.ACTIVE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return label;
    }


    @Override
    public List<Label> getAll() {
        List<Label> labels = new ArrayList<>();
        String query = "SELECT * FROM labels WHERE status = 'ACTIVE'";

        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()){
                Label label = new Label();
                label.setId(resultSet.getInt("id"));
                label.setName(resultSet.getString("name"));
                label.setStatus(Status.ACTIVE);

                labels.add(label);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return labels;
    }

    @Override
    public Label save(Label label) {
        Label resultLabel = null;
        String query = "INSERT INTO labels (name, status) VALUES (?, ?)";
        ResultSet generatedKeys;

        try (Connection connection = JdbcConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, label.getName());
            statement.setString(2, String.valueOf(PostStatus.ACTIVE));

            statement.executeUpdate();

            generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()){
                resultLabel = getById(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultLabel;
    }

    @Override
    public Label update(Label label) {
        Label resultLabel = null;
        String query = "UPDATE labels SET name = ? WHERE id = ?";

        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, label.getName());
            statement.setInt(2, label.getId());

            statement.executeUpdate();
            resultLabel = getById(label.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultLabel;
    }

    @Override
    public void deleteById(Integer id) {
        Label label = getById(id);
        String query = "UPDATE labels SET status = ? WHERE id = ?";

        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, String.valueOf(PostStatus.DELETED));
            statement.setInt(2, label.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
