package calm.example.ec.controller;

import java.util.Map;  
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import calm.example.ec.domain.Tour;
import calm.example.ec.domain.TourRating;
import calm.example.ec.repo.TourRatingRepository;
import calm.example.ec.repo.TourRepository;

@RestController
@RequestMapping(path = "tours/{tourId}/ratings")
public class TourRatingController {

    private TourRatingRepository tourRatingRepository;

    private TourRepository tourRepository;

    @Autowired
    public TourRatingController(TourRatingRepository tourRatingRepository, TourRepository tourRepository) {
        this.tourRatingRepository = tourRatingRepository;
        this.tourRepository = tourRepository;
    }

    protected TourRatingController() {
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTourRating(@PathVariable(value = "tourId") String tourId,
            @RequestBody @Validated TourRating tourRating) {
        verifyTour(tourId);
        tourRatingRepository.save(new TourRating(tourId, tourRating.getCustomerId(),
                tourRating.getScore(), tourRating.getComment()));
    }

    /**
     * 
     * @param tourId
     * @return Tour object found
     * @throws NoSuchElementException
     */
    private Tour verifyTour(String tourId) throws NoSuchElementException {
        return tourRepository.findById(tourId)
                .orElseThrow(() -> new NoSuchElementException("Tour not found " + tourId));
    }

    /**
     * Verify and Return the TourRating for a particular tourId and customerId
     * 
     * @param tourId
     * @param customerId
     * @return the TourRating found
     * @throws NoSuchElementException
     */
    private TourRating verifyTourRating(String tourId, int customerId) throws NoSuchElementException {
        return tourRatingRepository.findByTourIdAndCustomerId(tourId, customerId)
                .orElseThrow(() -> new NoSuchElementException(
                        "Tour-Rating pair for request(" + tourId + ") for customer (" + customerId + ")"));
    }

    @GetMapping
    public Page<TourRating> getAllRatingsForTour(@PathVariable(value = "tourId") String tourId, Pageable pageable) {

        verifyTour(tourId);
        Page<TourRating> ratings = tourRatingRepository.findByTourId(tourId, pageable);

        return new PageImpl<>(ratings.get().collect(Collectors.toList()),
        pageable, ratings.getTotalElements());
    }

    @GetMapping(path = "/average")
    public Map<String, Double> getAverage(@PathVariable(value = "tourId") String tourId) {
        verifyTour(tourId);
        return Map.of("average", tourRatingRepository.findByTourId(tourId).stream().mapToInt(TourRating::getScore)
                .average().orElseThrow(() -> new NoSuchElementException("Tour has no Ratings")));
    }

    @PutMapping
    public TourRating updateWithPut(@PathVariable(value = "tourId") String tourId,
            @RequestBody @Validated TourRating tourRating) {
        TourRating rating = verifyTourRating(tourId, tourRating.getCustomerId());
        rating.setComment(tourRating.getComment());
        rating.setScore(tourRating.getScore());
        return tourRatingRepository.save(rating);
    }

    @PatchMapping
    public TourRating updateWithPatch(@PathVariable(value = "tourId") String tourId,
            @RequestBody @Validated TourRating tourRating) {
        TourRating rating = verifyTourRating(tourId, tourRating.getCustomerId());
        if (tourRating.getComment() != null) {
            rating.setComment(tourRating.getComment());
        }
        if (tourRating.getScore() != null) {
            rating.setScore(tourRating.getScore());
        }
        return tourRatingRepository.save(rating);

    }

    @DeleteMapping(path = "/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "tourId") String tourId, @PathVariable(value = "customerId") int customerId) {
        TourRating tourRating = verifyTourRating(tourId, customerId);
        tourRatingRepository.delete(tourRating);
    }

    /***
     * Exception handler if NoSuchElementException is thrown
     * 
     * @param ex
     * @return Error under message String
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex) {
        return ex.getMessage();
    }

}
