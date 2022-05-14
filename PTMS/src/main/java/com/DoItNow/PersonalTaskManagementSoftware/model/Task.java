package com.DoItNow.PersonalTaskManagementSoftware.model;

import com.DoItNow.PersonalTaskManagementSoftware.utils.TaskCategories;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Data
@Entity
@NoArgsConstructor
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "\"end\"", nullable = false)
    private LocalDate end;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "start", nullable = false)
    private LocalDate start;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private TaskCategories category;

    @Column(name = "status")
    private String status;

    @Column(name = "user_id")
    private int userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id &&
                userId == task.userId &&
                Objects.equals(title, task.title) &&
                Objects.equals(description, task.description) &&
                Objects.equals(end, task.end) &&
                Objects.equals(start, task.start) &&
                category == task.category &&
                Objects.equals(status, task.status);
    }

    @Override
    public int hashCode() {

        return Objects.hash(category, description, end, start, status, title, userId, id);
    }
}
