package br.com.andydkeh.dao;

import br.com.andydkeh.enums.RefundStatus;
import br.com.andydkeh.models.RefundRequest;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class RefundDAO extends BaseDAO<RefundRequest>{

    public RefundDAO() {
        super(RefundRequest.class);
    }

    public List<RefundRequest> findByAllRefundPending() {
        try {
            TypedQuery<RefundRequest> query = em.createQuery(
                    "SELECT u FROM RefundRequest u WHERE u.status = :statusPending",
                    RefundRequest.class
            );
            query.setParameter("statusPending", RefundStatus.PENDING.name());
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
