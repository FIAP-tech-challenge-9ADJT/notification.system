package tech.challenge.notification.system.infrastructure.persistence.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
@Entity
@Table(name = "feedbacks")
public class FeedbackJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 200)
    @Size(max = 200)
    private String description;
    @Column(nullable = false)
    @Min(0)
    @Max(10)
    private Integer score;

    public FeedbackJpaEntity() {}

    public FeedbackJpaEntity(Long id, String description, Integer score) {
        this.id = id;
        this.description = description;
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

}
