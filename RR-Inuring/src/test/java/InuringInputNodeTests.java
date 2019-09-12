import com.scor.rr.domain.ScorPltHeaderEntity;
import com.scor.rr.entity.InuringInputAttachedPLT;
import com.scor.rr.entity.InuringInputNode;
import com.scor.rr.entity.InuringPackage;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.inuring.InputPLTNotFoundException;
import com.scor.rr.exceptions.inuring.InuringPackageNotFoundException;
import com.scor.rr.repository.InuringInputAttachedPLTRepository;
import com.scor.rr.repository.InuringInputNodeRepository;
import com.scor.rr.repository.InuringPackageRepository;
import com.scor.rr.repository.ScorpltheaderRepository;
import com.scor.rr.request.InuringInputNodeCreationRequest;
import com.scor.rr.service.InuringInputNodeService;
import javafx.beans.binding.When;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * Created by u004602 on 10/09/2019.
 */

public class InuringInputNodeTests {
    @InjectMocks
    private InuringInputNodeService inuringInputNodeService;

    @Mock
    private InuringInputAttachedPLTRepository inuringInputAttachedPLTRepository;

    @Mock
    private InuringInputNodeRepository inuringInputNodeRepository;

    @Mock
    private InuringPackageRepository inuringPackageRepository;

    @Mock
    private ScorpltheaderRepository scorpltheaderRepository;

    private Map<Integer, InuringInputNode> inuringInputNodes;

    private List<InuringInputAttachedPLT> inuringInputAttachedPLTS;

    private int inuringInputNodeCounter;
    private int inuringInputAttachedPLTCounter;

    private static int NOT_EXISTING_INURING_PACKAGE_ID = 0;
    private static int INURING_PACKAGE_ZERO_PLT_ID = 1;
    private static int INURING_PACKAGE_HAS_PLT_ID = 2;

    private static int PLT_ID_NOT_FOUND = 0;
    private static int PLT_ID_1 = 1;
    private static int PLT_ID_2 = 2;
    private static int PLT_ID_3 = 3;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        inuringInputNodeCounter = 1;
        inuringInputAttachedPLTCounter = 1;
        inuringInputNodes = new HashMap<>();
        inuringInputAttachedPLTS = new LinkedList<>();

        when(inuringInputNodeRepository.saveAndFlush(any(InuringInputNode.class))).thenAnswer(i -> {
            InuringInputNode inuringInputNode = i.getArgument(0);
            int id = inuringInputNodeCounter++;
            inuringInputNode.setInuringInputNodeId(id);
            inuringInputNodes.put(id, inuringInputNode);
            return inuringInputNode;
        });


        when(inuringInputAttachedPLTRepository.saveAndFlush(any(InuringInputAttachedPLT.class))).thenAnswer(i -> {
            InuringInputAttachedPLT inuringInputAttachedPLT = i.getArgument(0);
            inuringInputAttachedPLT.setInuringInputAttachedPLTId(inuringInputAttachedPLTCounter++);
            inuringInputAttachedPLTS.add(inuringInputAttachedPLT);
            return inuringInputAttachedPLT;
        });

        when(inuringInputNodeRepository.findByInuringInputNodeId(anyInt())).thenAnswer(i -> {
            int id = i.getArgument(0);
            return inuringInputNodes.get(id);
        });

        when(inuringInputNodeRepository.findByInuringPackageId(anyInt())).thenAnswer(inuringPackageId -> {
            int id = inuringPackageId.getArgument(0);
            return inuringInputNodes.values().stream().filter(i -> i.getInuringPackageId() == id).collect(Collectors.toList());
        });

        when(inuringInputAttachedPLTRepository.findByInuringInputNodeId(anyInt())).thenAnswer(node -> {
            int id = node.getArgument(0);
            return inuringInputAttachedPLTS.stream().filter(i -> i.getInuringInputNodeId() == id).collect(Collectors.toList());
        });

        when(inuringPackageRepository.findById(NOT_EXISTING_INURING_PACKAGE_ID)).thenReturn(null);
        when(inuringPackageRepository.findById(INURING_PACKAGE_ZERO_PLT_ID)).thenReturn(new InuringPackage());
        when(inuringPackageRepository.findById(INURING_PACKAGE_HAS_PLT_ID)).thenReturn(new InuringPackage());

        when(inuringInputNodeRepository.findAll()).thenReturn(new ArrayList<InuringInputNode> (inuringInputNodes.values()));
        when(inuringInputAttachedPLTRepository.findAll()).thenReturn(inuringInputAttachedPLTS);

        when(scorpltheaderRepository.findByPkScorPltHeaderId(anyInt())).thenAnswer(plt -> {
            int id = plt.getArgument(0);
            return id == PLT_ID_NOT_FOUND ? null : new ScorPltHeaderEntity();
        });

    }


    @Test
    public void testCreateAnInputNodeForNonExistingPackage() {
        try {
            InuringInputNodeCreationRequest request = new InuringInputNodeCreationRequest(NOT_EXISTING_INURING_PACKAGE_ID, null, null);
            inuringInputNodeService.createInuringInputNode(request);
            fail();
        } catch (InuringPackageNotFoundException ex) {
            assertEquals("Inuring Package id " + NOT_EXISTING_INURING_PACKAGE_ID + " not found", ex.getMessage());
        } catch (RRException other) {
            fail();
        }
    }

    @Test
    public void testCreateAnInputNodeWithoutAttachedPLT() {
        try {
            InuringInputNodeCreationRequest request = new InuringInputNodeCreationRequest(INURING_PACKAGE_ZERO_PLT_ID, null, null);
            inuringInputNodeService.createInuringInputNode(request);
            InuringInputNode expectedInuringInputNode = new InuringInputNode(INURING_PACKAGE_ZERO_PLT_ID);
            expectedInuringInputNode.setInuringInputNodeId(1);
            assertEquals(expectedInuringInputNode, inuringInputNodeService.findByInuringInputNodeId(1));
            assertTrue(inuringInputNodeService.findAttachedPLTByInuringInputNodeId(1).isEmpty());
        } catch (RRException ex) {
            fail();
        }

    }

    @Test
    public void testCreateAnInputNodeWithAttachedPLTs() {
        try {
            InuringInputNodeCreationRequest request = new InuringInputNodeCreationRequest(
                    INURING_PACKAGE_HAS_PLT_ID,
                    "Input Node - 2 PLTs",
                    Arrays.asList(PLT_ID_1, PLT_ID_2));
            inuringInputNodeService.createInuringInputNode(request);
            InuringInputNode expectedInuringInputNode = new InuringInputNode(INURING_PACKAGE_HAS_PLT_ID, "Input Node - 2 PLTs");
            expectedInuringInputNode.setInuringInputNodeId(1);
            assertEquals(expectedInuringInputNode, inuringInputNodeService.findByInuringInputNodeId(1));
            assertNotNull(inuringInputNodeService.findAttachedPLTByInuringInputNodeId(1));
            assertEquals(2, inuringInputNodeService.findAttachedPLTByInuringInputNodeId(1).size());
            for (InuringInputAttachedPLT inuringInputAttachedPLT : inuringInputNodeService.findAttachedPLTByInuringInputNodeId(1)) {
                assertEquals(1, inuringInputAttachedPLT.getInuringInputNodeId());
                assertTrue(inuringInputAttachedPLT.getPltHeaderId() == PLT_ID_1 || inuringInputAttachedPLT.getPltHeaderId() == PLT_ID_2);
            }
        } catch (RRException ex) {
            fail();
        }
    }

    @Test
    public void testCreateAnInputNodeWithNotFoundPLT() {
        try {
            InuringInputNodeCreationRequest request = new InuringInputNodeCreationRequest(
                    INURING_PACKAGE_HAS_PLT_ID,
                    "Input Node",
                    Arrays.asList(PLT_ID_1, PLT_ID_NOT_FOUND));
            inuringInputNodeService.createInuringInputNode(request);
            fail();
        } catch (InputPLTNotFoundException ex) {
            assertEquals("Input PLT id " + PLT_ID_NOT_FOUND + " not found", ex.getMessage());
            assertTrue(inuringInputNodeRepository.findAll().isEmpty());
            assertTrue(inuringInputAttachedPLTRepository.findAll().isEmpty());
        } catch (RRException other) {
            fail();
        }

    }

    @Test
    public void testAttachOnePLTToExistingInputNode() {
        fail();
    }

    @Test
    public void testDetachOnePLTFromExistingInputNode() {
        fail();
    }

    @Test
    public void testAssignTagToAnAttachedPLT() {
        fail();
    }

    @Test
    public void testAddTwoInputNodeToAnInuringPackage() {
        try {
            InuringInputNodeCreationRequest request1 = new InuringInputNodeCreationRequest(
                    INURING_PACKAGE_HAS_PLT_ID,
                    "Input Node 1",
                    Arrays.asList(PLT_ID_1));
            inuringInputNodeService.createInuringInputNode(request1);

            InuringInputNodeCreationRequest request2 = new InuringInputNodeCreationRequest(
                    INURING_PACKAGE_HAS_PLT_ID,
                    "Input Node 2",
                    Arrays.asList(PLT_ID_2));
            inuringInputNodeService.createInuringInputNode(request2);

            assertEquals(2, inuringInputNodeService.findInputNodesByInuringPackageId(INURING_PACKAGE_HAS_PLT_ID).size());

        } catch (RRException ex) {
            fail();
        }
    }

    @Test
    public void testDeleteAnInputNode() {
        fail();
    }

}
