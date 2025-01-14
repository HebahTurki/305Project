package Implementation;

import com.lowagie.text.DocumentException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.jsoup.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xhtmlrenderer.extend.FontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

//create a pie chart here to display in statics page
//also save as a file here if possible

public class ExpenseOverview  {
    //FILE IO will be used here.
    private DefaultPieDataset pieDataset;
    private JFreeChart pieChart;
    private ChartPanel chartPanel;
    DefaultTableModel model;
    JTable table;
    DefaultListModel dlm = new DefaultListModel();


    //generates a JScrollPane containing the expense history
    public JScrollPane scrollPane(){
        JScrollPane scrollPane = new JScrollPane(UserDistrubiter.getUser().getHistoryList());
        return scrollPane;
    }
    //generates a pie chart displaying the expense overview and saves it as a PNG file
    public ChartPanel showPie(){
        //generate a scroll pane containing the expense history
        scrollPane();
        try {
            //retrieve expense data from the database as a JTable
            table = UserDistrubiter.user.getTable();
            //initialize a dataset to store expense data for the pie chart
            pieDataset = new DefaultPieDataset();

            //initialize variables to accumulate total expenses for each category
            double bills = 0,shopping = 0,food = 0;

            //iterate through each row of the expense data
            for (int i = 0; i < table.getRowCount(); i++){
                //extract the expense category and amount from the table
                String name = table.getValueAt(i,0).toString();
                double amount = Double.valueOf(table.getValueAt(i,1).toString());

                //accumulate expenses based on the category name
                //and set values for the pie chart dataset
                switch (name.toLowerCase()){
                    case "bills" -> {
                        bills +=amount;
                        pieDataset.setValue(name,bills);
                    }
                    case "shopping" -> {
                        shopping +=amount;
                        pieDataset.setValue(name,shopping);
                    }

                    case "food" -> {
                        food +=amount;
                        pieDataset.setValue(name,food);
                    }

                }


            }

            //create a pie chart titled "Expenses Overview" using the dataset
            pieChart = ChartFactory.createPieChart("Expenses Overview",pieDataset,true,true,true);
            pieChart.getPlot();
            //wrap the pie chart in a chartPanel for display
            chartPanel = new ChartPanel(pieChart);

        } catch (SQLException e) {
            //throw a runtime exception if an SQL exception occurs
            throw new RuntimeException(e);
        }

        try {
            //save the pie chart as a PNG image named "PieChart.png"
            OutputStream out = new FileOutputStream("PieChart.png");
            ChartUtils.writeChartAsPNG(out, pieChart, 600, 300);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //return the chartPanel containing the generated pie chart
        return chartPanel;
    }
    //here we create an HTML then save it as PDF following the user request
    public void saveAsPDF() throws IOException {
        //create a two-dimensional array to store the expense history data
        String[][] historyArr = new String[table.getRowCount()][table.getColumnCount()];

        //get the current date and time in the specified format
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String date = dtf.format(now);

        //create string builder to construct the HTML content for the PDF
        StringBuilder buf = new StringBuilder();
        buf.append("<html>" +
                "<h1 style=\"color: #5e9ca0;\">Your Expense Overiew</h1>" +
                "<p><img src=\"PieChart.png\"></p>"+
                "<h2 style=\"color: #2e6c80;\">Date: "+date+"</h2>"+
                "<h2 style=\"color: #2e6c80;\">History:</h2>"+
                "<table> <tr>\n" +
                "<td style=\"width: 50%;\"><strong>Category</strong></td>\n" +
                "<td style=\"width: 50%;\"><strong>Amount</strong></td>\n" +
                "</tr>");
        //iterate through the expense history date to build the HTML table
        for (int i = 0; i < historyArr.length; i++){
            buf.append("<tr>");
            for (int j = 0; j < historyArr[i].length; j++){
                buf.append("<td>").append(""+table.getValueAt(i,j)).append("</td><td>");
            }
            buf.append("</tr>");
        }
        buf.append("</tbody>\n" +
                "</table>\n" +
                "<p>End of Overview</p>"+
                "</html>");

        //convert the StringBuilder content to a String
        String html = buf.toString();

        //convert HTML to XHTML format for compatibility with iTextRenderer
        String xhtml = htmlToXhtml(html);

        //create a file to save the pdf
        String filename = "ExpenseOverview.pdf";
        File outputPdf = new File(filename);
        //generate the PDF document from the XHTML content using iTextRenderer
        try (OutputStream outputStream = new FileOutputStream(outputPdf)) {
            ITextRenderer iTextRenderer = new ITextRenderer();
            iTextRenderer.setDocumentFromString(xhtml);
            iTextRenderer.layout();
            //after calling this method, the PDF representation of the HTML content will be written to the specified output stream (outputStream)
            iTextRenderer.createPDF(outputStream);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }

    }
    // a method for converting html to xhtml to use iTextRenderer methods
    private String htmlToXhtml(String html) {
        //parse the HTML content using Jsoup
        Document document = Jsoup.parse(html);
        //set the output syntax to XML for XHTML compatibility
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        return document.html();
    }

}
