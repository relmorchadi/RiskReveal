package com.scor.rr.repository.references;

import com.scor.rr.domain.entities.plt.ModellingSystem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Hamiani Mohammed
 * creation date  26/09/2019 at 09:11
 **/
public interface ModellingSystemRepository extends JpaRepository<ModellingSystem, String> {
    ModellingSystem findByVendorIdAndName(String vendorId, String name);
}
