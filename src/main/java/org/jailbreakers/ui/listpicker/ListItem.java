package org.jailbreakers.ui.listpicker;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

/**
 * <h1>View of an item in {@link ListPicker}.</h1>
 * Class extends {@link Label} which is an integrated class in JavaFx.<br>
 * It is a visible node/label on screen.
 * @see ListPicker
 * @see Label
 * @author JailBreakersTeam (Matej Kandráč, Martin Ragan, Ján Kočíš)
 * @version 1.0
 * @since 1.6.202
 */

public class ListItem extends Label {

    /**
     * Constructor sets basic attributes to visible Label.<br>
     * Method calculates necessary font size and opacity based on distance from currently selected item.<br>
     * If an item is clicked, {@link OnItemClick#onItemClicked(int)} method of listener is called.<br>
     *
     * @param text  is an item's visible text
     * @param positionFromCurrent is distance from currently selected item
     * @param onItemClick is listener which handles item selection
     */

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

    /**
     * Constructor of an invisible list item used when list is about to end.<br>
     * Label itself will not be visible so it is just space setting.
     */

    public ListItem(){
        super();
        setPrefHeight(30);
        setPrefWidth(70);
    }

    /**
     * Interface which is used to handle item click selection.
     */

    public interface OnItemClick{
        void onItemClicked(int positionFromCurrent);
    }
}
