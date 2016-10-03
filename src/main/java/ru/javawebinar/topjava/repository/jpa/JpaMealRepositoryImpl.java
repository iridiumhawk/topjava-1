package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User: gkisline
 * Date: 26.08.2014
 */

@Repository
@Transactional(readOnly = true)
public class JpaMealRepositoryImpl implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User ref = em.getReference(User.class, userId);
        if (meal.isNew()) {
            meal.setUser(ref);
            em.persist(meal);
            return meal;
        } else if (meal.getUser() == null)  //&&  (meal.getUser().getId().equals(ref.getId())))
        {
            meal.setUser(ref);
            return em.merge(meal);
        } else if (em.contains(meal)) {
            return em.merge(meal);
        }

        return null;

    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery("meal.del").setParameter("userId", userId).setParameter("id", id).executeUpdate() != 0;

    }

    @Override
    public Meal get(int id, int userId) {
//todo result must be Null if NoResultException
        return em.createNamedQuery("meal.get", Meal.class).setParameter("userId", userId).setParameter("id", id).getSingleResult();
    }

    @Override
    public List<Meal> getAll(int userId) {

        return em.createNamedQuery("meal.getAll", Meal.class).setParameter("userId", userId).getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createNamedQuery("meal.getBetween", Meal.class).setParameter("userId", userId).setParameter("startDate", startDate).setParameter("endDate", endDate).getResultList();
    }
}