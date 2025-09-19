package br.com.andydkeh.dao;

import br.com.andydkeh.enums.RoleType;
import br.com.andydkeh.enums.UserStatus;
import br.com.andydkeh.models.Users;
import jakarta.persistence.*;
import jakarta.persistence.TypedQuery;

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

    public Users findByEmail(String email) {
        try {
            TypedQuery<Users> query = em.createQuery(
                    "SELECT u FROM Users u WHERE u.email = :email",
                    Users.class
            );
            query.setParameter("email", email);
            return query.getSingleResult();
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

    public List<Users> findAllUsersBlock() {
        TypedQuery<Users> query = em.createQuery(
                "SELECT u FROM Users u WHERE u.status = :userBlockValue",
                Users.class
        );
        query.setParameter("userBlockValue", UserStatus.BLOCKED.name());
        return query.getResultList();
    }
}
