package org.jailbreakers.ui.datepicker;

import com.jfoenix.controls.JFXToggleNode;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class DayItem extends VBox {

    public static final int OTHER_MONTH_DAY = 0, SELECTED_DAY = 1, UNSELECTED_DAY = 2;

    private OnDaySelected listener;
    private JFXToggleNode child;
    private int positionInMonth;
    private int type;
    private boolean deselecting = false;

    /**
     * <h1>This class is structure of an object which represents one day in our calendar system - DatePicker </h1>
     */

    DayItem(int type, int positionInMonth, boolean isCurrent) {
        super();
        this.positionInMonth = positionInMonth;
        this.type = type;
        setAlignment(Pos.CENTER);
        child = new JFXToggleNode();
        child.setFocusTraversable(false);
        child.setPrefHeight(30);
        child.setPrefWidth(30);
        if (type == OTHER_MONTH_DAY)
            child.getStyleClass().add("otherMonthDay");
        else if (type == UNSELECTED_DAY)
            child.getStyleClass().add("unselectedDay");
        else if (isCurrent)
            child.setSelected(true);
        child.setText(String.valueOf(positionInMonth));
        child.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (deselecting)
                deselecting = false;
            else if(listener == null && newValue)
                child.setSelected(false);
            else if (listener != null && newValue)
                listener.onDaySelected(positionInMonth);
            else if (listener != null)
                child.setSelected(true);
        });
        getChildren().add(child);
    }

    void deselect(){
        if (child.isSelected()){
            deselecting = true;
            child.setSelected(false);
        }

    }

    public int getType() {
        return type;
    }

    public int getPositionInMonth() {
        return positionInMonth;
    }

    void setOnClickListener(OnDaySelected listener){
        this.listener = listener;
    }

    interface OnDaySelected{
        void onDaySelected(int positionInMonth);
    }
}
