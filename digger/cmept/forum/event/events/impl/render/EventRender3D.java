package digger.cmept.forum.event.events.impl.render;

import digger.cmept.forum.event.events.Event;

public class EventRender3D implements Event {
    private final float partialTicks;

    public EventRender3D(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }
}
