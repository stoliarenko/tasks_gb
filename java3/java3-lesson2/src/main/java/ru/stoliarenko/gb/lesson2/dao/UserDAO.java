package ru.stoliarenko.gb.lesson2.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import ru.stoliarenko.gb.lesson2.entity.User;

/**
 * Класс для получения пользователей из БД
 * 
 * @author Stoliarenko Alexander
 */

public class UserDAO {
    private static SessionFactory factory;

    /**
     * При первом вызове создает тяжеловесный объект SessionFactory при последующих
     * возвращает уже созданный.
     */
    private static SessionFactory getSessionFactory() {
        if (factory != null)
            return factory;
        return new Configuration().configure().addAnnotatedClass(User.class).buildSessionFactory();
    }

    /**
     * Записывает пользователя в БД
     * 
     * В случае недопустимости операции выводит сообщение в консоль без нарушения
     * работы
     * 
     * @param user - новый пользователь
     */
    public static void create(final User user) {
        final Session session = getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(user);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
    }

    /**
     * Удаляет пользователя
     * 
     * @param user - объект удаляемого пользователя
     * @throws exception если в параметре передан null
     */
    public static void delete(final User user) {
        final Session session = getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.remove(user);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /**
     * Удаляет пользователя
     * 
     * @param name - имя удаляемого пользователя
     */
    public static void deleteByName(final String name) {
        final Session session = getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.createQuery(String.format("delete from User where name='%s'", name)).executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /**
     * Получает пользователя по имени
     * 
     * @param name - имя пользователя
     * @return - объект пользователя
     */
    public static User getUserByName(final String name) {
        final Session session = getSessionFactory().openSession();
        final User user = session.get(User.class, name);
        session.close();
        return user;
    }

    /**
     * Получает список всех пользователей
     */
    @SuppressWarnings("unchecked")
    public static List<User> getAllUsers() {
        final Session session = getSessionFactory().openSession();
        final List<User> listOfUsers = session.createQuery("from User").getResultList();
        session.close();
        return listOfUsers;
    }

    /**
     * Обновляет данные если пользователь с таким именем существует
     * 
     * @param updatedUser - объект пользователя с обновленными данными
     * @return true - если пользователь был обновлен
     */
    public static boolean updateUser(final User updatedUser) {
        final Session session = getSessionFactory().openSession();
        Transaction tx = null;
        User user = null;
        try {
            tx = session.beginTransaction();
            user = session.get(User.class, updatedUser.getName());
            if (user == null)
                return false;
            user.setLogin(updatedUser.getLogin());
            user.setPassword(updatedUser.getPassword());
            user.setIsAdmin(updatedUser.getIsAdmin());
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
        return true;
    }
}
