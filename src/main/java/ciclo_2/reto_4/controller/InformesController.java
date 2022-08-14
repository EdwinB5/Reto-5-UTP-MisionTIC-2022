package ciclo_2.reto_4.controller;

import ciclo_2.reto_4.model.ProyectosConstructora;
import ciclo_2.reto_4.model.ProyectosCompra;
import ciclo_2.reto_4.model.InfoLideres;
import java.sql.SQLException;

public class InformesController {
	ProyectosConstructora proyectos_constructora;
	ProyectosCompra proyectos_ciudad;
	InfoLideres lideres;
	
	public String[][] informeProyectosCiudad(String ciudad) throws SQLException{
		proyectos_constructora = new ProyectosConstructora(ciudad);
		return proyectos_constructora.getInformeLista();
	}
	
	public String[] columnasProyectosCiudad() {
		return proyectos_constructora.getColumnasPB();
	}
	
	public String[][] informeProyectosConstructora() throws SQLException{
		proyectos_ciudad = new ProyectosCompra();
		return proyectos_ciudad.getInformeCompras();
	}
	
	public String[] columnasInformeProyectosConstructora() {
		return proyectos_ciudad.getColumnasInformeCompras();
	}
	
	public String[][] informeLider() throws SQLException{
		lideres = new InfoLideres();
		return lideres.getInformeLideres();
	}
	
	public String[] columnasInformeLideres() {
		return lideres.getColumnasLideres();
	}
	
	public int tamañoInformePC() {
		return proyectos_constructora.getTamañoPC();
	}
}
