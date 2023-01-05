package digger.cmept.forum.utils.otherutils.gayutil;

import digger.cmept.forum.event.events.Cancellable;
import digger.cmept.forum.event.events.Event;

public abstract class EventCancellable
        implements Event, Cancellable
{
    private boolean cancelled;

    public boolean isCancelled() {
        /* 12 */     return this.cancelled;
    }


    public void setCancelled(boolean state) {
        /* 17 */     this.cancelled = state;
    }

    public void cancel() {
        /* 21 */     this.cancelled = true;
    }
}