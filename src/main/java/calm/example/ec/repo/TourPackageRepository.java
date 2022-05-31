package calm.example.ec.repo;

import org.springframework.data.repository.CrudRepository;

import calm.example.ec.domain.TourPackage;

public interface TourPackageRepository extends CrudRepository<TourPackage, String> {

}
