package br.com.development.web.conversor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.com.development.domain.Cargo;
import br.com.development.web.client.CargoClient;

@Component
public class StringToCargoConversor implements Converter<String, Cargo>{
	
	@Autowired
	private CargoClient cargoClient;

	@Override
	public Cargo convert(String text) {
		if(text.isEmpty()) {
			return null;
		}
		Long id = Long.valueOf(text);
		return cargoClient.buscarPorId(id);
	}

}
