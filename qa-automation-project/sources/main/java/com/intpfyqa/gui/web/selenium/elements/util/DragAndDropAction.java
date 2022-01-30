package com.intpfyqa.gui.web.selenium.elements.util;


import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

/**
 * Created by RakitskyAK on 05.11.2018.
 */
public class DragAndDropAction {

    private final Element target;
    private boolean middle;
    private Boolean bottom;
    private Boolean right;
    private int xOffset;
    private int yOffset;

    private DragAndDropAction(Element target) {
        this.target = target;
        this.middle = true;
        yOffset = 0;
        xOffset = 0;
        bottom = null;
        right = null;
    }

    public static DragAndDropAction toMiddleOfElement(Element target) {
        return new DragAndDropAction(target);
    }

    public static DragAndDropAction toMiddleOfElementWithOffset(Element target, int xOffset, int yOffset) {
        DragAndDropAction action = new DragAndDropAction(target);
        action.xOffset = xOffset;
        action.yOffset = yOffset;
        return action;
    }

    public static DragAndDropAction toBottomOfElementWithOffset(Element target, int xOffset, int yOffset) {
        DragAndDropAction action = new DragAndDropAction(target);
        action.middle = false;
        action.bottom = true;
        action.xOffset = xOffset;
        action.yOffset = yOffset;

        return action;
    }

    public static DragAndDropAction toBottomOfElement(Element target) {
        DragAndDropAction action = new DragAndDropAction(target);
        action.middle = false;
        action.bottom = true;

        return action;
    }

    public static DragAndDropAction toTopOfElementWithOffset(Element target, int xOffset, int yOffset) {
        DragAndDropAction action = new DragAndDropAction(target);
        action.middle = false;
        action.bottom = false;
        action.xOffset = xOffset;
        action.yOffset = yOffset;

        return action;
    }

    public static DragAndDropAction toTopOfElement(Element target) {
        DragAndDropAction action = new DragAndDropAction(target);
        action.middle = false;
        action.bottom = false;

        return action;
    }

    public static DragAndDropAction toRightOfElementWithOffset(Element target, int xOffset, int yOffset) {
        DragAndDropAction action = new DragAndDropAction(target);
        action.middle = false;
        action.right = true;
        action.xOffset = xOffset;
        action.yOffset = yOffset;

        return action;
    }

    public static DragAndDropAction toRightOfElement(Element target) {
        DragAndDropAction action = new DragAndDropAction(target);
        action.middle = false;
        action.right = true;

        return action;
    }

    public static DragAndDropAction toLeftOfElementWithOffset(Element target, int xOffset, int yOffset) {
        DragAndDropAction action = new DragAndDropAction(target);
        action.middle = false;
        action.right = false;
        action.xOffset = xOffset;
        action.yOffset = yOffset;

        return action;
    }

    public static DragAndDropAction toLeftOfElement(Element target) {
        DragAndDropAction action = new DragAndDropAction(target);
        action.middle = false;
        action.right = false;

        return action;
    }

    public static DragAndDropAction toLocation(int x, int y) {
        DragAndDropAction action = new DragAndDropAction(null);
        action.middle = false;
        action.right = false;
        action.xOffset = x;
        action.yOffset = y;

        return action;
    }

    public String toString() {
        if (null == target)
            return String.format("To the %s, %s", xOffset, yOffset);
        return String.format("To the %s of element %s%s",
                (middle ? "middle" : (bottom != null ? (bottom ? "bottom" : "top") : (right ? "right" : "left"))),
                target.toString(),
                (xOffset != 0 || yOffset != 0) ? ". With offset (" + xOffset + ", " + yOffset + ")" : "");
    }

    public Point calcTargetPoint() {
        if (null == target)
            return new Point(xOffset, yOffset);

        Point targetPoint = target.getLocation();
        Dimension size = target.getSize();

        if (middle) {
            targetPoint = targetPoint.moveBy(size.getWidth() / 2, size.getHeight() / 2);
        } else {

            if (bottom == null) {
                targetPoint = targetPoint.moveBy(0, size.getHeight() / 2);
            } else if (bottom) {
                targetPoint = targetPoint.moveBy(0, size.getHeight());
            } else {
                targetPoint = targetPoint.moveBy(0, 0);
            }

            if (right == null) {
                targetPoint = targetPoint.moveBy(size.getWidth() / 2, 0);
            } else if (right) {
                targetPoint = targetPoint.moveBy(size.getWidth(), 0);
            } else {
                targetPoint = targetPoint.moveBy(0, 0);
            }
        }

        return targetPoint.moveBy(xOffset, yOffset);
    }
}
