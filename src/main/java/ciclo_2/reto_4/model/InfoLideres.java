package ciclo_2.reto_4.model;

import java.sql.*;
import ciclo_2.reto_4.util.JDBCUtilities;

public class InfoLideres {

	Connection conexion;
	String[][] informeLideres;
	
	public InfoLideres() throws SQLException{
		conexion = JDBCUtilities.getConnection();
		String consulta = "SELECT ID_Lider, Nombre, Primer_Apellido, Ciudad_Residencia \r\n"
				+ "FROM Lider\r\n"
				+ "ORDER BY Ciudad_Residencia ASC";
		
		Statement stmt = conexion.createStatement();
		
		ResultSet datos = stmt.executeQuery(consulta);

		informeLideres = new String[50][4];
		
		int aux = 0;
		
		while(datos.next()) {
			int id = datos.getInt("ID_Lider");
			String nombre = datos.getString("Nombre");
			String apellido = datos.getString("Primer_Apellido");
			String ciudad = datos.getString("Ciudad_Residencia");
			informeLideres[aux][0] = String.valueOf(id);
			informeLideres[aux][1] = nombre;
			informeLideres[aux][2] = apellido;
			informeLideres[aux][3] = ciudad;
			
			aux++;
		}
		
		conexion.close();
	}
	
	public String[][] getInformeLideres() {
		return informeLideres;
	}
	
	public String[] getColumnasLideres() {
		String[] columnas = {"ID_Lider", "Nombre", "Primer apellido", "Ciudad residencia"};
		return columnas;
	}
	
}