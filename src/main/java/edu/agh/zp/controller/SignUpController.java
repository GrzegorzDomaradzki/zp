package edu.agh.zp.controller;

import edu.agh.zp.hibernate.CitizenEntity;
import edu.agh.zp.hibernate.CitizenRepository;
import edu.agh.zp.objects.User;
import edu.agh.zp.services.CitizenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import edu.agh.zp.SecurityConfig;
import org.springframework.beans.factory.*;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping (value={"/signup"})
public class SignUpController {
	@Autowired
	private ApplicationContext context;
	private CitizenService cS;

	@GetMapping (value = {""})
	public ModelAndView index() {
		String viewName = "signup";
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("user", new User());

		return new ModelAndView(viewName, model);
	}
	@PostMapping("")
	public ModelAndView submitRegister(@ModelAttribute("user") User user, BindingResult res){
		if( res.hasErrors()){

		}else{
			CitizenEntity citizen = new CitizenEntity();
			citizen.setHash(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
			citizen.setPesel(user.getPesel());
			citizen.setIdNumber(user.getIdnumber());
			CitizenRepository repo = context.getBean(CitizenRepository.class);
			cS =  new CitizenService(context.getBean(CitizenRepository.class));
			repo.save(citizen);

			//cS.create(citizen);

		}
		RedirectView redirect = new RedirectView();
		redirect.setUrl("");
		return new ModelAndView(redirect);
	}
}