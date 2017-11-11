package examinator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public ModelAndView hello(ModelAndView modelAndView) {
		return new ModelAndView("redirect:/create");
	}
	
	@GetMapping("/create")
	public String hello(ModelMap model) {
		return "create";
	}

}
