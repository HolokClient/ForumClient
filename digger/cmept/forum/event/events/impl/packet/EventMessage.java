package digger.cmept.forum.event.events.impl.packet;

import digger.cmept.forum.event.events.Event;
import digger.cmept.forum.event.events.callables.EventCancellable;

public class EventMessage extends EventCancellable implements Event {

    public String message;

    public EventMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
