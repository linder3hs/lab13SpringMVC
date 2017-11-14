package com.tecsup.gestion.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tecsup.gestion.exception.DAOException;
import com.tecsup.gestion.exception.LoginException;
import com.tecsup.gestion.model.Credential;
import com.tecsup.gestion.model.Employee;
import com.tecsup.gestion.services.SecurityService;


/**
 *  Handles requests for the application home page.
 */
@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private SecurityService securityService;

	@GetMapping("/login")
	public ModelAndView preLogin() {
		Credential credential = new Credential();
		return new ModelAndView("login", "credential", credential);
	}

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@PostMapping("/login")
	public ModelAndView login(@ModelAttribute("credential")  @Valid Credential credential, BindingResult result, ModelMap model) {

		logger.info("login()");
		logger.info(credential.toString());

		ModelAndView modelAndView = null;

		if (result.hasErrors()) {
			
			modelAndView = new ModelAndView("login", "credential", credential);
			
		} else {
			
			try {
				Employee emp = securityService.validate(credential.getLogin(), credential.getPassword());
				logger.info("--" + emp.toString());
				modelAndView = new ModelAndView("redirect:/admin/menu", "command", emp);
			} catch (LoginException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				model.addAttribute("message", "Usuario y/o clave incorrectos");
				modelAndView = new ModelAndView("login", "credential", new Credential());
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				model.addAttribute("message", e.getMessage());
				modelAndView = new ModelAndView("login", "credential", new Credential());
			}
			
		}
		
		return modelAndView;
	}

}
