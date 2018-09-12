package br.com.development.web.client;

import java.net.URI;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.com.development.domain.Funcionario;

@Component
public class FuncionarioClient {
	
	RestTemplate restTemplate = new RestTemplate();

	public Funcionario[] buscarTodos() {
		RequestEntity<Void> request = RequestEntity
				.get(URI.create("http://localhost:9000/funcionarios")).build();
		ResponseEntity<Funcionario[]> response = restTemplate.exchange(request, Funcionario[].class);
		return response.getBody();
	}

	public Funcionario salvar(Funcionario funcionario) {
		return restTemplate.postForObject("http://localhost:9000/funcionarios", funcionario, Funcionario.class);	
	}

	public Funcionario buscarPorId(Long id) {
		Funcionario funcionario = restTemplate.getForObject("http://localhost:9000/departamentos/{id}", Funcionario.class, id);
		return funcionario;
	}

	public void editar(Funcionario funcionario) {
		restTemplate.put("http://localhost:9000/funcionarios/{id}", funcionario, funcionario.getId());
	}
	
	public void excluir(Long id) {
		restTemplate.delete("http://localhost:9000/funcionarios/{id}", id);
	}

	public List<Funcionario> buscarPorNome(String nome) {
		Funcionario[] funcionarios = restTemplate.getForObject("http://localhost:9000/funcionarios/{nome}", Funcionario[].class, nome);
		return Arrays.asList(funcionarios);
	}

	public List<Funcionario> buscarCargoPorId(Long id) {
		 Funcionario[] funcionarios = restTemplate.getForObject("http://localhost:9000/funcionarios/{id}", Funcionario[].class, id);
		return Arrays.asList(funcionarios);
	}

	public List<Funcionario> buscarPorDatas(LocalDate entrada, LocalDate saida) {
		 Funcionario[] funcionarios = restTemplate.getForObject("http://localhost:9000/funcionarios/{dataEntrada}/{dataSaida}", Funcionario[].class, entrada, saida);
		return Arrays.asList(funcionarios);
	}

}
