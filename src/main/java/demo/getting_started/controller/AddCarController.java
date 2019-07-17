package demo.getting_started.controller;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import demo.getting_started.model.Car;

public class AddCarController extends SelectorComposer<Component> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private SearchController parent;
	
	@Wire
	private Window addCarWin;
	
	@Wire
    private Textbox modelTextbox;
	@Wire
    private Textbox makeTextbox;
	@Wire
    private Intbox priceIntbox;
	@Wire
    private Textbox colourTextbox;
	@Wire
	private Textbox descriptionTextbox;
	
	@Listen("onClick = #submitButton")
	public void submit() {
		Car newCar = new Car();
		newCar.setModel(modelTextbox.getText());
		newCar.setMake(makeTextbox.getText());
		newCar.setPrice(priceIntbox.getValue());
		newCar.setColour(colourTextbox.getText());
		newCar.setDescription(descriptionTextbox.getText());
		parent.confirmCar(newCar);
		addCarWin.detach();
	}
	@Listen("onClick = #cancelButton")
	public void cancel() {
		addCarWin.detach();
	}
	
	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);
		parent = (SearchController) Executions.getCurrent().getArg().get("parent");
	}
}
