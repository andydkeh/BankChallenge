package br.com.compass.dao;

import br.com.compass.models.Transaction;
import br.com.compass.models.Transfers;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class TransfersDAO extends BaseDAO<Transfers> {
    public TransfersDAO() {
        super(Transfers.class);
    }

    public Transfers findByTransfersID(Long transfersId) {
        try {
            TypedQuery<Transfers> query = em.createQuery(
                    "SELECT u FROM Transfers u WHERE u.id = :transfersId",
                    Transfers.class
            );
            query.setParameter("transfersId", transfersId);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
