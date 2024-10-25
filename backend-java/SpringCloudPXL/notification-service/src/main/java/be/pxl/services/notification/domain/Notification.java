package be.pxl.services.notification.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//TODO: QUESTION: Deze service heeft geen DB. Waarvoor dient de ID en waar wordt de data opgeslagen?
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    private Long id;
    private String from;
    private String to;
    private String subject;
    private String message;

}
