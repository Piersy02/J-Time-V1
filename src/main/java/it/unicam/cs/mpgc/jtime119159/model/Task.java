package it.unicam.cs.mpgc.jtime119159.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(name = "estimated_minutes")
    private Long estimatedMinutes;

    @Column(name = "actual_minutes")
    private Long actualMinutes;

    @Column(name = "scheduled_date")
    private LocalDate scheduledDate;

    @Column(nullable = false)
    private boolean completed;

    public Task() {
        this.completed = false;
    }

    public Task(String name, String description, Long estimatedMinutes) {
        this.name = name;
        this.description = description;
        this.estimatedMinutes = estimatedMinutes;
        this.completed = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
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

    public Long getEstimatedMinutes() {
        return estimatedMinutes;
    }

    public void setEstimatedMinutes(Long estimatedMinutes) {
        this.estimatedMinutes = estimatedMinutes;
    }

    public Long getActualMinutes() {
        return actualMinutes;
    }

    public void setActualMinutes(Long actualMinutes) {
        this.actualMinutes = actualMinutes;
    }

    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDate scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
