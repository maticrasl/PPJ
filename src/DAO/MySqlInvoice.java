package DAO;

import com.company.Racun;
import database.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class MySqlInvoice implements InvoiceDao {
    @Override
    public Racun getById(String code) {
        return null;
    }

    @Override
    public Racun getById(UUID id) {
        return null;
    }

    @Override
    public List<Racun> getAll() {
        return null;
    }

    @Override
    public boolean insert(Racun m) {
        MySqlArticle MA = new MySqlArticle();
        for(int i = 0; i < m.getMojiArtikli().getSeznam().size(); i++) {
            MA.insert(m.getMojiArtikli().get(i));
        }
        MySqlCoupon MC = new MySqlCoupon();
        for(int i = 0; i < m.getMojiKuponi().size(); i++) {
            MC.insert(m.getMojiKuponi().get(i));
        }
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO mydb.invoice(id, cenaBrezDDV, cenaZDDV, popust, prodajalec, originalRacun, podjetje_prodajalec, podjetje_kupec, podjetjeDavcniZavezanec, deleted, created) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, FALSE, CURRENT_TIMESTAMP()) ON DUPLICATE KEY UPDATE deleted = FALSE;" )) {
            ps.setBytes(1, m.getId());
            ps.setDouble(2, m.getCenaBrezDDV());
            ps.setDouble(3, m.getCenaZDDV());
            ps.setDouble(4, m.getPopust());
            ps.setString(5, m.getProdajalec());
            ps.setBoolean(6, m.isOriginalRacun());
            ps.setBytes(7, m.getPodjetjeProdajalec().getId());
            if(m.getPodjetjeKupec() != null) {
                ps.setBytes(8, m.getPodjetjeKupec().getId());
                if(m.getPodjetjeKupec().isDavcniZavezanec())
                    ps.setBoolean(9, true);
                else
                    ps.setBoolean(9, false);
            }
            else {
                ps.setBytes(8, null);
                ps.setBoolean(9, false);
            }
            ps.executeUpdate();

            if(m.getMojiArtikli().count() > 0) {
                PreparedStatement ps1 = conn.prepareStatement("INSERT INTO mydb.article_invoice(id, Article_idArticle, Invoice_id, kolicina) " +
                        "VALUES (UUID_TO_BIN(UUID()), ?, ?, ?);");
                for (int i = 0; i < m.getMojiArtikli().count(); i++) {
                    ps1.setBytes(1, m.getMojiArtikli().getId(i));
                    ps1.setBytes(2, m.getId());
                    ps1.setDouble(3, m.getMojiArtikli().getKolicina(i));
                }
                ps1.executeUpdate();
            }
/*
            PreparedStatement ps2 = conn.prepareStatement("INSERT INTO mydb.coupon_invoice VALUES (UUID_TO_BIN(UUID()), ?, ?);");
            for(int i = 0; i < m.getMojiKuponi().size(); i++) {
                ps2.setBytes(1, m.getMojiKuponi().get(i).get)
                ps2.setBytes(2, m.getId());
            }
            ps2.executeUpdate();
*/
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Racun m) {
        return false;
    }

    @Override
    public boolean delete(Racun m) {
        return false;
    }

    @Override
    public Racun extractFromResultSet(ResultSet rs) throws SQLException {
        return null;
    }
}
