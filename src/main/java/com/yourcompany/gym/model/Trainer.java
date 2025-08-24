package com.yourcompany.gym.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Trainer extends User { // <-- ГЛАВНОЕ ИЗМЕНЕНИЕ: наследуем User

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Уникальное поле для Trainer, которое является ссылкой на TrainingType
    @ManyToOne
    @JoinColumn(name = "specialization_id")
    private TrainingType specialization;

    // Пустой конструктор для JPA
    public Trainer() {
    }

    // Геттеры и сеттеры для id и specialization
    // Геттеры и сеттеры для firstName, lastName и т.д. будут унаследованы от User


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TrainingType getSpecialization() {
        return specialization;
    }

    public void setSpecialization(TrainingType specialization) {
        this.specialization = specialization;
    }

    // Правильная реализация equals() и hashCode() для JPA-сущности
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass()!= o.getClass()) return false;
        Trainer trainer = (Trainer) o;
        return id!= null && id.equals(trainer.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
