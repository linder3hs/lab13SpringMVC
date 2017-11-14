package com.tecsup.gestion.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tecsup.gestion.exception.DAOException;
import com.tecsup.gestion.model.Employee;
import com.tecsup.gestion.services.EmployeeService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class EmployeeController {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private ApplicationContext context;

	@GetMapping("/admin/menu")
	public String menu() {

		return "/admin/menu";
	}

	@GetMapping("/admin/emp/list")
	public String list(@ModelAttribute("employee") Employee employee, ModelMap model) {

		try {
			model.addAttribute("employees", employeeService.findAll());
		} catch (Exception e) {
			logger.info(e.getMessage());
			model.addAttribute("message", e.getMessage());
		}

		return "admin/emp/list";
	}

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@GetMapping("/{employee_id}")
	public ModelAndView home(@PathVariable int employee_id, ModelMap model) {

		ModelAndView modelAndView = null;
		Employee emp = new Employee();
		try {
			emp = employeeService.find(employee_id);
			logger.info(emp.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		modelAndView = new ModelAndView("home", "command", emp);

		return modelAndView;
	}

	//////////////////////////////////////////////////////////////////////////////////////
	// SHOW EDIT FORM OR DELETE FORM//
	//////////////////////////////////////////////////////////////////////////////////////
	
	@GetMapping("/admin/emp/{action}/{employee_id}")
	public ModelAndView form(@PathVariable String action, @PathVariable int employee_id, ModelMap model) {

		// action = {"editform","deleteform"}
		logger.info("action = " + action);
		ModelAndView modelAndView = null;

		try {
			Employee emp = employeeService.find(employee_id);
			logger.info(emp.toString());
			modelAndView = new ModelAndView("admin/emp/" + action, "employee", emp);
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			modelAndView = new ModelAndView("admin/emp/" + action, "employee", new Employee());
		}

		return modelAndView;
	}

	//////////////////////////////////////////////////////////////////////////////////////
	// UPDATE //
	//////////////////////////////////////////////////////////////////////////////////////

	@PostMapping("/admin/emp/editsave")
	public ModelAndView editsave(@ModelAttribute("employee") @Valid Employee emp, BindingResult result,
			ModelMap model) {

		// logger.info("emp = " + emp);

		ModelAndView modelAndView = null;

		if (result.hasErrors()) {
			modelAndView = new ModelAndView("admin/emp/editform","employee", emp);
		} else {
			try {
				employeeService.update(emp.getLogin(), emp.getPassword(), emp.getFirstname(), emp.getLastname(),
						emp.getSalary(), 12);
				modelAndView = new ModelAndView("redirect:/admin/emp/list");
				
			} catch (Exception e) {
				// model.addAttribute("message", e.getMessage());
				// modelAndView = new ModelAndView("redirect:/admin/emp/list");
				model.addAttribute("message", e.getMessage());
				modelAndView = new ModelAndView("admin/emp/editform","employee", emp);
			}

		}

		return modelAndView;
	}

	//////////////////////////////////////////////////////////////////////////////////////
	// DELETE //
	//////////////////////////////////////////////////////////////////////////////////////

	@PostMapping("/admin/emp/delete")
	public ModelAndView delete(@ModelAttribute("employee") @Valid Employee emp, BindingResult result, ModelMap model) {

		ModelAndView modelAndView = null;

		if (result.hasErrors()) {
			modelAndView = new ModelAndView("admin/emp/deleteform/" + emp.getEmployeeId(), "employee", emp);
		} else {
			try {
				employeeService.delete(emp.getLogin());
				modelAndView = new ModelAndView("redirect:/admin/emp/list");
			} catch (Exception e) {
				// model.addAttribute("message", e.getMessage());
				// modelAndView = new ModelAndView("redirect:/admin/emp/list");
				model.addAttribute("message", e.getMessage());
				modelAndView = new ModelAndView("admin/emp/deleteform", "employee", emp);
			}
		}

		return modelAndView;
	}

	//////////////////////////////////////////////////////////////////////////////////////
	// CREATION //
	//////////////////////////////////////////////////////////////////////////////////////

	@GetMapping("/admin/emp/createform")
	public ModelAndView createform() {

		Employee emp = new Employee();

		ModelAndView modelAndView = new ModelAndView("admin/emp/createform", "employee", emp);

		return modelAndView;
	}

	@PostMapping("/admin/emp/create")
	public ModelAndView create(@ModelAttribute("employee") @Valid Employee emp, BindingResult result, ModelMap model) {

		// String msg = context.getMessage("employee.salary.min", null, Locale.US);
		// logger.info("message = " + msg);

		ModelAndView modelAndView = null;

		if (result.hasErrors()) {

			logger.info("result.getAllErrors();= " + result.getAllErrors());

			for (ObjectError error : result.getAllErrors()) {
				String theMessage = context.getMessage(error.getCode(), error.getArguments(), Locale.US);
				logger.info(error.getCode() + " = " + theMessage);
			}

			modelAndView = new ModelAndView("admin/emp/createform", "employee", emp);

		} else {
			try {
				employeeService.create(emp.getLogin(), emp.getPassword(), emp.getFirstname(), emp.getLastname(),
						emp.getSalary(), 12);
				logger.info("new Employee login = " + emp.getLogin());
				modelAndView = new ModelAndView("redirect:/admin/emp/list");

			} catch (DAOException e) {
				logger.error(e.getMessage());
				model.addAttribute("message", e.getMessage());
				modelAndView = new ModelAndView("admin/emp/createform", "employee", emp);
			}
		}
		//
		return modelAndView;
	}

}
