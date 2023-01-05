package net.minecraft.client.gui;

import net.minecraft.util.Util;

public class HoverUtility extends Util {
    public HoverUtility() {
    }

    public static boolean isHovered(int mouseX, int mouseY, double x, double y, double width, double height) {
        return (double)mouseX >= x && (double)mouseX <= x + width && (double)mouseY >= y && (double)mouseY <= y + height;
    }
}