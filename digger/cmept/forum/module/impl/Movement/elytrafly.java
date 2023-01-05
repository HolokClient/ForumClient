package digger.cmept.forum.module.impl.Movement;

import digger.cmept.forum.event.EventTarget;
import digger.cmept.forum.event.events.impl.player.EventUpdate;
import digger.cmept.forum.module.Module;
import digger.cmept.forum.module.impl.Combat.KillAura;
import digger.cmept.forum.module.ModuleCategory;
import digger.cmept.forum.ui.settings.impl.ListSetting;
import digger.cmept.forum.utils.movement.MovementUtils;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketEntityAction;

import static digger.cmept.forum.utils.math.KillauraUtils.timerHelper;

public class elytrafly extends Module {

    public static ListSetting flyMode = new ListSetting("Mode", "Hard", () -> true, "Hard", "Safe");
    int eIndex = -1;

    private int boostTicks;
    boolean allowfly100;

    boolean jumped;
    boolean packetsended;


    public elytrafly() {
        super("ElytraFly", "��������� ������ � �������� � ���������", ModuleCategory.Movement);
        addSettings(flyMode);
    }

    public void onDisable() {
        this.boostTicks = 0;
        allowfly100 = false;
        packetsended = false;
        mc.player.capabilities.isFlying = false;
        mc.player.capabilities.allowFlying = false;
        jumped = false;
        super.onDisable();
    }

    public void onEnable() {
        super.onEnable();
    }


    public boolean attackCheck() {
        return (mc.player.isInLava() || mc.player.isInWater() || mc.player.isOnLadder() || mc.player.isInWeb);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        String mode = flyMode.getOptions();
        if (mode.equalsIgnoreCase("Hard")) {
            if (allowfly100) {
                this.boostTicks++;
                mc.player.capabilities.isFlying = true;
                mc.player.capabilities.allowFlying = true;
                if (this.boostTicks > 1) {
                    mc.player.motionX *= 1.1D;
                    mc.player.motionZ *= 1.1D;
                } else {
                    mc.player.jumpMovementFactor = 0.25f;
                }
                if (MovementUtils.getSpeed() > 4) {
                    this.boostTicks = 0;
                }
                MovementUtils.setSpeed(MovementUtils.getSpeed());
                if (!MovementUtils.isMoving())
                    this.boostTicks = 0;
                if (mc.gameSettings.keyBindJump.isKeyDown() || mc.player.isCollidedHorizontally) {
                    mc.player.motionY = 0.25D;
                } else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                    mc.player.motionY = -0.30000001192092896D;
                }
            }
        } else if (mode.equalsIgnoreCase("Safe")) {
            for (int i = 0; i < 45; i++)
                if (mc.player.inventory.getStackInSlot(i).getItem() == Items.ELYTRA && eIndex == -1) {
                    if (allowfly100) {
                        mc.player.motionY = 0.012121210f;
                        boostTicks += 1;

                        if (mc.gameSettings.keyBindJump.pressed) {
                            boostTicks--;
                            mc.player.motionY = 0.45f;
                        }

                        if (mc.gameSettings.keyBindSneak.pressed) {
                            boostTicks -= 2;
                            mc.player.motionY = -0.25f;
                        }

                        MovementUtils.setSpeed(0.8f);
                        boostTicks += 1;
                        if (boostTicks > 5) {
                            //   ChatUtils.addChatMessage("Fly step [1]"); //debug
                            MovementUtils.setSpeed(0.85f);

                        }
                        if (boostTicks > 10) {
                            //  ChatUtils.addChatMessage("Fly step [2]"); //debug
                            MovementUtils.setSpeed(1.0f);

                        }
                        if (boostTicks > 15) {
                            //  ChatUtils.addChatMessage("Fly step [3]"); //debug
                            MovementUtils.setSpeed(1.1f);

                        }
                        if (boostTicks > 20) {
                            // ChatUtils.addChatMessage("Fly step [4]"); //debug
                            MovementUtils.setSpeed(1.2f);

                        }
                        if (!(KillAura.target == null)) {
                            boostTicks -= 2;
                            //       ChatUtils.addChatMessage("Target finded, [stopping]"); //debug
                        }
                        if (mc.player.isCollidedHorizontally) {
                            //ChatUtils.addChatMessage("You colided with block, [stopping]"); //debug
                            boostTicks = 0;
                        }
                        if (!MovementUtils.isMoving() && timerHelper.hasReached(1000)) {
                            //    ChatUtils.addChatMessage("You stopped, [stopping]"); //debug
                            boostTicks = 0;
                        }
                    }
                }
        }


        for (int i = 0; i < 45; i++)
            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.ELYTRA && eIndex == -1) {
                if (mc.player.onGround) {
                    mc.player.jump();
                    jumped = true;
                }


                for (i = 0; i < 45; i++)
                    if (mc.player.inventory.getStackInSlot(i).getItem() == Items.ELYTRA && eIndex == -1) {
                        if (mc.player.isAirBorne) {
                            jumped = true;
                        }
                        for (i = 0; i < 45; i++)
                            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.ELYTRA && eIndex == -1) {
                                if (packetsended) {
                                    if (mc.player.isElytraFlying()) {
                                        allowfly100 = true;
                                    }
                                }

                                for (i = 0; i < 45; i++)
                                    if (mc.player.inventory.getStackInSlot(i).getItem() == Items.ELYTRA && eIndex == -1) {
                                        if (jumped) {
                                            if (timerHelper.hasReached(2000)) {
                                                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
                                                packetsended = true;
                                            }
                                        }
                                    }
                            }
                    }
            }
    }
}
