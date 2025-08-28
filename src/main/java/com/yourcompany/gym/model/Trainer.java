package com.yourcompany.gym.model;

import javax.persistence.*;

@Entity // <-- ДОБАВЛЕНО: Указываем, что это JPA-сущность
public class Trainer extends User {

    @Id // <-- ДОБАВЛЕНО: Указываем, что это поле - первичный ключ
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // <-- ИСПРАВЛЕНО: Заменили String на объектную ссылку TrainingType
    @ManyToOne
    @JoinColumn(name = "specialization_id")
    private TrainingType specialization;

    // Пустой конструктор для JPA
    public Trainer() {
    }

    // Геттеры и сеттеры для id и specialization
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

    // <-- ДОБАВЛЕНО: Правильная реализация equals() и hashCode()
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
