package digger.cmept.forum.drag;

import digger.cmept.forum.drag.comp.DragComp;
import digger.cmept.forum.forum;
import net.minecraft.util.math.MathHelper;

public class DragScreen {

    public void draw(int mouseX, int mouseY) {
        for (DragComp draggableComponent : forum.instance.draggableHUD.getComponents()) {

            if (draggableComponent.allowDraw()) {
                drawComponent(mouseX, mouseY, draggableComponent);
            }
        }
    }

    private void drawComponent(int mouseX, int mouseY, DragComp draggableComponent) {
        boolean hover = MathHelper.isMouseHoveringOnRect(draggableComponent.getX(), draggableComponent.getY(), draggableComponent.getWidth(), draggableComponent.getHeight(), mouseX, mouseY);
        draggableComponent.draw(mouseX, mouseY);
    }

    public void click(int mouseX, int mouseY) {
        for (DragComp draggableComponent : forum.instance.draggableHUD.getComponents()) {
            if (draggableComponent.allowDraw()) {
                draggableComponent.click(mouseX, mouseY);
            }
        }
    }

    public void release() {
        for (DragComp draggableComponent : forum.instance.draggableHUD.getComponents()) {
            draggableComponent.release();
        }
    }

}