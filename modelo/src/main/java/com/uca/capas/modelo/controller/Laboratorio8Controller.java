package com.uca.capas.modelo.controller;

import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;	
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;	
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;	


import com.uca.capas.modelo.domain.Cliente;
import com.uca.capas.modelo.service.ClienteService;

@Controller
public class Laboratorio8Controller {
	@Autowired
	private ClienteService clienteService;

	
	
	//MENU PRINCIPAL LABORATORIO 8
	@RequestMapping("/indexLaboratorio8")
	public ModelAndView indexLaboratorio8() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("Laboratorio/indexLaboratorio8");
		return mav;
	}
	//Pantalla de insertar cliente
	@RequestMapping("/insertcliente")
	public ModelAndView cliente() {
		ModelAndView mav = new ModelAndView();
		Cliente cliente = new Cliente();
		mav.addObject("cliente", cliente);
		mav.setViewName("Laboratorio/agregarCliente");
		return mav;
	}
	//Pantalla de procedimiento
	@RequestMapping("/procAlmacenadoJdbc")
	public ModelAndView procedimientoAlmacenadoJdbc() {
		ModelAndView mav = new ModelAndView();
		Cliente cliente = new Cliente();
		mav.addObject("usuario", cliente );
		mav.setViewName("Laboratorio/procedimiento");
		return mav;
	}
	
	//Guardar cliente
	@RequestMapping(value = "/savecliente", method = RequestMethod.POST)
	public ModelAndView guardarCliente(@Valid @ModelAttribute("cliente") Cliente client, BindingResult result) {
		ModelAndView mav = new ModelAndView();
		Cliente cliente = new Cliente();
		Integer codigo = null;
		mav.addObject("cliente", cliente);

		if(!result.hasErrors()) {
			
			if(client.getCcliente() != null) {
				mav.addObject("resultado", 1);
				clienteService.updateCliente(client);
				
			}else {
				mav.addObject("resultado", 1);
				codigo = clienteService.insertClienteAutoId(client);
			}

			mav.addObject("resultado", codigo);
			mav.setViewName("Laboratorio/indexLaboratorio8");
			
			
		}else {
			mav.addObject("resultado", 0);
			mav.setViewName("Laboratorio/agregarCliente");
			

		}

		return mav;
	}
	//Ejecutar procedimiento
	@RequestMapping("/ejecutarProcedimientoJdbc")
    public ModelAndView ejecutarProcedimiento(@RequestParam Integer cliente, @RequestParam Boolean estado){
        ModelAndView mav = new ModelAndView();
        Integer resultado;
        resultado = clienteService.ejecutarProcedimiento(cliente, estado);
        mav.addObject("resultado", resultado);
        mav.setViewName("Laboratorio/resultado");
        return mav;
    }
	//CargarVehiculo
	@RequestMapping("/batchVehiculo")
    public ModelAndView cargarVehiculos() throws ParseException{
        ModelAndView mav = new ModelAndView();
        Logger log = Logger.getLogger(Laboratorio8Controller.class.getName());
        long tiempo1 = System.nanoTime();
        clienteService.cargaMasiva();
        long tiempo2 = System.nanoTime();
        long tiempoTotal = (tiempo2-tiempo1)/1000000;
        log.info("Duracion del metodo -> "+Long.toString(tiempoTotal)+" milisegundos");

        mav.setViewName("Laboratorio/resultado");
        return mav;
    }
}
