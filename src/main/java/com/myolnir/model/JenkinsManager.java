package com.myolnir.model;

import com.myolnir.services.BackupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JenkinsManager {

    private static final Logger log = LoggerFactory.getLogger(BackupService.class);

    @Autowired
    private JenkinsConfigData data;

    @Value("${jenkinsBackup.defaultUrl}")
    private String jenkinsDefaultUrl;

    @Value("${jenkinsBackup.defaultBackupFolder}")
    private String jenkinsDefaultBackupFolder;

    public void start(String... args) {
        if (args == null || args.length < 2) {
            log.error("There are not provided jenkins url and backup location, it will use default values");
            setJenkinsConfigData(jenkinsDefaultUrl, jenkinsDefaultBackupFolder);
        } else  {
            setJenkinsConfigData(args[0], args[1]);
        }
    }

    private void setJenkinsConfigData(final String url, final String folder) {
        data.setJenkinsBaseUrl(url);
        data.setBackupUrl(folder);
    }

}
