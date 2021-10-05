/*
 * Copyright (C) 2014 Delcio Amarillo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
package com.javadeepcafe.main;

import com.ezware.oxbow.swingbits.table.filter.TableRowFilterSupport;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import org.jdesktop.swingx.JXFindBar;
import org.jdesktop.swingx.JXTable;
import com.javadeepcafe.domain.Procesador;
import com.javadeepcafe.tablemodel.GenericDomainTableModel;

/**
 * @author Delcio Amarillo
 */
public class DemoAdvancedTable {
    
    private GenericDomainTableModel<Procesador> procesadoresTableModel;
    private JXTable table;
    private Action nuevoProcesadorAction, eliminarProcesadorAction;
    
    private void createAndShowGui() {        
        JPanel content = new JPanel(new BorderLayout(8, 8));
        content.setBorder(new EmptyBorder(12, 12, 12, 12));
        content.add(getCenterPanel(), BorderLayout.CENTER);
        content.add(getTopPanel(), BorderLayout.NORTH);
        
        JFrame frame = new JFrame("Bienvenido!");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(content);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private JPanel getTopPanel() {
        JXFindBar findBar = new JXFindBar(table.getSearchable());
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(new TitledBorder("Búsqueda rápida:"));
        topPanel.add(findBar);
        return topPanel;
    }
    
    private JPanel getCenterPanel() {        
        Object[] columnIdentifiers = new Object[]{
            "Fabricante",
            "Denominación", 
            "Núcleos", 
            "Frecuencia CPU @GHz", 
            "Cache MB"
        };
        
        procesadoresTableModel = new GenericDomainTableModel<Procesador>(Arrays.asList(columnIdentifiers)) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0: return String.class;
                    case 1: return String.class;
                    case 2: return Integer.class;
                    case 3: return Double.class;
                    case 4: return Double.class;
                        default: throw new ArrayIndexOutOfBoundsException(columnIndex);
                }
            }
            
            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                Procesador procesador = getDomainObject(rowIndex);
                switch (columnIndex) {
                    case 0: return procesador.getFabricante();
                    case 1: return procesador.getDenominacion();
                    case 2: return procesador.getNumeroNucleos();
                    case 3: return procesador.getFrecuenciaCpu();
                    case 4: return procesador.getCache();
                        default: throw new ArrayIndexOutOfBoundsException(columnIndex);
                }
            }
            
            @Override
            public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
                Procesador procesador = getDomainObject(rowIndex);
                switch (columnIndex) {
                    case 0: procesador.setFabricante((String)aValue); break;
                    case 1: procesador.setDenominacion((String)aValue); break;
                    case 2: procesador.setNumeroNucleos((Integer)aValue); break;
                    case 3: procesador.setFrecuenciaCpu((Double)aValue); break;
                    case 4: procesador.setCache((Double)aValue); break;
                        default: throw new ArrayIndexOutOfBoundsException(columnIndex);
                }
            }
        };
        
        table = new JXTable(procesadoresTableModel);
        table.setPreferredScrollableViewportSize(new Dimension(600, 300));
        table.setAutoCreateRowSorter(true);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        /*
         * Agregamos el control de columnas a nuestra tabla
         */
        table.setColumnControlVisible(true);
        
        /*
         * Agregamos la funcionalidad de filtrar en nuestra tabla
         */
        TableRowFilterSupport.forTable(table).searchable(true).apply();
        
        eliminarProcesadorAction = new AbstractAction("-") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedRows = table.getSelectedRows();
                for (int row = selectedRows.length - 1; row >= 0; row--) {
                    int viewIndex = selectedRows[row];
                    int modelIndex = table.convertRowIndexToModel(viewIndex);
                    procesadoresTableModel.deleteRow(modelIndex);
                }
                setEnabled(procesadoresTableModel.getRowCount() > 0);
            }
        };        
        eliminarProcesadorAction.setEnabled(false);
        
        nuevoProcesadorAction = new AbstractAction("+") {
            @Override
            public void actionPerformed(ActionEvent e) {
                procesadoresTableModel.addRow(new Procesador());
                eliminarProcesadorAction.setEnabled(true);
            }
        };
        
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(new JButton(nuevoProcesadorAction));
        buttonsPanel.add(new JButton(eliminarProcesadorAction));
        
        JPanel centerPanel = new JPanel(new BorderLayout(8,8));
        CompoundBorder border = BorderFactory.createCompoundBorder(new TitledBorder("Procesadores:"), new EmptyBorder(8,8,8,8));
        centerPanel.setBorder(border);
        centerPanel.add(new JScrollPane(table));
        centerPanel.add(buttonsPanel, BorderLayout.EAST);
        
        return centerPanel;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    new DemoAdvancedTable().createAndShowGui();
                } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                    Logger.getLogger(DemoAdvancedTable.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}