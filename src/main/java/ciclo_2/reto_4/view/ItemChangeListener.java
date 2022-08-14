package ciclo_2.reto_4.view;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JTextField;

public class ItemChangeListener implements ItemListener {
	
	JTextField buscar_informe;
	
	public ItemChangeListener(JTextField buscar) {
		this.buscar_informe = buscar;
	}
	
	public void itemStateChanged(ItemEvent event) {
		if(event.getStateChange() == ItemEvent.SELECTED) {
			Object item = event.getItem();
			if(("Lideres".equals(item.toString())) || ("".equals(item.toString()) || ("Proyectos por proveedor".equals(item.toString())))) {
				this.buscar_informe.setEnabled(false);
			} else {
				this.buscar_informe.setEnabled(true);
			}
			this.buscar_informe.setText("");
		}
	}
}
