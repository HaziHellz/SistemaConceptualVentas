/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package papeleria.model;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author heber
 */
public class Chart {

    DefaultPieDataset pieDataSet;
    DefaultCategoryDataset barDataSet;

    Chart(DefaultPieDataset dataSet) {
        this.pieDataSet = dataSet;
    }

    Chart(DefaultCategoryDataset barDataSet) {
        this.barDataSet = barDataSet;
    }

    public JFreeChart generateBarChart() {

        JFreeChart chart = ChartFactory.createBarChart(
                "Ventas por Dia", "Dia", "$",
                barDataSet, PlotOrientation.VERTICAL, false, true, false);
        
        return chart;
    }

    public JFreeChart generatePieChart() {

        /*
        DefaultPieDataset dataSet = new DefaultPieDataset();
        dataSet.setValue("China", 19.64);
        dataSet.setValue("India", 17.3);
        dataSet.setValue("United States", 4.54);
        dataSet.setValue("Indonesia", 3.4);
        dataSet.setValue("Brazil", 2.83);
        dataSet.setValue("Pakistan", 2.48);
        dataSet.setValue("Bangladesh", 2.38);
        
         */
        JFreeChart chart = ChartFactory.createPieChart("Distribucion de las ventas", pieDataSet, true, true, false);

        return chart;
    }
}
