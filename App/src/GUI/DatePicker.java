package GUI;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

//this class creates the date picker to be implemented in GUI
public class DatePicker {
    public DatePicker() {
    }

    //method to create and return JDatePickerImpl object
    public JDatePickerImpl DatePicker(JPanel panel){
        // Using GridLayout to add components vertically
        panel.setLayout(new GridLayout(0, 1));

        //create UtilDateModel
        UtilDateModel model = new UtilDateModel();
        //model.setDate(20,04,2014);
        // Need this...Properties for JDatePickerImpl
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        //create JDatePickerImpl
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);

        //create JDatePickerImpl with custom data formatter
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        return datePicker;
    }
}
//custom data label formatter class
class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

    //define the Date pattern
    private String datePattern = "yyyy-MM-dd";
    //create a Date formatter with the defined pattern
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    //convert string to value
    @Override
    public Object stringToValue(String text) throws ParseException {
        //parse the input text into a Date object using the date formatter
        return dateFormatter.parseObject(text);
    }

    //convert value to string
    @Override
    public String valueToString(Object value) throws ParseException {
        //check if the value is not null
        if (value != null) {
            //cast the value to a calendar object
            Calendar cal = (Calendar) value;
            //format the calendar object to a string using thr date formatter
            return dateFormatter.format(cal.getTime());
        }
        //if the value is null, return an empty string
        return "";
    }

}

