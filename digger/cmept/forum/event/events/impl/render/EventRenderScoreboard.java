package digger.cmept.forum.event.events.impl.render;

import digger.cmept.forum.event.events.Event;
import digger.cmept.forum.event.types.EventType;

public class EventRenderScoreboard implements Event {

    private EventType state;

    public EventRenderScoreboard(EventType state) {
        this.state = state;
    }

    public EventType getState() {
        return this.state;
    }

    public void setState(EventType state) {
        this.state = state;
    }

    public boolean isPre() {
        return this.state == EventType.PRE;
    }
}
