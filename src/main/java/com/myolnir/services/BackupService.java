package com.myolnir.services;

import com.myolnir.model.JenkinsConfigData;
import com.myolnir.model.JenkinsJob;
import com.myolnir.model.JenkinsModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Myolnir on 03/07/2017.
 */
@Service
public class BackupService {

    private static final Logger log = LoggerFactory.getLogger(BackupService.class);

    @Autowired
    private HttpComponentsClientHttpRequestFactory customHttpRequestFactory;

    @Autowired
    private JenkinsConfigData data;

    /**
     * Scheduled task every minute that backups data into given backup
     */
    @Scheduled(cron = "${jenkinsBackup.cron}")
    public void backupData()  {
        log.debug("Init backup data from jenkins");
        RestTemplate restTemplate = new RestTemplate(customHttpRequestFactory);
        restTemplate.getInterceptors().add(
                new BasicAuthorizationInterceptor(data.getJenkinsUsername(), data.getJenkinsPassword()));
        try {
            JenkinsModel jobs = restTemplate.getForObject(data.getJenkinsJobsUrl(), JenkinsModel.class);
            jobs.getJobs().forEach((JenkinsJob job) -> {
                String xmlData = restTemplate.getForObject(data.getJenkinsJobUrl() + job.getName() + "/config.xml", String.class);
                writeFileToDisk(xmlData, data.getBackupUrl() + "/" + job.getName() + ".xml");
            });
        } catch (RestClientException connectionError) {
            log.error("Error connecting Jenkins caused by: " + connectionError.getMessage());
        }
        log.debug("Finish backup data");
    }


    /**
     * Writes retrieved job info to disk.
     * @param fileData info for job.
     * @param filePath path to save the file.
     */
    private void writeFileToDisk(final String fileData, final String filePath) {
        log.debug("Writing file " + filePath);
        Path path = Paths.get(filePath);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(fileData);
        } catch (IOException e) {
            log.error("Error writing file " + path + " to disk caused by: " + e.getMessage());
        }
    }


}
