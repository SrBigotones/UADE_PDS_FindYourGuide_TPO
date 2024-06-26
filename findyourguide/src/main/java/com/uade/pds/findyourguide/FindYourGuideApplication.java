package com.uade.pds.findyourguide;

import com.uade.pds.findyourguide.model.Resenia;
import com.uade.pds.findyourguide.model.trofeo.TrofeoExito;
import com.uade.pds.findyourguide.model.trofeo.TrofeoResenia;
import com.uade.pds.findyourguide.service.TrofeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FindYourGuideApplication {


	@Bean
	@Autowired
	public TrofeoExito newObserverExito(Resenia resenia){
		TrofeoExito trofeoExito = new TrofeoExito();
		resenia.agregar(trofeoExito);
		return trofeoExito;
	}
	@Bean
	@Autowired
	public TrofeoResenia newObserverResenia(Resenia resenia){
		TrofeoResenia trofeoExito = new TrofeoResenia();
		resenia.agregar(trofeoExito);
		return trofeoExito;
	}
	public static void main(String[] args) {

		SpringApplication.run(FindYourGuideApplication.class, args);
	}

}
