package com.scor.rr.importBatch.processing.batch;

import com.scor.rr.importBatch.processing.p2p.SocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * Created by U002629 on 01/10/2015.
 */
public class RestNotifier implements Notifier {

    private static Logger logger = LoggerFactory.getLogger(RestNotifier.class);

    String eventUrl;
    String messageUrl;
    String importUrl;

    public RestNotifier(String notifyRestHost, String notifyRestPort, String eventUrlPath, String messageUrlPath, String importUrlPath) {
        final URI uri = UriComponentsBuilder.newInstance().scheme("http").host(notifyRestHost).port(Integer.parseInt(notifyRestPort)).build().toUri();
        eventUrl = UriComponentsBuilder.fromUri(uri).path(eventUrlPath).build().toUriString();
        messageUrl = UriComponentsBuilder.fromUri(uri).path(messageUrlPath).build().toUriString();
        importUrl = UriComponentsBuilder.fromUri(uri).path(importUrlPath).build().toUriString();
    }

    @Override
    public void notifyEvent(String carID) {
        logger.info("notifyEvent {}", carID);

        SocketServer.sendMessage(carID);

        int progress = Integer.parseInt(carID);
        if (progress == 100) {
            logger.info("notifyEvent close socket{}");
            SocketServer.close();
        }

        RestTemplate restTemplate = new RestTemplate();
        try {
            final String result = restTemplate.getForObject(eventUrl, String.class, carID);
        } catch (Exception e) {
            logger.info("notifyEvent {} error: {}", carID, e.getMessage());
        }
    }

    @Override
    public void notifyMessage(String userID) {
        logger.info("notifyMessage {}", userID);
        RestTemplate restTemplate = new RestTemplate();
        try {
            final String result = restTemplate.getForObject(messageUrl, String.class, userID);
        } catch (Exception e) {
            logger.info("notifyMessage {} error: {}", userID, e.getMessage());
        }
    }

    @Override
    public void notifyImport(Long jobId, String rmspicId, String date, Boolean done) {
        // ALMFWeb/rest/notify/import?jobId={jobId}&pdaId={pdaId}&time={time}&done={done}
        logger.info("notifyImport jobId {}, rmspicId {}, date {}", jobId, rmspicId, date);
        RestTemplate restTemplate = new RestTemplate();
        try {
            final String result = restTemplate.getForObject(importUrl, String.class, jobId, rmspicId, date, done);
        } catch (Exception e) {
            logger.info("notifyImport jobId {} error: {}", jobId, e.getMessage());
        }
    }
}
