package ciclo_2.reto_4.model;

import ciclo_2.reto_4.util.JDBCUtilities;
import java.sql.*;

public class ProyectosConstructora {
	
	Connection conexion;
	String[][] informeLista;
	int tamaño;
	
	public ProyectosConstructora(String ciudad_consulta) throws SQLException{
		conexion = JDBCUtilities.getConnection();
		String consulta = "SELECT ID_Proyecto, Constructora, Numero_Habitaciones, Ciudad \r\n"
				+ "FROM Proyecto\r\n"
				+ "WHERE Ciudad = '" + ciudad_consulta + "' AND Clasificacion = 'Casa Campestre'";
		
		String consulta_total_registros = "SELECT COUNT(*) AS TOTAL \r\n"
				+ "FROM Proyecto\r\n"
				+ "WHERE Ciudad = '"+ ciudad_consulta +"' AND Clasificacion = 'Casa Campestre'";
		
		Statement stmt = conexion.createStatement();
		Statement stmt_total = conexion.createStatement();
		
		ResultSet datos = stmt.executeQuery(consulta);
		ResultSet total_datos = stmt_total.executeQuery(consulta_total_registros);
		
		int aux = 0;
		tamaño = total_datos.getInt("TOTAL");
		informeLista = new String[28][4];
		
		while(datos.next()) {
			int id = datos.getInt("ID_Proyecto");
			String constructora = datos.getString("Constructora");
			int habitaciones = datos.getInt("Numero_Habitaciones");
			String ciudad = datos.getString("Ciudad");
		
			informeLista[aux][0] = String.valueOf(id);
			informeLista[aux][1] = constructora;
			informeLista[aux][2] = String.valueOf(habitaciones);
			informeLista[aux][3] = ciudad;
			
			aux++;	
		}
		
		conexion.close();
	}
	
	ProyectosConstructora(){
		
	}

	public String[][] getInformeLista() {
		return informeLista;
	}
	
	public String[] getColumnasPB() {
		String[] columnas = {"ID_Proyecto", "Constructora", "Habitaciones", "Ciudad"};
		return columnas;
	}
	
	public int getTamañoPC() {
		return tamaño;
	}
	
}