package PlacementManagement.Ui.AdminPages;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;
import PlacementManagement.database.ReportDAO;

public class ReportsFrame extends JFrame {
    private ReportDAO reportDAO = new ReportDAO();
    
    public ReportsFrame(JFrame previous) {
        setTitle("Placement Statistics Report");
        setSize(950, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));
        
        // Main container with padding
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        
        // Title
        JLabel title = new JLabel("Placement Statistics", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        mainPanel.add(title, BorderLayout.NORTH);
        
        // Center panel for stats and table
        JPanel centerContainer = new JPanel(new BorderLayout(0, 15));
        
        // Overall stats panel
        JPanel stats = new JPanel(new GridLayout(2, 4, 15, 15));
        stats.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                "Summary Statistics",
                0,
                0,
                new Font("Arial", Font.BOLD, 16)
            ),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        int total = reportDAO.getTotalStudents();
        int placed = reportDAO.getPlacedStudents();
        int unplaced = total - placed;
        double percent = total == 0 ? 0 : (placed * 100.0) / total;
        
        stats.add(info("Total Students", total, new Color(70, 130, 180)));
        stats.add(info("Placed Students", placed, new Color(60, 179, 113)));
        stats.add(info("Unplaced Students", unplaced, new Color(220, 20, 60)));
        stats.add(info("Placement Rate", String.format("%.1f%%", percent), new Color(255, 140, 0)));
        
        int applied = reportDAO.getApplicationsByStatus("applied");
        int shortlisted = reportDAO.getApplicationsByStatus("shortlisted");
        int selected = reportDAO.getApplicationsByStatus("selected");
        int rejected = reportDAO.getApplicationsByStatus("rejected");
        
        stats.add(info("Total Applications", applied + shortlisted + selected + rejected, new Color(70, 130, 180)));
        stats.add(info("Shortlisted", shortlisted, new Color(255, 165, 0)));
        stats.add(info("Selected", selected, new Color(60, 179, 113)));
        stats.add(info("Rejected", rejected, new Color(169, 169, 169)));
        
        centerContainer.add(stats, BorderLayout.NORTH);
        
        // Department-wise table
        JPanel deptPanel = new JPanel(new BorderLayout());
        deptPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                "Department-wise Placement Statistics",
                0,
                0,
                new Font("Arial", Font.BOLD, 16)
            ),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        String[] cols = {"Department", "Total Students", "Placed", "Placement %"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        List<String[]> rows = reportDAO.getDepartmentStats();
        for (String[] row : rows) model.addRow(row);
        
        JTable table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(70, 130, 180));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getWidth(), 35));
        
        // Center align table cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        deptPanel.add(scrollPane, BorderLayout.CENTER);
        
        centerContainer.add(deptPanel, BorderLayout.CENTER);
        
        mainPanel.add(centerContainer, BorderLayout.CENTER);
        
        // Back button
        JButton back = new JButton("Back to Dashboard");
        back.setFont(new Font("Arial", Font.PLAIN, 14));
        back.setPreferredSize(new Dimension(160, 38));
        back.setBackground(new Color(70, 130, 180));
        back.setForeground(Color.WHITE);
        back.setFocusPainted(false);
        back.setCursor(new Cursor(Cursor.HAND_CURSOR));
        back.addActionListener(e -> {
            previous.setVisible(true);
            dispose();
        });
        
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottom.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        bottom.add(back);
        
        mainPanel.add(bottom, BorderLayout.SOUTH);
        
        add(mainPanel);
        setVisible(true);
    }
    
    private JPanel info(String title, Object value, Color accentColor) {
        JPanel p = new JPanel(new BorderLayout(5, 5));
        p.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(15, 10, 15, 10)
        ));
        p.setBackground(Color.WHITE);
        
        JLabel t = new JLabel(title, SwingConstants.CENTER);
        t.setFont(new Font("Arial", Font.PLAIN, 13));
        t.setForeground(new Color(100, 100, 100));
        
        JLabel v = new JLabel(value.toString(), SwingConstants.CENTER);
        v.setFont(new Font("Arial", Font.BOLD, 26));
        v.setForeground(accentColor);
        
        p.add(t, BorderLayout.NORTH);
        p.add(v, BorderLayout.CENTER);
        
        return p;
    }
}