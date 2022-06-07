package calm.example.ec.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import calm.example.ec.domain.Tour;
import calm.example.ec.domain.TourPackage;
import calm.example.ec.repo.TourPackageRepository;
import calm.example.ec.repo.TourRepository;

@Service
public class TourService {

    private TourPackageRepository tourPackageRepository;

    private TourRepository tourRepository;

    @Autowired
    public TourService(TourRepository tourRepository, TourPackageRepository tourPackageRepository) {
        this.tourPackageRepository = tourPackageRepository;
        this.tourRepository = tourRepository;
    }

   /**
    * 
    * @param title
    * @param tourPackageName
    * @param details
    * @return
    */
    public Tour createTour(String title, String tourPackageName, Map<String, String> details) {

        TourPackage tourPackage = tourPackageRepository.findByName(tourPackageName)
                .orElseThrow(() -> new RuntimeException("Tour Package not found " + tourPackageName));

        return tourRepository.save(new Tour(title, tourPackage, details));
    }

    public long total() {
        return tourRepository.count();
    }
}
