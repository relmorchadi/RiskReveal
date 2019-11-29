import com.scor.rr.domain.PltHeaderEntity;
import com.scor.rr.entity.InuringInputAttachedPLT;
import com.scor.rr.entity.InuringInputNode;
import com.scor.rr.entity.InuringPackage;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.inuring.InputPLTNotFoundException;
import com.scor.rr.exceptions.inuring.InuringInputNodeNotFoundException;
import com.scor.rr.exceptions.inuring.InuringPackageNotFoundException;
import com.scor.rr.repository.InuringInputAttachedPLTRepository;
import com.scor.rr.repository.InuringInputNodeRepository;
import com.scor.rr.repository.InuringPackageRepository;
import com.scor.rr.repository.PltHeaderRepository;
import com.scor.rr.request.InuringInputNodeCreationRequest;
import com.scor.rr.request.InuringInputNodeUpdateRequest;
import com.scor.rr.service.InuringInputNodeService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.*;

import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * Created by u004602 on 10/09/2019.
 */

public class InuringInputNodeTest {
    @InjectMocks
    private InuringInputNodeService inuringInputNodeService;

    @Mock
    private InuringInputAttachedPLTRepository inuringInputAttachedPLTRepository;

    @Mock
    private InuringInputNodeRepository inuringInputNodeRepository;

    @Mock
    private InuringPackageRepository inuringPackageRepository;

    @Mock
    private PltHeaderRepository scorpltheaderRepository;

    private Map<Long, InuringInputNode> inuringInputNodes;

    private Map<Long, InuringInputAttachedPLT> inuringInputAttachedPLTS;

    private int inuringInputNodeCounter;
    private int inuringInputAttachedPLTCounter;

    private static long NOT_EXISTING_INURING_PACKAGE_ID = 0;
    private static long INURING_PACKAGE_ZERO_PLT_ID = 1;
    private static long INURING_PACKAGE_HAS_PLT_ID = 2;

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
        inuringInputAttachedPLTS = new HashMap<>();

        when(inuringInputNodeRepository.saveAndFlush(any(InuringInputNode.class))).thenAnswer(i -> {
            InuringInputNode inuringInputNode = i.getArgument(0);
            long id = inuringInputNode.getInuringInputNodeId() != 0 ? inuringInputNode.getInuringInputNodeId() : inuringInputNodeCounter++;
            inuringInputNode.setInuringInputNodeId(id);
            inuringInputNodes.put(id, inuringInputNode);
            return inuringInputNode;
        });


        when(inuringInputAttachedPLTRepository.saveAndFlush(any(InuringInputAttachedPLT.class))).thenAnswer(i -> {
            InuringInputAttachedPLT inuringInputAttachedPLT = i.getArgument(0);
            long id = inuringInputAttachedPLT.getInuringInputAttachedPLTId() != 0 ? inuringInputAttachedPLT.getInuringInputAttachedPLTId() : inuringInputAttachedPLTCounter++;
            inuringInputAttachedPLT.setInuringInputAttachedPLTId(id);
            inuringInputAttachedPLTS.put(id, inuringInputAttachedPLT);
            return inuringInputAttachedPLT;
        });

        when(inuringInputNodeRepository.findByInuringInputNodeId(anyLong())).thenAnswer(i -> {
            long id = i.getArgument(0);
            return inuringInputNodes.get(id);
        });

        when(inuringInputNodeRepository.findByInuringPackageId(anyLong())).thenAnswer(inuringPackageId -> {
            long id = inuringPackageId.getArgument(0);
            return inuringInputNodes.values().stream().filter(i -> i.getInuringPackageId() == id).collect(Collectors.toList());
        });

        when(inuringInputAttachedPLTRepository.findByInuringInputNodeId(anyLong())).thenAnswer(node -> {
            long id = node.getArgument(0);
            return inuringInputAttachedPLTS.values().stream().filter(i -> i.getInuringInputNodeId() == id).collect(Collectors.toList());
        });

        when(inuringPackageRepository.findByInuringPackageId(NOT_EXISTING_INURING_PACKAGE_ID)).thenReturn(null);
        when(inuringPackageRepository.findByInuringPackageId(INURING_PACKAGE_ZERO_PLT_ID)).thenReturn(new InuringPackage());
        when(inuringPackageRepository.findByInuringPackageId(INURING_PACKAGE_HAS_PLT_ID)).thenReturn(new InuringPackage());

        when(inuringInputNodeRepository.findAll()).thenReturn(new ArrayList<InuringInputNode> (inuringInputNodes.values()));
        when(inuringInputAttachedPLTRepository.findAll()).thenReturn(new ArrayList<InuringInputAttachedPLT>(inuringInputAttachedPLTS.values()));

        when(scorpltheaderRepository.findByPltHeaderId(anyLong())).thenAnswer(plt -> {
            long id = plt.getArgument(0);
            return id == PLT_ID_NOT_FOUND ? null : new PltHeaderEntity();
        });

        doAnswer(node -> {
            long id = node.getArgument(0);
            inuringInputNodes.remove(id);
            return null;
        }).when(inuringInputNodeRepository).deleteByInuringInputNodeId(anyLong());

        doAnswer(node -> {
            long id = node.getArgument(0);
            inuringInputAttachedPLTS.entrySet().removeIf(i -> i.getValue().getInuringInputNodeId() == id);
            return null;
        }).when(inuringInputAttachedPLTRepository).deleteByInuringInputNodeId(anyLong());

        doAnswer(node -> {
            long id = node.getArgument(0);
            inuringInputAttachedPLTS.remove(id);
            return null;
        }).when(inuringInputAttachedPLTRepository).deleteByInuringInputAttachedPLTId(anyLong());

        when(inuringInputAttachedPLTRepository.findByInuringInputAttachedPLTId(1)).thenReturn(new InuringInputAttachedPLT(1, PLT_ID_1));
        when(inuringInputAttachedPLTRepository.findByInuringInputAttachedPLTId(2)).thenReturn(new InuringInputAttachedPLT(1, PLT_ID_2));
        when(inuringInputAttachedPLTRepository.findByInuringInputAttachedPLTId(3)).thenReturn(new InuringInputAttachedPLT(1, PLT_ID_3));

    }


    @Test
    public void testCreateAnInputNodeForNonExistingPackage() {
        try {
            inuringInputNodeService.createInuringInputNode(new InuringInputNodeCreationRequest(NOT_EXISTING_INURING_PACKAGE_ID, null, null));
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
            inuringInputNodeService.createInuringInputNode(new InuringInputNodeCreationRequest(INURING_PACKAGE_ZERO_PLT_ID, null, null));
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
            inuringInputNodeService.createInuringInputNode(new InuringInputNodeCreationRequest(
                    INURING_PACKAGE_HAS_PLT_ID,
                    "Input Node - 2 PLTs",
                    Arrays.asList(PLT_ID_1, PLT_ID_2)));
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
            inuringInputNodeService.createInuringInputNode(new InuringInputNodeCreationRequest(
                    INURING_PACKAGE_HAS_PLT_ID,
                    "Input Node",
                    Arrays.asList(PLT_ID_1, PLT_ID_NOT_FOUND)));
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
    public void testAddTwoInputNodeToAnInuringPackage() {
        try {
            inuringInputNodeService.createInuringInputNode(new InuringInputNodeCreationRequest(
                    INURING_PACKAGE_HAS_PLT_ID,
                    "Input Node 1",
                    Arrays.asList(PLT_ID_1)));

            inuringInputNodeService.createInuringInputNode(new InuringInputNodeCreationRequest(
                    INURING_PACKAGE_HAS_PLT_ID,
                    "Input Node 2",
                    Arrays.asList(PLT_ID_2)));

            assertEquals(2, inuringInputNodeService.findInputNodesByInuringPackageId(INURING_PACKAGE_HAS_PLT_ID).size());

        } catch (RRException ex) {
            fail();
        }
    }

   /* @Test
    public void testDeleteAnInputNode() {
        try {
            inuringInputNodeService.createInuringInputNode(new InuringInputNodeCreationRequest(
                    INURING_PACKAGE_HAS_PLT_ID,
                    "Input Node 1",
                    Arrays.asList(PLT_ID_1, PLT_ID_2)));

            inuringInputNodeService.createInuringInputNode(new InuringInputNodeCreationRequest(
                    INURING_PACKAGE_HAS_PLT_ID,
                    "Input Node 2",
                    Arrays.asList(PLT_ID_1, PLT_ID_2)));

            assertEquals(2, inuringInputNodeService.findInputNodesByInuringPackageId(INURING_PACKAGE_HAS_PLT_ID).size());

            inuringInputNodeService.deleteInuringInputNode(1);

            assertNull(inuringInputNodeService.findByInuringInputNodeId(1));
            assertEquals(0, inuringInputNodeService.findAttachedPLTByInuringInputNodeId(1).size());

            assertNotNull(inuringInputNodeService.findByInuringInputNodeId(2));
            assertEquals(2, inuringInputNodeService.findAttachedPLTByInuringInputNodeId(2).size());
        } catch (RRException ex) {
            fail();
        }
    }*/

    @Test
    public void testRenameAnNotExistingInputNode() {
        try {
            inuringInputNodeService.updateInuringInputNode(new InuringInputNodeUpdateRequest(1, "Input Node 2"));
            fail();
        } catch (InuringInputNodeNotFoundException ex) {
            assertEquals("Inuring Input Node id 1 not found", ex.getMessage());
        } catch (RRException other) {
            fail();
        }
    }

    @Test
    public void testRenameAnExistingInputNode() {
        try {
            inuringInputNodeService.createInuringInputNode(new InuringInputNodeCreationRequest(
                    INURING_PACKAGE_HAS_PLT_ID,
                    "Input Node 1",
                    Arrays.asList(PLT_ID_1)));

            inuringInputNodeService.updateInuringInputNode(new InuringInputNodeUpdateRequest(1, "Input Node 2"));

            assertEquals("Input Node 2", inuringInputNodeService.findByInuringInputNodeId(1).getInputNodeName());

        } catch (RRException ex) {
            fail();
        }
    }

    @Test
    public void testAttachNotExistingPLTToInputNode() {
        try {
            inuringInputNodeService.createInuringInputNode(new InuringInputNodeCreationRequest(
                    INURING_PACKAGE_HAS_PLT_ID,
                    "Input Node 1",
                    Arrays.asList(PLT_ID_1)));
            inuringInputNodeService.updateInuringInputNode(new InuringInputNodeUpdateRequest(1, "Input Node 2", Arrays.asList(PLT_ID_1, PLT_ID_NOT_FOUND)));
            fail();
        } catch (InputPLTNotFoundException ex) {
            assertEquals("Input PLT id " + PLT_ID_NOT_FOUND + " not found", ex.getMessage());
            assertEquals("Input Node 1", inuringInputNodeService.findByInuringInputNodeId(1).getInputNodeName());
            assertEquals(1, inuringInputNodeService.findAttachedPLTByInuringInputNodeId(1).size());
            assertEquals(1, inuringInputAttachedPLTRepository.findByInuringInputAttachedPLTId(1).getInuringInputNodeId());
            assertEquals(PLT_ID_1, inuringInputAttachedPLTRepository.findByInuringInputAttachedPLTId(1).getPltHeaderId());
        } catch (RRException other) {
            fail();
        }
    }

    @Test
    public void testAttachOnePLTToNotExistingInputNode() {
        try {
            inuringInputNodeService.updateInuringInputNode(new InuringInputNodeUpdateRequest(1, Arrays.asList(PLT_ID_1)));
            fail();
        } catch (InuringInputNodeNotFoundException ex) {
            assertEquals("Inuring Input Node id 1 not found", ex.getMessage());
        } catch (RRException other) {
            fail();
        }
    }

    @Test
    public void testAttachOnePLTToExistingInputNode() {
        try {
            inuringInputNodeService.createInuringInputNode(new InuringInputNodeCreationRequest(
                    INURING_PACKAGE_HAS_PLT_ID,
                    "Input Node 1",
                    Arrays.asList(PLT_ID_1)));
            inuringInputNodeService.updateInuringInputNode(new InuringInputNodeUpdateRequest(1, Arrays.asList(PLT_ID_1, PLT_ID_2)));
            assertNotNull(inuringInputNodeService.findAttachedPLTByInuringInputNodeId(1));
            assertEquals("Input Node 1", inuringInputNodeService.findByInuringInputNodeId(1).getInputNodeName());
            assertEquals(2, inuringInputNodeService.findAttachedPLTByInuringInputNodeId(1).size());
            assertEquals(1, inuringInputAttachedPLTRepository.findByInuringInputAttachedPLTId(1).getInuringInputNodeId());
            assertEquals(PLT_ID_1, inuringInputAttachedPLTRepository.findByInuringInputAttachedPLTId(1).getPltHeaderId());
            assertEquals(1, inuringInputAttachedPLTRepository.findByInuringInputAttachedPLTId(2).getInuringInputNodeId());
            assertEquals(PLT_ID_2, inuringInputAttachedPLTRepository.findByInuringInputAttachedPLTId(2).getPltHeaderId());

        } catch (RRException other) {
            fail();
        }
    }

    @Test
    public void testDetachOnePLTFromNotExistingInputNode() {
        try {
            inuringInputNodeService.updateInuringInputNode(new InuringInputNodeUpdateRequest(1, Arrays.asList(PLT_ID_1, PLT_ID_2)));
            fail();
        } catch (InuringInputNodeNotFoundException ex) {
            assertEquals("Inuring Input Node id 1 not found", ex.getMessage());
        } catch (RRException other) {
            fail();
        }
    }

    @Test
    public void testDetachOnePLTFromExistingInputNode() {
        try {
            inuringInputNodeService.createInuringInputNode(new InuringInputNodeCreationRequest(
                    INURING_PACKAGE_HAS_PLT_ID,
                    "Input Node 1",
                    Arrays.asList(PLT_ID_1, PLT_ID_2, PLT_ID_3)));
            inuringInputNodeService.updateInuringInputNode(new InuringInputNodeUpdateRequest(1, Arrays.asList(PLT_ID_1, PLT_ID_3)));
            assertNotNull(inuringInputNodeService.findAttachedPLTByInuringInputNodeId(1));
            assertEquals("Input Node 1", inuringInputNodeService.findByInuringInputNodeId(1).getInputNodeName());
            assertEquals(2, inuringInputNodeService.findAttachedPLTByInuringInputNodeId(1).size());
            assertEquals(1, inuringInputAttachedPLTRepository.findByInuringInputAttachedPLTId(1).getInuringInputNodeId());
            assertEquals(PLT_ID_1, inuringInputAttachedPLTRepository.findByInuringInputAttachedPLTId(1).getPltHeaderId());
            assertEquals(1, inuringInputAttachedPLTRepository.findByInuringInputAttachedPLTId(3).getInuringInputNodeId());
            assertEquals(PLT_ID_3, inuringInputAttachedPLTRepository.findByInuringInputAttachedPLTId(3).getPltHeaderId());
        } catch (RRException other) {
            fail();
        }
    }


    @Test
    public void testAssignTagToAnAttachedPLT() {
//        fail();
    }

}
