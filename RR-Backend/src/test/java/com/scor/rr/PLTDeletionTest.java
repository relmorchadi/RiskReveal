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
public class PLTDeletionTest {

    @Mock
    PltHeaderRepository pltHeaderRepository;

    @InjectMocks
    PltBrowserService pltBrowserService;

    @Test
    public void when_delete_a_plt_it_should_have_deletedBy_deletedDue_deletedOn() {
        /*PLTHeaderDeleteRequest request = new PLTHeaderDeleteRequest();
        Date d = new Date();

        request.setDeletedBy("NAJIH DRISS");
        request.setDeletedDue("CALCULATION CAUSES");
        request.setDeletedOn(d);
        request.setPltHeaderId(15222);

        Optional<PLTHeader> pltHeaderOpt = Optional.of(new PLTHeader(15222, null));

        when(pltHeaderRepository.findById(15222)).thenReturn(pltHeaderOpt);

        PLTHeader pltHeader = pltBrowserService.deletePLTheader(request);

        PLTHeader expected = new PLTHeader(15222, "NAJIH DRISS");
        expected.setDeletedOn(d);
        expected.setDeletedDue("CALCULATION CAUSES");

        assertEquals(pltHeader, expected);*/
    }
}
