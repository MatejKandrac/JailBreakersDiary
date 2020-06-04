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

import org.jailbreakers.obj.StageHandler;
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
    public static final String[] monthNames = new String[]
            {"January", "February", "March", "April", "May", "June", "July",
                    "August", "September", "October", "November", "December"};
    private int currentMonth, currentYear, currentDay, shownMonth, shownYear;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dateParent = pickerParent;
        setYear(calendar.get(Calendar.YEAR), false);
        setMonth(calendar.get(Calendar.MONTH));
        yearPicker.setOnAction(event -> {
            List<Object> list = new ArrayList<>();
            for (int i = 1; i <= calendar.get(Calendar.YEAR); i++)
                list.add(i);
            listPicker = new ListPicker(list, shownYear - 1, new ListPicker.ListPickerEvent() {
                @Override
                public void onPicked(int position) {
                    setYear(position + 1, true);
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
        monthPicker.setOnAction(event -> {

            listPicker = new ListPicker(
                    shownYear == calendar.get(Calendar.YEAR) ?
                            Arrays.copyOf(monthNames, calendar.get(Calendar.MONTH) + 1) :
                            monthNames,
                    shownMonth, new ListPicker.ListPickerEvent() {
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
        monthPrevious.setOnAction(event -> setMonth(shownMonth - 1));
        monthNext.setOnAction(event -> setMonth(shownMonth + 1));
    }

    private void setMonth(int month) {
        if (month == -1) {
            setYear(shownYear - 1, false);
            shownMonth = 11;
        } else if (month == 12) {
            setYear(shownYear + 1, false);
            shownMonth = 0;
        } else
            shownMonth = month;
        monthPicker.setText(monthNames[shownMonth]);
        //noinspection MagicConstant
        monthNext.setDisable(shownMonth == calendar.get(Calendar.MONTH) && shownYear == calendar.get(Calendar.YEAR));
        updateDays();
    }

    private void setYear(int year, boolean updateDays) {
        shownYear = year;
        yearPicker.setText(String.valueOf(shownYear));
        if (year < calendar.get(Calendar.YEAR))
            monthNext.setDisable(false);
        if (calendar.get(Calendar.MONTH) < shownMonth && shownYear == calendar.get(Calendar.YEAR))
            setMonth(calendar.get(Calendar.MONTH));
        else if (updateDays)
            updateDays();
    }

    private void setDay(int day) {
        currentDay = day;
        currentMonth = shownMonth;
        currentYear = shownYear;
        StageHandler handler = StageHandler.getInstance();
        handler.getOnDatePickedListener().onDatePicked(shownYear, shownMonth, currentDay);
        for (Node child : daysLayout.getChildren()) {
            if (child instanceof DayItem) {
                DayItem item = (DayItem) child;
                if (item.getType() == DayItem.SELECTED_DAY && item.getPositionInMonth() != day)
                    item.deselect();
            }
        }
    }

    private void updateDays() {
        if (daysLayout.getChildren().size() > 7)
            daysLayout.getChildren().remove(7, daysLayout.getChildren().size());
        Thread th = new Thread(() -> Platform.runLater(() -> {
            YearMonth pastMonth = YearMonth.of(shownYear, shownMonth == 0 ? 12 : shownMonth);
            YearMonth thisMonth = YearMonth.of(shownYear, (shownMonth + 1) == 12 ? 1 : (shownMonth + 1));
            Calendar firstDay = Calendar.getInstance();
            firstDay.set(Calendar.DAY_OF_MONTH, 1);
            firstDay.set(Calendar.MONTH, shownMonth);
            firstDay.set(Calendar.YEAR, shownYear);
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
                    } else if (dayCounter == currentDay && shownMonth == currentMonth && shownYear == currentYear) {
                        DayItem dayItem = new DayItem(DayItem.SELECTED_DAY, dayCounter, true);
                        dayItem.setOnClickListener(this);
                        daysLayout.add(dayItem, column, row);
                        dayCounter++;
                    } else if ((dayCounter <= calendar.get(Calendar.DAY_OF_MONTH)) || (dayCounter <= thisMonth.lengthOfMonth() && (shownMonth < calendar.get(Calendar.MONTH) || shownYear < calendar.get(Calendar.YEAR)))) {
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
        }));
        th.setDaemon(true);
        th.start();
    }

    @Override
    public void onDaySelected(int positionInMonth) {
        setDay(positionInMonth);
    }

    public interface OnDatePickedListener {
        void onDatePicked(int year, int month, int day);
    }
}
