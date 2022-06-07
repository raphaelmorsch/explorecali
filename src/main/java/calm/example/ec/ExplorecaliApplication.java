package calm.example.ec;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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
				.forEach(importedTour -> tourService.createTour(importedTour.getTitle(), importedTour.getPackageName(),
						importedTour.getDetails()));
	}

	/**
	 * Helper class to import ExploreCalifornia.json
	 */
	private static class TourFromFile {
		// fields
		private String title;
		private String packageName;
		private Map<String, String> details;

		static List<TourFromFile> read(String fileToImport) throws IOException {
			List<Map<String, String>> recordsFromFile = new ObjectMapper()
					.setVisibility(PropertyAccessor.FIELD, Visibility.ANY).readValue(
							new FileInputStream(fileToImport),
							new TypeReference<List<Map<String, String>>>() {
							});
			return recordsFromFile.stream().map(TourFromFile::new).collect(Collectors.toList());
		}

		TourFromFile(Map<String, String> recordFromFile) {
			this.title = recordFromFile.get("title");
			this.packageName = recordFromFile.get("packageType");
			this.details = recordFromFile;
			this.details.remove("packageType");
			this.details.remove("title");
		}

		public Map<String, String> getDetails() {
			return details;
		}

		public String getPackageName() {
			return packageName;
		}

		public String getTitle() {
			return title;
		}

	}
}
