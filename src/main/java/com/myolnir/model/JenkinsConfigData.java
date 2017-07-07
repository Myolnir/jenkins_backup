package com.myolnir.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class JenkinsConfigData {

    @Value("${jenkinsBackup.jenkins.url}")
    private String jenkinsJobsUrl;

    @Value("${jenkinsBackup.jenkins.username}")
    private String jenkinsUsername;

    @Value("${jenkinsBackup.jenkins.password}")
    private String jenkinsPassword;

    @Value("${jenkinsBackup.jenkins.jobUrl}")
    private String jenkinsJobUrl;

    private String jenkinsBaseUrl;

    private String backupUrl;

    public String getJenkinsJobsUrl() {
        return this.jenkinsBaseUrl + jenkinsJobsUrl + "/";
    }

    public String getJenkinsUsername() {
        return jenkinsUsername;
    }

    public String getJenkinsPassword() {
        return jenkinsPassword;
    }

    public String getJenkinsJobUrl() {
        return this.jenkinsBaseUrl + jenkinsJobUrl;
    }

    public void setJenkinsBaseUrl(String jenkinsBaseUrl) {
        this.jenkinsBaseUrl = jenkinsBaseUrl;
    }

    public String getBackupUrl() {
        return backupUrl;
    }

    public void setBackupUrl(String backupUrl) {
        this.backupUrl = backupUrl;
    }
}
