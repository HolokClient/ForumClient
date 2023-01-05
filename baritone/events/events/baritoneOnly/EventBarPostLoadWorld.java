/*
 * Decompiled with CFR 0.150.
 */
package baritone.events.events.baritoneOnly;

import net.minecraft.client.multiplayer.WorldClient;
import digger.cmept.forum.event.events.Event;
import digger.cmept.forum.event.events.callables.EventCancellable;
public class EventBarPostLoadWorld
extends EventCancellable
implements Event {
    public WorldClient world;

    public EventBarPostLoadWorld(WorldClient worldIn) {
        this.world = worldIn;
    }
}

