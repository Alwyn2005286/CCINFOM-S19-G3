import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReportsUI extends JFrame {
    public ReportsUI() {
        setTitle("Reports");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 1));

        JButton btnEstablishmentCompliance = new JButton("Establishment Compliance Trend Report");
        btnEstablishmentCompliance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EstablishmentComplianceTrendReportUI().setVisible(true);
            }
        });

        panel.add(btnEstablishmentCompliance);

        add(panel);
    }
}
