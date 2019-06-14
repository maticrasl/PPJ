package DAO;

import com.company.Artikel;
import database.DBHelper;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MySqlArticle implements ArticleDao {

    @Override
    public Artikel getByEAN(String code) {
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM article WHERE EAN = ?;")) {
            ps.setString(1, code);

            ResultSet rs = ps.executeQuery();
            Artikel a = extractFromResultSet(rs);

            return a;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Artikel getById(UUID id) {
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM article WHERE id = ?;")) {
            byte[] ID = new byte[16];

            ByteBuffer.wrap(ID).order(ByteOrder.BIG_ENDIAN).putLong(id.getMostSignificantBits()).putLong(id.getLeastSignificantBits());

            ps.setBytes(1, ID);

            ResultSet rs = ps.executeQuery();
            rs.next();
            Artikel a = new Artikel(rs.getString("EAN"), rs.getString("ime"), rs.getFloat("cenaBrezDDV"), rs.getFloat("DDV"), rs.getFloat("zaloga"));
            a.setDrzava(rs.getString("drzava"));
            return a;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Artikel> getAll() {
        try (Connection conn = DBHelper.getConnection();
             Statement ps = conn.createStatement()) {

            Artikel a;
            List<Artikel> artikelList = new ArrayList<Artikel>();
            try (ResultSet rs = ps.executeQuery("SELECT * FROM article;")) {
                while(rs.next()) {
                    a = new Artikel(rs.getString("EAN"), rs.getString("ime"), rs.getFloat("cenaBrezDDV"), rs.getFloat("DDV"), rs.getFloat("zaloga"));
                    a.setDrzava(rs.getString("drzava"));
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
    public boolean insert(Artikel m) {
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO mydb.article(id, EAN, ime, cenaBrezDDV, cenaZDDV, DDV, drzava, zaloga, deleted, created) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, FALSE, CURRENT_TIMESTAMP()) ON DUPLICATE KEY UPDATE deleted = FALSE;" )) {
            ps.setBytes(1, m.getId());
            ps.setString(2, m.getEAN());
            ps.setString(3, m.getIme());
            ps.setFloat(4, m.getCenaBrezDDV());
            ps.setFloat(5, m.getCenaZDDV());
            ps.setFloat(6, m.getDDV());
            ps.setString(7, m.getDrzava());
            ps.setFloat(8, m.getKolicina());

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Artikel m) {
        return false;
    }

    @Override
    public boolean delete(Artikel m) {
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE article SET deleted = 1 WHERE id = ?;" )) {
            ps.setBytes(1, m.getId());

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Artikel extractFromResultSet(ResultSet rs) throws SQLException {
        rs.next();
        Artikel a = new Artikel(rs.getString("EAN"), rs.getString("ime"), rs.getFloat("cenaBrezDDV"), rs.getFloat("DDV"), rs.getFloat("zaloga"));
        a.setDrzava(rs.getString("drzava"));
        return a;
    }
}
