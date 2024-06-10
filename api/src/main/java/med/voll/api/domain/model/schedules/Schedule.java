package med.voll.api.domain.model.schedules;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.domain.model.medic.Medic;
import med.voll.api.domain.model.patient.Patient;

import java.time.LocalDateTime;

@Entity
@Table(name = "consultas")
@EqualsAndHashCode(of = "id")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "medico_id")
    private Medic medic;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "paciente_id")
    private Patient patient;

    @Column(name = "data_hora")
    private LocalDateTime scheduleDateTime;

    @Setter
    @Transient
    @OneToOne(mappedBy = "schedule", cascade = CascadeType.MERGE)
    private ScheduleCancel scheduleCancel;

    @Setter
    @Column(name = "cancelada")
    private boolean isCanceled;

    public Schedule(LocalDateTime dateTime, Medic medicToSave, Patient patientToSave) {
        this.scheduleDateTime = dateTime;
        this.patient = patientToSave;
        this.medic = medicToSave;
        this.scheduleCancel = null;
        this.isCanceled = false;
    }

    public void cancel(String cancelMotive, LocalDateTime dateTimeNow) {
        this.scheduleCancel = new ScheduleCancel(this, cancelMotive, dateTimeNow);
        this.scheduleCancel.setSchedule(this);
        this.isCanceled = true;
    }

}
