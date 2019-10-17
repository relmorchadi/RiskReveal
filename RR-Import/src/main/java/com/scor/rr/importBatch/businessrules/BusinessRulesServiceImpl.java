package com.scor.rr.importBatch.businessrules;

import org.drools.base.RuleNameStartsWithAgendaFilter;
import org.drools.command.Command;
import org.drools.command.CommandFactory;
import org.drools.command.runtime.rule.FireAllRulesCommand;
import org.drools.runtime.ExecutionResults;
import org.drools.runtime.StatelessKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "businessRulesService")
public class BusinessRulesServiceImpl implements IBusinessRulesService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(BusinessRulesServiceImpl.class);

//    @Autowired
//    @Qualifier(value = "eventsKSession")
    private StatelessKnowledgeSession statefullSession;


    @Override
    public synchronized void runRuleByName(Object model, final String ruleName) {
        LOGGER.debug("Begin execute the rule : " + ruleName);
        LOGGER.debug("The model is : " + model);

        List<Command> cmds = new ArrayList<>();
        cmds.add(CommandFactory.newInsert(model));
        cmds.add(new FireAllRulesCommand(new RuleNameStartsWithAgendaFilter(ruleName), 1));

        ExecutionResults results = statefullSession.execute(CommandFactory.newBatchExecution(cmds));

        LOGGER.debug("End execute the rule : " + ruleName);
        LOGGER.debug("The model of the rule is : " + model);
        LOGGER.debug("The result of the rule is : " + results);
    }

    @Override
    public synchronized void invokeRule(Object model) {
        LOGGER.debug("Begin execute fireRule : ");
        LOGGER.debug("The model is : " + model);

        List<Command> cmds = new ArrayList<Command>();
        cmds.add(CommandFactory.newInsert(model));
        cmds.add(new FireAllRulesCommand(1));

        ExecutionResults results = statefullSession.execute(CommandFactory.newBatchExecution(cmds));

        LOGGER.debug("The model of the rule is : " + model);
        LOGGER.debug("The result of the rule is : " + results);
    }

    public StatelessKnowledgeSession getStatefullSession() {
        return statefullSession;
    }

    public void setStatefullSession(StatelessKnowledgeSession statefullSession) {
        this.statefullSession = statefullSession;
    }


}
