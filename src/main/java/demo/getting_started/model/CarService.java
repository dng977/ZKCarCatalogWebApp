package demo.getting_started.model;

import java.util.List;

public interface CarService {

	/**
	 * Retrieve all cars in the catalog.
	 * @return all cars
	 */
	public List<Car> findAll();
	
	/**
	 * search cars according to keyword in name and company.
	 * @param keyword for search
	 * @return list of car that match the keyword
	 */
	public List<Car> search(String keyword);
	
	/**
	 * adds a car to the list
	 * @param car
	 */
	public void addCar(Car car);

	public void deleteCar(Car selectedCar);
}
