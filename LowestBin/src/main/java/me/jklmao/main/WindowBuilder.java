package me.jklmao.main;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class WindowBuilder {

	// Code Improvement
	private JFrame frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JLabel totalInvestedText;
	private JLabel totalMadeText;
	private JLabel netProfitText;
	private final String[] columns = new String[] { "Item", "BIN Price", "Paid", "Net" };
	private String[][] data = new String[29][4];

	private final Font font = new Font("Calibri", Font.BOLD, 15);

	private List<String> itemNames;
	private List<Integer> itemPrices;

	private int netProfit;

	public void drawTable() throws InterruptedException, ExecutionException {
		DataFinder dataFinder = new DataFinder();
		dataFinder.makeCall();

		itemNames = dataFinder.getItemNames();
		itemPrices = dataFinder.getItemPrices();

		table = new JTable(getData(), columns);

	}

	public void updateTable() throws InterruptedException, ExecutionException {
		DataFinder dataFinder = new DataFinder();
		dataFinder.makeCall();

		itemNames = dataFinder.getItemNames();
		itemPrices = dataFinder.getItemPrices();

		// update column 2 and 4;

		updateTwoColumn();
		updateFourColumn();

	}

	public void drawText() {
		totalInvestedText = new JLabel("Total Invested: 1,228,580,000");
		totalInvestedText.setFont(font);

		totalMadeText = new JLabel("Total Made: " + String.format("%,d", (1228580000 + netProfit)));
		totalMadeText.setFont(font);

		netProfitText = new JLabel("Net Profit: " + String.format("%,d", netProfit));
		netProfitText.setFont(font);
	}

	public void updateText() {

		totalMadeText.setText("Total Made: " + String.format("%,d", 1228580000 + netProfit));
		netProfitText.setText("Net Profit: " + String.format("%,d", netProfit));
	}

	public WindowBuilder() throws InterruptedException, ExecutionException {

		frame = new JFrame("BIN Finder");
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

		drawTable();
		drawText();

		table.getColumnModel().getColumn(0).setPreferredWidth(125);
		table.setPreferredScrollableViewportSize(new Dimension(600, table.getPreferredSize().height));

		scrollPane = new JScrollPane(table);

		JPanel textPanel = new JPanel();
		textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
		textPanel.add(totalInvestedText);
		textPanel.add(totalMadeText);
		textPanel.add(netProfitText);

		JPanel buttonPanel = new JPanel();
		JButton cancelButton = new JButton("Refresh");
		cancelButton.setPreferredSize(new Dimension(200, 50));
		cancelButton.setFocusable(false);

		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cancelButton.setEnabled(false);

				try {
					updateTable();
				} catch (InterruptedException | ExecutionException e1) {
					e1.printStackTrace();
				}
				updateText();

				frame.revalidate();
				frame.repaint();

				cancelButton.setEnabled(true);

			}

		});

		buttonPanel.add(cancelButton);
		JPanel textPlusButtonPanel = new JPanel();
		textPlusButtonPanel.setLayout(new BoxLayout(textPlusButtonPanel, BoxLayout.X_AXIS));
		textPlusButtonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		textPlusButtonPanel.add(textPanel);
		textPlusButtonPanel.add(buttonPanel);

		frame.add(scrollPane);
		frame.add(Box.createVerticalStrut(5));
		frame.add(textPlusButtonPanel);

		frame.setDefaultCloseOperation(3);
		frame.pack();

	}

	public void open() {
		frame.setResizable(false);
		frame.setVisible(true);

	}

	public String[][] getData() {

		oneColumnBuilder(data);
		twoColumnBuilder(data);
		threeColumnBuilder(data);
		fourColumnBuilder(data);

		return data;

	}

	private String compare(String oldString, int newBin) {

		String returnString;
		int old = Integer.parseInt(oldString.replaceAll(",", "").replace("    ↑", "").replace("    ↓", ""));

		if (newBin > old) {
			returnString = String.format("%,d", newBin) + "    ↑";
			return returnString;
		}

		if (newBin < old) {
			returnString = String.format("%,d", newBin) + "    ↓";
			return returnString;
		}

		return String.format("%,d", newBin);
	}

	private void oneColumnBuilder(String[][] data) {

		for (int i = 0; i < 29; i++)
			data[i][0] = itemNames.get(i);

	}

	private void twoColumnBuilder(String[][] data) {
		for (int i = 0; i < 29; i++) {

			data[i][1] = String.format("%,d", itemPrices.get(i));

			if (data[i][1].equals("2,147,483,647"))
				data[i][1] = "Not in Auction!";
		}
	}

	private void updateTwoColumn() {

		String currentCell;
		for (int i = 0; i < 29; i++) {

			currentCell = table.getModel().getValueAt(i, 1).toString();

			table.getModel().setValueAt(compare(currentCell, itemPrices.get(i)), i, 1);

			if (itemPrices.get(i) == 2147483647)
				table.getModel().setValueAt("Not in Auction!", i, 1);
		}
	}

	private void threeColumnBuilder(String[][] data) {
		data[0][2] = "35,000,000";
		data[1][2] = "49,780,000";
		data[2][2] = "32,500,000";
		data[3][2] = "18,000,000";
		data[4][2] = "19,000,000";
		data[5][2] = "30,000,000";
		data[6][2] = "28,000,000";
		data[7][2] = "25,000,000";
		data[8][2] = "20,000,000";
		data[9][2] = "59,000,000";
		data[10][2] = "48,000,000";
		data[11][2] = "38,000,000";
		data[12][2] = "42,000,000";
		data[13][2] = "47,000,000";
		data[14][2] = "45,000,000";
		data[15][2] = "14,500,000";
		data[16][2] = "23,500,000";
		data[17][2] = "27,500,000";
		data[18][2] = "48,000,000";
		data[19][2] = "45,000,000";
		data[20][2] = "40,800,000";
		data[21][2] = "45,000,000";
		data[22][2] = "0";
		data[23][2] = "31,000,000";
		data[24][2] = "18,000,000";
		data[25][2] = "35,000,000";
		data[26][2] = "44,000,000";
		data[27][2] = "55,000,000";
		data[28][2] = "8,000,000";
	}

	private void fourColumnBuilder(String[][] data) {
		netProfit = 0; // safety
		for (int i = 0; i < 29; i++) {

			if (data[i][1].equals("Not in Auction!")) {
				continue;
			}
			int binPrice = Integer.parseInt(data[i][1].replaceAll(",", "").replace("    ↑", "").replace("    ↓", ""));
			int paidPrice = Integer.parseInt(data[i][2].replaceAll(",", ""));
			data[i][3] = String.format("%,d", (binPrice - paidPrice));

			netProfit = netProfit + (binPrice - paidPrice);
		}

	}

	private void updateFourColumn() {
		netProfit = 0;
		for (int i = 0; i < 29; i++) {

			if (table.getModel().getValueAt(i, 3) == null) {
				continue;
			}
			int binPrice = Integer.parseInt(data[i][1].replaceAll(",", "").replace("    ↑", "").replace("    ↓", ""));
			int paidPrice = Integer.parseInt(data[i][2].replaceAll(",", ""));

			table.getModel().setValueAt(String.format("%,d", (binPrice - paidPrice)), i, 3);

			netProfit = netProfit + (binPrice - paidPrice);
		}

	}

}
