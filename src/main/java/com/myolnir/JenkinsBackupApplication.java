package com.myolnir;

import com.myolnir.model.JenkinsConfigData;
import com.myolnir.model.JenkinsManager;
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


    @Autowired
    private JenkinsManager manager;

	public static void main(String[] args) {

	    SpringApplication.run(JenkinsBackupApplication.class, args);
	}

	public void run(String... args) {
	    manager.start(args);
    }
}
