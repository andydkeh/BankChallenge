package br.com.compass.dao;

import br.com.compass.enums.RoleType;
import br.com.compass.models.Users;
import jakarta.persistence.*;
import jakarta.persistence.TypedQuery;

import java.util.Date;
import java.util.List;

public class UserDAO extends BaseDAO<Users> {
    public UserDAO() {
        super(Users.class);
    }

    public static Users findAdministrator() {
        try {
            return em.createQuery(
                            "SELECT u FROM Users u WHERE u.role = :role", Users.class)
                    .setParameter("role", RoleType.ADMINISTRATOR.name())
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Users> findByEmail(String email) {
        try {
            TypedQuery<Users> query = em.createQuery(
                    "SELECT u FROM Users u WHERE u.email = :email",
                    Users.class
            );
            query.setParameter("email", email);
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Users findByCpf(String cpf) {
        try {
            TypedQuery<Users> query = em.createQuery(
                    "SELECT u FROM Users u WHERE u.cpf = :cpf",
                    Users.class
            );
            query.setParameter("cpf", cpf);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Users> findByStatus(String status) {
        TypedQuery<Users> query = em.createQuery(
                "SELECT u FROM Users u WHERE u.status = :status",
                Users.class
        );
        query.setParameter("status", status);
        return query.getResultList();
    }
}
