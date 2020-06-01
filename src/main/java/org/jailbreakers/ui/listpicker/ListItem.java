package org.jailbreakers.ui.listpicker;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class ListItem extends Label {

    public ListItem(String text, int positionFromCurrent, OnItemClick onItemClick) {
        super(text);
        setPrefHeight(30);
        setFont(new Font(22-(Math.abs(positionFromCurrent)*4)));
        setOpacity(1-(Math.abs(positionFromCurrent)*0.4));
        setAlignment(Pos.CENTER);
        setOnMouseClicked(event -> {
            if (onItemClick != null)
                onItemClick.onItemClicked(positionFromCurrent);
        });
    }

    public ListItem(){
        super();
        setPrefHeight(30);
        setPrefWidth(70);
    }

    public interface OnItemClick{
        void onItemClicked(int positionFromCurrent);
    }
}
