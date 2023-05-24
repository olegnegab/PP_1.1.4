package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String sql = ("create table if not exists users" +
                    "(" +
                    "    name     varchar(45) null," +
                    "    lastName varchar(45) null," +
                    "    age      int          null," +
                    "    id       int auto_increment" +
                    "        primary key" +
                    ");");
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("createUsersTable");
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String dropTable = "DROP TABLE users";
            Query query = session.createSQLQuery(dropTable).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("dropUsersTable");
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        User user = new User(name, lastName, age);

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();


        } catch (Exception e) {
            System.out.println("saveUser");
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            System.out.println("removeUserById");
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Session session = Util.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery criteriaQuery = criteriaBuilder.createQuery();
            Root<User> userRoot = criteriaQuery.from(User.class);
            criteriaQuery.select(userRoot);


            Query query = session.createQuery(criteriaQuery);
            userList = query.getResultList();

        } catch (Exception e) {
            System.out.println("getAllUsers");

        }

        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String clean = "TRUNCATE TABLE users";
            Query query = session.createSQLQuery(clean);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("cleanUsersTable");
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }
}
