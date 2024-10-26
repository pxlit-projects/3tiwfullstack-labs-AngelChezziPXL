package be.pxl.services.notification.service;

import be.pxl.services.notification.domain.Notification;

public interface INotificationService {
    public void sendMessage(Notification notification);
}
