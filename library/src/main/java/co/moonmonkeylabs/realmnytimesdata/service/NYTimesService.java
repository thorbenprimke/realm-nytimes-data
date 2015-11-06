package co.moonmonkeylabs.realmnytimesdata.service;

import java.util.List;

import co.moonmonkeylabs.realmnytimesdata.model.NYTimesResponse;
import co.moonmonkeylabs.realmnytimesdata.model.NYTimesStory;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface NYTimesService {

    @GET("svc/topstories/v1/{section}.json")
    Call<NYTimesResponse<List<NYTimesStory>>> topStories(
            @Path("section") String section,
            @Query("api-key") String apiKey);
}