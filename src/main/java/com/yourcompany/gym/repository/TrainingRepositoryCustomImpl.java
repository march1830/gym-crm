package com.yourcompany.gym.repository;

import com.yourcompany.gym.model.Training;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TrainingRepositoryCustomImpl implements TrainingRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Training> findTrainingsByTraineeUsernameAndCriteria(
            String username, LocalDate fromDate, LocalDate toDate, String trainerName, String trainingType) {

        // 1. Получаем "строителя" запросов
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Training> query = cb.createQuery(Training.class);
        Root<Training> training = query.from(Training.class);

        // 2. Создаем список для наших условий (предикатов)
        List<Predicate> predicates = new ArrayList<>();

        // 3. Добавляем обязательное условие: ищем по username стажера
        predicates.add(cb.equal(training.get("trainee").get("username"), username));

        // 4. Динамически добавляем необязательные условия
        if (fromDate != null) {
            predicates.add(cb.greaterThanOrEqualTo(training.get("trainingDate"), fromDate));
        }
        if (toDate != null) {
            predicates.add(cb.lessThanOrEqualTo(training.get("trainingDate"), toDate));
        }
        if (trainerName != null && !trainerName.isBlank()) {
            predicates.add(cb.equal(training.get("trainer").get("firstName"), trainerName));
        }
        if (trainingType != null && !trainingType.isBlank()) {
            predicates.add(cb.equal(training.get("trainingType").get("trainingTypeName"), trainingType));
        }

        // 5. Собираем все условия в одно через "И" (AND)
        query.where(cb.and(predicates.toArray(new Predicate[0])));

        // 6. Выполняем запрос
        return entityManager.createQuery(query).getResultList();
    }
    @Override
    public List<Training> findTrainingsByTrainerUsernameAndCriteria(
            String username, LocalDate fromDate, LocalDate toDate, String traineeName) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Training> query = cb.createQuery(Training.class);
        Root<Training> training = query.from(Training.class);

        List<Predicate> predicates = new ArrayList<>();

        // Изменили фильтр на trainer.username
        predicates.add(cb.equal(training.get("trainer").get("username"), username));

        if (fromDate != null) {
            predicates.add(cb.greaterThanOrEqualTo(training.get("trainingDate"), fromDate));
        }
        if (toDate != null) {
            predicates.add(cb.lessThanOrEqualTo(training.get("trainingDate"), toDate));
        }
        // Изменили фильтр на trainee.firstName
        if (traineeName != null && !traineeName.isBlank()) {
            predicates.add(cb.equal(training.get("trainee").get("firstName"), traineeName));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(query).getResultList();
    }
}