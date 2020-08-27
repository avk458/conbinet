package cn.hitek.cabinetserver.product.domain.performance;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * @author chenlm
 */
@CrossOrigin(origins = "*")
@RepositoryRestResource(collectionResourceRel = "perform", path = "performance")
public interface PerformanceRepo extends PagingAndSortingRepository<Performance, Long> {
}
