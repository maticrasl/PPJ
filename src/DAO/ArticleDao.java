package DAO;

import com.company.Artikel;

public interface ArticleDao extends DaoCrud<Artikel> {
    Artikel getByEAN(String code);
}
