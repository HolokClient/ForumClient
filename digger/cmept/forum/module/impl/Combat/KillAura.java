package digger.cmept.forum.module.impl.Combat;

import digger.cmept.forum.event.EventTarget;
import digger.cmept.forum.event.events.impl.packet.EventAttackSilent;
import digger.cmept.forum.event.events.impl.packet.EventReceivePacket;
import digger.cmept.forum.event.events.impl.packet.EventSendPacket;
import digger.cmept.forum.event.events.impl.player.EventPreMotion;
import digger.cmept.forum.event.events.impl.player.EventUpdate;
import digger.cmept.forum.module.Module;
import digger.cmept.forum.module.ModuleCategory;
import digger.cmept.forum.ui.notif.NotifModern;
import digger.cmept.forum.ui.notif.NotifRender;
import digger.cmept.forum.ui.settings.impl.BooleanSetting;
import digger.cmept.forum.ui.settings.impl.ListSetting;
import digger.cmept.forum.ui.settings.impl.MultipleBoolSetting;
import digger.cmept.forum.ui.settings.impl.NumberSetting;
import digger.cmept.forum.utils.Helper;
import digger.cmept.forum.utils.inventory.InventoryUtil;
import digger.cmept.forum.utils.math.GCDFix;
import digger.cmept.forum.utils.math.KillauraUtils;
import digger.cmept.forum.utils.math.RotationHelper;
import digger.cmept.forum.utils.math.TimerHelper;
import digger.cmept.forum.utils.movement.MovementUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class KillAura extends Module {
    public static TimerHelper timerHelper = new TimerHelper();
    public static float yaw;
    public static float pitch;
    private int notiTicks;
    public static boolean isAttacking;
    TimerHelper shieldFixerTimer = new TimerHelper();

    public static float yawStatic, pitchStatic;
    public static boolean isBreaked;
    public static EntityLivingBase target;
        public static ListSetting clickMode = new ListSetting("Click Mode", "1.9", () -> true, "1.9", "1.8");
        public static NumberSetting minAPS = new NumberSetting("Min APS", "Минимальная скорость клика", 12.0f, 1.0f, 20.0f, 1.0f, () -> clickMode.currentMode.equals("1.8"), NumberSetting.NumberType.APS);
        public static NumberSetting maxAPS = new NumberSetting("Max APS", "Максимальная скорость клика", 13.0f, 1.0f, 20.0f, 1.0f, () -> clickMode.currentMode.equals("1.8"), NumberSetting.NumberType.APS);
        public static ListSetting rotationMode = new ListSetting("Rotation Mode", "Matrix", "Matrix", "Advanced", "MagicGrief", "Custom");
        public static ListSetting typeMode = new ListSetting("Type Mode", "Single", "Single", "Switch");
        public static BooleanSetting silent = new BooleanSetting("Silent Aura", true);
        public static ListSetting sortMode = new ListSetting("Priority Mode", "Distance", () -> typeMode.currentMode.equalsIgnoreCase("Switch"), "Distance", "Health", "Crosshair", "Higher Armor", "Lowest Armor");
        public static NumberSetting fov = new NumberSetting("FOV", "Позволяет редактировать радиус в котором вы можете ударить игрока", 180, 0, 180, 1);
        public static NumberSetting attackCoolDown = new NumberSetting("Attack CoolDown", "Куллдаун перед ударом", 0.85F, 0.1F, 1F, 0.01F, () -> !rotationMode.currentMode.equals("Snap") && clickMode.currentMode.equalsIgnoreCase("1.9"));
        public static NumberSetting range = new NumberSetting("AttackRange", "Дистанция удара", 3.6F, 3, 6, 0.01f);
        public static NumberSetting preAimRange = new NumberSetting("Pre Aim Range", 0.0f, 0.0f, 4.0f, 0.1f, () -> !rotationMode.currentMode.equals("Vanilla"));
        public static NumberSetting yawrandom = new NumberSetting("Yaw Random", 1.6f, 0.1f, 20, 0.01F, () -> rotationMode.currentMode.equals("Custom"));
        public static NumberSetting pitchRandom = new NumberSetting("Pitch Random", 1.6f, 0.1f, 20, 0.01F, () -> rotationMode.currentMode.equals("Custom"));
        public static BooleanSetting staticPitch = new BooleanSetting("Static Pitch", false, () -> rotationMode.currentMode.equals("Custom"));
        public static NumberSetting pitchHead = new NumberSetting("Pitch Head", 0.35f, 0.1f, 1.2f, 0.01F, () -> rotationMode.currentMode.equals("Custom"));
        public BooleanSetting rayCast = new BooleanSetting("RayCast", "Проверяет навелась ли киллаура на хитбокс энтити", true);
    public static NumberSetting rotPredict = new NumberSetting("Rotation Predict", 0.05F, 0.0F, 10, 0.001F, () -> true);

    public static BooleanSetting walls = new BooleanSetting("Walls", "Удары сквозь стены", true);
        public static BooleanSetting onlyCritical = new BooleanSetting("Only Critical", "Бьет только критическим ударом", true, () -> clickMode.currentMode.equalsIgnoreCase("1.9"));
        public BooleanSetting spaceOnly = new BooleanSetting("Space Only", "Only Crits будет работать при нажатии пробела", false, () -> onlyCritical.getCurrentValue() && clickMode.currentMode.equalsIgnoreCase("1.9"));
        public static BooleanSetting checkCrystals = new BooleanSetting("Check Crystal", "Only Crits не будут работать если рядом кристалл", false, () -> onlyCritical.getCurrentValue() && clickMode.currentMode.equalsIgnoreCase("1.9"));
        public static NumberSetting radiusCrystals = new NumberSetting("Distance to Crystal", 3, 1, 8, 1, () -> checkCrystals.getCurrentValue() && clickMode.currentMode.equalsIgnoreCase("1.9"));
        public static NumberSetting criticalFallDistance = new NumberSetting("Critical Fall Distance", "Регулировка дистанции до земли для крита", 0.2F, 0.08F, 1F, 0.01f, () -> onlyCritical.getCurrentValue() && clickMode.currentMode.equalsIgnoreCase("1.9"));
        public BooleanSetting shieldFixer = new BooleanSetting("ShieldFixer", "Позволяет бить с зажатым щитом обходит Matrix", false, () -> clickMode.currentMode.equalsIgnoreCase("1.9"));
        public NumberSetting fixerDelay = new NumberSetting("Fixer Delay", "Регулировка того как быстро щит будет отжиматься", 150.0f, 0.0f, 600.0f, 10.0f, () -> shieldFixer.getCurrentValue() && clickMode.currentMode.equalsIgnoreCase("1.9"));
        public BooleanSetting shieldDesync = new BooleanSetting("Shield Desync", false, () -> clickMode.currentMode.equalsIgnoreCase("1.9"));
        public static BooleanSetting shieldBreaker = new BooleanSetting("ShieldBreaker", "Автоматически ломает щит сопернику", true, () -> clickMode.currentMode.equalsIgnoreCase("1.9"));
        public static BooleanSetting breakNotifications = new BooleanSetting("Break Notifications", true, () -> shieldBreaker.getCurrentValue() && clickMode.currentMode.equalsIgnoreCase("1.9"));
        public static BooleanSetting silentMove = new BooleanSetting("SilentMove", false);
        public static MultipleBoolSetting targetsSetting = new MultipleBoolSetting("Targets", new BooleanSetting("Players", true), new BooleanSetting("Mobs"), new BooleanSetting("Animals"), new BooleanSetting("Villagers"), new BooleanSetting("Invisibles", true));

    public KillAura() {
        super("Aura", "Автоматически атакует энтити", ModuleCategory.Combat);
        addSettings(rotationMode, typeMode, sortMode, clickMode, minAPS, maxAPS, targetsSetting, fov, attackCoolDown, range, preAimRange, rayCast, yawrandom, pitchRandom, pitchHead, staticPitch, walls, onlyCritical, spaceOnly, checkCrystals, radiusCrystals, criticalFallDistance, shieldBreaker, breakNotifications, shieldFixer, fixerDelay, shieldDesync);
    }

    @EventTarget
    public void onFix(EventSendPacket event) {
        /* INTERACT FIX */
        if (target == null) return;

        if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
            event.setCancelled(true);
        }

        if (event.getPacket() instanceof CPacketUseEntity) {
            CPacketUseEntity packetUseEntity = (CPacketUseEntity) event.getPacket();
            if ((packetUseEntity.getAction() == CPacketUseEntity.Action.INTERACT) || (packetUseEntity.getAction() == CPacketUseEntity.Action.INTERACT_AT)) {
                event.setCancelled(true);
            }
        }
    }

    @EventTarget
    public void onEventPre(EventPreMotion event) {
        String mode = rotationMode.getOptions();
        /* Sorting */
        target = KillauraUtils.getSortEntities();
        /* ����� ������ */
        if (target == null) {
            return;
        }
        /* RayCast */
        if (!RotationHelper.isLookingAtEntity(false, yawStatic, pitchStatic, 0.06f, 0.06f, 0.06f, target, range.getCurrentValue() + preAimRange.getCurrentValue())) {
            return;
        }
        /* Only Critical */
        if (!(!Helper.mc.gameSettings.keyBindJump.isKeyDown() && spaceOnly.getCurrentValue() || KillauraUtils.checkCrystal() && checkCrystals.getCurrentValue() || Helper.mc.player.capabilities.isFlying)) {
            if (MovementUtils.airBlockAboveHead()) {
                if (!(Helper.mc.player.fallDistance >= criticalFallDistance.getCurrentValue() || !onlyCritical.getCurrentValue() || Helper.mc.player.isRiding() || Helper.mc.player.isOnLadder() || Helper.mc.player.isInLiquid() || Helper.mc.player.isInWeb)) {
                    return;
                }
            } else if (!(!(Helper.mc.player.fallDistance > 0.0f) || Helper.mc.player.onGround || !onlyCritical.getCurrentValue() || Helper.mc.player.isRiding() || Helper.mc.player.isOnLadder() || Helper.mc.player.isInLiquid() || Helper.mc.player.isInWeb)) {
                return;
            }
        }

        KillauraUtils.attackEntity(target);

    }

    public float prevYaw, prevPitch;

    @EventTarget
    public void onRotations(EventPreMotion event) {

        String mode = rotationMode.getOptions();

        if (target == null) {
            return;
        }

        if (!target.isDead) {

            /* ROTATIONS */
            float[] sunrise = getSunriseRots(target);
            float[] matrix = getRotations(target);

            float[] fake = RotationHelper.getRotationsA(target);
            float[] rotations2 = RotationHelper.getMagicRotations(target, true, 360, 360, 2, 2);

            float[] custom = RotationHelper.getCustomRotations(target);

            if (mode.equalsIgnoreCase("Matrix")) {
                if (silent.getCurrentValue()) {
                    event.setYaw(matrix[0]);
                    event.setPitch(matrix[1]);
                    yawStatic = matrix[0];
                    pitchStatic = matrix[1];
                    Helper.mc.player.renderYawOffset = matrix[0];
                    Helper.mc.player.rotationYawHead = matrix[0];
                    Helper.mc.player.rotationPitchHead = matrix[1];
                } else {
                    Helper.mc.player.rotationYaw = matrix[0];
                    Helper.mc.player.rotationPitch = matrix[1];
                }
            } else if (mode.equalsIgnoreCase("MagicGrief")) {

                event.setYaw(rotations2[0]);
                event.setPitch(rotations2[1]);
                yaw = rotations2[0];
                pitch = rotations2[1];
                Helper.mc.player.renderYawOffset = rotations2[0];
                Helper.mc.player.rotationYawHead = rotations2[0];
                Helper.mc.player.rotationPitchHead = rotations2[1];
            } else if (mode.equalsIgnoreCase("Advanced")) {
                if (silent.getCurrentValue()) {
                    event.setYaw(sunrise[0]);
                    event.setPitch(sunrise[1]);
                    yawStatic = sunrise[0];
                    pitchStatic = sunrise[1];
                    Helper.mc.player.renderYawOffset = sunrise[0];
                    Helper.mc.player.rotationYawHead = sunrise[0];
                    Helper.mc.player.rotationPitchHead = sunrise[1];
                } else {
                    Helper.mc.player.rotationYaw = sunrise[0];
                    Helper.mc.player.rotationPitch = sunrise[1];
                }

            } else if (mode.equalsIgnoreCase("Custom")) {
                if (silent.getCurrentValue()) {
                    event.setYaw(custom[0]);
                    event.setPitch(custom[1]);
                    Helper.mc.player.renderYawOffset = custom[0];
                    Helper.mc.player.rotationYawHead = custom[0];
                    Helper.mc.player.rotationPitchHead = custom[1];
                } else {
                    Helper.mc.player.rotationYaw = fake[0];
                    Helper.mc.player.rotationPitch = fake[1];
                }
            }

        } else {
            yawStatic = Helper.mc.player.rotationYaw;
            pitchStatic = Helper.mc.player.rotationPitch;
        }
    }

    private static double getDist(Entity entity) {
        Vec3d vec = entity.getPositionVector().add(new Vec3d(0, MathHelper.clamp(entity.posY - Helper.mc.player.posY + Helper.mc.player.getEyeHeight(), 0, entity.height), 0));
        return Helper.mc.player.getPositionVector().add(new Vec3d(0, Helper.mc.player.height / 2, 0)).distanceTo(vec);
    }

    public static float[] getRotations(Entity entity) {
        Vec3d vec = entity.getPositionVector().add(new Vec3d(0, MathHelper.clamp(entity.getEyeHeight() * (getDist(entity) / (range.getCurrentValue() + entity.width)), 0.2, Helper.mc.player.getEyeHeight()), 0));
        double diffX = vec.x - Helper.mc.player.posX;
        double diffY = vec.y - (Helper.mc.player.posY + Helper.mc.player.getEyeHeight());
        double diffZ = vec.z - Helper.mc.player.posZ;
        double dist = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
        float yawTo = (float) ((Math.toDegrees(Math.atan2(diffZ, diffX)) - 90) + GCDFix.getFixedRotation((float) (Math.sin(System.currentTimeMillis() / 30) * 2)));
        float pitchTo = (float) (-(Math.toDegrees(Math.atan2(diffY, dist))) + GCDFix.getFixedRotation((float) (Math.cos(System.currentTimeMillis() / 30) * 2)));
        yawTo = Helper.mc.player.rotationYaw + GCDFix.getFixedRotation(MathHelper.wrapDegrees(yawTo - Helper.mc.player.rotationYaw));
        pitchTo = Helper.mc.player.rotationPitch + GCDFix.getFixedRotation(MathHelper.wrapDegrees(pitchTo - Helper.mc.player.rotationPitch));
        pitchTo = MathHelper.clamp(pitchTo, -90, 90);
        yawStatic = GCDFix.getFixedRotation(MathHelper.Rotate(yawStatic, yawTo, 90, 90));
        pitchStatic = GCDFix.getFixedRotation(MathHelper.Rotate(pitchStatic, pitchTo, 1, 12));
        return new float[]{yawStatic, pitchStatic};
    }

    public static float[] getSunriseRots(Entity entity) {
        Vec3d vec = entity.getPositionVector().add(new Vec3d(0, MathHelper.clamp(entity.getEyeHeight() * (getDist(entity) / (range.getCurrentValue() + entity.width)), 0.2, Helper.mc.player.getEyeHeight()), 0));
        double diffX = vec.x - Helper.mc.player.posX;
        double diffY = vec.y - (Helper.mc.player.posY + Helper.mc.player.getEyeHeight());
        double diffZ = vec.z - Helper.mc.player.posZ;
        double dist = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
        float yawTo = (float) ((Math.toDegrees(Math.atan2(diffZ, diffX)) - 90) + GCDFix.getFixedRotation((float) (Math.sin(System.currentTimeMillis() / 30) * 2)));
        float pitchTo = (float) (-(Math.toDegrees(Math.atan2(diffY, dist))) + GCDFix.getFixedRotation((float) (Math.cos(System.currentTimeMillis() / 30) * 2)));
        yawTo = Helper.mc.player.rotationYaw + GCDFix.getFixedRotation(MathHelper.wrapDegrees(yawTo - Helper.mc.player.rotationYaw));
        pitchTo = Helper.mc.player.rotationPitch + GCDFix.getFixedRotation(MathHelper.wrapDegrees(pitchTo - Helper.mc.player.rotationPitch));
        pitchTo = MathHelper.clamp(pitchTo, -90, 90);
        yawStatic = GCDFix.getFixedRotation(MathHelper.Rotate(yawStatic, yawTo, 89, 89));
        pitchStatic = GCDFix.getFixedRotation(MathHelper.Rotate(pitchStatic, pitchTo, 1f, 3f));
        return new float[]{yawStatic, pitchStatic};
    }

    @EventTarget
    public void onAttackSilent(EventAttackSilent eventAttackSilent) {
        /* SHIELD Fix */
        if (Helper.mc.player.isBlocking() && this.shieldFixerTimer.hasReached(fixerDelay.getCurrentValue()) && Helper.mc.player.getHeldItem(EnumHand.OFF_HAND).getItem() instanceof ItemShield && shieldFixer.getCurrentValue()) {
            Helper.mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(900, 900, 900), EnumFacing.UP));
            Helper.mc.playerController.processRightClick(Helper.mc.player, Helper.mc.world, EnumHand.OFF_HAND);
            this.shieldFixerTimer.reset();
        }
    }


    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (target == null) {
            return;
        }
        /* SHIELD Desync */
        if (shieldDesync.getCurrentValue() && Helper.mc.player.isBlocking() && target != null && Helper.mc.player.ticksExisted % 8 == 0) {
            Helper.mc.player.stopActiveHand();
        }
        if (shieldFixer.getCurrentValue()) {
            if (target.getHeldItemMainhand().getItem() instanceof ItemAxe) {
                if (Helper.mc.gameSettings.keyBindUseItem.isKeyDown()) {
                    Helper.mc.player.stopActiveHand();
                }
            }
        }
    }

    @EventTarget
    public void onSound(EventReceivePacket sound) {
        if (breakNotifications.getCurrentValue()) {
            if (sound.getPacket() instanceof SPacketEntityStatus) {
                SPacketEntityStatus sPacketEntityStatus = (SPacketEntityStatus) sound.getPacket();
                if (sPacketEntityStatus.getOpCode() == 30) {
                    if (sPacketEntityStatus.getEntity(Helper.mc.world) == target) {
                        if (notiTicks < 2) {
                            NotifRender.queue("Shield Debug", target.getName() + " shield destroyed", 2, NotifModern.BREAK);
                        } else {
                            notiTicks = 0;
                        }
                    }
                }
            }
        }
    }

    public static void BreakShield(EntityLivingBase tg) {
        if (InventoryUtil.doesHotbarHaveAxe() && shieldBreaker.getCurrentValue()) {
            int item = InventoryUtil.getAxe();
            if (InventoryUtil.getAxe() >= 0 && tg instanceof EntityPlayer && tg.isHandActive() && tg.getActiveItemStack().getItem() instanceof ItemShield) {
                Helper.mc.player.connection.sendPacket(new CPacketHeldItemChange(item));
                Helper.mc.playerController.attackEntity(Helper.mc.player, target);
                Helper.mc.player.swingArm(EnumHand.MAIN_HAND);
                Helper.mc.player.connection.sendPacket(new CPacketHeldItemChange(Helper.mc.player.inventory.currentItem));
            }
        }
    }

    @Override
    public void onDisable() {
        target = null;
        super.onDisable();
    }
}
