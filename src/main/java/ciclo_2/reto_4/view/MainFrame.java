package ciclo_2.reto_4.view;

import ciclo_2.reto_4.controller.InformesController;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	Color WHITE_BONE = new java.awt.Color(238, 238, 238);
	Color PALE_RED = new java.awt.Color(240, 128, 128);
	Color PALE_GREEN = new java.awt.Color(25, 135, 84);

	JPanel tablePanel = new JPanel();
	JPanel panelScreen = new JPanel();
	JPanel errorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel headPanel = new JPanel();
	JPanel resultPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JScrollPane scrollTable;
	JLabel label_informe;
	JLabel label_error = new JLabel();
	JLabel label_success = new JLabel();
	JButton button_generar;
	JTextField buscar_informe;
	JComboBox<String> cb_informes;
	JTable tabla;
	InformesController reportes = new InformesController();

	public void createTable(String[][] datos, String[] columnas) throws SQLException {

		if (scrollTable != null) {
			tablePanel.remove(scrollTable);
		}

		if (datos.length != 0) {
			tabla = new JTable(datos, columnas);
		} else {
			createTable(emptyData(), emptyColumns());
		}

		scrollTable = new JScrollPane(tabla);

		scrollTable.setPreferredSize(new Dimension(720, 489));
		scrollTable.validate();
		scrollTable.repaint();

		tablePanel.add(scrollTable);
	}

	public JComboBox<String> listaInformes() {
		cb_informes = new JComboBox<String>();
		cb_informes.setFont(new Font("Arial", Font.PLAIN, 12));
		cb_informes.addItem(" ");
		cb_informes.addItem("Lideres");
		cb_informes.addItem("Proyectos por ciudad");
		cb_informes.addItem("Proyectos por proveedor");
		cb_informes.addItemListener(new ItemChangeListener(buscar_informe));

		return cb_informes;
	}

	public String[][] emptyData() {
		String[][] datosVacios = new String[28][5];

		for (int i = 0; i < datosVacios.length; i++) {
			datosVacios[i][0] = "";
			datosVacios[i][1] = "";
			datosVacios[i][2] = "";
			datosVacios[i][3] = "";
			datosVacios[i][4] = "";
		}

		return datosVacios;
	}

	public String[] emptyColumns() {
		String[] columnasVacias = { " ", " ", " ", " ", " "};
		return columnasVacias;
	}

	public void setLabelError(String text) {

		if (label_error != null) {
			errorPanel.remove(label_error);
		}

		label_error.setText(text);
		label_error.setForeground(WHITE_BONE);
		errorPanel.setBackground(PALE_RED);
		errorPanel.setPreferredSize(new Dimension(720, 35));

		errorPanel.add(label_error);
		errorPanel.validate();
		errorPanel.repaint();

		panelScreen.validate();
		panelScreen.repaint();

	}

	public void setLabelSuccess(String text) {
		if (label_success != null) {
			resultPanel.remove(label_success);
		}

		label_success.setText(text);
		label_success.setForeground(WHITE_BONE);

		resultPanel.setBackground(PALE_GREEN);
		resultPanel.setPreferredSize(new Dimension(720, 35));
		resultPanel.add(label_success);
		resultPanel.validate();
		resultPanel.repaint();

		panelScreen.validate();
		panelScreen.repaint();

	}

	public void generarAction() throws SQLException {
		String textoBusqueda = buscar_informe.getText();
		String consultaRealizar = (String) cb_informes.getSelectedItem();
		String[][] datos = null;
		String[] columnas = null;
		String label_text = " ";

		errorPanel.setBackground(WHITE_BONE);
		resultPanel.setBackground(WHITE_BONE);

		if (label_success != null) {
			resultPanel.remove(label_success);
		}

		resultPanel.setPreferredSize(new Dimension(0, 0));
		errorPanel.setPreferredSize(new Dimension(0, 0));

		datos = emptyData();
		columnas = emptyColumns();
		if (consultaRealizar.equals(" ")) {
			label_text = "Seleccione una de las opciones para generar el informe...";
			setLabelError(label_text);
		} else {
			if (consultaRealizar.equals("Lideres")) {
				datos = reportes.informeLider();
				columnas = reportes.columnasInformeLideres();
				label_text = "Total registros: " + datos.length;
				setLabelSuccess(label_text);
			} else {
				if (consultaRealizar.equals("Proyectos por proveedor")) {
					try {
						datos = reportes.informeProyectosConstructora();
						if (datos.length == 0) {
							datos = emptyData();
							columnas = emptyColumns();
						} else {
							label_text = "Total registros: 20";
							setLabelSuccess(label_text);
						}
						columnas = reportes.columnasInformeProyectosConstructora();
					} catch (Exception error) {
						if (textoBusqueda.equals("") || textoBusqueda.equals(null)) {
							label_text = "Introduzca un valor para realizar la búsqueda...";
							setLabelError(label_text);
						} else {
							label_text = "Introduzca un valor válido...";
							setLabelError(label_text);
						}
					} finally {
						createTable(datos, columnas);
					}
				} else if (consultaRealizar.equals("Proyectos por ciudad")) {
					if (textoBusqueda.equals("") || textoBusqueda.equals(null)) {
						datos = emptyData();
						columnas = emptyColumns();
						label_text = "Introduzca un valor para realizar la búsqueda...";
						setLabelError(label_text);
					} else {
						try {
							datos = reportes.informeProyectosCiudad(textoBusqueda);
							if (datos.length == 0) {
								datos = emptyData();
								columnas = emptyColumns();
							} else {
								label_text = "Total registros: " + reportes.tamañoInformePC();
								setLabelSuccess(label_text);
							}
							columnas = reportes.columnasProyectosCiudad();
						} catch (Exception error) {
							label_text = "Se encontraron errores en las busquedas...";
							setLabelError(label_text);
						} finally {
							createTable(datos, columnas);
						}

					}
				}
			}
		}
		try {
			createTable(datos, columnas);
			panelScreen.validate();
			panelScreen.repaint();
		} catch (Exception e) {
			label_text = "Error al crear la tabla...";
			setLabelError(label_text);
		}

	}

	public JPanel createPanelScreen() {
		label_informe = new JLabel("Informe");
		// top left bottom right
		label_informe.setBorder(new EmptyBorder(0, 0, 0, 10));

		button_generar = new JButton("Generar");
		button_generar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					generarAction();
				} catch (SQLException e) {

				}
				tablePanel.validate();
				tablePanel.repaint();
			}
		});

		buscar_informe = new JTextField();
		buscar_informe.setEnabled(false);

		headPanel.setLayout(new BoxLayout(headPanel, BoxLayout.X_AXIS));
		headPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		headPanel.add(label_informe);
		headPanel.add(listaInformes());
		headPanel.add(buscar_informe);
		headPanel.add(button_generar);

		try {
			createTable(emptyData(), emptyColumns());
		} catch (SQLException e) {
			label_error.setText("Error al cargar el componente tabla...");
			errorPanel.add(label_error);
		}

		tablePanel.setPreferredSize(new Dimension(720, 600));
		// Add labels to main panel
		panelScreen.setLayout(new BoxLayout(panelScreen, BoxLayout.Y_AXIS));
		panelScreen.add(errorPanel);
		panelScreen.add(headPanel);
		panelScreen.add(resultPanel);
		panelScreen.add(tablePanel);

		return panelScreen;
	}

	public void createAndShowGUI() {
		// 720, 480
		this.setSize(735, 595);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.setTitle("Reto 5: Proyectos vivienda | Misiontic 2022");
		this.getContentPane().setBackground(WHITE_BONE);
		this.setIconImage(new ImageIcon(getClass().getResource("favicon.png")).getImage());
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.getContentPane().add(createPanelScreen());
	}

	public static void run() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainFrame mainFrame = new MainFrame();
				mainFrame.createAndShowGUI();
			}
		});
	}
}
