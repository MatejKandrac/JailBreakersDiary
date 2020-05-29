package org.jailbreakers.ui.listpicker;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


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

//    private Parent root;
    private VBox root;
    private ListPickerEvent listPickerEvent;
    private List<Object> items;
    private int selected = 0;

    public ListPicker(List<Object> items, int initPosition, ListPickerEvent listPickerEvent) {
        initPicker(items, initPosition, listPickerEvent);
    }

    public ListPicker(Object[] items, int initPosition, ListPickerEvent listPickerEvent){
        List<Object> parsedItems = new ArrayList<>(Arrays.asList(items));
        initPicker(parsedItems, initPosition, listPickerEvent);
    }

    private void initPicker(List<Object> items, int initPosition, ListPickerEvent listPickerEvent){
        this.items = items;
        this.listPickerEvent = listPickerEvent;
        FXMLLoader loader = new FXMLLoader();
        loader.setController(this);
        loader.setRoot(this);
        loader.setLocation(getClass().getResource("/fxml/list_picker.fxml"));
        try {
            root = loader.load();
            setSelected(initPosition);
        } catch (IOException e) {
            listPickerEvent.onError();
        }
    }

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

    public VBox getView(){
        return root;
    }

    @Override
    public void onItemClicked(int positionFromCurrent) {
        setSelected(selected-positionFromCurrent);
    }

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

    public interface ListPickerEvent {
        void onPicked(int position);

        void onError();

        void onCanceled();
    }
}
