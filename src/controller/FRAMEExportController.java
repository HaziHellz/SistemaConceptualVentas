/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import papeleria.model.Base;
import papeleria.model.Chart;
import papeleria.model.TableBaseDAO;
import papeleria.model.VentaDAO;
import papeleria.view.BaseTable;

/**
 *
 * @author heber
 */
public class FRAMEExportController extends MouseAdapter implements ActionListener {

    private List<JComboBox> combos;
    private JFrame gui;
    private List<JPanel> modificadores;
    private final Font fontTitulo = new Font(Font.FontFamily.COURIER, 20);
    private final Font fontTexto = new Font(Font.FontFamily.COURIER, 14);

    private int xMouse;
    private int yMouse;

    private final JButton export;
    private final List<JRadioButton> rbtnModo;

    //INDEXS DEL ARRAY DE BARRA DE NAVEGACION
    private final int MINIMIZAR = 0;
    private final int SALIR = 1;
    private final int TOGRAB = 2;

    //INDEXS DEL ARRAY DE COMBO BOXES
    private final int CBX_CONCEPTOS = 0;
    private final int CBX_INITIAL_YEAR = 1;
    private final int CBX_INITIAL_MONTH = 2;
    private final int CBX_INITIAL_DAY = 3;
    private final int CBX_FINAL_YEAR = 4;
    private final int CBX_FINAL_MONTH = 5;
    private final int CBX_FINAL_DAY = 6;

    private final HashMap meses = new HashMap();

    //INDEXS DEL ARRAY DE RADIOBUTTONS
    private final byte RBTN_MODO_MENSUAL = 0;
    private final byte RBTN_MODO_INTERVALO = 1;

    /*
    
    01 -> Enero
    02 -> Febrero
    03
    04
    05
    
    
     */
    public FRAMEExportController(List<JComboBox> combos, JFrame gui, List<JPanel> modificadores, JButton export, List<JRadioButton> rbtnModo) {
        this.combos = combos;
        this.gui = gui;
        this.modificadores = modificadores;
        this.export = export;
        this.rbtnModo = rbtnModo;
        meses.put("01", "Enero");
        meses.put("02", "Febrero");
        meses.put("03", "Marzo");
        meses.put("04", "Abril");
        meses.put("05", "Mayo");
        meses.put("06", "Junio");
        meses.put("07", "Julio");
        meses.put("08", "Agosto");
        meses.put("09", "Septiembre");
        meses.put("10", "Octubre");
        meses.put("11", "Noviembre");
        meses.put("12", "Diciembre");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //JCOMBO BOXES
        if (e.getSource() instanceof JComboBox) {

            JComboBox cbx = (JComboBox) e.getSource();

            /*SI EL COMBO DEL AÑO INICAL ES ACTIVADO, ACTUALIZA LOS CAMPOS DE LOS COMBO-BOX
              DE LOS MESES Y EL DE LOS DIAS*/
            if (cbx.equals(combos.get(CBX_INITIAL_YEAR))) {
                JComboBox cbxInitialYear = (JComboBox) e.getSource();
                JComboBox cbxInitialMonth = combos.get(CBX_INITIAL_MONTH);
                cbxInitialMonth.setModel(VentaDAO.comboModelMeses(cbxInitialYear));
                combos.get(CBX_INITIAL_DAY).setModel(getCBXModelDias(cbxInitialYear, cbxInitialMonth));
            }

            //SI EL COMBO DEL MES INICIAL ES ACTIVADO, ACTUALIZA LOS DIAS
            if (cbx.equals(combos.get(CBX_INITIAL_MONTH))) {
                combos.get(CBX_INITIAL_DAY).setModel(getCBXModelDias(combos.get(CBX_INITIAL_YEAR), cbx));
            }

            //SI EL COMBO DEL AÑO FINAL ES ACTIVADO, ACTUALIZA LOS DEMAS COMBOS FINALES
            if (cbx.equals(combos.get(CBX_FINAL_YEAR))) {
                JComboBox cbxFinalYear = combos.get(CBX_FINAL_YEAR);
                JComboBox cbxFinalMonth = combos.get(CBX_FINAL_MONTH);
                combos.get(CBX_FINAL_MONTH).setModel(VentaDAO.comboModelMeses(cbxFinalYear));
                combos.get(CBX_FINAL_DAY).setModel(getCBXModelDias(cbxFinalYear, cbxFinalMonth));
            }

            //SI EL COMBO DEL MES FINAL ES ACTIVADO, ACTUALIZA EL COMBO DEL DIA FINAL 
            if (cbx.equals(combos.get(CBX_FINAL_MONTH))) {
                combos.get(CBX_FINAL_DAY).setModel(getCBXModelDias(combos.get(CBX_FINAL_YEAR), cbx));
            }

        }

        //JBUTTONS
        if (e.getSource() instanceof JButton) {
            //PROCEDIMIENTO PARA EL BOTON EXPORTAR
            if (e.getSource().equals(export)) {
                monthlyExport();

            }
        }

        //JRADIO BUTTONS
        if (e.getSource() instanceof JRadioButton) {
            JRadioButton rbtn = (JRadioButton) e.getSource();
            if (rbtn.equals(rbtnModo.get(RBTN_MODO_MENSUAL))) {
                //SE ACTUALIZA LA VISTA PARA SELECCIONAR EL MES
                combos.get(CBX_INITIAL_DAY).setEnabled(false);
                combos.get(CBX_FINAL_YEAR).setEnabled(false);
                combos.get(CBX_FINAL_MONTH).setEnabled(false);
                combos.get(CBX_FINAL_DAY).setEnabled(false);
            }

            if (rbtn.equals(rbtnModo.get(RBTN_MODO_INTERVALO))) {
                //SE ACTUALIZA LA VISTA PARA SELECCIONAR EL INTERVALO
                combos.get(CBX_INITIAL_DAY).setEnabled(true);
                combos.get(CBX_FINAL_YEAR).setEnabled(true);
                combos.get(CBX_FINAL_MONTH).setEnabled(true);
                combos.get(CBX_FINAL_DAY).setEnabled(true);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() instanceof JPanel) {
            JPanel boton = (JPanel) e.getSource();
            if (boton.equals(modificadores.get(MINIMIZAR))) {
                minimizarVentana();
            } else if (boton.equals(modificadores.get(SALIR))) {
                gui.setVisible(false);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource().equals(modificadores.get(TOGRAB))) {
            xMouse = e.getX();
            yMouse = e.getY();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() instanceof JPanel) {
            if (e.getSource().equals(modificadores.get(SALIR))) {
                modificadores.get(SALIR).setBackground(Color.red);
            } else if (e.getSource().equals(modificadores.get(MINIMIZAR))) {
                modificadores.get(MINIMIZAR).setBackground(Color.lightGray);
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() instanceof JPanel) {
            if (e.getSource().equals(modificadores.get(SALIR))) {
                modificadores.get(SALIR).setBackground(new java.awt.Color(153, 153, 153));
                /*} else if (e.getSource().equals(modificadores.get(MAXIMIZAR))) {
                modificadores.get(MAXIMIZAR).setBackground(new java.awt.Color(153, 153, 153));
                 */
            } else if (e.getSource().equals(modificadores.get(MINIMIZAR))) {
                modificadores.get(MINIMIZAR).setBackground(new java.awt.Color(153, 153, 153));
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getSource().equals(modificadores.get(TOGRAB))) {
            int x = e.getXOnScreen();
            int y = e.getYOnScreen();
            gui.setLocation(x - xMouse, y - yMouse);
        }
    }

    public void minimizarVentana() {
        gui.setExtendedState(BaseTable.ICONIFIED);

    }

    public static DefaultComboBoxModel getCBXModelDias(JComboBox comboYear, JComboBox comboMonth) {
        Object[] days;
        String treintaYUno = " 01, 03, 05, 07, 08, 10, 12";
        String treinta = " 04, 06, 09, 11";
        String month = comboMonth.getSelectedItem().toString();

        //SI EL MES ES ALGUNO DE LOS QUE TIENE 31 DIAS, 
        if (treintaYUno.contains(month)) {
            days = new Object[31];

            //LLENA EL ARREGLO CON LOS 31 DIAS
            for (int i = 0; i < 31; i++) {
                days[i] = i + 1;
            }

            //SI EL MES TIENE 30 DIAS
        } else if (treinta.contains(month)) {
            //30 dias
            //LLENA EL ARREGLO CON 30 DIAS
            days = new Object[30];
            for (int i = 0; i < 30; i++) {
                days[i] = i + 1;
            }

        } else {
            //SI NO ES NINGUNO ANTERIOR, SIGNIFICA QUE ES FEBRERO

            //SI ES BICIESTO, TIENE 29 DIAS, SI NO, TIENE 28
            int febrero = (Integer.parseInt(comboYear.getSelectedItem().toString()) % 4 == 0) ? 29 : 28;

            //LLENA EL ARREGLO CON 28 O 29 DIAS
            days = new Object[febrero];
            for (int i = 0; i < febrero; i++) {
                days[i] = i + 1;
            }

        }

        //RETORNA EL MODELO DEL COMBO BOX PARA LOS DIAS
        return new DefaultComboBoxModel(days);

    }

    public static DefaultComboBoxModel getDiasDelMes(JComboBox comboYear, JComboBox comboMonth) {
        Object[] days;
        String treintaYUno = " 01, 03, 05, 07, 08, 10, 12";
        String treinta = " 04, 06, 09, 11";
        String month = comboMonth.getSelectedItem().toString();

        //SI EL MES ES ALGUNO DE LOS QUE TIENE 31 DIAS, 
        if (treintaYUno.contains(month)) {
            days = new Object[31];

            //LLENA EL ARREGLO CON LOS 31 DIAS
            for (int i = 0; i < 31; i++) {
                days[i] = i + 1;
            }

            //SI EL MES TIENE 30 DIAS
        } else if (treinta.contains(month)) {
            //30 dias
            //LLENA EL ARREGLO CON 30 DIAS
            days = new Object[30];
            for (int i = 0; i < 30; i++) {
                days[i] = i + 1;
            }

        } else {
            //SI NO ES NINGUNO ANTERIOR, SIGNIFICA QUE ES FEBRERO

            //SI ES BICIESTO, TIENE 29 DIAS, SI NO, TIENE 28
            int febrero = (Integer.parseInt(comboYear.getSelectedItem().toString()) % 4 == 0) ? 29 : 28;

            //LLENA EL ARREGLO CON 28 O 29 DIAS
            days = new Object[febrero];
            for (int i = 0; i < febrero; i++) {
                days[i] = i + 1;
            }

        }

        //RETORNA EL MODELO DEL COMBO BOX PARA LOS DIAS
        return new DefaultComboBoxModel(days);

    }

    private void monthlyExport() {
        double totalConcepto;
        BufferedImage bufferedImage;
        ByteArrayOutputStream baos;
        Image pieChartImage;
        DefaultPieDataset pieDataSet = new DefaultPieDataset();
        PiePlot plot;
        JFreeChart chart;
        Document document = new Document(PageSize.LETTER);
        Paragraph parrafo;
        String year = combos.get(CBX_INITIAL_YEAR).getSelectedItem().toString();
        String month = combos.get(CBX_INITIAL_MONTH).getSelectedItem().toString();
        String conceptoDeVentas = combos.get(CBX_CONCEPTOS).getSelectedItem().toString();
        String path = System.getProperty("user.home") + "/OneDrive/Documentos/Reportes Papeleria/Reporte-" + combos.get(CBX_CONCEPTOS).getSelectedItem().toString()
                + "-" + meses.get(combos.get(CBX_INITIAL_MONTH).getSelectedItem().toString())
                + "-" + combos.get(CBX_INITIAL_YEAR).getSelectedItem().toString()
                + ".pdf";

        try {
            // CREA EL WRITER
            PdfWriter.getInstance(document, new FileOutputStream(path));
            // AGREGAMOS METADATOS AL DOCUMENTO
            document.addAuthor("Papeleria MG");
            document.addSubject("Analisis de Ventas");
            document.addCreator("Papeleria MG");
            // SE ABRE EL DOCUMENTO PARA ESCRIBIR
            document.open();
            document.add(new Paragraph("Papeleria MG", fontTitulo));
            //SI EL CONCEPTO ES "TODO", SE AGREGAN TODOS LOS CONCEPTOS Y SU CANTIDAD
            if (conceptoDeVentas.equals("Todo")) {
                List<Base> conceptos = TableBaseDAO.getTodosTipos();

                for (Base concepto : conceptos) {
                    totalConcepto = VentaDAO.ventaMensual(year, month, concepto.getNombreBase(), "Registradas");
                    if (totalConcepto > 0) {
                        parrafo = new Paragraph("Ventas de " + concepto.getNombreBase() + ": $" + totalConcepto, fontTexto);
                        parrafo.setAlignment(Element.ALIGN_RIGHT);
                        document.add(parrafo);

                        pieDataSet.setValue(concepto.getNombreBase(), totalConcepto);
                    }

                }
                //AL FINAL SE CONSULTA EL TOTAL Y SE ESCRIBE
                parrafo = new Paragraph("Total " + combos.get(CBX_CONCEPTOS).getSelectedItem().toString() + ": $" + VentaDAO.ventaMensual(year, month, "Todo", "Registradas"), fontTexto);
                parrafo.setAlignment(Element.ALIGN_RIGHT);
                document.add(parrafo);

                chart = ChartFactory.createPieChart("", pieDataSet, true, true, false);
                plot = (PiePlot) chart.getPlot();
                plot.setCircular(true);

                PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator("{0}: ${1} ({2})", new DecimalFormat("0.0"), new DecimalFormat("0.00%"));
                plot.setLabelGenerator(gen);
                
                for (Base concepto : conceptos) {
                    plot.setExplodePercent(concepto.getNombreBase(), 0.10);
                }

                bufferedImage = chart.createBufferedImage(545, 250);
                baos = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "png", baos);
                pieChartImage = Image.getInstance(baos.toByteArray());
                document.add(pieChartImage);

            } else {
                //SI EL CONCEPTO NO ES "TODO", SE CALCULA SÓLO EL CONCEPTO
                parrafo = new Paragraph("Venta de " + combos.get(CBX_CONCEPTOS).getSelectedItem().toString() + ": " + VentaDAO.ventaMensual(year, month, combos.get(CBX_CONCEPTOS).getSelectedItem().toString(), "Registradas"), fontTexto);
                parrafo.setAlignment(Element.ALIGN_RIGHT);
                document.add(parrafo);
            }

        } catch (DocumentException de) {
            System.err.println(de.getMessage());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FRAMEExportController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FRAMEExportController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //SE CIERRA EL DOCUMENTO
        document.close();
        try {
            //SE ABRE EL PDF CREADO
            File archivo = new File(path);
            Desktop.getDesktop().open(archivo);
        } catch (IOException ex) {
            Logger.getLogger(FRAMEExportController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
