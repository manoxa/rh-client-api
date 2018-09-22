package br.com.development.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.development.domain.Departamento;
import br.com.development.web.client.DepartamentoClient;

@Controller
@RequestMapping("/departamentos")
public class DepartamentoController {
	
	@Autowired
	private DepartamentoClient departamentoClient;
	
	@GetMapping("/cadastrar")
	public String cadastrar(Departamento departamento) {
		return "departamento/cadastro";
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("departamentos", departamentoClient.buscarTodos());
		return "departamento/lista";
	}

	@PostMapping("/salvar")
	public String salvar(Departamento departamento, RedirectAttributes redirectAttributes) {
		departamentoClient.salvar(departamento);
		redirectAttributes.addFlashAttribute("success","Departamento inserido com sucesso.");
		return "redirect:/departamentos/cadastrar";
	}
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("departamento", departamentoClient.buscarPorId(id));
		return "departamento/cadastro";
		
	}
	
	@PostMapping("/editar")
	public String editar(Departamento departamento, RedirectAttributes redirectAttributes) {
		departamentoClient.editar(departamento);
		redirectAttributes.addFlashAttribute("success","Departamento editado com sucesso.");
		return "redirect:/departamentos/cadastrar";
	}
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap model) {
		
		if(departamentoClient.departamentoTemCargoRelacionado(id)) {
			model.addAttribute("fail", "Departamento não removido. Possui cargo(s) vinculados.");
		}else {
			Departamento departamento = departamentoClient.buscarPorId(id);
			departamentoClient.excluir(id);
			model.addAttribute("success", "Departamento " + departamento.getNome() + " removido com sucesso.");
		}
		return listar(model);
	}
	

}
