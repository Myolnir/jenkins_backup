package com.myolnir.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Set;

/**
 * Created by Myolnir on 03/07/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JenkinsModel {
    private List<JenkinsJob> jobs;

    public List<JenkinsJob> getJobs() {
        return jobs;
    }

    public void setJobs(List<JenkinsJob> jobs) {
        this.jobs = jobs;
    }

}
