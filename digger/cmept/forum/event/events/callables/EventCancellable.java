package digger.cmept.forum.event.events.callables;

import digger.cmept.forum.event.events.Cancellable;
import digger.cmept.forum.event.events.Event;

public abstract class EventCancellable implements Event, Cancellable {

    private boolean cancelled;

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean state) {
        cancelled = state;
    }

}
