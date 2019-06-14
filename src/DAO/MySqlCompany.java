package DAO;

import com.company.Podjetje;
import database.DBHelper;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MySqlCompany implements CompanyDao {
    @Override
    public Podjetje getByIme(String ime) {
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM company WHERE ime = ?;")) {
            ps.setString(1, ime);

            ResultSet rs = ps.executeQuery();
            Podjetje a = extractFromResultSet(rs);

            return a;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Podjetje getById(UUID id) {
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM company WHERE id = ?;")) {
            byte[] ID = new byte[16];

            ByteBuffer.wrap(ID).order(ByteOrder.BIG_ENDIAN).putLong(id.getMostSignificantBits()).putLong(id.getLeastSignificantBits());

            ps.setBytes(1, ID);

            ResultSet rs = ps.executeQuery();
            Podjetje a = extractFromResultSet(rs);

            return a;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Podjetje> getAll() {
        try (Connection conn = DBHelper.getConnection();
             Statement ps = conn.createStatement()) {

            Podjetje a;
            List<Podjetje> podjetjeList = new ArrayList<Podjetje>();
            try (ResultSet rs = ps.executeQuery("SELECT * FROM article;")) {
                while(rs.next()) {
                    a = extractFromResultSet(rs);
                    podjetjeList.add(a);
                }
            }
            return podjetjeList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insert(Podjetje m) {
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO mydb.company(id, ime, telSt, email, davcnaSt, maticnaSt, davcniZavezanec, deleted, created) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?, FALSE, CURRENT_TIMESTAMP()) ON DUPLICATE KEY UPDATE deleted = FALSE;" )) {
            ps.setBytes(1, m.getId());
            ps.setString(2, m.getIme());
            ps.setString(3, m.getTelSt());
            ps.setString(4, m.getEmail());
            ps.setInt(5, m.getDavcnaSt());
            ps.setLong(6, m.getMaticnaSt());
            ps.setInt(7, m.isDavcniZavezanec() ? 1 : 0);

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Podjetje m) {
        return false;
    }

    @Override
    public boolean delete(Podjetje m) {
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE company SET deleted = 1 WHERE id = ?;" )) {
            ps.setBytes(1, m.getId());

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Podjetje extractFromResultSet(ResultSet rs) throws SQLException {
        rs.next();
        int dz = rs.getInt("davcniZavezanec");
        boolean DZ = false;
        if(dz % 2 == 1)
            DZ = true;

        Podjetje a = new Podjetje(rs.getString("ime"), rs.getString("telSt"), rs.getString("email"), rs.getInt("davcnaSt"), rs.getLong("maticnaSt"), DZ);
        return a;
    }
}
