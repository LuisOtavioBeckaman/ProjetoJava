package br.app.com; 

import javax.swing.*;
import br.app.com.database.DatabaseInitializer;
import br.app.com.ui.MainFrame;

public class Main {
    public static void main(String[] args) {
      
        // Inicializar o banco de dados e criar as tabelas
        DatabaseInitializer.initializeDatabase();
        DatabaseInitializer.createTables();
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
}
}

