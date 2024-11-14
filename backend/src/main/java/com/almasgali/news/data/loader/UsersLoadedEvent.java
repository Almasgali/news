package com.almasgali.news.data.loader;

import org.springframework.context.ApplicationEvent;

public class UsersLoadedEvent extends ApplicationEvent {
    public UsersLoadedEvent(Object source) {
        super(source);
    }
}
