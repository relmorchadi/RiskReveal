import com.scor.rr.entity.InuringInputAttachedPLT;
import com.scor.rr.entity.InuringInputNode;
import com.scor.rr.repository.InuringInputAttachedPLTRepository;
import com.scor.rr.repository.InuringInputNodeRepository;
import com.scor.rr.service.InuringInputNodeService;
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

    private Map<Integer, InuringInputNode> inuringInputNodes;

    private List<InuringInputAttachedPLT> inuringInputAttachedPLTS;

    private int counter = 1;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        inuringInputNodes = new HashMap<>();
        inuringInputAttachedPLTS = new LinkedList<>();

        when(inuringInputNodeService.saveOrUpdateInuringInputNode(any(InuringInputNode.class))).thenAnswer(i -> {
            InuringInputNode inuringInputNode = i.getArgument(0);
            inuringInputNodes.put(counter++, inuringInputNode);
            return null;
        });

        when(inuringInputNodeService.findByInuringInputNodeId(anyInt())).thenAnswer(i -> {
            int id = i.getArgument(0);
            return inuringInputNodes.get(id);
        });
    }

    @Test
    public void testCreateAnInputNodeForNonExistingPackage() {

    }

    @Test
    public void testCreateAnInputNodeWithoutAttachedPLT() {

    }

    @Test
    public void testCreateAnInputNodeWithAttachedPLTs() {

    }

    @Test
    public void testAttachOnePLTToExistingInputNode() {

    }

    @Test
    public void testDetachOnePLTFromExistingInputNode() {

    }

    @Test
    public void testAssignTagToAnAttachedPLT() {

    }

    @Test
    public void testAddTwoInputNodeToAnInuringPackage() {

    }

    @Test
    public void testDeleteAnInputNode() {

    }

}
