package org.jailbreakers.ui.listpicker;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.jailbreakers.obj.Layout;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * <h1>ListPicker is a part of screen which lets user pick an item from list or array of items.</h1>
 * View is initialized via fx:root attribute in FXML document and FXMLLoader.setController method.<br>
 * You can choose if you want to use a list or array of items and their type has to be Object.<br>
 * Class extends VBox integrated in JavaFx.<br>
 *
 * @see ListItem
 * @see ListItem.OnItemClick
 * @see Layout
 * @see VBox
 * @see Initializable
 * @author JailBreakersTeam (Matej Kandráč, Martin Ragan, Ján Kočíš)
 * @version 1.0
 * @since 1.6.202
 */

public class ListPicker extends VBox implements ListItem.OnItemClick, Initializable {

    @FXML
    private JFXButton upButton;

    @FXML
    private VBox listParent;

    @FXML
    private JFXButton downButton;

    @FXML
    private HBox buttonsParent;

    @FXML
    private JFXButton closeButton;

    @FXML
    private JFXButton confirmButton;

    /**
     * Parent of loaded view (ListPicker).
     */
    private VBox root;
    /**
     * Interface which is used to handle events of ListPicker.
     */
    private ListPickerEvent listPickerEvent;
    /**
     * List of items to show.
     */
    private List<Object> items;
    /**
     * Currently selected item.
     */
    private int selected = 0;

    /**
     * Constructor for List as items.
     */
    public ListPicker(List<Object> items, int initPosition, ListPickerEvent listPickerEvent) {
        initPicker(items, initPosition, listPickerEvent);
    }
    /**
     * Constructor for array as items.<br>
     * It creates a List from provided array.
     */
    public ListPicker(Object[] items, int initPosition, ListPickerEvent listPickerEvent){
        List<Object> parsedItems = new ArrayList<>(Arrays.asList(items));
        initPicker(parsedItems, initPosition, listPickerEvent);
    }

    /**
     * Method called from constructors.<br>
     * It loads FXML resource and sets this as controller.<br>
     * After creation of layout, it sets first selected item on initial position.<br>
     * If there is an error, {@link ListPickerEvent#onError()} method is called.
     *
     * @param items are items to be shown
     * @param initPosition is initial position in list
     * @param listPickerEvent is listener used for handling ListPicker events
     */
    private void initPicker(List<Object> items, int initPosition, ListPickerEvent listPickerEvent){
        this.items = items;
        this.listPickerEvent = listPickerEvent;
        FXMLLoader loader = new FXMLLoader();
        loader.setController(this);
        loader.setRoot(this);
        loader.setLocation(Layout.LIST_PICKER.getResourceByLayout());
        try {
            root = loader.load();
            setSelected(initPosition);
        } catch (IOException e) {
            listPickerEvent.onError();
        }
    }

    /**
     * Method reloads shown items to new ones based on selected index.<br>
     * Method creates a List of new items to be visible.<br>
     * If index is out of bounds in items array, it creates an empty {@link ListItem}.<br>
     * In the end, new visible children are set to {@link #listParent}.
     *
     * @param index is index of selected item
     */

    private void setSelected(int index) {
        if (index >= 0 && index < items.size()) {
            selected = index;
            List<Node> newChildren = new ArrayList<>();
            for (int i = index - 2; i <= index + 2; i++) {
                if (i < 0 || i >= items.size())
                    newChildren.add(new ListItem());
                else
                    newChildren.add(new ListItem(items.get(i).toString(), (selected-i),this));
            }
            listParent.getChildren().setAll(newChildren);
        }

    }

    /**
     * Getter of view parent so it can be shown to screen.
     * @return parent of ListPicker
     */

    public VBox getView(){
        return root;
    }

    /**
     * Method is overridden from {@link ListItem.OnItemClick} and it handles click of {@link ListItem}.<br>
     * Method sets selected item to clicked item.
     * @param positionFromCurrent is position from currently selected item
     */

    @Override
    public void onItemClicked(int positionFromCurrent) {
        setSelected(selected-positionFromCurrent);
    }

    /**
     * Method initializes view events.<br>
     * When confirm is pressed, ListPicker event listener calls a corresponding method in interface.<br>
     * Same goes for close button.<br>
     * Up and down buttons set new selected item either up or down.<br>
     * If mouse is scrolled, list items are also updated by direction of scroll.<br>
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        confirmButton.setOnAction(event -> {
            if (listPickerEvent != null)
                listPickerEvent.onPicked(selected);
        });
        closeButton.setOnAction(event -> {
            if (listPickerEvent != null)
                listPickerEvent.onCanceled();
        });
        upButton.setOnAction(event -> setSelected(selected - 1));
        downButton.setOnAction(event -> setSelected(selected + 1));
        listParent.setOnScroll(event -> {
            if (event.getDeltaY() < 0)
                setSelected(selected + 1);
            else
                setSelected(selected -1);
        });
    }

    /**
     * Interface used for handling events of ListPicker
     */

    public interface ListPickerEvent {
        void onPicked(int position);

        void onError();

        void onCanceled();
    }
}
