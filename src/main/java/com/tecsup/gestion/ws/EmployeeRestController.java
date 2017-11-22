package com.tecsup.gestion.ws;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.tecsup.gestion.exception.DAOException;
import com.tecsup.gestion.exception.EmptyResultException;
import com.tecsup.gestion.model.Employee;
import com.tecsup.gestion.services.EmployeeService;

@RestController
public class EmployeeRestController {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeRestController.class);

	@Autowired
	private EmployeeService employeeService;

	/**
	 * 
	 * @return
	 */
	@GetMapping("/emp")
	public ResponseEntity<List<Employee>> list() {

		List<Employee> list = null;

		try {
			list = employeeService.findAll();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		if (list == null || list.isEmpty())
			return new ResponseEntity<List<Employee>>(HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<List<Employee>>(list, HttpStatus.OK);
	}
	
    /**
* 
* @param id
* @return
*/
@GetMapping(path = "/emp/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<Employee> getEmployee(@PathVariable("id") int id) {
	Employee emp = null;

	try {
		emp = employeeService.find(id);
		logger.info(emp.toString());
	} catch (Exception e) {
		logger.error(e.getMessage());
	}

	if (emp == null)
		return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
	else
		return new ResponseEntity<Employee>(emp, HttpStatus.OK);
	}

/**
* 
* @param emp
* @param ucBuilder
* @return
*/
@PostMapping("/emp")
public ResponseEntity<Void> createUser(@RequestBody Employee emp, UriComponentsBuilder ucBuilder) {

logger.info("Creating User " + emp.getLogin());

try {
	if (employeeService.isEmployeeExist(emp)) {
		logger.info("A Employee with login " + emp.getLogin() + " already exist");
		return new ResponseEntity<Void>(HttpStatus.CONFLICT);
	}

	employeeService.create(emp.getLogin(), emp.getPassword(), emp.getFirstname(), emp.getLastname(),
			emp.getSalary(), 12);

	emp = employeeService.findEmployeeByLogin(emp.getLogin());

} catch (DAOException | EmptyResultException e) {
	logger.error(e.getMessage());

}

HttpHeaders headers = new HttpHeaders();
headers.setLocation(ucBuilder.path("/emp/{id}").buildAndExpand(emp.getEmployeeId()).toUri());
return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
}

/**
 * 
 * @param id
 * @param emp
 * @return
 */
@PutMapping("/emp/{id}")
public ResponseEntity<Employee> updateEmp(@PathVariable("id") int id, @RequestBody Employee emp) {

	logger.info("Updating Employee " + id);

	Employee currentEmp = null;
	try {
		currentEmp = employeeService.find(id);

		if (currentEmp == null) {
			logger.info("Employee with id " + id + " not found");
			return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
		}

		currentEmp.setFirstname(emp.getFirstname());
		currentEmp.setLastname(emp.getLastname());

		employeeService.update(currentEmp.getLogin(), currentEmp.getPassword(), currentEmp.getFirstname(),
				currentEmp.getLastname(), currentEmp.getSalary(), -1);

	} catch (DAOException | EmptyResultException e) {
		logger.error(e.getMessage());
	}

	
	return new ResponseEntity<Employee>(currentEmp, HttpStatus.OK);

}

/**
 * 
 * @param id
 * @return
 */
@DeleteMapping("/emp/{id}")
public ResponseEntity<Employee> deleteEmp(@PathVariable("id") int id) {
	logger.info("Fetching & Deleting Employee with id " + id);

	Employee emp;
	try {
		emp = employeeService.find(id);
		
		if (emp == null) {
			logger.info("Unable to delete. Employee with id " + id + " not found");
			return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
		}

		employeeService.delete(emp.getLogin());
		
	} catch (DAOException | EmptyResultException e) {

		logger.error(e.getMessage());
	}

	return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
}

}
