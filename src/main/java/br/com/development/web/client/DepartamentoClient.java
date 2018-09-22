package br.com.development.web.client;

import java.net.URI;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.com.development.domain.Departamento;

@Component
public class DepartamentoClient {


	RestTemplate restTemplate = new RestTemplate();
	
	public Departamento[] buscarTodos(){
		RequestEntity<Void> request = RequestEntity
				.get(URI.create("http://rh-api:8080/departamentos")).build();
		ResponseEntity<Departamento[]> response = restTemplate.exchange(request, Departamento[].class);
		return response.getBody();
	}
	
	public Departamento buscarPorId(Long id){
		Departamento departamento = restTemplate.getForObject("http://rh-api:8080/departamentos/{id}", Departamento.class, id);
		return departamento;
	}
	
	public boolean departamentoTemCargoRelacionado(Long id) {
		return restTemplate.getForObject("http://rh-api:8080/departamentos/temcargos/{id}", Boolean.class, id);
	}
	
	public Departamento salvar(Departamento departamento){
		return restTemplate.postForObject("http://rh-api:8080/departamentos", departamento, Departamento.class);
	}

	public void editar(Departamento departamento) {
		restTemplate.put("http://rh-api:8080/departamentos/{id}", departamento, departamento.getId());
	}
	
	public void excluir(Long id) {
		restTemplate.delete("http://rh-api:8080/departamentos/{id}", id);
	}
}
