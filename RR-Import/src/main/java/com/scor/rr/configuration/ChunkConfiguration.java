package com.scor.rr.configuration;

import com.scor.rr.service.batch.processor.AccItemProcessor;
import com.scor.rr.service.batch.processor.LocItemProcessor;
import com.scor.rr.service.batch.reader.RLAccCursorItemReader;
import com.scor.rr.service.batch.reader.RLLocCursorItemReader;
import com.scor.rr.service.batch.writer.RLAccAndLocItemWriter;
import com.scor.rr.util.Utils;
import org.beanio.StreamFactory;
import org.beanio.spring.BeanIOFlatFileItemWriter;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

@Configuration
public class ChunkConfiguration {

    /**
     * Resources
     */

    @Bean("locResource")
    @StepScope
    public Resource locResource(@Value("${ihub.treaty.out.path}") String iHubPath, @Value("#{jobParameters['carId']}") String carId, @Value("#{jobParameters['division']}") String division) {
        return new FileSystemResource("file:" + iHubPath + "/tmp/" + carId + "_" + division + ".loc");
    }

    @Bean("locFWResource")
    @StepScope
    public Resource locFWResource(@Value("${ihub.treaty.out.path}") String iHubPath, @Value("#{jobParameters['carId']}") String carId, @Value("#{jobParameters['division']}") String division) {
        return new FileSystemResource("file:" + iHubPath + "/tmp/" + carId + "_" + division + "_FW.loc");
    }

    @Bean("accResource")
    @StepScope
    public Resource accResource(@Value("${ihub.treaty.out.path}") String iHubPath, @Value("#{jobParameters['carId']}") String carId, @Value("#{jobParameters['division']}") String division) {
        return new FileSystemResource("file:" + iHubPath + "/tmp/" + carId + "_" + division + ".acc");
    }

    /**
     * Readers
     */

    @Bean(name = "AccReader")
    public RLAccCursorItemReader getAccReader() {
        RLAccCursorItemReader reader = new RLAccCursorItemReader();
        reader.setUseSharedExtendedConnection(true);
        reader.setDriverSupportsAbsolute(true);
        reader.setVerifyCursorPosition(false);

        return reader;
    }

    @Bean(name = "LocReader")
    public RLLocCursorItemReader getLocReader() {
        RLLocCursorItemReader reader = new RLLocCursorItemReader();
        reader.setUseSharedExtendedConnection(true);
        reader.setDriverSupportsAbsolute(true);
        reader.setVerifyCursorPosition(false);

        return reader;
    }

    /**
     * Processors
     */

    @Bean(name = "AccProcessor")
    public AccItemProcessor getAccProcessor() {
        return new AccItemProcessor();
    }

    @Bean(name = "LocProcessor")
    public LocItemProcessor getLocProcessor() {
        return new LocItemProcessor();
    }

    /**
     * Writers
     */

    @Bean(name = "AccWriter")
    public BeanIOFlatFileItemWriter getAccWriter(@Qualifier("accResource") Resource resource) {
        BeanIOFlatFileItemWriter writer = new BeanIOFlatFileItemWriter();
        writer.setHeaderCallback(new RLAccAndLocItemWriter(Utils.ACC_HEADER));
        writer.setAppendAllowed(false);
        writer.setResource(resource);
        writer.setStreamFactory(getStreamFactory());
        writer.setStreamName("accStream");
        writer.setTransactional(false);
        writer.setShouldDeleteIfExists(true);
        writer.setEncoding("UTF-8");
        return writer;
    }

    @Bean(name = "LocWriter")
    public BeanIOFlatFileItemWriter getLocWriter(@Qualifier("locResource") Resource resource) {
        BeanIOFlatFileItemWriter writer = new BeanIOFlatFileItemWriter();
        writer.setHeaderCallback(new RLAccAndLocItemWriter(Utils.LOC_HEADER));
        writer.setResource(resource);
        writer.setAppendAllowed(false);
        writer.setStreamFactory(getStreamFactory());
        writer.setStreamName("locStream");
        writer.setTransactional(false);
        writer.setShouldDeleteIfExists(true);
        writer.setEncoding("UTF-8");
        return writer;
    }

    @Bean(name = "LocWriterFW")
    public BeanIOFlatFileItemWriter getLocWriterFW(@Qualifier("locFWResource") Resource resource) {
        BeanIOFlatFileItemWriter writer = new BeanIOFlatFileItemWriter();
        writer.setHeaderCallback(new RLAccAndLocItemWriter(Utils.LOC_FW_HEADER));
        writer.setAppendAllowed(false);
        writer.setStreamFactory(getStreamFactory());
        writer.setResource(resource);
        writer.setStreamName("locStreamFW");
        writer.setTransactional(false);
        writer.setShouldDeleteIfExists(true);
        writer.setEncoding("UTF-8");
        return writer;
    }

    @Bean
    public StreamFactory getStreamFactory() {
        StreamFactory streamFactory = StreamFactory.newInstance();
        streamFactory.load("classpath:/beanio/accloc.xml");
        return streamFactory;
    }

}
