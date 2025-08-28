package com.yourcompany.gym.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity // <-- ДОБАВЛЕНО: Указываем, что это JPA-сущность
public class Training {

    @Id
    // Примечание: здесь нет @GeneratedValue, так как мы договорились
    // присваивать ID вручную через наш IdGenerator в DAO. Это нормально.
    private Long id;

    // --- ГЛАВНОЕ ИЗМЕНЕНИЕ: Заменяем примитивы на объектные ссылки ---
    @ManyToOne
    @JoinColumn(name = "trainee_id")
    private Trainee trainee;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @ManyToOne
    @JoinColumn(name = "training_type_id")
    private TrainingType trainingType;
    // ----------------------------------------------------------------

    private String trainingName;
    private LocalDate trainingDate;
    private int trainingDuration;

    // --- УДАЛЕНЫ СТАРЫЕ ПОЛЯ: traineeId, trainerId ---

    // Геттеры и сеттеры для ВСЕХ полей
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Trainee getTrainee() {
        return trainee;
    }

    public void setTrainee(Trainee trainee) {
        this.trainee = trainee;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public TrainingType getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(TrainingType trainingType) {
        this.trainingType = trainingType;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public LocalDate getTrainingDate() {
        return trainingDate;
    }

    public void setTrainingDate(LocalDate trainingDate) {
        this.trainingDate = trainingDate;
    }

    public int getTrainingDuration() {
        return trainingDuration;
    }

    public void setTrainingDuration(int trainingDuration) {
        this.trainingDuration = trainingDuration;
    }

    // --- ИСПРАВЛЕНО: equals() и hashCode() теперь основаны на ID ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass()!= o.getClass()) return false;
        Training training = (Training) o;
        return id!= null && id.equals(training.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // toString() можно оставить для отладки, но лучше обновить
    @Override
    public String toString() {
        return "Training{" +
                "id=" + id +
                ", traineeId=" + (trainee!= null? trainee.getId() : "null") +
                ", trainerId=" + (trainer!= null? trainer.getId() : "null") +
                ", trainingName='" + trainingName + '\'' +
                '}';
    }
}