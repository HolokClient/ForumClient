package digger.cmept.forum.event.events.impl.render;

import digger.cmept.forum.event.events.Event;

public class EventBossBar implements Event {
    public String bossName;

    public EventBossBar(String bossName) {
        this.bossName = bossName;
    }

}