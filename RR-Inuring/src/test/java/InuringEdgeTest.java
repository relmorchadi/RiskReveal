import com.scor.rr.entity.*;
import com.scor.rr.enums.InuringFinancialPerspective;
import com.scor.rr.enums.InuringFinancialTreatment;
import com.scor.rr.enums.InuringNodeType;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.inuring.IllogicalEdgeCreationException;
import com.scor.rr.exceptions.inuring.InuringNodeNotFoundException;
import com.scor.rr.repository.*;
import com.scor.rr.request.InuringEdgeCreationRequest;
import com.scor.rr.request.InuringEdgeUpdateRequest;
import com.scor.rr.service.InuringEdgeService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

/**
 * Created by u004602 on 16/09/2019.
 */
public class InuringEdgeTest {
    @InjectMocks
    private InuringEdgeService inuringEdgeService;
    @Mock
    private InuringInputNodeRepository inuringInputNodeRepository;
    @Mock
    private InuringPackageRepository inuringPackageRepository;
    @Mock
    private InuringEdgeRepository inuringEdgeRepository;
    @Mock
    private InuringFinalNodeRepository inuringFinalNodeRepository;
    @Mock
    private InuringContractNodeRepository inuringContractNodeRepository;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    private static long NOT_EXISTING_INURING_PACKAGE_ID = 2;
    private static long INURING_PACKAGE_ID = 1;

    private static long NOT_EXISTING_INURING_EDGE_ID = 2;
    private static long INURING_EDGE_ID = 1;

    private static long NOT_EXISTING_INPUT_NODE_ID = 2;
    private static long INPUT_NODE_ID = 1;

    private static long NOT_EXISTING_CONTRACT_NODE_ID = 2;
    private static long CONTRACT_NODE_ID = 1;

    private static long NOT_EXISTING_FINAL_NODE_ID = 2;
    private static long FINAL_NODE_ID = 1;



    private Map<Long, InuringEdge> inuringEdges;

    private static int inuringEdgeCounter;

    @Before
    public void init() {

        MockitoAnnotations.initMocks(this);

        inuringEdgeCounter = 1;

        inuringEdges = new HashMap<>();


        when(inuringPackageRepository.findByInuringPackageId(NOT_EXISTING_INURING_PACKAGE_ID)).thenReturn(null);
        when(inuringPackageRepository.findByInuringPackageId(INURING_PACKAGE_ID)).thenReturn(new InuringPackage());

        when(inuringInputNodeRepository.findByInuringInputNodeId(INPUT_NODE_ID)).thenReturn(new InuringInputNode());
        when(inuringInputNodeRepository.findByInuringInputNodeId(NOT_EXISTING_INPUT_NODE_ID)).thenReturn(null);

        when(inuringContractNodeRepository.findByInuringContractNodeId(CONTRACT_NODE_ID)).thenReturn(new InuringContractNode());
        when(inuringContractNodeRepository.findByInuringContractNodeId(NOT_EXISTING_CONTRACT_NODE_ID)).thenReturn(null);

        when(inuringFinalNodeRepository.findByInuringFinalNodeId(FINAL_NODE_ID)).thenReturn(new InuringFinalNode());
        when(inuringFinalNodeRepository.findByInuringFinalNodeId(NOT_EXISTING_FINAL_NODE_ID)).thenReturn(null);

        when(inuringEdgeRepository.findByInuringEdgeId(anyLong())).thenAnswer(i -> {
            long id = i.getArgument(0);
            return inuringEdges.get(id);
        });

        when(inuringEdgeRepository.save(any(InuringEdge.class))).thenAnswer(i -> {
            InuringEdge inuringEdge = i.getArgument(0);
            long id = inuringEdge.getInuringEdgeId() != 0 ? inuringEdge.getInuringEdgeId() : inuringEdgeCounter++;
            inuringEdge.setInuringEdgeId(id);
            inuringEdges.put(id, inuringEdge);
            return inuringEdge;
        });


    }

    @Test
    public void testCreatingEdgeBetweenInputNodeAndContractNode() {
        try {
            inuringEdgeService.createInuringEdge(new InuringEdgeCreationRequest(INURING_PACKAGE_ID, INPUT_NODE_ID, InuringNodeType.InputNode, CONTRACT_NODE_ID, InuringNodeType.ContractNode));
            InuringEdge expectedInuringEdge = new InuringEdge(INURING_PACKAGE_ID, INPUT_NODE_ID, InuringNodeType.InputNode, CONTRACT_NODE_ID, InuringNodeType.ContractNode, InuringFinancialPerspective.Net, InuringFinancialTreatment.Positive);
            expectedInuringEdge.setInuringEdgeId(1);
            assertEquals(expectedInuringEdge, inuringEdgeRepository.findByInuringEdgeId(1));
        } catch (RRException ex) {
            fail();
        }

    }
    @Test
    public void testCreatingEdgeBetweenInputNodeAndContractNodeWithNonExistingSourceNode() {
        try {
            inuringEdgeService.createInuringEdge(new InuringEdgeCreationRequest(INURING_PACKAGE_ID, NOT_EXISTING_INPUT_NODE_ID, InuringNodeType.InputNode, CONTRACT_NODE_ID, InuringNodeType.ContractNode));
            fail();
        } catch (InuringNodeNotFoundException ex) {
            assertEquals("Inuring Node type " + InuringNodeType.InputNode + " id " + NOT_EXISTING_INPUT_NODE_ID + " not found", ex.getMessage());
        } catch (RRException other) {
            fail();
        }

    }

    @Test
    public void testCreatingEdgeBetweenInputNodeAndContractNodeWithNonExistingTargetNode() {
        try {
            inuringEdgeService.createInuringEdge(new InuringEdgeCreationRequest(INURING_PACKAGE_ID, INPUT_NODE_ID, InuringNodeType.InputNode, NOT_EXISTING_CONTRACT_NODE_ID, InuringNodeType.ContractNode));
            fail();
        } catch (InuringNodeNotFoundException ex) {
            assertEquals("Inuring Node type " + InuringNodeType.ContractNode + " id " + NOT_EXISTING_CONTRACT_NODE_ID + " not found", ex.getMessage());
        } catch (RRException other) {
            fail();
        }

    }

    @Test
    public void testCreatingEdgeBetweenInputNodeAndContractNodeWithSameSourceAndTargetNode() {
        try {
            inuringEdgeService.createInuringEdge(new InuringEdgeCreationRequest(INURING_PACKAGE_ID, INPUT_NODE_ID, InuringNodeType.InputNode, INPUT_NODE_ID, InuringNodeType.InputNode));
            fail();
        } catch (IllogicalEdgeCreationException ex) {
            assertEquals("IllogicalEdgeCreation", ex.getMessage());
        } catch (RRException other) {
            fail();
        }

    }

    @Test
    public void testCreatingEdgeWithFinalNodeAsSourceNode() {
        try {
            inuringEdgeService.createInuringEdge(new InuringEdgeCreationRequest(INURING_PACKAGE_ID, FINAL_NODE_ID, InuringNodeType.FinalNode, CONTRACT_NODE_ID, InuringNodeType.ContractNode));
            fail();
        } catch (IllogicalEdgeCreationException ex) {
            assertEquals("IllogicalEdgeCreation", ex.getMessage());
        } catch (RRException other) {
            fail();
        }

    }

    @Test
    public void testCreatingEdgeWithInputNodeAsTargetNode() {
        try {
            inuringEdgeService.createInuringEdge(new InuringEdgeCreationRequest(INURING_PACKAGE_ID, CONTRACT_NODE_ID, InuringNodeType.ContractNode, INPUT_NODE_ID, InuringNodeType.InputNode));
            fail();
        } catch (IllogicalEdgeCreationException ex) {
            assertEquals("IllogicalEdgeCreation", ex.getMessage());
        } catch (RRException other) {
            fail();
        }

    }

    @Test
    public void testUpdatingAnExistingEdge() {
        try {
            inuringEdgeService.createInuringEdge(new InuringEdgeCreationRequest(INURING_PACKAGE_ID, INPUT_NODE_ID, InuringNodeType.InputNode, CONTRACT_NODE_ID, InuringNodeType.ContractNode));
            inuringEdgeService.updateInuringEdge(new InuringEdgeUpdateRequest(INURING_EDGE_ID, InuringFinancialPerspective.Net, InuringFinancialTreatment.Negative, false));

            InuringEdge expectedInuringEdge = new InuringEdge(INURING_PACKAGE_ID, INPUT_NODE_ID, InuringNodeType.InputNode, CONTRACT_NODE_ID, InuringNodeType.ContractNode, InuringFinancialPerspective.Net, InuringFinancialTreatment.Negative);
            expectedInuringEdge.setInuringEdgeId(1);
            assertEquals(expectedInuringEdge, inuringEdgeRepository.findByInuringEdgeId(1));
        } catch (RRException ex) {
            fail();
        }

    }

}
