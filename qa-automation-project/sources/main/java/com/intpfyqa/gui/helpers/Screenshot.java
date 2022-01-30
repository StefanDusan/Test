package com.intpfyqa.gui.helpers;

import com.intpfyqa.logging.impl.ImageSnapshot;
import com.intpfyqa.gui.ITakeScreenshot;
import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Screenshot {

    public static ITakeScreenshot fromDesktop() {
        return () -> {
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                ImageIO.write(image, "png", baos);
                return new ImageSnapshot(Base64.encodeBase64String(baos.toByteArray()));
            } catch (AWTException | IOException e) {
                return null;
            }
        };
    }
}
