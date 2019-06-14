package DAO;

import com.company.Podjetje;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface CompanyDao extends DaoCrud<Podjetje> {
    Podjetje getByIme(String ime);
}
