package com.scor.rr.repository.plt;

import com.scor.rr.domain.entities.references.plt.TTFinancialPerspective;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Hamiani Mohammed
 * creation date  16/09/2019 at 15:56
 **/
public interface TTFinancialPerspectiveRepository extends JpaRepository<TTFinancialPerspective, String> {

    TTFinancialPerspective findByCode(String code);

    /**
     * when trying to fetch a unique FinancialPerspective use null ad integer value for all perspective that is NOT "TY"
     *
     * @param code
     * @param treatyId
     * @return FinancialPerspective if found
     */
    TTFinancialPerspective findByCodeAndTreatyId(String code, Integer treatyId);

    List<TTFinancialPerspective> findByUserSelectableForElt(boolean userSelectableForElt);
}
