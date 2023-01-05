package digger.cmept.forum.event.events;

public interface Cancellable {

    boolean isCancelled();

    void setCancelled(boolean state);

}
