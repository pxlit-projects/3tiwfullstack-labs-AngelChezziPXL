package be.pxl.services.notification.service;

import be.pxl.services.notification.domain.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class NotificationService implements INotificationService {

    private Logger log = LoggerFactory.getLogger(NotificationService.class);
    /**
     * Fe using a Mail API to send the notification would be applicable
     *
     * @param notification
     */
    @Override
    public void sendMessage(Notification notification) {
        log.info("Receiving notification...");
        log.info("Sending... {}", notification.getMessage());
        log.info("To {}", notification.getTo());
    }
}
