package calm.example.ec.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import calm.example.ec.domain.Tour;


public interface TourRepository extends PagingAndSortingRepository<Tour, Integer>{
    
    /**
     * 
     * @param code tour package code
     * @param pageable 
     * @return List of found tours
     */
    Page<Tour> findByTourPackageCode(@Param("code") String code, Pageable pageable);

    @Override
    @RestResource(exported = false)
     void delete(Tour entity);

    @Override
    @RestResource(exported = false)
     void deleteAll();

    @Override
    @RestResource(exported = false)
     void deleteAll(Iterable<? extends Tour> entities);

    @Override
    @RestResource(exported = false)
     void deleteAllById(Iterable<? extends Integer> ids);

    @Override
    @RestResource(exported = false)
     void deleteById(Integer id);

    @Override
    @RestResource(exported = false)
     <S extends Tour> S save(S entity);

    @Override
    @RestResource(exported = false)
     <S extends Tour> Iterable<S> saveAll(Iterable<S> entities);
}
