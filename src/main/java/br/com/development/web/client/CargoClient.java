package br.com.development.web.client;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.com.development.domain.Cargo;

@Component
public class CargoClient {
	
	RestTemplate restTemplate = new RestTemplate();
	
	public List<Cargo> buscarTodos(){
		RequestEntity<Void> request = RequestEntity
				.get(URI.create("http://localhost:9000/cargos")).build();
		ResponseEntity<Cargo[]> response = restTemplate.exchange(request, Cargo[].class);
		return Arrays.asList(response.getBody());
	}
	
	public Cargo buscarPorId(Long id){
		return restTemplate.getForObject("http://localhost:9000/cargos/{id}", Cargo.class, id);
	}
	
	public boolean cargoTemFuncionarioRelacionado(Long id) {
		return restTemplate.getForObject("http://localhost:9000/cargos/temfuncionarios/{id}", Boolean.class, id);
	}
	
	public Cargo salvar(Cargo cargo){
		return restTemplate.postForObject("http://localhost:9000/cargos/", cargo, Cargo.class);
	}
	
	public void editar(Cargo cargo) {
		restTemplate.put("http://localhost:9000/cargos/{id}", cargo, cargo.getId());
	}
	
	public void excluir(Long id) {
		restTemplate.delete("http://localhost:9000/cargos/{id}", id);
	}


}
