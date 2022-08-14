package ciclo_2.reto_4.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ciclo_2.reto_4.util.JDBCUtilities;

public class ProyectosCompra {

	Connection conexion;
	String[][] informeCompra;
	
	public ProyectosCompra() throws SQLException{
		conexion = JDBCUtilities.getConnection();
		String consulta = "SELECT ID_Compra, Constructora, Banco_Vinculado \r\n"
				+ "FROM Compra Cp\r\n"
				+ "JOIN Proyecto Py ON Cp.ID_Proyecto  = Py.ID_Proyecto\r\n"
				+ "WHERE Py.Ciudad = 'Salento' AND Cp.Proveedor = 'Homecenter'";
		
		Statement stmt = conexion.createStatement();
		ResultSet datos = stmt.executeQuery(consulta);
		
		int aux = 0;
		informeCompra = new String[28][3];
		
		while(datos.next()) {
			int id = datos.getInt("ID_Compra");
			String constructora = datos.getString("Constructora");
			String banco = datos.getString("Banco_Vinculado");
			informeCompra[aux][0] = String.valueOf(id);
			informeCompra[aux][1] = constructora;
			informeCompra[aux][2] = banco;
		
			aux++;
		}
		
	}
	
	public String[][] getInformeCompras() {
		return informeCompra;
	}
	
	public String[] getColumnasInformeCompras() {
		String[] columnas = {"Id", "Constructora", "Banco vinculado"};
		return columnas;
	}
	
}
