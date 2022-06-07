package calm.example.ec.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import calm.example.ec.domain.TourRating;

@RestResource(exported = false)
public interface TourRatingRepository extends CrudRepository<TourRating, String> {

   /**
    * Lookup all the TourRatings for a tour
    * @param tourId
    * @return
    */
   List<TourRating> findByTourId(String tourId);

   /**
    * Lookup TourRating by the TourId and CustomerId
    * @param tourId
    * @param customerId
    * @return Optional of TourRating
    */
   Optional<TourRating> findByTourIdAndCustomerId(String tourId, Integer customerId);

   Page<TourRating> findByTourId(String tourId, Pageable pageable);
    
}
