package tech.challenge.notification.system.infrastructure.persistence.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "lessons")
public class LessonJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(length = 200)
    @Size(max = 200)
    private String description;
    @Column(columnDefinition = "DECIMAL(2,1)")
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "10.0")
    private Double score;
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "lesson_id")
    private Set<FeedbackJpaEntity> feedbacks = new HashSet<>();

    public LessonJpaEntity() {}

    public LessonJpaEntity(Long id, String name, String description, Double score, Set<FeedbackJpaEntity> feedbacks) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.score = score;
        this.feedbacks = feedbacks == null ? new HashSet<>() : feedbacks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Set<FeedbackJpaEntity> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(Set<FeedbackJpaEntity> feedbacks) {
        this.feedbacks = feedbacks == null ? new HashSet<>() : feedbacks;
    }
}