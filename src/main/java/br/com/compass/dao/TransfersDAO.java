package br.com.compass.dao;

import br.com.compass.models.Transaction;
import br.com.compass.models.Transfers;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class TransfersDAO extends BaseDAO<Transfers> {
    public TransfersDAO() {
        super(Transfers.class);
    }

    public Transfers findByTransfersID(Long transfersId) {
        try {
            TypedQuery<Transfers> query = em.createQuery(
                    "SELECT u FROM Transaction u WHERE u.transfersId = :transfersId",
                    Transfers.class
            );
            query.setParameter("transfersId", transfersId);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
