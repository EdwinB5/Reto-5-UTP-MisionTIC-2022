package ciclo_2.reto_4.util;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCUtilities {
	// Atributos de clase para gestión de conexión con la base de datos
	private static final String UBICACION_BD = "ProyectosConstruccion.db";

	public static Connection getConnection() throws SQLException {
		String url = "jdbc:sqlite:" + UBICACION_BD;
		return DriverManager.getConnection(url);
	}
}
