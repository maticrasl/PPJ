package DAO;

import com.company.Kupon;
import database.DBHelper;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MySqlCoupon implements CouponDao {
    @Override
    public Kupon getByEAN(String code) {
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM coupon WHERE EAN = ?;")) {
            ps.setString(1, code);

            ResultSet rs = ps.executeQuery();
            Kupon a = extractFromResultSet(rs);

            return a;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Kupon getById(UUID id) {
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM coupon WHERE id = ?;")) {
            byte[] ID = new byte[16];

            ByteBuffer.wrap(ID).order(ByteOrder.BIG_ENDIAN).putLong(id.getMostSignificantBits()).putLong(id.getLeastSignificantBits());

            ps.setBytes(1, ID);

            ResultSet rs = ps.executeQuery();
            Kupon a = extractFromResultSet(rs);

            return a;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Kupon> getAll() {
        try (Connection conn = DBHelper.getConnection();
            Statement ps = conn.createStatement()) {

            Kupon a;
            List<Kupon> artikelList = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery("SELECT * FROM coupon;")) {
                while(rs.next()) {
                    a = extractFromResultSet(rs);
                    artikelList.add(a);
                }
            }
            return artikelList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insert(Kupon m) {
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO mydb.coupon(id, tip, popust, EANIzdelka, EAN, opis, deleted, created) VALUES " +
                     "(UUID_TO_BIN(UUID()), ?, ?, ?, ?, '', FALSE, CURRENT_TIMESTAMP()) ON DUPLICATE KEY UPDATE deleted = FALSE;" )) {
            ps.setInt(1, m.getTip());
            ps.setDouble(2, m.getPopust());
            ps.setString(3, m.getEANIzdelka());
            ps.setString(4, m.getEAN());

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Kupon m) {
        return false;
    }

    @Override
    public boolean delete(Kupon m) {
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE coupon SET deleted = 1 WHERE EAN = ?;" )) {
            ps.setString(1, m.getEAN());

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Kupon extractFromResultSet(ResultSet rs) throws SQLException {
        rs.next();
        Kupon a = new Kupon(rs.getInt("tip"), rs.getString("EANIzdelka"));
        a.setEAN(rs.getString("EAN"));
        a.setPopust(rs.getDouble("popust"));
        return a;
    }
}
