/*
 * Decompiled with CFR 0.150.
 */
package baritone.events.events.network;

import net.minecraft.network.Packet;
import digger.cmept.forum.event.events.Event;
import digger.cmept.forum.event.events.callables.EventCancellable;

public class EventReceivePacket
extends EventCancellable
implements Event {
    private Packet packet;

    public EventReceivePacket(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return this.packet;
    }
}

