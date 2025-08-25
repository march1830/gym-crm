package com.yourcompany.gym.model;

import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Training {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trainee_id")
    private Trainee trainee;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @ManyToOne
    @JoinColumn(name = "training_type_id")
    private TrainingType trainingType;


    private String trainingName;
    private LocalDate trainingDate;
    private int trainingDuration;


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

    @Override
    public String toString() {
        return "Training{" +
                "id=" + id +
                ", trainee=" + (trainee != null ? trainee.getId() : "null") +
                ", trainer=" + (trainer != null ? trainer.getId() : "null") +
                ", trainingName='" + trainingName + '\'' +
                ", trainingType=" + (trainingType != null ? trainingType.getTrainingTypeName() : "null") +
                ", trainingDate=" + trainingDate +
                ", trainingDuration=" + trainingDuration +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        // 1. Проверка на равенство ссылок
        if (this == o) return true;

        // 2. Проверка, что o не null и что классы совпадают
        // Использование getClass() вместо instanceof
        if (o == null || getClass() != o.getClass()) return false;

        // 3. Приводим тип, чтобы получить доступ к полям
        Training training = (Training) o;

        // 4. Сравниваем только по ID, и только если ID не null.
        // Два новых, еще не сохраненных объекта не могут быть равны друг другу.
        return id != null && id.equals(training.id);
    }

    @Override
    public int hashCode() {
        // 5. Возвращаем константу.
        // Это гарантирует, что хэш-код не изменится в течение жизни объекта.
        return getClass().hashCode();
    }
}