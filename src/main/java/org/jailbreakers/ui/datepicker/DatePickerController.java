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

/**
 * <h1>DatePicker where user can pick a date but only until today.</h1>
 * DatePicker also uses {@link ListPicker} when choosing year or month manually.<br>
 *
 * @see ListPicker
 * @see DayItem.OnDaySelected
 * @see org.jailbreakers.obj.Layout
 * @see Initializable
 * @author JailBreakersTeam (Matej Kandráč, Martin Ragan, Ján Kočíš)
 * @version 1.0
 * @since 1.6.202
 */

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

    /**
     * ListPicker used when picking month or year.
     */
    private ListPicker listPicker;
    /**
     * Saved DatePicker parent when switching to listPicker view.
     */
    private VBox dateParent;
    /**
     * Calendar instance which holds information about today.
     */
    private final Calendar calendar = Calendar.getInstance();
    /**
     * Constant array holding names of months.
     */
    public static final String[] monthNames = new String[]
            {"January", "February", "March", "April", "May", "June", "July",
                    "August", "September", "October", "November", "December"};
    /**
     * Variables with shown key word are currently shown on screen but current key word means selected date.
     */
    private int currentMonth, currentYear, currentDay, shownMonth, shownYear;

    /**
     * Initializes all necessary events.<br>
     * It saves parent of DatePicker because it would get lost if switching to ListPicker.<br>
     * It also sets current year without updating view because they will get updated right after setting month
     * to current month.<br>
     * If user chooses to switch to new year, {@link ListPicker} is created with year values.<br>
     * Same goes for months except months cannot be set to future months.<br>
     * Month next and previous buttons set current month up or down.<br>
     */
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

    /**
     * Method sets new month and updates visible days.<br>
     * It also handles next and previous year calls.<br>
     * Method also sets text of selected month to its name.<br>
     * If current month is today's month and today's year, next button is disabled to ensure no future days will be shown.<br>
     * @param month index of month in range 0-11
     */

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

    /**
     * Method updates selected year.<br>
     * It sets the text of selected year to corresponding value and if year is not today's year, next month button is
     * enabled.<br>
     * If next year is selected and month is selected to future month, it automatically sets month to today.
     * @param year is year to be selected
     * @param updateDays means if you want or dont want to update visible days after switching year
     */

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

    /**
     * Sets the day to the one we want.<br>
     * Method updates selected date and notifies listener that new day was selected.<br>
     * It also deselects day if it is active to ensure that only one day will be selected.
     * @param day is selected day
     */

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

    /**
     * Method updates visible days to user.<br>
     * First off id clears children which are shown now but ignores first seven because these are month names
     * (mo, tu, we....).
     * Since it is a different thread, {@link Platform#runLater(Runnable)} must handle view changes.<br>
     * Using {@link YearMonth} class we calculate number of days in past and current month.<br>
     * {@link Calendar} class is used to determine which day is the first day of month.<br>
     * {@link Calendar#DAY_OF_WEEK} returns position of day in week but as follows: sunday - 1 monday - 2 tuesday - 3, so
     * we calculate number of days from past month to show by simple equation.<br>
     * Example: if first day of month is sunday: 7 - abs(1 - 2) = 6<br>
     * First children to be added are the ones from past month, that's what dayOffset (number of days from past month) is.<br>
     * If day to be added is selected date, it creates the day with SELECTABLE attribute and is set as current.<br>
     * All days in month which were in the past are considered SELECTABLE but not current.<br>
     * All other days in month are UNSELECTABLE.<br>
     * Last days are ones from next month.<br>
     */

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
            System.out.println(firstDay.get(Calendar.DAY_OF_WEEK));
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

    /**
     * Method gets called from {@link DayItem} when it is clicked.
     * @param positionInMonth is position of selected day in month
     */

    @Override
    public void onDaySelected(int positionInMonth) {
        setDay(positionInMonth);
    }

    /**
     * Interface used for handling date pick event.
     */

    public interface OnDatePickedListener {
        void onDatePicked(int year, int month, int day);
    }
}
