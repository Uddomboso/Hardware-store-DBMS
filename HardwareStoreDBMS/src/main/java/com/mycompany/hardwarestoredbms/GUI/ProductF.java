package com.mycompany.hardwarestoredbms.GUI;

import com.mycompany.hardwarestoredbms.Product;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProductF extends javax.swing.JFrame {
    
    private PlaceholderTextField searchField;
    private JTextArea descriptionArea;
    private JPanel inputCardPanel;
    private TableRowSorter<DefaultTableModel> tableSorter;
    private DefaultTableModel tableModel;
    private JPanel leftPanelContainer;
    private JPanel rightPanelContainer;

    public ProductF() {
        initComponents(); 
        setupCustomLogic(); 
        setupUIEnhancements();
        refreshTable(); 
        setupMenuActions();
    }
    
    private void setupUIEnhancements() {
        // Set modern look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            // Use default if system L&F fails
        }
        
        // Center window on screen
        setLocationRelativeTo(null);
        
        // Improve table appearance
        enhanceTable();
        
        // Improve button appearance
        enhanceButtons();
        
        // Improve text fields
        enhanceTextFields();
        
        // Reorganize layout
        reorganizeLayout();
        
        // Create input card panel
        createInputCard();
        
        // Add search functionality
        setupSearchBar();
        
        // Set window title
        setTitle("Hardware Store - Product Management");
        
        // Set minimum window size
        setMinimumSize(new Dimension(1200, 700));
    }
    
    private void reorganizeLayout() {
        // Remove old layout and create new BorderLayout
        getContentPane().removeAll();
        getContentPane().setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(new Color(245, 247, 250));
        
        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(52, 73, 94));
        headerPanel.add(jTextField1, BorderLayout.WEST);
        headerPanel.setPreferredSize(new Dimension(getWidth(), 70));
        
        // Main content panel with card and table
        JPanel mainPanel = new JPanel(new BorderLayout(15, 0));
        mainPanel.setBackground(new Color(245, 247, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Left side - Input card container
        leftPanelContainer = new JPanel(new BorderLayout());
        leftPanelContainer.setBackground(new Color(245, 247, 250));
        leftPanelContainer.setPreferredSize(new Dimension(420, getHeight()));
        
        // Right side - Search bar and table container
        rightPanelContainer = new JPanel(new BorderLayout(0, 10));
        rightPanelContainer.setBackground(new Color(245, 247, 250));
        
        // Table panel with scroll
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        tablePanel.add(jScrollPane1, BorderLayout.CENTER);
        rightPanelContainer.add(tablePanel, BorderLayout.CENTER);
        
        // Add panels
        mainPanel.add(leftPanelContainer, BorderLayout.WEST);
        mainPanel.add(rightPanelContainer, BorderLayout.CENTER);
        
        getContentPane().add(headerPanel, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
    }
    
    private void createInputCard() {
        // Create a card panel with titled border for input fields
        inputCardPanel = new JPanel();
        inputCardPanel.setBackground(Color.WHITE);
        inputCardPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            "Product Information",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 13),
            new Color(52, 73, 94)
        ));
        inputCardPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 15, 8, 15);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Add fields to card
        gbc.gridx = 0; gbc.gridy = 0;
        inputCardPanel.add(jLabel12, gbc);
        gbc.gridx = 1;
        jTextField7.setPreferredSize(new Dimension(150, 30));
        inputCardPanel.add(jTextField7, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        inputCardPanel.add(jLabel7, gbc);
        gbc.gridx = 1;
        jTextField5.setPreferredSize(new Dimension(250, 30));
        inputCardPanel.add(jTextField5, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        inputCardPanel.add(jLabel11, gbc);
        gbc.gridx = 1;
        jTextField2.setPreferredSize(new Dimension(150, 30));
        inputCardPanel.add(jTextField2, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        inputCardPanel.add(jLabel9, gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        if (descriptionArea != null) {
            descriptionArea.setPreferredSize(new Dimension(250, 70));
            inputCardPanel.add(descriptionArea, gbc);
        } else {
            jTextField3.setPreferredSize(new Dimension(250, 70));
            inputCardPanel.add(jTextField3, gbc);
        }
        
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        inputCardPanel.add(jLabel10, gbc);
        gbc.gridx = 1;
        jTextField4.setPreferredSize(new Dimension(150, 30));
        inputCardPanel.add(jTextField4, gbc);
        
        // Add action buttons below fields
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(jButton2);
        buttonPanel.add(jButton3);
        buttonPanel.add(jButton4);
        
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 15, 8, 15);
        inputCardPanel.add(buttonPanel, gbc);
        
        // Add utility buttons
        JPanel utilityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        utilityPanel.setBackground(Color.WHITE);
        utilityPanel.add(jButton6);
        utilityPanel.add(jButton5);
        utilityPanel.add(jButton1);
        
        gbc.gridy = 6;
        gbc.insets = new Insets(5, 15, 15, 15);
        inputCardPanel.add(utilityPanel, gbc);
        
        // Add card to left panel container
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
        searchField.setPlaceholder("Search by name, ID, or description...");
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filterTable(searchField.getText());
            }
        });
        
        // Add search icon label
        JLabel searchIcon = new JLabel("🔍");
        searchIcon.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchIcon.setForeground(new Color(150, 150, 150));
        searchIcon.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        
        // Wrap search field with icon
        JPanel searchWrapper = new JPanel(new BorderLayout());
        searchWrapper.setBackground(Color.WHITE);
        searchWrapper.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEmptyBorder(),
            "Search Products",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 11),
            new Color(100, 100, 100)
        ));
        searchWrapper.add(searchIcon, BorderLayout.WEST);
        searchWrapper.add(searchField, BorderLayout.CENTER);
        
        // Add to right panel container
        if (rightPanelContainer != null) {
            rightPanelContainer.remove(0); // Remove table panel temporarily
            rightPanelContainer.add(searchWrapper, BorderLayout.NORTH);
            // Re-add table panel
            JPanel tablePanel = new JPanel(new BorderLayout());
            tablePanel.setBackground(Color.WHITE);
            tablePanel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
            tablePanel.add(jScrollPane1, BorderLayout.CENTER);
            rightPanelContainer.add(tablePanel, BorderLayout.CENTER);
            rightPanelContainer.revalidate();
            rightPanelContainer.repaint();
        }
    }
    
    // Helper class for placeholder text
    private static class PlaceholderTextField extends JTextField {
        private String placeholder;
        
        public void setPlaceholder(String placeholder) {
            this.placeholder = placeholder;
        }
        
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
    
    private void enhanceTable() {
        ProductTable.setRowHeight(28);
        ProductTable.setSelectionBackground(new Color(70, 130, 180));
        ProductTable.setSelectionForeground(Color.WHITE);
        ProductTable.setGridColor(new Color(220, 220, 220));
        ProductTable.setShowGrid(true);
        ProductTable.setIntercellSpacing(new Dimension(0, 0));
        ProductTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        // Style table header - make it more visible with better contrast
        JTableHeader header = ProductTable.getTableHeader();
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
        
        // Set up table sorter for search functionality
        tableModel = (DefaultTableModel) ProductTable.getModel();
        tableSorter = new TableRowSorter<>(tableModel);
        ProductTable.setRowSorter(tableSorter);
        
        // Enable alternating row colors with better zebra striping
        ProductTable.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                // Set background color
                if (isSelected) {
                    c.setBackground(new Color(70, 130, 180));
                    c.setForeground(Color.WHITE);
                } else {
                    // Zebra striping
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 249, 250));
                    c.setForeground(new Color(33, 37, 41));
                }
                
                // Set font
                c.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                
                // Column-specific alignment
                if (column == 3 || column == 5) { // Price and Quantity columns
                    ((JLabel) c).setHorizontalAlignment(JLabel.RIGHT);
                } else {
                    ((JLabel) c).setHorizontalAlignment(JLabel.LEFT);
                }
                
                return c;
            }
        });
        
        // Set column widths
        ProductTable.getColumnModel().getColumn(0).setPreferredWidth(80);  // Product ID
        ProductTable.getColumnModel().getColumn(1).setPreferredWidth(80);  // Stock ID
        ProductTable.getColumnModel().getColumn(2).setPreferredWidth(150);  // Product Name
        ProductTable.getColumnModel().getColumn(3).setPreferredWidth(100);  // Price
        ProductTable.getColumnModel().getColumn(4).setPreferredWidth(200);  // Description
        ProductTable.getColumnModel().getColumn(5).setPreferredWidth(80);   // Quantity
    }
    
    private void enhanceButtons() {
        // INSERT button - Softer Green with icon
        styleButton(jButton2, new Color(76, 175, 80), Color.WHITE, "Add Product", "+");
        
        // UPDATE button - Softer Blue with icon
        styleButton(jButton3, new Color(33, 150, 243), Color.WHITE, "Update Product", "✎");
        
        // DELETE button - Softer Red (Indian Red) with icon
        styleButton(jButton4, new Color(239, 83, 80), Color.WHITE, "Delete Product", "✕");
        
        // REFRESH button - Softer Teal with icon
        styleButton(jButton6, new Color(38, 198, 218), Color.WHITE, "Refresh Table", "↻");
        
        // BACKUP button - Softer Orange with icon
        styleButton(jButton5, new Color(255, 167, 38), Color.WHITE, "Backup Data", "💾");
        
        // RESTORE button - Softer Purple with icon
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
        
        // Add hover effect
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
        // Right-align labels
        jLabel12.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel7.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel11.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel9.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel10.setHorizontalAlignment(SwingConstants.RIGHT);
        
        // Set label preferred width for alignment
        int labelWidth = 120;
        jLabel12.setPreferredSize(new Dimension(labelWidth, 25));
        jLabel7.setPreferredSize(new Dimension(labelWidth, 25));
        jLabel11.setPreferredSize(new Dimension(labelWidth, 25));
        jLabel9.setPreferredSize(new Dimension(labelWidth, 25));
        jLabel10.setPreferredSize(new Dimension(labelWidth, 25));
        
        // Style regular text fields
        JTextField[] textFields = {jTextField2, jTextField4, jTextField5, jTextField7};
        for (JTextField field : textFields) {
            styleTextField(field);
        }
        
        // Convert description field to text area
        descriptionArea = new JTextArea(3, 20);
        descriptionArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descriptionArea.setBackground(Color.WHITE);
        descriptionArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        
        // Add focus effect to text area
        descriptionArea.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                descriptionArea.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(25, 118, 210), 2),
                    BorderFactory.createEmptyBorder(7, 9, 7, 9)
                ));
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                descriptionArea.setBorder(BorderFactory.createCompoundBorder(
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
        
        // Add focus effect
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
                // Remove error highlighting if present
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

        jButton2.addActionListener(e -> insertProduct());
        jButton3.addActionListener(e -> editProduct());
        jButton4.addActionListener(e -> deleteProduct());
        jButton5.addActionListener(e -> backupProducts());
        jButton1.addActionListener(e -> restoreProducts());
        jButton6.addActionListener(e -> refreshTable());
    }

    private void refreshTable() {
        try (Connection conn = Product.getConnection()) {
            String query = "SELECT * FROM product";
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {

                tableModel = (DefaultTableModel) ProductTable.getModel();
                tableModel.setRowCount(0);

                int rowCount = 0;
                while (rs.next()) {
                    tableModel.addRow(new Object[]{
                            rs.getInt("product_id"),
                            rs.getInt("stock_id"),
                            rs.getString("product_name"),
                            String.format("$%.2f", rs.getFloat("price")),
                            rs.getString("description"),
                            rs.getInt("stock_quantity")
                    });
                    rowCount++;
                }
                
                // Update table sorter
                if (tableSorter != null) {
                    tableSorter.setModel(tableModel);
                }
                
                // Show empty state if no products
                if (rowCount == 0) {
                    showEmptyState();
                } else {
                    hideEmptyState();
                }
            }
        } catch (Exception ex) {
            showStatusMessage("Error loading products: " + ex.getMessage(), Color.RED);
            JOptionPane.showMessageDialog(this, 
                "Error refreshing table: " + ex.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
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
        // Show empty state message in a label overlay
        if (tableModel.getRowCount() == 0) {
            JLabel emptyLabel = new JLabel("<html><div style='text-align: center;'><br><br><br>" +
                "<font size='5' color='#999999'>📦</font><br><br>" +
                "<font size='4' color='#666666'><b>No Products Found</b></font><br><br>" +
                "<font color='#999999'>Click the <b>+ INSERT</b> button to add a new product</font></div></html>");
            emptyLabel.setHorizontalAlignment(JLabel.CENTER);
            emptyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            
            // Add empty label to scroll pane
            jScrollPane1.setViewportView(emptyLabel);
        }
    }
    
    private void hideEmptyState() {
        // Restore table view
        jScrollPane1.setViewportView(ProductTable);
    }
    
    private void showStatusMessage(String message, Color color) {
        // Create a temporary status label if needed
        // For now, just show in console - can be enhanced with a status bar
        System.out.println(message);
    }

    private void insertProduct() {
        // Validate inputs
        if (!validateInputs()) {
            return;
        }
        
        try (Connection conn = Product.getConnection()) {
            String query = "INSERT INTO product (product_id, stock_id, product_name, description, price, stock_quantity) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, Integer.parseInt(jTextField7.getText().trim()));
                stmt.setInt(2, Integer.parseInt(jTextField7.getText().trim()));
                stmt.setString(3, jTextField5.getText().trim());
                String description = (descriptionArea != null) ? descriptionArea.getText().trim() : jTextField3.getText().trim();
                stmt.setString(4, description);
                stmt.setFloat(5, Float.parseFloat(jTextField2.getText().trim()));
                stmt.setInt(6, Integer.parseInt(jTextField4.getText().trim()));

                stmt.executeUpdate();
                showSuccessMessage("Product added successfully!");
                clearFields();
                refreshTable();
            }
        } catch (Exception ex) {
            showErrorMessage("Failed to add product: " + ex.getMessage());
        }
    }
    
    private boolean validateInputs() {
        boolean isValid = true;
        
        // Reset all field borders
        styleTextField(jTextField7);
        styleTextField(jTextField5);
        styleTextField(jTextField2);
        styleTextField(jTextField4);
        if (descriptionArea != null) {
            descriptionArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
            ));
        }
        
        if (jTextField7.getText().trim().isEmpty()) {
            showErrorMessage("Product ID is required");
            highlightErrorField(jTextField7);
            isValid = false;
        } else {
            try {
                Integer.parseInt(jTextField7.getText().trim());
            } catch (NumberFormatException e) {
                showErrorMessage("Product ID must be a valid number");
                highlightErrorField(jTextField7);
                isValid = false;
            }
        }
        
        if (jTextField5.getText().trim().isEmpty()) {
            if (isValid) {
                showErrorMessage("Product Name is required");
                highlightErrorField(jTextField5);
            }
            isValid = false;
        }
        
        if (jTextField2.getText().trim().isEmpty()) {
            if (isValid) {
                showErrorMessage("Price is required");
                highlightErrorField(jTextField2);
            }
            isValid = false;
        } else {
            try {
                Float.parseFloat(jTextField2.getText().trim());
            } catch (NumberFormatException e) {
                if (isValid) {
                    showErrorMessage("Price must be a valid number");
                    highlightErrorField(jTextField2);
                }
                isValid = false;
            }
        }
        
        if (jTextField4.getText().trim().isEmpty()) {
            if (isValid) {
                showErrorMessage("Stock Quantity is required");
                highlightErrorField(jTextField4);
            }
            isValid = false;
        } else {
            try {
                Integer.parseInt(jTextField4.getText().trim());
            } catch (NumberFormatException e) {
                if (isValid) {
                    showErrorMessage("Stock Quantity must be a valid number");
                    highlightErrorField(jTextField4);
                }
                isValid = false;
            }
        }
        
        return isValid;
    }
    
    private void clearFields() {
        jTextField7.setText("");
        jTextField5.setText("");
        jTextField2.setText("");
        if (descriptionArea != null) {
            descriptionArea.setText("");
        } else {
            jTextField3.setText("");
        }
        jTextField4.setText("");
        // Reset borders
        styleTextField(jTextField7);
        styleTextField(jTextField5);
        styleTextField(jTextField2);
        styleTextField(jTextField4);
    }
    
    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", 
            JOptionPane.ERROR_MESSAGE);
    }

    private void editProduct() {
        if (!validateInputs()) {
            return;
        }
        
        try (Connection conn = Product.getConnection()) {
            String query = "UPDATE product SET stock_id = ?, product_name = ?, description = ?, price = ?, stock_quantity = ? WHERE product_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, Integer.parseInt(jTextField7.getText().trim()));
                stmt.setString(2, jTextField5.getText().trim());
                String description = (descriptionArea != null) ? descriptionArea.getText().trim() : jTextField3.getText().trim();
                stmt.setString(3, description);
                stmt.setFloat(4, Float.parseFloat(jTextField2.getText().trim()));
                stmt.setInt(5, Integer.parseInt(jTextField4.getText().trim()));
                stmt.setInt(6, Integer.parseInt(jTextField7.getText().trim()));

                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    showSuccessMessage("Product updated successfully!");
                    clearFields();
                    refreshTable();
                } else {
                    showErrorMessage("Product ID not found.");
                }
            }
        } catch (Exception ex) {
            showErrorMessage("Failed to update product: " + ex.getMessage());
        }
    }

    private void deleteProduct() {
        try (Connection conn = Product.getConnection()) {
            String query = "DELETE FROM product WHERE product_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, Integer.parseInt(jTextField7.getText()));

                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    showSuccessMessage("Product deleted successfully!");
                    clearFields();
                    refreshTable();
                } else {
                    showErrorMessage("Product ID not found.");
                }
            }
        } catch (Exception ex) {
            showErrorMessage("Failed to delete product: " + ex.getMessage());
        }
    }

    private void backupProducts() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to backup all products?", 
            "Confirm Backup", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = Product.getConnection()) {
                Product.backupProducts(conn);
                showSuccessMessage("Products backed up successfully!");
            } catch (Exception ex) {
                showErrorMessage("Failed to backup products: " + ex.getMessage());
            }
        }
    }

    private void restoreProducts() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Warning: This will replace all current products with backup data.\nAre you sure?", 
            "Confirm Restore", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = Product.getConnection()) {
                Product.restoreProducts(conn);
                showSuccessMessage("Products restored successfully!");
                refreshTable();
            } catch (Exception ex) {
                showErrorMessage("Failed to restore products: " + ex.getMessage());
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

        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenuBar3 = new javax.swing.JMenuBar();
        jMenu5 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        ProductTable = new javax.swing.JTable();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu7 = new javax.swing.JMenu();
        jMenu8 = new javax.swing.JMenu();
        jMenu9 = new javax.swing.JMenu();

        jMenu3.setText("File");
        jMenuBar2.add(jMenu3);

        jMenu4.setText("Edit");
        jMenuBar2.add(jMenu4);

        jMenu5.setText("File");
        jMenuBar3.add(jMenu5);

        jMenu6.setText("Edit");
        jMenuBar3.add(jMenu6);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(245, 247, 250));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 12));
        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        jPanel4.setBackground(new java.awt.Color(245, 247, 250));

        jTextField1.setBackground(new java.awt.Color(52, 73, 94));
        jTextField1.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 32));
        jTextField1.setForeground(new java.awt.Color(255, 255, 255));
        jTextField1.setText("Product Management");
        jTextField1.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        jTextField1.setEditable(false);
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 11));
        jLabel7.setForeground(new java.awt.Color(52, 73, 94));
        jLabel7.setText("Product Name:");

        jTextField2.setBackground(new java.awt.Color(255, 255, 255));
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        ProductTable.setBackground(new java.awt.Color(255, 255, 255));
        ProductTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Product ID", "Stock ID", "Product Name", "Product Price", "Description", "Quantity"
            }
        ));
        jScrollPane1.setViewportView(ProductTable);

        jTextField3.setBackground(new java.awt.Color(255, 255, 255));
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jTextField4.setBackground(new java.awt.Color(255, 255, 255));
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 11));
        jLabel9.setForeground(new java.awt.Color(52, 73, 94));
        jLabel9.setText("Description:");

        jTextField5.setBackground(new java.awt.Color(255, 255, 255));
        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 11));
        jLabel10.setForeground(new java.awt.Color(52, 73, 94));
        jLabel10.setText("Stock Quantity:");

        jLabel11.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 11));
        jLabel11.setForeground(new java.awt.Color(52, 73, 94));
        jLabel11.setText("Price ($):");

        jTextField7.setBackground(new java.awt.Color(255, 255, 255));
        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 11));
        jLabel12.setForeground(new java.awt.Color(52, 73, 94));
        jLabel12.setText("Product ID:");

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

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(41, 41, 41)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10))
                                .addGap(21, 21, 21)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                                        .addComponent(jTextField5, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jTextField2)
                                        .addComponent(jTextField7, javax.swing.GroupLayout.Alignment.TRAILING)))))
                        .addGap(191, 191, 191)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 556, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(570, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(42, 42, 42)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2)
                            .addComponent(jButton3)
                            .addComponent(jButton4)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 496, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton6)
                    .addComponent(jButton5))
                .addGap(82, 82, 82))
        );

        jPanel2.add(jPanel4);

        jMenuBar1.setBackground(new java.awt.Color(52, 73, 94));
        jMenuBar1.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(25, 118, 210)));
        jMenuBar1.setForeground(new java.awt.Color(255, 255, 255));

        jMenu1.setBackground(new java.awt.Color(52, 73, 94));
        jMenu1.setForeground(new java.awt.Color(255, 255, 255));
        jMenu1.setFont(new Font("Segoe UI", Font.BOLD, 12));
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
        jMenu7.setFont(new Font("Segoe UI", Font.PLAIN, 12));
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
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

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
        // TODO  your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProductF().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable ProductTable;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuBar jMenuBar3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField7;
    // End of variables declaration//GEN-END:variables
}
