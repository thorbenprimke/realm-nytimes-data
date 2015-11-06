package co.moonmonkeylabs.realmnytimesdata.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NYTimesResponse<T> {

    @JsonProperty("status")
    public String status;

    @JsonProperty("copyright")
    public String copyright;

    @JsonProperty("section")
    public String section;

    @JsonProperty("last_updated")
    public String lastUpdated;

    @JsonProperty("num_results")
    public Integer numResults;

    @JsonProperty("results")
    public T results;
}
