package demo.getting_started.tests;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;

import demo.getting_started.model.Car;
import demo.getting_started.model.CarService;
import demo.getting_started.model.CarServiceImpl;

class AddDeleteCarTest {
	
	CarService carService;
	
	
	@Test
	void testAddCar() {
		carService = new CarServiceImpl();
		int sizeBefore = carService.findAll().size();
		carService.addCar(new Car());
		assertEquals(sizeBefore + 1, carService.findAll().size());
	}
	
	@Test
	void deleteCar() {
		carService = new CarServiceImpl();
		int sizeBefore = carService.findAll().size();
		carService.deleteCar(carService.findAll().get(0));
		assertEquals(sizeBefore - 1, carService.findAll().size());
	}

}
