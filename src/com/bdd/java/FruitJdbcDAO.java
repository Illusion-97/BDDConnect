package com.bdd.java;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FruitJdbcDAO implements CrudDao<Long,Fruit>, AutoCloseable{

    private final ConnectionManager cm;

    public FruitJdbcDAO() {
        cm = ConnectionManager.getInstance();
    }

    @Override
    public boolean create(Fruit object) {
        String querySql = "INSERT INTO fruit(name,dlc) VALUES (?,?)";
        try (PreparedStatement preparedStatement = cm.getConnexion().prepareStatement(querySql,Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, object.getName());
            preparedStatement.setDate(2, Date.valueOf(object.getDlc()));
            preparedStatement.execute();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                object.setId(rs.getInt(1));
            }
            cm.getConnexion().commit();
            return true;
        } catch (SQLException e) {
            try {
                cm.getConnexion().rollback();
            } catch (SQLException r) {
                r.printStackTrace();
            }
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Fruit findById(Long aLong) {
        Fruit aFruit = null;
        String querySql = "SELECT * FROM fruit WHERE id = ?";
        try (PreparedStatement preparedStatement = cm.getConnexion().prepareStatement(querySql)) {
            preparedStatement.setLong(1, aLong);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            aFruit = mapToFruit(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aFruit;
    }

    @Override
    public List<Fruit> findAll() {
        try (Statement st = cm.getConnexion().createStatement()) {
            return updateList(st.executeQuery("SELECT * FROM fruit"));

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean update(Fruit object) {
        String querySql = "UPDATE fruit SET name = ?, dlc = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = cm.getConnexion().prepareStatement(querySql)) {
            preparedStatement.setString(1, object.getName());
            preparedStatement.setDate(2, Date.valueOf(object.getDlc()));
            preparedStatement.setLong(3, object.getId());
            preparedStatement.executeUpdate();
            cm.getConnexion().commit();
            return true;
        } catch (SQLException e) {
            try {
                cm.getConnexion().rollback();
            } catch (SQLException r) {
                r.printStackTrace();
            }
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Long aLong) {
        String querySql = "DELETE FROM fruit WHERE id = ?";
        try (PreparedStatement preparedStatement = cm.getConnexion().prepareStatement(querySql)) {
            preparedStatement.setLong(1, aLong);
            preparedStatement.executeUpdate();
            cm.getConnexion().commit();
            return true;
        } catch (SQLException e) {
            try {
                cm.getConnexion().rollback();
            } catch (SQLException r) {
                r.printStackTrace();
            }
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void close() throws SQLException {
        cm.close();
    }

    private List<Fruit> updateList(ResultSet rs) throws SQLException {
        List<Fruit> all = new ArrayList<>();
        while (rs.next()) {
            all.add(mapToFruit(rs));
        }
        return all;
    }

    private Fruit mapToFruit(ResultSet rs) throws SQLException {
       return new Fruit(rs.getLong("id"),rs.getString("name"),rs.getDate("dlc"));

    }
}
