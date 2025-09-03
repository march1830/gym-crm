package com.yourcompany.gym.model;

import jakarta.persistence.*; // <-- Добавлены импорты для JPA
import java.util.List;
import java.util.Objects;

@Entity // <-- 1. Указываем, что это сущность для базы данных
@Table(name = "training_types") // <-- Указываем имя таблицы
public class TrainingType {

    @Id // <-- 2. Добавляем поле ID как первичный ключ
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "training_type_name", nullable = false, unique = true)
    private String trainingTypeName;

    // 3. Добавляем обратную связь к Training
    // `mappedBy` - это прямое исправление твоей последней ошибки
    @OneToMany(mappedBy = "trainingType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Training> trainings;

    // Геттеры и сеттеры для новых полей
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Training> getTrainings() {
        return trainings;
    }

    public void setTrainings(List<Training> trainings) {
        this.trainings = trainings;
    }

    // --- Твои существующие методы ---
    public String getTrainingTypeName() {
        return trainingTypeName;
    }

    public void setTrainingTypeName(String trainingTypeName) {
        this.trainingTypeName = trainingTypeName;
    }

    @Override
    public String toString() {
        return "TrainingType{" +
                "id=" + id +
                ", trainingTypeName='" + trainingTypeName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainingType that = (TrainingType) o;
        // Для справочника, где имя уникально, такой equals - хороший вариант
        return Objects.equals(trainingTypeName, that.trainingTypeName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(trainingTypeName);
    }
}