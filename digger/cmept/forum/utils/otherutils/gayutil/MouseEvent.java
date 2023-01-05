package digger.cmept.forum.utils.otherutils.gayutil;

import digger.cmept.forum.event.events.Event;

public class MouseEvent implements Event {

    public int button;

    public MouseEvent(int button) {
        this.button = button;
    }

}
