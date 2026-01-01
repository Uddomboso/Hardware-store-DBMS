/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.hardwarestoredbms.GUI;

/**
 *
 * @author asus
 */
import com.mycompany.hardwarestoredbms.Supplier;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SupplierF extends javax.swing.JFrame {
    
    private PlaceholderTextField searchField;
    private JPanel inputCardPanel;
    private TableRowSorter<DefaultTableModel> tableSorter;
    private DefaultTableModel tableModel;
    private JPanel leftPanelContainer;
    private JPanel rightPanelContainer;
    private JTextArea addressArea;

    public SupplierF() {
        initComponents(); 
        setupCustomLogic(); 
        setupUIEnhancements();
        refreshTable(); 
        setupMenuActions();
    }
    
    private void setupUIEnhancements() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {}
        
        setLocationRelativeTo(null);
        enhanceTable();
        enhanceButtons();
        enhanceTextFields();
        reorganizeLayout();
        createInputCard();
        setupSearchBar();
        setTitle("Hardware Store - Supplier Management");
        setMinimumSize(new Dimension(1200, 700));
    }
    
    private void enhanceTable() {
        suppliertable.setRowHeight(28);
        suppliertable.setSelectionBackground(new Color(70, 130, 180));
        suppliertable.setSelectionForeground(Color.WHITE);
        suppliertable.setGridColor(new Color(220, 220, 220));
        suppliertable.setShowGrid(true);
        suppliertable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        JTableHeader header = suppliertable.getTableHeader();
        header.setBackground(new Color(34, 49, 63)); // Even darker for better contrast
        header.setForeground(new Color(255, 255, 255)); // Pure white
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setReorderingAllowed(false);
        header.setPreferredSize(new Dimension(header.getWidth(), 35));
        
        // Ensure header cells are also styled
        header.setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(new Color(34, 49, 63));
                c.setForeground(Color.WHITE);
                c.setFont(new Font("Segoe UI", Font.BOLD, 13));
                ((JLabel) c).setHorizontalAlignment(JLabel.CENTER);
                return c;
            }
        });
        
        tableModel = (DefaultTableModel) suppliertable.getModel();
        tableSorter = new TableRowSorter<>(tableModel);
        suppliertable.setRowSorter(tableSorter);
        
        suppliertable.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    c.setBackground(new Color(70, 130, 180));
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 249, 250));
                    c.setForeground(new Color(33, 37, 41));
                }
                c.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                ((JLabel) c).setHorizontalAlignment(JLabel.LEFT);
                return c;
            }
        });
    }
    
    private void enhanceButtons() {
        styleButton(jButton2, new Color(76, 175, 80), Color.WHITE, "Add Supplier", "+");
        styleButton(jButton3, new Color(33, 150, 243), Color.WHITE, "Update Supplier", "✎");
        styleButton(jButton4, new Color(239, 83, 80), Color.WHITE, "Delete Supplier", "✕");
        styleButton(jButton6, new Color(38, 198, 218), Color.WHITE, "Refresh Table", "↻");
        styleButton(jButton5, new Color(255, 167, 38), Color.WHITE, "Backup Data", "💾");
        styleButton(jButton1, new Color(171, 71, 188), Color.WHITE, "Restore Data", "↺");
    }
    
    private void styleButton(JButton button, Color bgColor, Color fgColor, String tooltip, String icon) {
        button.setText(icon + " " + button.getText());
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(new Font("Segoe UI", Font.BOLD, 11));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setToolTipText(tooltip);
        button.setPreferredSize(new Dimension(110, 38));
        button.setMinimumSize(new Dimension(110, 38));
        button.setMaximumSize(new Dimension(110, 38));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            Color originalColor = bgColor;
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor);
            }
        });
    }
    
    private void enhanceTextFields() {
        jLabel12.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel7.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel13.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel14.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel15.setHorizontalAlignment(SwingConstants.RIGHT);
        
        int labelWidth = 140;
        jLabel12.setPreferredSize(new Dimension(labelWidth, 25));
        jLabel7.setPreferredSize(new Dimension(labelWidth, 25));
        jLabel13.setPreferredSize(new Dimension(labelWidth, 25));
        jLabel14.setPreferredSize(new Dimension(labelWidth, 25));
        jLabel15.setPreferredSize(new Dimension(labelWidth, 25));
        
        JTextField[] textFields = {jTextField1, jTextField5, jTextField7, jTextField8, jTextField9, jTextField10};
        for (JTextField field : textFields) {
            styleTextField(field);
        }
        
        // Convert address field to text area
        addressArea = new JTextArea(3, 20);
        addressArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        addressArea.setBackground(Color.WHITE);
        addressArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        addressArea.setLineWrap(true);
        addressArea.setWrapStyleWord(true);
        
        addressArea.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                addressArea.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(25, 118, 210), 2),
                    BorderFactory.createEmptyBorder(7, 9, 7, 9)
                ));
            }
            @Override
            public void focusLost(FocusEvent e) {
                addressArea.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                    BorderFactory.createEmptyBorder(8, 10, 8, 10)
                ));
            }
        });
    }
    
    private void styleTextField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        field.setBackground(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(25, 118, 210), 2),
                    BorderFactory.createEmptyBorder(5, 9, 5, 9)
                ));
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (!field.getBorder().toString().contains("255,0,0")) {
                    field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                        BorderFactory.createEmptyBorder(6, 10, 6, 10)
                    ));
                }
            }
        });
    }
    
    private void highlightErrorField(JTextField field) {
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(239, 83, 80), 2),
            BorderFactory.createEmptyBorder(5, 9, 5, 9)
        ));
        field.requestFocus();
    }
    
    private void reorganizeLayout() {
        getContentPane().removeAll();
        getContentPane().setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(new Color(245, 247, 250));
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(52, 73, 94));
        headerPanel.add(jTextField1, BorderLayout.WEST);
        headerPanel.setPreferredSize(new Dimension(getWidth(), 70));
        
        JPanel mainPanel = new JPanel(new BorderLayout(15, 0));
        mainPanel.setBackground(new Color(245, 247, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        leftPanelContainer = new JPanel(new BorderLayout());
        leftPanelContainer.setBackground(new Color(245, 247, 250));
        leftPanelContainer.setPreferredSize(new Dimension(420, getHeight()));
        
        rightPanelContainer = new JPanel(new BorderLayout(0, 10));
        rightPanelContainer.setBackground(new Color(245, 247, 250));
        
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        tablePanel.add(jScrollPane1, BorderLayout.CENTER);
        rightPanelContainer.add(tablePanel, BorderLayout.CENTER);
        
        mainPanel.add(leftPanelContainer, BorderLayout.WEST);
        mainPanel.add(rightPanelContainer, BorderLayout.CENTER);
        
        getContentPane().add(headerPanel, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
    }
    
    private void createInputCard() {
        inputCardPanel = new JPanel();
        inputCardPanel.setBackground(Color.WHITE);
        inputCardPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            "Supplier Information",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 13),
            new Color(52, 73, 94)
        ));
        inputCardPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 15, 8, 15);
        gbc.anchor = GridBagConstraints.WEST;
        
        JLabel supplierIdLabel = new JLabel("Supplier ID:");
        supplierIdLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        supplierIdLabel.setForeground(new Color(52, 73, 94));
        supplierIdLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        supplierIdLabel.setPreferredSize(new Dimension(140, 25));
        
        gbc.gridx = 0; gbc.gridy = 0;
        inputCardPanel.add(supplierIdLabel, gbc);
        gbc.gridx = 1;
        jTextField1.setPreferredSize(new Dimension(200, 30));
        inputCardPanel.add(jTextField1, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        inputCardPanel.add(jLabel12, gbc);
        gbc.gridx = 1;
        jTextField7.setPreferredSize(new Dimension(250, 30));
        inputCardPanel.add(jTextField7, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        inputCardPanel.add(jLabel7, gbc);
        gbc.gridx = 1;
        jTextField5.setPreferredSize(new Dimension(250, 30));
        inputCardPanel.add(jTextField5, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        inputCardPanel.add(jLabel13, gbc);
        gbc.gridx = 1;
        jTextField8.setPreferredSize(new Dimension(250, 30));
        inputCardPanel.add(jTextField8, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        inputCardPanel.add(jLabel14, gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        addressArea.setPreferredSize(new Dimension(250, 60));
        inputCardPanel.add(addressArea, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        inputCardPanel.add(jLabel15, gbc);
        gbc.gridx = 1;
        jTextField10.setPreferredSize(new Dimension(200, 30));
        inputCardPanel.add(jTextField10, gbc);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(jButton2);
        buttonPanel.add(jButton3);
        buttonPanel.add(jButton4);
        
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 15, 8, 15);
        inputCardPanel.add(buttonPanel, gbc);
        
        JPanel utilityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        utilityPanel.setBackground(Color.WHITE);
        utilityPanel.add(jButton6);
        utilityPanel.add(jButton5);
        utilityPanel.add(jButton1);
        
        gbc.gridy = 7;
        gbc.insets = new Insets(5, 15, 15, 15);
        inputCardPanel.add(utilityPanel, gbc);
        
        if (leftPanelContainer != null) {
            leftPanelContainer.removeAll();
            leftPanelContainer.add(inputCardPanel, BorderLayout.CENTER);
            leftPanelContainer.revalidate();
            leftPanelContainer.repaint();
        }
    }
    
    private void setupSearchBar() {
        searchField = new PlaceholderTextField();
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(8, 35, 8, 10)
        ));
        searchField.setPreferredSize(new Dimension(300, 40));
        searchField.setPlaceholder("Search by name, email, phone, or ID...");
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filterTable(searchField.getText());
            }
        });
        
        JLabel searchIcon = new JLabel("🔍");
        searchIcon.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchIcon.setForeground(new Color(150, 150, 150));
        searchIcon.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        
        JPanel searchWrapper = new JPanel(new BorderLayout());
        searchWrapper.setBackground(Color.WHITE);
        searchWrapper.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEmptyBorder(),
            "Search Suppliers",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 11),
            new Color(100, 100, 100)
        ));
        searchWrapper.add(searchIcon, BorderLayout.WEST);
        searchWrapper.add(searchField, BorderLayout.CENTER);
        
        if (rightPanelContainer != null) {
            rightPanelContainer.remove(0);
            rightPanelContainer.add(searchWrapper, BorderLayout.NORTH);
            JPanel tablePanel = new JPanel(new BorderLayout());
            tablePanel.setBackground(Color.WHITE);
            tablePanel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
            tablePanel.add(jScrollPane1, BorderLayout.CENTER);
            rightPanelContainer.add(tablePanel, BorderLayout.CENTER);
            rightPanelContainer.revalidate();
            rightPanelContainer.repaint();
        }
    }
    
    private static class PlaceholderTextField extends JTextField {
        private String placeholder;
        public void setPlaceholder(String placeholder) { this.placeholder = placeholder; }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (getText().isEmpty() && placeholder != null) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(150, 150, 150));
                g2.setFont(getFont().deriveFont(Font.ITALIC));
                int x = getInsets().left + 5;
                int y = (getHeight() + g.getFontMetrics().getAscent()) / 2 - 2;
                g2.drawString(placeholder, x, y);
            }
        }
    }
    
    private void setupMenuActions() {
        // Clear existing menu items
        jMenu1.removeAll();
        jMenu2.removeAll();
        jMenu7.removeAll();
        jMenu8.removeAll();
        jMenu9.removeAll();
        
        // Create and populate all menus with navigation items
        addMenuItems(jMenu1);
        addMenuItems(jMenu2);
        addMenuItems(jMenu7);
        addMenuItems(jMenu8);
        addMenuItems(jMenu9);
    }
    
    private void addMenuItems(JMenu menu) {
        JMenuItem products = new JMenuItem("Products");
        JMenuItem customers = new JMenuItem("Customers");
        JMenuItem suppliers = new JMenuItem("Suppliers");
        JMenuItem orders = new JMenuItem("Orders");
        JMenuItem stocks = new JMenuItem("Stocks");
        
        // Style menu items for better visibility
        styleMenuItem(products);
        styleMenuItem(customers);
        styleMenuItem(suppliers);
        styleMenuItem(orders);
        styleMenuItem(stocks);
        
        menu.add(products);
        menu.addSeparator();
        menu.add(customers);
        menu.add(suppliers);
        menu.add(orders);
        menu.add(stocks);
        
        products.addActionListener(e -> {
            ProductF frame = new ProductF();
            frame.setVisible(true);
            dispose();
        });
        
        customers.addActionListener(e -> {
            CustomerF frame = new CustomerF();
            frame.setVisible(true);
            dispose();
        });
        
        suppliers.addActionListener(e -> {
            SupplierF frame = new SupplierF();
            frame.setVisible(true);
            dispose();
        });
        
        orders.addActionListener(e -> {
            OrderF frame = new OrderF();
            frame.setVisible(true);
            dispose();
        });
        
        stocks.addActionListener(e -> {
            StockF frame = new StockF();
            frame.setVisible(true);
            dispose();
        });
    }
    
    private void styleMenuItem(JMenuItem item) {
        item.setForeground(new Color(33, 37, 41)); // Dark text
        item.setBackground(Color.WHITE);
        item.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        item.setOpaque(true);
        item.setBorderPainted(false);
        
        // Add hover effect
        item.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                item.setBackground(new Color(240, 240, 240));
                item.setForeground(new Color(25, 118, 210));
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                item.setBackground(Color.WHITE);
                item.setForeground(new Color(33, 37, 41));
            }
        });
    }

    

    private void setupCustomLogic() {
    
        jButton2.addActionListener(e -> insertSupplier());
        jButton3.addActionListener(e -> editSupplier());
        jButton4.addActionListener(e -> deleteSupplier());
        jButton5.addActionListener(e -> backupSuppliers());
        jButton1.addActionListener(e -> restoreSuppliers());
        jButton6.addActionListener(e -> refreshTable());
    }

    private void refreshTable() {
        try (Connection conn = Supplier.getConnection()) {
            String query = "SELECT * FROM supplier";
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {

                tableModel = (DefaultTableModel) suppliertable.getModel();
                tableModel.setRowCount(0);

                int rowCount = 0;
                while (rs.next()) {
                    tableModel.addRow(new Object[]{
                            rs.getInt("supplier_id"),
                            rs.getString("supplier_name"),
                            rs.getString("supplier_email"),
                            rs.getString("supplier_phone"),
                            rs.getString("supplier_address"),
                            rs.getInt("stock_id")
                    });
                    rowCount++;
                }
                
                if (tableSorter != null) {
                    tableSorter.setModel(tableModel);
                }
                
                if (rowCount == 0) {
                    showEmptyState();
                } else {
                    hideEmptyState();
                }
            }
        } catch (Exception ex) {
            showErrorMessage("Error loading suppliers: " + ex.getMessage());
        }
    }
    
    private void filterTable(String searchText) {
        if (tableSorter != null && searchText != null) {
            if (searchText.trim().isEmpty()) {
                tableSorter.setRowFilter(null);
            } else {
                tableSorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText.trim()));
            }
        }
    }
    
    private void showEmptyState() {
        if (tableModel.getRowCount() == 0) {
            JLabel emptyLabel = new JLabel("<html><div style='text-align: center;'><br><br><br>" +
                "<font size='5' color='#999999'>🏢</font><br><br>" +
                "<font size='4' color='#666666'><b>No Suppliers Found</b></font><br><br>" +
                "<font color='#999999'>Click the <b>+ INSERT</b> button to add a new supplier</font></div></html>");
            emptyLabel.setHorizontalAlignment(JLabel.CENTER);
            emptyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            jScrollPane1.setViewportView(emptyLabel);
        }
    }
    
    private void hideEmptyState() {
        jScrollPane1.setViewportView(suppliertable);
    }

    private void insertSupplier() {
        if (!validateInputs()) return;
        
        try (Connection conn = Supplier.getConnection()) {
            String query = "INSERT INTO supplier (supplier_name, supplier_email, supplier_phone, supplier_address, stock_id) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, jTextField7.getText().trim()); 
                stmt.setString(2, jTextField5.getText().trim()); 
                stmt.setString(3, jTextField8.getText().trim()); 
                String address = (addressArea != null) ? addressArea.getText().trim() : jTextField9.getText().trim();
                stmt.setString(4, address); 
                stmt.setInt(5, Integer.parseInt(jTextField10.getText().trim())); 

                stmt.executeUpdate();
                showSuccessMessage("Supplier added successfully!");
                clearFields();
                refreshTable();
            }
        } catch (Exception ex) {
            showErrorMessage("Failed to add supplier: " + ex.getMessage());
        }
    }
    
    private boolean validateInputs() {
        boolean isValid = true;
        styleTextField(jTextField7);
        styleTextField(jTextField5);
        styleTextField(jTextField8);
        styleTextField(jTextField10);
        
        if (jTextField7.getText().trim().isEmpty()) {
            showErrorMessage("Supplier Name is required");
            highlightErrorField(jTextField7);
            isValid = false;
        }
        if (jTextField5.getText().trim().isEmpty()) {
            if (isValid) {
                showErrorMessage("Email is required");
                highlightErrorField(jTextField5);
            }
            isValid = false;
        }
        if (jTextField8.getText().trim().isEmpty()) {
            if (isValid) {
                showErrorMessage("Phone Number is required");
                highlightErrorField(jTextField8);
            }
            isValid = false;
        }
        String address = (addressArea != null) ? addressArea.getText().trim() : jTextField9.getText().trim();
        if (address.isEmpty()) {
            if (isValid) {
                showErrorMessage("Address is required");
                if (addressArea != null) {
                    addressArea.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(239, 83, 80), 2),
                        BorderFactory.createEmptyBorder(7, 9, 7, 9)
                    ));
                } else {
                    highlightErrorField(jTextField9);
                }
            }
            isValid = false;
        }
        if (jTextField10.getText().trim().isEmpty()) {
            if (isValid) {
                showErrorMessage("Stock ID is required");
                highlightErrorField(jTextField10);
            }
            isValid = false;
        } else {
            try {
                Integer.parseInt(jTextField10.getText().trim());
            } catch (NumberFormatException e) {
                if (isValid) {
                    showErrorMessage("Stock ID must be a valid number");
                    highlightErrorField(jTextField10);
                }
                isValid = false;
            }
        }
        
        return isValid;
    }
    
    private void clearFields() {
        jTextField1.setText("");
        jTextField7.setText("");
        jTextField5.setText("");
        jTextField8.setText("");
        if (addressArea != null) {
            addressArea.setText("");
        } else {
            jTextField9.setText("");
        }
        jTextField10.setText("");
        styleTextField(jTextField1);
        styleTextField(jTextField7);
        styleTextField(jTextField5);
        styleTextField(jTextField8);
        styleTextField(jTextField10);
    }
    
    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void editSupplier() {
        if (!validateInputs()) return;
        if (jTextField1.getText().trim().isEmpty()) {
            showErrorMessage("Supplier ID is required for update");
            highlightErrorField(jTextField1);
            return;
        }
        
        try (Connection conn = Supplier.getConnection()) {
            String query = "UPDATE supplier SET supplier_name = ?, supplier_email = ?, supplier_phone = ?, supplier_address = ?, stock_id = ? WHERE supplier_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, jTextField7.getText().trim());
                stmt.setString(2, jTextField5.getText().trim());
                stmt.setString(3, jTextField8.getText().trim());
                String address = (addressArea != null) ? addressArea.getText().trim() : jTextField9.getText().trim();
                stmt.setString(4, address);
                stmt.setInt(5, Integer.parseInt(jTextField10.getText().trim())); 
                stmt.setInt(6, Integer.parseInt(jTextField1.getText().trim())); 

                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    showSuccessMessage("Supplier updated successfully!");
                    clearFields();
                    refreshTable();
                } else {
                    showErrorMessage("Supplier ID not found.");
                }
            }
        } catch (Exception ex) {
            showErrorMessage("Failed to update supplier: " + ex.getMessage());
        }
    }

    private void deleteSupplier() {
        if (jTextField1.getText().trim().isEmpty()) {
            showErrorMessage("Supplier ID is required for deletion");
            highlightErrorField(jTextField1);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this supplier?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = Supplier.getConnection()) {
                String query = "DELETE FROM supplier WHERE supplier_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setInt(1, Integer.parseInt(jTextField1.getText().trim())); 

                    int rows = stmt.executeUpdate();
                    if (rows > 0) {
                        showSuccessMessage("Supplier deleted successfully!");
                        clearFields();
                        refreshTable();
                    } else {
                        showErrorMessage("Supplier ID not found.");
                    }
                }
            } catch (Exception ex) {
                showErrorMessage("Failed to delete supplier: " + ex.getMessage());
            }
        }
    }

    private void backupSuppliers() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to backup all suppliers?", 
            "Confirm Backup", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Supplier.backupSuppliers();
                showSuccessMessage("Suppliers backed up successfully!");
            } catch (Exception ex) {
                showErrorMessage("Failed to backup suppliers: " + ex.getMessage());
            }
        }
    }

    private void restoreSuppliers() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Warning: This will replace all current suppliers with backup data.\nAre you sure?", 
            "Confirm Restore", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Supplier.restoreSuppliers();
                showSuccessMessage("Suppliers restored successfully!");
                refreshTable();
            } catch (Exception ex) {
                showErrorMessage("Failed to restore suppliers: " + ex.getMessage());
            }
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        suppliertable = new javax.swing.JTable();
        jTextField5 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu7 = new javax.swing.JMenu();
        jMenu8 = new javax.swing.JMenu();
        jMenu9 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel4.setBackground(new java.awt.Color(245, 247, 250));

        jTextField1.setBackground(new java.awt.Color(52, 73, 94));
        jTextField1.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 32));
        jTextField1.setForeground(new java.awt.Color(255, 255, 255));
        jTextField1.setText("Supplier Management");
        jTextField1.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        jTextField1.setEditable(false);
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 11));
        jLabel7.setForeground(new java.awt.Color(52, 73, 94));
        jLabel7.setText("Email:");

        suppliertable.setBackground(new java.awt.Color(255, 255, 255));
        suppliertable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Supplier ID", "Supplier Name", "Email", "Suplier Phone", "Supplier Address", "Stock ID"
            }
        ));
        jScrollPane1.setViewportView(suppliertable);

        jTextField5.setBackground(new java.awt.Color(255, 255, 255));
        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        jTextField7.setBackground(new java.awt.Color(255, 255, 255));
        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 11));
        jLabel12.setForeground(new java.awt.Color(52, 73, 94));
        jLabel12.setText("Name:");

        jButton2.setText("INSERT");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("UPDATE");

        jButton4.setText("DELETE");

        jButton5.setText("BACKUP");

        jButton1.setText("RESTORE");

        jButton6.setText("REFRESH");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 11));
        jLabel13.setForeground(new java.awt.Color(52, 73, 94));
        jLabel13.setText("Phone:");

        jTextField8.setBackground(new java.awt.Color(255, 255, 255));
        jTextField8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField8ActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 11));
        jLabel14.setForeground(new java.awt.Color(52, 73, 94));
        jLabel14.setText("Address:");

        jTextField9.setBackground(new java.awt.Color(255, 255, 255));
        jTextField9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField9ActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 11));
        jLabel15.setForeground(new java.awt.Color(52, 73, 94));
        jLabel15.setText("Stock ID:");

        jTextField10.setBackground(new java.awt.Color(255, 255, 255));
        jTextField10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addGap(132, 132, 132)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(41, 41, 41)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel14)
                                        .addGap(196, 196, 196)))
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(54, 54, 54)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel15))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 129, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 617, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2)
                            .addComponent(jButton3)
                            .addComponent(jButton4)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 496, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 91, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton6)
                    .addComponent(jButton5))
                .addGap(82, 82, 82))
        );

        jMenuBar1.setBackground(new java.awt.Color(52, 73, 94));
        jMenuBar1.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(25, 118, 210)));
        jMenuBar1.setForeground(new java.awt.Color(255, 255, 255));

        jMenu1.setBackground(new java.awt.Color(52, 73, 94));
        jMenu1.setForeground(new java.awt.Color(255, 255, 255));
        jMenu1.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jMenu1.setText("Products");
        jMenu1.setOpaque(true);
        jMenuBar1.add(jMenu1);

        jMenu2.setBackground(new java.awt.Color(52, 73, 94));
        jMenu2.setForeground(new java.awt.Color(255, 255, 255));
        jMenu2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jMenu2.setText("Customers");
        jMenu2.setOpaque(true);
        jMenuBar1.add(jMenu2);

        jMenu7.setBackground(new java.awt.Color(52, 73, 94));
        jMenu7.setForeground(new java.awt.Color(255, 255, 255));
        jMenu7.setFont(new Font("Segoe UI", Font.BOLD, 12));
        jMenu7.setText("Suppliers");
        jMenu7.setOpaque(true);
        jMenuBar1.add(jMenu7);

        jMenu8.setBackground(new java.awt.Color(52, 73, 94));
        jMenu8.setForeground(new java.awt.Color(255, 255, 255));
        jMenu8.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jMenu8.setText("Orders");
        jMenu8.setOpaque(true);
        jMenuBar1.add(jMenu8);

        jMenu9.setBackground(new java.awt.Color(52, 73, 94));
        jMenu9.setForeground(new java.awt.Color(255, 255, 255));
        jMenu9.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jMenu9.setText("Stocks");
        jMenu9.setOpaque(true);
        jMenuBar1.add(jMenu9);
        
        // Set UIManager properties for menu styling
        UIManager.put("Menu.background", new Color(52, 73, 94));
        UIManager.put("Menu.foreground", Color.WHITE);
        UIManager.put("Menu.selectionBackground", new Color(25, 118, 210));
        UIManager.put("Menu.selectionForeground", Color.WHITE);
        UIManager.put("MenuItem.background", Color.WHITE);
        UIManager.put("MenuItem.foreground", new Color(33, 37, 41));
        UIManager.put("MenuItem.selectionBackground", new Color(240, 240, 240));
        UIManager.put("MenuItem.selectionForeground", new Color(25, 118, 210));
        UIManager.put("PopupMenu.background", Color.WHITE);
        UIManager.put("PopupMenu.border", BorderFactory.createLineBorder(new Color(220, 220, 220), 1));

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8ActionPerformed

    private void jTextField9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField9ActionPerformed

    private void jTextField10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField10ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SupplierF().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTable suppliertable;
    // End of variables declaration//GEN-END:variables
}
