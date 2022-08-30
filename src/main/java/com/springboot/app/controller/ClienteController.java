package com.springboot.app.controller;

import java.util.Map;

import javax.naming.Binding;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.springboot.app.models.dao.IClienteDao;
import com.springboot.app.models.entity.Cliente;

@Controller
@SessionAttributes("cliente")
public class ClienteController {
		// Qualifier que implementacion en concreta utilizaremos
		@Autowired
		private IClienteDao clienteDao;
	
		@RequestMapping(value="/listar", method=RequestMethod.GET)
		public String listar(Model model) {
			model.addAttribute("titulo", "Listado de clientes");
			model.addAttribute("clientes", clienteDao.findAll());
			return "listar";
		}
		@RequestMapping(value="/form", method=RequestMethod.GET)
		public String crear(Map<String, Object> model){
			//<nombredelparametro,objeto>
		Cliente cliente = new Cliente();
			
		model.put("cliente", cliente );
		model.put("titulo", "Formulario de cliente");
			return "form";
		}
		
		@RequestMapping(value="/form/{id}")
		public String editar(@PathVariable(value="id") Long id, Map<String, Object> model) {
				
				Cliente cliente = null;
				
			if(id>0) {
				cliente = clienteDao.findOne(id);
			}else {
				return "redirect:/listar";
			}
			model.put("cliente", cliente);
			model.put("titulo", "Editar cliente");
			return "form";
		}
		
		
		
		// El bindinResult siempre tiene que ir junto al objeto del formulario(cliente)
		@RequestMapping(value="/form", method=RequestMethod.POST)
		public String guardar(@Valid Cliente cliente, BindingResult result, Model model, SessionStatus status) {
			
			if(result.hasErrors()) {
				model.addAttribute("titulo", "Formulario del cliente");
				return "form";
			}
			clienteDao.save(cliente);
			// eliminamos la sesion
			status.setComplete();
			return "redirect:listar";
		}
		
		@RequestMapping(value="/eliminar/{id}")
		public String eliminar(@PathVariable(value="id")Long id ) {
				if(id>0) {
					clienteDao.delete(id);
				}
			return "redirect:/listar";
		}
}
