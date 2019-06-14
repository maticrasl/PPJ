package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface DaoCrud<T> {
    abstract T getById(UUID id);
    abstract List<T> getAll();
    abstract boolean insert(T m);
    abstract boolean update(T m);
    abstract boolean delete(T m);
    abstract T extractFromResultSet(ResultSet rs) throws SQLException;
}
