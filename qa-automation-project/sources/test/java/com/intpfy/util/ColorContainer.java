package com.intpfy.util;

import java.awt.*;

public final class ColorContainer {

    public static final ColorContainer GREEN = new ColorContainer(new Color(0, 176, 80, 1));

    private final Color color;

    public ColorContainer(Color color) {
        this.color = color;
    }

    public static ColorContainer fromCssBackground(String cssBackground) {

        //if (cssBackground.toLowerCase().contains("rgba")) {
        String[] rgba = cssBackground.replace("rgba(", "").replace(")", "").split(",");
        return new ColorContainer(new Color(Integer.valueOf(rgba[0].trim(), 10), Integer.valueOf(rgba[1].trim(), 10),
                Integer.valueOf(rgba[2].trim(), 10)));
        //}
    }

    public Color getColor() {
        return color;
    }

    public String hexString() {
        return String.format("%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    public String toRgbCssString() {
        return "rgb(" + color.getRed() + ", " + color.getGreen() + ", " + color.getBlue() + ")";
    }

    public String toRgbCssStringWithoutSpaces() {
        return "rgb(" + color.getRed() + "," + color.getGreen() + "," + color.getBlue() + ")";
    }

    public String hexCssString() {
        return "#" + hexString();
    }

    public String toString() {
        return hexCssString();
    }

    public int hashCode() {
        return color.hashCode();
    }

    public boolean equals(Object o) {
        if (o instanceof ColorContainer) {
            return o.toString().equals(toString());
        } else if (o instanceof Color) {
            return getColor().equals(o);
        } else {
            return super.equals(o);
        }
    }
}
