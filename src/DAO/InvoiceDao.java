package DAO;

import com.company.Racun;

public interface InvoiceDao extends DaoCrud<Racun> {
    Racun getById(String code);
}
