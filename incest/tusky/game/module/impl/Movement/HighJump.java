package incest.tusky.game.module.impl.Movement;

import incest.tusky.game.module.Module;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.utils.Helper;

import java.util.concurrent.TimeUnit;

public class HighJump extends Module {
    public HighJump() {
        super("HighJump", "����������� ��� ������ �����", ModuleCategory.Movement);
    }

    @Override
    public void onEnable() {
        if (Helper.mc.player.onGround) {
            Helper.mc.player.jump();
        }
        new Thread(() -> {
            Helper.mc.player.motionY = 9f;
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Helper.mc.player.motionY = 8.742f;
            this.toggle();
        }).start();
    }
}