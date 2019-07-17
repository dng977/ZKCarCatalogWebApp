package demo.getting_started.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.ext.Selectable;

import demo.getting_started.model.Car;
import demo.getting_started.model.CarService;
import demo.getting_started.model.CarServiceImpl;

public class SearchController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;
	
	private ListModel<Car> carsModel;
	
	private ItemRenderer itemRenderer;
	
	private CarService carService;
	
	private Car selectedCar;

	@Wire
	private Textbox keywordBox;
	@Wire
	private Listbox carListbox;
	@Wire
	private Label modelLabel;
	@Wire
	private Label makeLabel;
	@Wire
	private Label priceLabel;
	@Wire
	private Label descriptionLabel;
	@Wire
	private Image previewImage;
	@Wire
	private Component detailBox;
	@Wire
	private Button deleteButton;

	
	public SearchController() {
		System.out.println("Search controller  initialized");
		carService = new CarServiceImpl();
		itemRenderer = new ItemRenderer();
		
        carsModel = new ListModelList<Car>(carService.findAll());
        ((ListModelList<Car>)carsModel).setMultiple(true);
	}
	
    public ListModel<Car> getCarsModel() {
        return carsModel;
    }
    
    public CarService getCarService() {
    	return carService;
    }
    
	public ItemRenderer getItemRenderer() {
		return itemRenderer;
	}
    /**
     * Setting paging feature On and Off
     */
    @Listen("onCheck = #pageCheckBox")
    public void changePaging() {
    	if(carListbox.getMold().equals("paging")) {
    		//System.out.println("Mold set to default");
    		carListbox.setMold("default");
    		
    	}
    	else {
    		//System.out.println("Mold set to paging");	
    		carListbox.setMold("paging");
    		carListbox.getPagingChild().setPageSize(5);
    	}
    }
    
    @Listen("onClick = #addButton")
    public void addCar() {
        // show result
        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("parent", this);
        String template = "addcar.zul";
        Window window = (Window)Executions.createComponents(template,this.getSelf(), arguments);
        window.doModal();
    }
   
    public void confirmCar(Car newCar) {
    	carService.addCar(newCar);
    	search();
    }

    @Listen("onClick = #deleteButton")
    public void deleteCar() {
    	EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
    		public void onEvent(ClickEvent event) throws Exception {
    			if(Messagebox.Button.YES.equals(event.getButton())) {
    				carService.deleteCar(selectedCar);
					search();
					detailBox.setVisible(false);
					deleteButton.setVisible(false);
    			}
    		}
    	};
		Messagebox.show("Are you sure you want to delete "+selectedCar.getMake() +" " + selectedCar.getModel() + "?", "Delete car", 
				new Messagebox.Button[]{ Messagebox.Button.YES, Messagebox.Button.NO }, Messagebox.QUESTION, clickListener);
    	

    }
	@Listen("onClick = #searchButton")
	public void search(){
		String keyword = keywordBox.getValue();
		List<Car> result = carService.search(keyword);
		
		carsModel = new ListModelList<Car>(result);
    	carListbox.setModel(carsModel);
    	
		detailBox.setVisible(false);
		deleteButton.setVisible(false);	
	}
	
	@Listen("onSelect = #carListbox")
	public void showDetail(){
		detailBox.setVisible(true);	
		Set<Car> selection = ((Selectable<Car>)carListbox.getModel()).getSelection();
		if (selection!=null && !selection.isEmpty()){
			deleteButton.setVisible(true);
			Car selected = selection.iterator().next();
			
			selectedCar = selected;
			
			previewImage.setSrc(selected.getPreview());
			modelLabel.setValue(selected.getModel());
			makeLabel.setValue(selected.getMake());
			priceLabel.setValue(selected.getPrice().toString());
			descriptionLabel.setValue(selected.getDescription());
		}
	}
    
}
