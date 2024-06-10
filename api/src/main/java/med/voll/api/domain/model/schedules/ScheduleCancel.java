package med.voll.api.domain.model.schedules;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "cancelamento_consultas")
@EqualsAndHashCode(of = "id")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleCancel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "consulta_id")
    private Schedule schedule;

    @Setter
    @Column(name = "motivo")
    private String cancelMotive;

    @Setter
    @Column(name = "data_hora")
    private LocalDateTime dateTime;

    public ScheduleCancel(Schedule schedule, String cancelMotive, LocalDateTime dateTime) {
        this.schedule = schedule;
        this.cancelMotive = cancelMotive;
        this.dateTime = dateTime;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
        if (schedule != null && schedule.getScheduleCancel() != this) {
            schedule.setScheduleCancel(this);
        }
    }

}
