package cn.hitek.cabinetserver.product.domain.person;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * @author chenlm
 */
@CrossOrigin(origins = "*")
@RepositoryRestResource(collectionResourceRel = "person", path = "person")
public interface PersonRepository extends PagingAndSortingRepository<Person, Long> {
}
