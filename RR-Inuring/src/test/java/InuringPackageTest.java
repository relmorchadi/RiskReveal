import com.scor.rr.entity.InuringContractNode;
import com.scor.rr.entity.InuringFinalNode;
import com.scor.rr.entity.InuringInputNode;
import com.scor.rr.entity.InuringPackage;
import com.scor.rr.enums.InuringNodeStatus;
import com.scor.rr.enums.InuringNodeType;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.InuringContractNodeRepository;
import com.scor.rr.repository.InuringFinalNodeRepository;
import com.scor.rr.repository.InuringInputNodeRepository;
import com.scor.rr.repository.InuringPackageRepository;
import com.scor.rr.request.InuringPackageCreationRequest;
import com.scor.rr.service.InuringFinalNodeService;
import com.scor.rr.service.InuringPackageService;
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
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Matchers.any;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

public class InuringPackageTest {
    @InjectMocks
    private InuringPackageService inuringPackageService;
    @Mock
    private InuringPackageRepository inuringPackageRepository;
    @Mock
    private InuringInputNodeRepository inuringInputNodeRepository;
    @Mock
    private InuringFinalNodeRepository inuringFinalNodeRepository;
    @Mock
    private InuringContractNodeRepository inuringContractNodeRepository;
    @Mock
    private InuringFinalNodeService inuringFinalNodeService;


    private Map<Long, InuringPackage> inuringPackages;
    private Map<Long, InuringFinalNode> inuringFinalNodes;
    private Map<Long, InuringContractNode> inuringContractNodes;
    private Map<Long, InuringInputNode> inuringInputNodes;

    private static long WORKSPACE_ID = 1;
    private static long USER_ID = 1;
    private static String NAME = "PackageName";
    private static String DESCRIPTION = "PackageDescription";


    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    private int inuringPackageCounter;
    private int inuringFinalNodeCounter;
    private int inuringContractNodeCounter;
    private int inuringInputNodeCounter;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        inuringPackageCounter = 1;
        inuringFinalNodeCounter = 1;
        inuringContractNodeCounter = 1;
        inuringInputNodeCounter = 1;

        inuringPackages = new HashMap<>();
        inuringFinalNodes = new HashMap<>();
        inuringContractNodes = new HashMap<>();
        inuringInputNodes = new HashMap<>();

        when(inuringPackageRepository.saveAndFlush(any(InuringPackage.class))).thenAnswer(i -> {
            InuringPackage inuringPackage = i.getArgument(0);
            long id = inuringPackage.getInuringPackageId() != 0 ? inuringPackage.getInuringPackageId() : inuringPackageCounter++;
            inuringPackage.setInuringPackageId(id);
            inuringPackages.put(id, inuringPackage);
            InuringFinalNode inuringFinalNode = new InuringFinalNode();
            inuringFinalNode.setInuringPackageId(id);
            inuringFinalNodeRepository.save(inuringFinalNode);
            return inuringPackage;
        });

        when(inuringFinalNodeRepository.save(any(InuringFinalNode.class))).thenAnswer(i -> {
            InuringFinalNode inuringFinalNode = i.getArgument(0);
            long id = inuringFinalNode.getInuringFinalNodeId() != 0 ? inuringFinalNode.getInuringFinalNodeId() : inuringFinalNodeCounter++;
            inuringFinalNode.setInuringFinalNodeId(id);
            inuringFinalNodes.put(id, inuringFinalNode);
            return inuringFinalNode;
        });

        when(inuringContractNodeRepository.save(any(InuringContractNode.class))).thenAnswer(i -> {
            InuringContractNode inuringContractNode = i.getArgument(0);
            long id = inuringContractNode.getInuringContractNodeId() != 0 ? inuringContractNode.getInuringContractNodeId() : inuringContractNodeCounter++;
            inuringContractNode.setInuringContractNodeId(id);
            inuringContractNodes.put(id, inuringContractNode);
            return inuringContractNode;
        });

        when(inuringInputNodeRepository.save(any(InuringInputNode.class))).thenAnswer(i -> {
            InuringInputNode inuringInputNode = i.getArgument(0);
            long id = inuringInputNode.getInuringInputNodeId() != 0 ? inuringInputNode.getInuringInputNodeId() : inuringInputNodeCounter++;
            inuringInputNode.setInuringInputNodeId(id);
            inuringInputNodes.put(id, inuringInputNode);
            return inuringInputNode;
        });

        when(inuringInputNodeRepository.findByInuringInputNodeId(anyLong())).thenAnswer(i -> {
            long id = i.getArgument(0);
            return inuringInputNodes.get(id);
        });

        when(inuringContractNodeRepository.findByInuringContractNodeId(anyLong())).thenAnswer(i -> {
            long id = i.getArgument(0);
            return inuringContractNodes.get(id);
        });

        when(inuringPackageRepository.findByInuringPackageId(anyLong())).thenAnswer(i -> {
            long id = i.getArgument(0);
            return inuringPackages.get(id);
        });

        when(inuringFinalNodeRepository.findByInuringFinalNodeId(anyLong())).thenAnswer(i -> {
            long id = i.getArgument(0);
            return inuringFinalNodes.get(id);
        });

        when(inuringFinalNodeRepository.findByInuringPackageId(anyLong())).thenAnswer(node -> {
            long id = node.getArgument(0);
            return inuringFinalNodes.values().stream().filter(i -> i.getInuringPackageId() == id).collect(Collectors.toList());
        });

        doAnswer(node -> {
            long id = node.getArgument(0);
            inuringPackages.remove(id);
            long finalNodeId = inuringFinalNodeRepository.findByInuringPackageId(id).getInuringFinalNodeId();
            inuringFinalNodes.remove(finalNodeId);
            return null;
        }).when(inuringPackageRepository).deleteById(anyLong());
    }


        @Test
        public void testCreateInuringPackageAndFinalNode(){
            try {
                inuringPackageService.createInuringPackage(new InuringPackageCreationRequest(NAME,DESCRIPTION,WORKSPACE_ID,USER_ID));
                InuringPackage expectedInuringPackage = new InuringPackage(NAME,DESCRIPTION,WORKSPACE_ID,USER_ID);
                expectedInuringPackage.setInuringPackageId(1);
                assertEquals(expectedInuringPackage, inuringPackageService.findByInuringPackageId(1));
                assertEquals(inuringFinalNodeRepository.findByInuringFinalNodeId(1).getInuringPackageId(),expectedInuringPackage.getInuringPackageId());
            } catch (RRException ex) {
                fail();
            }

        }

    @Test
    public void testInvalidatingInputNode(){
        try {
            inuringInputNodeRepository.save(new InuringInputNode(1,null));
            inuringPackageService.invalidateNode(InuringNodeType.InputNode,inuringInputNodeRepository.findByInuringInputNodeId(1).getInuringInputNodeId());
            assertEquals(InuringNodeStatus.Invalid,inuringInputNodeRepository.findByInuringInputNodeId(1).getInputInuringNodeStatus());
        } catch (RRException ex) {
            fail();
        }

    }
    @Test
    public void testInvalidatingContractNode(){
        try {
            inuringContractNodeRepository.save(new InuringContractNode(1,null));
            inuringPackageService.invalidateNode(InuringNodeType.ContractNode,inuringContractNodeRepository.findByInuringContractNodeId(1).getInuringContractNodeId());
            assertEquals(InuringNodeStatus.Invalid,inuringContractNodeRepository.findByInuringContractNodeId(1).getContractNodeStatus());
        } catch (RRException ex) {
            fail();
        }

    }

    @Test
    public void testInvalidatingFinalNode(){
        try {
            inuringFinalNodeRepository.save(new InuringFinalNode(1));
            inuringPackageService.invalidateNode(InuringNodeType.FinalNode,inuringFinalNodeRepository.findByInuringFinalNodeId(1).getInuringFinalNodeId());
            assertEquals(InuringNodeStatus.Invalid,inuringFinalNodeRepository.findByInuringFinalNodeId(1).getFinalNodeStatus());
        } catch (RRException ex) {
            fail();
        }

    }

    /**Testing the delete of the package after adjusting the code to the cascade delete**/






}
