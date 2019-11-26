package com.scor.rr;

import com.scor.rr.repository.PltHeaderRepository;
import com.scor.rr.service.PltBrowserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PLTRestoreTest {

    @Mock
    PltHeaderRepository pltHeaderRepository;

    @InjectMocks
    PltBrowserService pltBrowserService;

    @Test
    public void when_restore_a_plt_it_should_have_no_deletedBy_deletedDue_deletedOn() {
        /*PLTHeaderDeleteRequest request = new PLTHeaderDeleteRequest();
        Date d = new Date();

        PLTHeader mockPLT = new PLTHeader(15222, "NAJIH DRISS");
        mockPLT.setDeletedDue("CALCULATION CAUSES");
        mockPLT.setDeletedOn(d);

        Optional<PLTHeader> pltHeaderOpt = Optional.of(mockPLT);

        when(pltHeaderRepository.findById(15222)).thenReturn(pltHeaderOpt);

        PLTHeader pltHeader = pltBrowserService.restorePLTHeader(15222);

        PLTHeader expected = new PLTHeader(15222, null);

        assertEquals(pltHeader, expected);*/
    }

}
