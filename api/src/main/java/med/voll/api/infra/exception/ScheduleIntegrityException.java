package med.voll.api.infra.exception;

public class ScheduleIntegrityException extends RuntimeException {
    public ScheduleIntegrityException(String message) {
        super(message);
    }
}