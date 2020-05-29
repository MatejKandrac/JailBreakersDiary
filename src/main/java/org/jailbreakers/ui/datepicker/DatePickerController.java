package org.jailbreakers.ui.datepicker;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.YearMonth;
import java.util.*;

import org.jailbreakers.ui.listpicker.*;

public class DatePickerController implements Initializable, DayItem.OnDaySelected {

    @FXML
    private Pane root;

    @FXML
    private VBox pickerParent;

    @FXML
    private JFXButton yearPicker;

    @FXML
    private JFXButton monthPrevious;

    @FXML
    private JFXButton monthPicker;

    @FXML
    private JFXButton monthNext;

    @FXML
    private GridPane daysLayout;

    private ListPicker listPicker;
    private VBox dateParent;
    private final Calendar calendar = Calendar.getInstance();
    private final String[] monthNames = new String[]
            {"January", "February", "March", "April", "May", "June", "July",
                    "August", "September", "October", "November", "December"};
    private int currentMonth, currentYear, currentDay;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dateParent = pickerParent;
        setYear(calendar.get(Calendar.YEAR), false);
        setMonth(calendar.get(Calendar.MONTH));
        yearPicker.setOnAction(event -> {
            List<Object> list = new ArrayList<>();
            for (int i = 1; i <= calendar.get(Calendar.YEAR); i++)
                list.add(i);
            listPicker = new ListPicker(list, currentYear - 1, new ListPicker.ListPickerEvent() {
                @Override
                public void onPicked(int position) {
                    setYear(position + 1, true);
                    root.getChildren().set(0, dateParent);
                }

                @Override
                public void onError() {

                }

                @Override
                public void onCanceled() {
                    root.getChildren().set(0, dateParent);
                }
            });
            root.getChildren().set(0, listPicker.getView());
        });
        monthPicker.setOnAction(event -> {

            listPicker = new ListPicker(
                    currentYear == calendar.get(Calendar.YEAR) ?
                            Arrays.copyOf(monthNames, calendar.get(Calendar.MONTH) + 1) :
                            monthNames,
                    currentMonth, new ListPicker.ListPickerEvent() {
                @Override
                public void onPicked(int position) {
                    setMonth(position);
                    root.getChildren().set(0, dateParent);
                }

                @Override
                public void onError() {
                    root.getChildren().set(0, dateParent);
                }

                @Override
                public void onCanceled() {
                    root.getChildren().set(0, dateParent);
                }
            });
            root.getChildren().set(0, listPicker.getView());
        });
        monthPrevious.setOnAction(event -> setMonth(currentMonth - 1));
        monthNext.setOnAction(event -> setMonth(currentMonth + 1));
    }

    private void setMonth(int month) {
        if (month == -1) {
            setYear(currentYear - 1, false);
            currentMonth = 11;
        } else if (month == 12) {
            setYear(currentYear + 1, false);
            currentMonth = 0;
        } else
            currentMonth = month;
        monthPicker.setText(monthNames[currentMonth]);
        //noinspection MagicConstant
        monthNext.setDisable(currentMonth == calendar.get(Calendar.MONTH) && currentYear == calendar.get(Calendar.YEAR));
        updateDays();
    }

    private void setYear(int year, boolean updateDays) {
        currentYear = year;
        yearPicker.setText(String.valueOf(currentYear));
        if (calendar.get(Calendar.MONTH) < currentMonth && currentYear == calendar.get(Calendar.YEAR))
            setMonth(calendar.get(Calendar.MONTH));
        else if(updateDays)
            updateDays();
    }

    private void setDay(int day) {
        currentDay = day;
        System.out.println("SETTING DAY TO: " + currentDay);
        for (Node child : daysLayout.getChildren()) {
            if (child instanceof DayItem){
                DayItem item = (DayItem) child;
                if (item.getType() == DayItem.SELECTED_DAY && item.getPositionInMonth() != day)
                    item.deselect();
            }
        }
    }

    private void updateDays() {
        if (daysLayout.getChildren().size() > 7)
            daysLayout.getChildren().remove(7, daysLayout.getChildren().size());
        Thread th = new Thread(() -> {
            Platform.runLater(() -> {
                YearMonth pastMonth = YearMonth.of(currentYear, currentMonth == 0 ? 12 : currentMonth);
                YearMonth thisMonth = YearMonth.of(currentYear, (currentMonth + 1) == 12 ? 1 : (currentMonth + 1));
                Calendar firstDay = Calendar.getInstance();
                firstDay.set(Calendar.DAY_OF_MONTH, 1);
                firstDay.set(Calendar.MONTH, currentMonth);
                firstDay.set(Calendar.YEAR, currentYear);
                int dayOffset = (firstDay.get(Calendar.DAY_OF_WEEK) - 2) > 1 ?
                        (firstDay.get(Calendar.DAY_OF_WEEK) - 2) :
                        7 - Math.abs(firstDay.get(Calendar.DAY_OF_WEEK) - 2);
                int dayCounter = 1;
                int offsetCounter = 1;
                for (int row = 1; row < 7; row++) {
                    for (int column = 0; column < 7; column++) {
                        if (dayOffset > 0) {
                            dayOffset--;
                            daysLayout.add(new DayItem(DayItem.OTHER_MONTH_DAY, pastMonth.lengthOfMonth() - dayOffset, false), column, row);
                        }//TODO SELECT DAY WHICH WAS LAST CHOSEN
                        else if (dayCounter == currentDay && currentMonth == calendar.get(Calendar.MONTH) && currentYear == calendar.get(Calendar.YEAR)) {
                            DayItem dayItem = new DayItem(DayItem.SELECTED_DAY, dayCounter, true);
                            dayItem.setOnClickListener(this);
                            daysLayout.add(dayItem, column, row);
                            dayCounter++;
                        }
                        else if ((dayCounter <= calendar.get(Calendar.DAY_OF_MONTH)) || (dayCounter <= thisMonth.lengthOfMonth() && (currentMonth < calendar.get(Calendar.MONTH) || currentYear < calendar.get(Calendar.YEAR)))) {
                            DayItem dayItem = new DayItem(DayItem.SELECTED_DAY, dayCounter, false);
                            dayItem.setOnClickListener(this);
                            daysLayout.add(dayItem, column, row);
                            dayCounter++;
                        } else if (dayCounter <= thisMonth.lengthOfMonth()) {
                            daysLayout.add(new DayItem(DayItem.UNSELECTED_DAY, dayCounter, false), column, row);
                            dayCounter++;
                        } else {
                            daysLayout.add(new DayItem(DayItem.OTHER_MONTH_DAY, offsetCounter, false), column, row);
                            offsetCounter++;
                        }
                    }
                }
            });
        });
        th.setDaemon(true);
        th.start();
    }

    @Override
    public void onDaySelected(int positionInMonth) {
        setDay(positionInMonth);
    }
}
