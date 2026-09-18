import javax.swing.*;
import java.awt.*;

public class TooltipComboBoxRenderer  extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value,
                                                  int index, boolean isSelected, boolean cellHasFocus) {
        Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (c instanceof JComponent && value != null) {
            ((JComponent) c).setToolTipText(value.toString()); // Tooltip με όλο το κείμενο
        }
        return c;
    }
}