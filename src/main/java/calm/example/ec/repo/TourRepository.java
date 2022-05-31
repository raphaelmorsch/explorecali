package calm.example.ec.repo;

import org.springframework.data.repository.CrudRepository;

import calm.example.ec.domain.Tour;

public interface TourRepository extends CrudRepository<Tour, Integer>{
    
}
