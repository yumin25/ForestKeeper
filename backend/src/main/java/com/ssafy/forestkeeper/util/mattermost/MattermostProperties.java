package com.ssafy.forestkeeper.util.mattermost;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Getter
@Setter
@ConfigurationProperties("notification.mattermost")
@Primary
public class MattermostProperties {

    private String channel;

    private String pretext;

    private String color = "#ff5d52";

    private String authorName = "Server Notification";

    private String authorIcon = "https://i.ibb.co/kG8x5sv/premium-icon-notification-bell-4519342.png";

    private String title = "title";

    private String text = "";

    private String footer = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

}
