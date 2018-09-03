package br.com.development.web.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.development.domain.Cargo;
import br.com.development.domain.Departamento;
import br.com.development.web.client.CargoClient;
import br.com.development.web.client.DepartamentoClient;

@Controller
@RequestMapping("/cargos")
public class CargoController {
	
	@Autowired
	private CargoClient cargoClient;
	
	@Autowired
	private DepartamentoClient departamentoClient;
	
	@GetMapping("/cadastrar")
	public String cadastrar(Cargo cargo) {
		return "/cargo/cadastro";
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("cargos", cargoClient.buscarTodos());
		return "/cargo/lista";
	}
	
	@PostMapping("/salvar")
	public String salvar(Cargo cargo, RedirectAttributes redirectAttributes) {
		cargoClient.salvar(cargo);
		redirectAttributes.addFlashAttribute("success", "Cargo inserido com sucesso.");
		return "redirect:/cargos/cadastrar";
	}
	
	@ModelAttribute("departamentos")
	public List<Departamento> listaDeDepartamentos(){
		return Arrays.asList(departamentoClient.buscarTodos());
	}
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("cargo", cargoClient.buscarPorId(id));
		return "cargo/cadastro";
		
	}
	
	@PostMapping("/editar")
	public String editar(Cargo cargo, RedirectAttributes redirectAttributes) {
		cargoClient.editar(cargo);
		redirectAttributes.addFlashAttribute("success","Cargo editado com sucesso.");
		return "redirect:/cargos/cadastrar";	
	}
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		if(cargoClient.cargoTemFuncionarioRelacionado(id)) {
			redirectAttributes.addFlashAttribute("fail", "Cargo não excluido. Existem funcionário(s) vinculado(s).");
		}else {
			cargoClient.excluir(id);
			redirectAttributes.addFlashAttribute("success", "Cargo excluido com sucesso.");
		}
		
		return "redirect:/cargos/listar";
	}

}
