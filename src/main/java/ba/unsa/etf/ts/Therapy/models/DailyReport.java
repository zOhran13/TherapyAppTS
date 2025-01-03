package ba.unsa.etf.ts.Therapy.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "daily_report")
public class DailyReport {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "daily_report_id", columnDefinition = "VARCHAR(64)")
    private String dailyReportId;

    @NotBlank(message = "Content cannot be blank")
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @NotNull(message = "Patient must be specified")
    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id") // Promijenjeno referencedColumnName sa "patient_user_id" na "id"
    private Patient patient;



    @ManyToOne
    @JoinColumn(name = "weekly_report_id", referencedColumnName = "weekly_report_id")
    private WeeklyReport weeklyReport;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDate createdAt;
    public DailyReport(String sampleContent, Patient patient, WeeklyReport weeklyReport) {
        content=sampleContent;
        this.patient=patient;
        this.weeklyReport=weeklyReport;
        this.createdAt=LocalDate.now();
    }

    public DailyReport() {
    }

    public void setContent(String content) {
        this.content=content;
    }
}

