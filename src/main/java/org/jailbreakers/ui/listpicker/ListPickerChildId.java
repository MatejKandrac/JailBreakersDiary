package org.jailbreakers.ui.listpicker;

public enum ListPickerChildId {
    UP_BUTTON, DOWN_BUTTON, CLOSE_BUTTON, CONFIRM_BUTTON, BUTTONS_PARENT, ITEMS_PARENT;

    @Override
    public String toString() {
        switch (this){
            case UP_BUTTON:
                return "upButton";
            case DOWN_BUTTON:
                return "downButton";
            case BUTTONS_PARENT:
                return "buttonsParent";
            case CLOSE_BUTTON:
                return "closeButton";
            case ITEMS_PARENT:
                return "listParent";
            case CONFIRM_BUTTON:
                return "confirmButton";
            default:
                throw new IllegalStateException("Unknown value of ListPickerChildId");
        }
    }
}
