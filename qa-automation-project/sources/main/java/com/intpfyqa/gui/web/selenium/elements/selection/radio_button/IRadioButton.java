package com.intpfyqa.gui.web.selenium.elements.selection.radio_button;

import com.intpfyqa.gui.web.selenium.elements.Selectable;
import com.intpfyqa.gui.web.selenium.elements.Unselectable;

public interface IRadioButton extends Selectable, Unselectable {

    @Override
    default void unselect() {
        throw new UnsupportedOperationException("Radio button can not be unselected.");
    }
}
