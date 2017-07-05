package com.myolnir;

import com.myolnir.services.BackupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestClientException;

@SpringBootApplication
public class JenkinsBackupApplication implements CommandLineRunner{

    private static final Logger log = LoggerFactory.getLogger(BackupService.class);


    @Autowired
    private BackupService backupService;

	public static void main(String[] args) {

	    SpringApplication.run(JenkinsBackupApplication.class, args);
	}

	public void run(String... args) {
	    if (args.length < 2) {
            log.error("To start backup process it is needed jenkins url and backup folder location");
            System.exit(1);
        }
        backupService.backupData(true, args[0], args[1]);
    }
}
