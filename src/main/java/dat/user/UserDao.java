package dat.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class UserDao {

    private static UserDao instance;
    private static EntityManagerFactory emf;

    public static UserDao getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserDao();
        }
        return instance;
    }

    public User getVerifiedUser(String username, String password)  {

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            User user = em.find(User.class, username);

            if (user == null || !user.verifyPassword(password)) {
               throw new Exception("Invalid user name or password");
            }
            em.getTransaction().commit();
            return user;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public User registerUser(String username, String password)  {

        try (EntityManager em = emf.createEntityManager()) {

            User existingUser = em.find(User.class, username);

            if (existingUser != null) {
                throw new Exception("User already exists");
            }

            User user = new User(username, password);
            em.getTransaction().begin();

            em.persist(user);
            em.getTransaction().commit();
            return user;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public User getUser(String username)  {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            User user = em.find(User.class, username);
            em.getTransaction().commit();
            return user;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void readAll(){
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("SELECT u FROM User u", User.class).getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void create(User user) {
        throw new UnsupportedOperationException("Use register.");
    }

    public User update(String userName, User user){
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            User userToUpdate = em.find(User.class, userName);
            userToUpdate.setUsername(user.getUsername());
            userToUpdate.setUserPassword(user.getPassword());
            em.getTransaction().commit();
            return userToUpdate;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void delete(String userName) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            User user = em.find(User.class, userName);
            em.remove(user);
            em.getTransaction().commit();
        }
    }


    public boolean validatePrimaryKey(String userName) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            User user = em.find(User.class, userName);
            em.getTransaction().commit();
            return user != null;
        }
    }

}
