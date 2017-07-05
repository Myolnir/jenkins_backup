package com.myolnir.services;

import com.myolnir.model.JenkinsJob;
import com.myolnir.model.JenkinsModel;
import org.apache.http.conn.ConnectTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.SocketTimeoutException;
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
    private RestTemplate restTemplate;

    @Value("${jenkinsBackup.jenkins.url}")
    private String jenkinsJobsUrl;

    @Value("${jenkinsBackup.jenkins.username}")
    private String jenkinsUsername;

    @Value("${jenkinsBackup.jenkins.password}")
    private String jenkinsPassword;

    @Value("${jenkinsBackup.jenkins.jobUrl}")
    private String jenkinsJobUrl;

    /**
     * Connects to jenkins retrieve list of jobs and save all of them to disk.
     */
    public void backupData(final Boolean withAuth, final String jenkinsBaseUrl, final String backupUrl)  {
        log.debug("Init backup data from jenkins");
        if (withAuth) {
            restTemplate.getInterceptors().add(
                    new BasicAuthorizationInterceptor(jenkinsUsername, jenkinsPassword));
        }
        try {
            JenkinsModel jobs = restTemplate.getForObject(jenkinsBaseUrl + jenkinsJobsUrl, JenkinsModel.class);
            jobs.getJobs().forEach((JenkinsJob job) -> {
                String xmlData = restTemplate.getForObject(jenkinsBaseUrl + jenkinsJobUrl + job.getName() + "/config.xml", String.class);
                writeFileToDisk(xmlData, backupUrl + "/" + job.getName() + ".xml");
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
