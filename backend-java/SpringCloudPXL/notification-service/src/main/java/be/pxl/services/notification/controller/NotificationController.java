package be.pxl.services.notification.controller;

import be.pxl.services.notification.domain.Notification;
import be.pxl.services.notification.service.INotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/notification")
public class NotificationController {
    @Autowired
    private final INotificationService notificationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    //TODO: QUESTION: Moet dit geen NotificationRequest zijn?
    public  void sendMessage(@RequestBody Notification notification) {
        notificationService.sendMessage(notification);
    }

}
