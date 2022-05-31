package calm.example.ec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import calm.example.ec.service.TourPackageService;
import calm.example.ec.service.TourService;

@SpringBootApplication
public class ExplorecaliApplication {

	@Autowired
	private TourService tourService;

	@Autowired
	private TourPackageService tourPackageService;

	public static void main(String[] args) {
		SpringApplication.run(ExplorecaliApplication.class, args);
	}

}
