package calm.example.ec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import calm.example.ec.domain.Difficulty;
import calm.example.ec.domain.Region;
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
     * @param title title
     * @param description description
     * @param blurb blurb
     * @param price price
     * @param duration duration
     * @param bullets bullets
     * @param keywords keywords
     * @param tourPackageName tourPacakageName
     * @param difficulty difficulty
     * @param region region
     * @return Tour entity
     */
    public Tour createTour(String title, String description, String blurb, Integer price, String duration,
            String bullets, String keywords, String tourPackageName,
            Difficulty difficulty, Region region) {

        TourPackage tourPackage = tourPackageRepository.findById(tourPackageName)
                .orElseThrow(() -> new RuntimeException("Tour Package not found " + tourPackageName));

        return tourRepository.save(new Tour(title, description, blurb, price, duration, bullets, keywords, tourPackage,
                difficulty, region));
    }

    public long total() {
        return tourRepository.count();
    }
}
