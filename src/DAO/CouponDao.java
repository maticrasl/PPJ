package DAO;

import com.company.Kupon;

public interface CouponDao extends DaoCrud<Kupon> {
    Kupon getByEAN(String code);
}
