package com.ssafy.forestkeeper.util.mattermost;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationManager {

    private Logger log = LoggerFactory.getLogger(NotificationManager.class);

    @Autowired
    private MattermostSender mmSender;

    public void sendNotification(Exception e, String message, String uri, String params) {

        log.info("### SEND Notification");

        mmSender.sendMessage(e, message, uri, params);

    }

}
