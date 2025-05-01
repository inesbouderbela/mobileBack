package mobile.gestionSpectacle.Dto;



import java.time.LocalDate;

public class DateCountDTO {
    private String date;
    private Long eventCount;


    public DateCountDTO(String date, Long eventCount) {
        this.date = date;
        this.eventCount = eventCount;
    }


    public DateCountDTO(LocalDate date, Long eventCount) {
        this.date = date.toString();
        this.eventCount = eventCount;
    }

    // Getters
    public String getDate() { return date; }
    public Long getEventCount() { return eventCount; }
}