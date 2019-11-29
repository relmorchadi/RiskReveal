import com.scor.rr.entity.*;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.*;
import com.scor.rr.request.InuringContractNodeCreationRequest;
import com.scor.rr.service.InuringContractNodeService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class InuringContractNodeTest {
    @InjectMocks
    private InuringContractNodeService inuringContractNodeService;

    @Mock
    private InuringPackageRepository inuringPackageRepository;

    @Mock
    private InuringContractNodeRepository inuringContractNodeRepository;

    @Mock
    private InuringContractLayerRepository inuringContractLayerRepository;

    @Mock
    private InuringContractLayerParamRepository inuringContractLayerParamRepository;

    @Mock
    private InuringFilterCriteriaRepository inuringFilterCriteriaRepository;

    @Mock
    private InuringContractLayerPerilLimitRepository inuringContractLayerPerilLimitRepository;

    @Mock
    private InuringContractLayerReinstatementDetailRepository inuringContractLayerReinstatementDetailRepository;

    @Mock
    private RefFMFContractAttributeRepository refFMFContractAttributeRepository;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    private static long CONTRACT_ID = 1;

    private static int inuringContractCounter;
    private static int inuringContractLayerCounter;
    private static int inuringContractLayerParamCounter;

    private Map<Long, InuringContractNode> inuringContractNodes;
    private Map<Long, InuringContractLayer> inuringContractLayers;
    private Map<Long, InuringContractLayerParam> inuringContractLayerParams;

    private static long INURING_PACKAGE_ID = 1;

    private static String CONTRACT_TYPE = "XOL_EVT";
    private static Date INCEPTION_DATE = new Date();
    private static Date EXPIRATION_DATE = new Date();

    private static String UIATTRIBUTE_NAME = "AttributeName";
    private static String DATA_TYPE = "DataType";
    private static String DEFAULT_VALUE = "defaultValue";


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        inuringContractNodes = new HashMap<>();
        inuringContractLayers = new HashMap<>();
        inuringContractLayerParams = new HashMap<>();

        inuringContractCounter = 1;
        inuringContractLayerCounter = 1;
        inuringContractLayerParamCounter = 1;

        when(inuringContractNodeRepository.saveAndFlush(any(InuringContractNode.class))).thenAnswer(i -> {
            InuringContractNode inuringContractNode = i.getArgument(0);
            long id = inuringContractNode.getInuringContractNodeId() != 0 ? inuringContractNode.getInuringContractNodeId() : inuringContractCounter++;
            inuringContractNode.setInuringContractNodeId(id);
            inuringContractNodes.put(id, inuringContractNode);
            return inuringContractNode;
        });

        when(inuringContractLayerRepository.saveAndFlush(any(InuringContractLayer.class))).thenAnswer(i -> {
            InuringContractLayer inuringContractLayer = i.getArgument(0);
            long layerId = inuringContractLayer.getInuringContractLayerId() != 0 ? inuringContractLayer.getInuringContractLayerId() : inuringContractLayerCounter++;
            inuringContractLayer.setInuringContractLayerId(layerId);
            inuringContractLayers.put(layerId,inuringContractLayer);
            return inuringContractLayer;
        });

        when(inuringContractLayerParamRepository.save(any(InuringContractLayerParam.class))).thenAnswer(i -> {
            InuringContractLayerParam inuringContractLayerParam = i.getArgument(0);
            long id = inuringContractLayerParam.getInuringContractParamId() != 0 ? inuringContractLayerParam.getInuringContractParamId() : inuringContractLayerParamCounter++;
            inuringContractLayerParam.setInuringContractParamId(id);
            inuringContractLayerParams.put(id,inuringContractLayerParam);
            return inuringContractLayerParam;
        });

        when(inuringContractNodeRepository.findByInuringContractNodeId(anyLong())).thenAnswer(i -> {
            long id = i.getArgument(0);
            return inuringContractNodes.get(id);
        });

        when(inuringContractLayerRepository.findByInuringContractLayerId(anyLong())).thenAnswer(i -> {
            long id = i.getArgument(0);
            return inuringContractLayers.get(id);
        });

        when(inuringContractLayerParamRepository.findByInuringContractLayerId(anyLong())).thenAnswer(inuringContractLayerId -> {
            long id = inuringContractLayerId.getArgument(0);
            return inuringContractLayerParams.values().stream().filter(i -> i.getInuringContractLayerId() == id).collect(Collectors.toList());
        });

        when(inuringContractLayerRepository.findByInuringContractNodeId(anyLong())).thenAnswer(inuringContractNodeId -> {
            long id = inuringContractNodeId.getArgument(0);
            return inuringContractLayers.values().stream().filter(i -> i.getInuringContractNodeId() == id).collect(Collectors.toList());
        });
        when(refFMFContractAttributeRepository.getAttributesForContract(CONTRACT_TYPE)).thenAnswer(inuringContractNodeId -> {
            List<RefFMFContractAttribute> listOfAttributes = new ArrayList<>();
            RefFMFContractAttribute refFMFContractAttribute = new RefFMFContractAttribute(UIATTRIBUTE_NAME,DATA_TYPE,DEFAULT_VALUE);
            listOfAttributes.add(refFMFContractAttribute);
            return listOfAttributes;
        });



        when(inuringPackageRepository.findByInuringPackageId(INURING_PACKAGE_ID)).thenReturn(new InuringPackage());


    }

    @Test
    public void testCreatingContractNode() {
        try {
            inuringContractNodeService.createInuringContractNode(new InuringContractNodeCreationRequest(INURING_PACKAGE_ID,
                    CONTRACT_TYPE,
            INCEPTION_DATE,EXPIRATION_DATE));

            InuringContractNode expectedInuringContractNode = new InuringContractNode(INURING_PACKAGE_ID, CONTRACT_TYPE);
            expectedInuringContractNode.setInuringContractNodeId(1);
            assertEquals(expectedInuringContractNode, inuringContractNodeRepository.findByInuringContractNodeId(1));


            assertNotNull(inuringContractLayerRepository.findByInuringContractNodeId(1));
            InuringContractLayer expectedInuringContractLayer = new InuringContractLayer(1,
                    expectedInuringContractNode.getInuringContractNodeId(),
                    1, "", "");
            expectedInuringContractLayer.setInuringContractLayerId(1);
            assertEquals(expectedInuringContractLayer, inuringContractLayerRepository.findByInuringContractLayerId(1));


           assertEquals( 1,inuringContractLayerParamRepository.findByInuringContractLayerId(1).size());
            for (InuringContractLayerParam inuringContractLayerParam : inuringContractLayerParamRepository.findByInuringContractLayerId(1)) {
                assertEquals(1, inuringContractLayerParam.getInuringContractLayerId());
           }
        } catch (RRException ex) {
            fail();
        }
    }
}
