package com.yourcompany.gym.model;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // 1. Помечаем класс как JPA-сущность
public class TrainingType {

    @Id // Указываем, что это поле - уникальный идентификатор
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID будет генерироваться автоматически
    private Long id;

    private String trainingTypeName;

    // Пустой конструктор обязателен для JPA
    public TrainingType() {
    }

    //Геттеры и сеттеры

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrainingTypeName() {
        return trainingTypeName;
    }

    public void setTrainingTypeName(String trainingTypeName) {
        this.trainingTypeName = trainingTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass()!= o.getClass()) return false;
        TrainingType that = (TrainingType) o;
        return id!= null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
