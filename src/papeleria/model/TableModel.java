package papeleria.model;

import java.awt.Color;
import java.awt.Component;
import java.util.Vector;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class TableModel extends DefaultTableModel {

    public TableModel() {

    }

    public TableModel(int rowCount, int columnCount) {
        super(rowCount, columnCount);
    }

    public TableModel(Vector<?> columnNames, int rowCount) {
        super(columnNames, rowCount);
    }

    public TableModel(Object[] columnNames, int rowCount) {
        super(columnNames, rowCount);
    }

    public TableModel(Vector<? extends Vector> data, Vector<?> columnNames) {
        super(data, columnNames);
    }

    public TableModel(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    public static class RenderCheckBox extends JCheckBox implements TableCellRenderer {

        private final JComponent component = new JCheckBox();
        private Color colorSel = new Color(0, 200, 0);
        private Color colorUnSel = Color.LIGHT_GRAY;

        public RenderCheckBox() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

            //obtiene valor boolean y coloca valor en el JCheckBox
            boolean b = ((Boolean) value).booleanValue();
            JCheckBox box = (JCheckBox) component;
            box.setSelected(b);
            if (box.isSelected()) {
                box.setBackground(colorSel);
            } else {
                box.setBackground(colorUnSel);
            }
            return ((JCheckBox) component);
        }
    }
}
