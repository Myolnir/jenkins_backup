package com.myolnir;

import com.myolnir.model.JenkinsConfigData;
import com.myolnir.services.BackupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JenkinsBackupApplication implements CommandLineRunner{

    private static final Logger log = LoggerFactory.getLogger(BackupService.class);

    @Autowired
    private JenkinsConfigData data;

	public static void main(String[] args) {

	    SpringApplication.run(JenkinsBackupApplication.class, args);
	}

	public void run(String... args) {
	    if (args.length < 2) {
            log.error("To start backup process it is need jenkins url and backup folder location");
            System.exit(1);
        }
        data.setJenkinsBaseUrl(args[0]);
	    data.setBackupUrl(args[1]);
    }
}
