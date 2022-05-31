package calm.example.ec;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import calm.example.ec.domain.Difficulty;
import calm.example.ec.domain.Region;
import calm.example.ec.service.TourPackageService;
import calm.example.ec.service.TourService;

@SpringBootApplication
public class ExplorecaliApplication implements CommandLineRunner {

	@Value("${ec.importfile}")
	private String importFile;

	@Autowired
	private TourService tourService;

	@Autowired
	private TourPackageService tourPackageService;

	public static void main(String[] args) {
		SpringApplication.run(ExplorecaliApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		createTourPackages();

		createTours(importFile);

	}

	private void createTourPackages() {
		tourPackageService.createTourPackage("BC", "Backpack Cal");
		tourPackageService.createTourPackage("CC", "California Calm");
		tourPackageService.createTourPackage("CH", "California Hot springs");
		tourPackageService.createTourPackage("CY", "Cycle California");
		tourPackageService.createTourPackage("DS", "From Desert to Sea");
		tourPackageService.createTourPackage("KC", "Kids California");
		tourPackageService.createTourPackage("NW", "Nature Watch");
		tourPackageService.createTourPackage("SC", "Snowboard Cali");
		tourPackageService.createTourPackage("TC", "Taste of California");

	}

	private void createTours(String fileToImport) throws IOException {
		TourFromFile.read(fileToImport)
				.forEach(importedTour -> tourService.createTour(importedTour.getTitle(), importedTour.getDescription(),
						importedTour.getBlurb(),
						importedTour.getPrice(), importedTour.getLength(), importedTour.getBullets(),
						importedTour.getKeywords(), importedTour.getPackageType(), importedTour.getDifficulty(),
						importedTour.getRegion()));
	}

	/**
	 * Helper class to import ExploreCalifornia.json
	 */
	private static class TourFromFile {
		// fields
		private String packageType;
		private String title;
		private String description;
		private String blurb;
		private String price;
		private String length;
		private String bullets;
		private String keywords;
		private String difficulty;
		private String region;

		static List<TourFromFile> read(String fileToImport) throws IOException {
			return new ObjectMapper().setVisibility(PropertyAccessor.FIELD, Visibility.ANY).readValue(
					new FileInputStream(fileToImport),
					new TypeReference<List<TourFromFile>>() {
					});
		}

		public String getPackageType() {
			return packageType;
		}

		public String getTitle() {
			return title;
		}

		public String getDescription() {
			return description;
		}

		public String getBlurb() {
			return blurb;
		}

		public Integer getPrice() {
			return Integer.getInteger(price, 0);
		}

		public String getLength() {
			return length;
		}

		public String getBullets() {
			return bullets;
		}

		public String getKeywords() {
			return keywords;
		}

		public Difficulty getDifficulty() {
			return Difficulty.valueOf(difficulty);
		}

		public Region getRegion() {
			return Region.findByLabel(region);
		}

	}
}
