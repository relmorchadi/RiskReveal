import com.scor.rr.entity.InuringFinalNode;
import com.scor.rr.entity.InuringPackage;
import com.scor.rr.enums.InuringOutputGrain;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.inuring.InuringFinalNodeNotFoundException;
import com.scor.rr.exceptions.inuring.InuringPackageNotFoundException;
import com.scor.rr.repository.InuringFinalNodeRepository;
import com.scor.rr.repository.InuringPackageRepository;
import com.scor.rr.request.InuringFinalNodeUpdateRequest;
import com.scor.rr.request.InuringPackageCreationRequest;
import com.scor.rr.service.InuringFinalNodeService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class InuringFinalNodeTest {

    @InjectMocks
    private InuringFinalNodeService inuringFinalNodeService;
    @Mock
    private InuringPackageRepository inuringPackageRepository;
    @Mock
    private InuringFinalNodeRepository inuringFinalNodeRepository;

    private Map<Integer, InuringPackage> inuringPackages;
    private Map<Integer, InuringFinalNode> inuringFinalNodes;

    private static int INURING_PACKAGE_ID = 1;
    private static int USER_ID = 1;
    private static int NON_EXISTING_FINAL_NODE_ID = 1;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    private int inuringPackageCounter;
    private int inuringFinalNodeCounter;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        inuringPackageCounter = 1;
        inuringFinalNodeCounter = 1;

        inuringPackages = new HashMap<>();
        inuringFinalNodes = new HashMap<>();


        when(inuringPackageRepository.saveAndFlush(any(InuringPackage.class))).thenAnswer(i -> {
            InuringPackage inuringPackage = i.getArgument(0);
            int id = inuringPackage.getInuringPackageId() != 0 ? inuringPackage.getInuringPackageId() : inuringPackageCounter++;
            inuringPackage.setInuringPackageId(id);
            inuringPackages.put(id, inuringPackage);
            return inuringPackage;
        });

        when(inuringPackageRepository.findByInuringPackageId(anyInt())).thenAnswer(i -> {
            int id = i.getArgument(0);
            return inuringPackages.get(id);
        });

        when(inuringFinalNodeRepository.save(any(InuringFinalNode.class))).thenAnswer(i -> {
            InuringFinalNode inuringFinalNode = i.getArgument(0);
            int id = inuringFinalNode.getInuringFinalNodeId() != 0 ? inuringFinalNode.getInuringFinalNodeId() : inuringFinalNodeCounter++;
            inuringFinalNode.setInuringFinalNodeId(id);
            inuringFinalNodes.put(id, inuringFinalNode);
            return inuringFinalNode;
        });

        when(inuringFinalNodeRepository.findByInuringFinalNodeId(anyInt())).thenAnswer(i -> {
            int id = i.getArgument(0);
            return inuringFinalNodes.get(id);
        });
    }

    @Test
    public void testCreateInuringPackageAndFinalNode(){
        try {
            inuringPackageRepository.saveAndFlush(new InuringPackage(null,null,INURING_PACKAGE_ID,USER_ID));
            inuringFinalNodeService.createInuringFinalNodeForPackage(INURING_PACKAGE_ID);
            InuringFinalNode expectedInuringFinalNode = new InuringFinalNode(INURING_PACKAGE_ID);
            expectedInuringFinalNode.setInuringFinalNodeId(1);
            assertEquals(expectedInuringFinalNode,inuringFinalNodeRepository.findByInuringFinalNodeId(1));

        } catch (RRException ex) {
            fail();
        }

    }

    @Test
    public void testUpdatingAFinalNode(){
        try {
            inuringPackageRepository.saveAndFlush(new InuringPackage(null,null,INURING_PACKAGE_ID,USER_ID));
            inuringFinalNodeService.createInuringFinalNodeForPackage(INURING_PACKAGE_ID);
            inuringFinalNodeService.updateInuringFinalNode(new InuringFinalNodeUpdateRequest(1, InuringOutputGrain.OriginalPLT) );

            InuringFinalNode expectedInuringFinalNode = new InuringFinalNode(INURING_PACKAGE_ID);
            expectedInuringFinalNode.setInuringOutputGrain(InuringOutputGrain.OriginalPLT);
            expectedInuringFinalNode.setInuringFinalNodeId(1);
            assertEquals(expectedInuringFinalNode,inuringFinalNodeRepository.findByInuringFinalNodeId(1));

        } catch (RRException ex) {
            fail();
        }

    }

    @Test
    public void testCreatingFinalNodeForNonExistingWorkspace() {
        try {
            inuringFinalNodeService.createInuringFinalNodeForPackage(INURING_PACKAGE_ID);
            fail();
        } catch (InuringPackageNotFoundException ex) {
            assertEquals("Inuring Package id 1 not found", ex.getMessage());
        } catch (RRException other) {
            fail();
        }
    }

    @Test
    public void testUpdatingANonExistingFinalNode() {
        try {
            inuringFinalNodeService.updateInuringFinalNode(new InuringFinalNodeUpdateRequest(NON_EXISTING_FINAL_NODE_ID,InuringOutputGrain.OriginalPLT));
            fail();
        } catch (InuringFinalNodeNotFoundException ex) {
            assertEquals("Inuring Final Node id 1 not found", ex.getMessage());
        } catch (RRException other) {
            fail();
        }
    }



}
