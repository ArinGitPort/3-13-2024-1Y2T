package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

//this section is for backend logic
class ReservationManager {
    private static final int maxReservations = 100;
    private Reservation[] reservations = new Reservation[maxReservations];
    private int reservationCount = 0;
    private JLabel totalCountLabel;
    private JTable reservationTable;
    //for counting reservations
    public void setTotalCountLabel(JLabel totalCountLabel) {
        this.totalCountLabel = totalCountLabel;
        updateTotalCountLabel();
    }
    //for preventing conflict of deleting and adding
    public void setReservationTable(JTable reservationTable) {
        this.reservationTable = reservationTable;
        updateReservationTable();
    }
    //add reserve
    public void addReservation(String name, String date, String time, int adults, int children) {
        if (reservationCount < maxReservations) {
            reservations[reservationCount++] = new Reservation(name, date, time, adults, children);
            updateTotalCountLabel();
            updateReservationTable();
        } else {
            JOptionPane.showMessageDialog(null, "Ain't no way you added more than a 100", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    //delete reserve
    public void deleteReservation(int reservationNumber) {
        if (reservationNumber >= 1 && reservationNumber <= reservationCount) {
            for (int i = reservationNumber - 1; i < reservationCount - 1; i++) {
                reservations[i] = reservations[i + 1];
            }
            reservationCount--;

            // Reassign reservation numbers after deletion
            for (int i = 0; i < reservationCount; i++) {
                reservations[i].setReservationNumber(i + 1);
            }

            updateTotalCountLabel();
            updateReservationTable();
            JOptionPane.showMessageDialog(null, "Reservation deleted.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Invalid reservation number. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //generate report
    public void generateReservationReport() {
        if (reservationCount == 0) {
            JOptionPane.showMessageDialog(null, "Please add reservations.", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        //array for heading
        String[] columnNames = {"#", "Date", "Time", "Name", "Adults", "Children", "Subtotal"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        double totalPayment = 0.0;
        int totalAdults = 0;
        int totalChildren = 0;
        // Update total payment, adults, and children count
        for (int i = 0; i < reservationCount; i++) {
            Reservation reservation = reservations[i];
            Object[] rowData = reservation.toTableRowWithNumber();
            tableModel.addRow(rowData);

            totalPayment += reservation.calculateSubtotalReturn();
            totalAdults += reservation.getAdults();
            totalChildren += reservation.getChildren();
        }

        // Create a panel to hold the summary information
        JPanel summaryPanel = new JPanel();
        summaryPanel.setLayout(new GridLayout(3, 2));

        summaryPanel.add(new JLabel("Total Adults: "));
        summaryPanel.add(new JLabel(Integer.toString(totalAdults)));

        summaryPanel.add(new JLabel("Total Children: "));
        summaryPanel.add(new JLabel(Integer.toString(totalChildren)));

        summaryPanel.add(new JLabel("Grand Total: "));
        summaryPanel.add(new JLabel(String.format("%.2f PHP", totalPayment)));

        // Create a new frame for the summary and the table
        JFrame reportFrame = new JFrame("Reservation Report");
        reportFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        reportFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        reportFrame.getContentPane().add(summaryPanel, BorderLayout.SOUTH);

        reportFrame.setSize(600, 400);
        reportFrame.setLocationRelativeTo(null);
        reportFrame.setVisible(true);
    }
    //view all reservations
    public void viewAllReservations() {
        if (reservationCount == 0) {
            JOptionPane.showMessageDialog(null, "No reservations found.", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] columnNames = {"#", "Date", "Time", "Name", "Adults", "Children"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (int i = 0; i < reservationCount; i++) {
            Reservation reservation = reservations[i];
            tableModel.addRow(reservation.toTableRowWithNumber());
        }

        JTable table = new JTable(tableModel);

        JFrame viewReservationsFrame = new JFrame("All Reservations");
        viewReservationsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JScrollPane scrollPane = new JScrollPane(table);
        viewReservationsFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        viewReservationsFrame.setSize(500, 400);
        viewReservationsFrame.setLocationRelativeTo(null);
        viewReservationsFrame.setVisible(true);
    }
    //for counting reservation
    public int getReservationCount() {
        return reservationCount;
    }
    //updates total reservations on show menu panel(YELLOW BAR)
    private void updateTotalCountLabel() {
        if (totalCountLabel != null) {
            totalCountLabel.setText("Total Reservations: " + reservationCount);
        }
    }
    //properly updates reservation table adding/deleting
    private void updateReservationTable() {
        if (reservationTable != null) {
            DefaultTableModel tableModel = (DefaultTableModel) reservationTable.getModel();
            tableModel.setRowCount(0); 
            for (int i = 0; i < reservationCount; i++) {
                Reservation reservation = reservations[i];
                tableModel.addRow(reservation.toTableRowWithNumber());
            }
        }
    }
}
