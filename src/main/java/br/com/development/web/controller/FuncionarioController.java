package br.com.development.web.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.development.domain.Cargo;
import br.com.development.domain.Funcionario;
import br.com.development.domain.UF;
import br.com.development.web.client.CargoClient;
import br.com.development.web.client.FuncionarioClient;

@Controller
@RequestMapping("/funcionarios")
public class FuncionarioController {

	@Autowired
	private FuncionarioClient funcionarioClient;

	@Autowired
	private CargoClient cargoClient;

	@GetMapping("/cadastrar")
	public String cadastrar(Funcionario funcionario) {
		return "/funcionario/cadastro";
	}

	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("funcionarios", funcionarioClient.buscarTodos());
		return "/funcionario/lista";
	}

	@PostMapping("/salvar")
	public String salvar(Funcionario funcionario, RedirectAttributes redirectAttributes) {
		funcionarioClient.salvar(funcionario);
		redirectAttributes.addFlashAttribute("success", "Funcionario inserido com sucesso.");
		return "redirect:/funcionarios/cadastrar";
	}

	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("funcionario", funcionarioClient.buscarPorId(id));
		return "/funcionario/cadastro";

	}

	@PostMapping("/editar")
	public String editar(Funcionario funcionario, RedirectAttributes redirectAttributes) {
		funcionarioClient.editar(funcionario);
		redirectAttributes.addFlashAttribute("success", "Funcionario editado com sucesso.");
		return "redirect:/funcionarios/cadastrar";
	}

	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap model) {
		Funcionario funcionario = funcionarioClient.buscarPorId(id);
		funcionarioClient.excluir(id);
		model.addAttribute("success", "Funcionario " + funcionario.getNome() + " removido com sucesso.");
		return listar(model);
	}
	
	@GetMapping("/buscar/nome")
	public String getPorNome(@RequestParam("nome") String nome, ModelMap model) {
		model.addAttribute("funcionarios", funcionarioClient.buscarPorNome(nome));
		return "/funcionario/lista";
	}
	
	@GetMapping("/buscar/cargo")
	public String getPorcargo(@RequestParam("id") Long id, ModelMap model) {
		model.addAttribute("funcionarios", funcionarioClient.buscarCargoPorId(id));
		return "/funcionario/lista";
	}
	
	@GetMapping("/buscar/data")
	public String getPorDatas(@RequestParam("entrada") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate entrada,
							  @RequestParam("saida") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  LocalDate saida,
							  ModelMap model) {
		model.addAttribute("funcionarios", funcionarioClient.buscarPorDatas(entrada, saida));
		return "/funcionario/lista";
		
	}
	

	@ModelAttribute("cargos")
	public List<Cargo> getCargos() {
		return cargoClient.buscarTodos();
	}

	@ModelAttribute("ufs")
	public UF[] getUfs() {
		return UF.values();
	}

}
