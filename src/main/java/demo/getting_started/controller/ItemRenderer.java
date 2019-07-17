package demo.getting_started.controller;

import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import demo.getting_started.model.Car;

public class ItemRenderer implements ListitemRenderer<Car> {

	@Override
	public void render(Listitem listitem, Car data, int index) throws Exception {
		listitem.appendChild(new Listcell(data.getModel()));
		listitem.appendChild(new Listcell(data.getMake()));
		
		Listcell cell = new Listcell();
		changeColour(cell, data.getPrice());
		listitem.appendChild(cell);
		
		listitem.appendChild(new Listcell(data.getColour()));
		listitem.setValue(data);	
	}
	
	private void changeColour(Listcell cell, int price) {	
		Label label = new Label(price + "");
		if(price < 20000) {
			label.setStyle("color: green;font-weight: bold");
		}
		else 
			if(price<50000) {
				label.setStyle("color: yellow;font-weight: bold");	
			}
			else {
				label.setStyle("color: red;font-weight: bold");
			}			
		cell.appendChild(label);
	}

}
