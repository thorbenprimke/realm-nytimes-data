package co.moonmonkeylabs.realmnytimesdata;

import android.util.Log;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import co.moonmonkeylabs.realmnytimesdata.model.NYTimesResponse;
import co.moonmonkeylabs.realmnytimesdata.model.NYTimesStory;
import co.moonmonkeylabs.realmnytimesdata.service.NYTimesService;
import io.realm.Realm;
import retrofit.Call;
import retrofit.Callback;
import retrofit.JacksonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class NYTimesDataLoader {

    private static final String TAG = NYTimesDataLoader.class.getName();

    String[] nytSections = new String[]{
            "home",
            "world",
            "national",
            "politics",
            "nyregion",
            "business",
            "opinion",
            "technology",
            "science",
            "health",
            "sports",
            "arts",
            "fashion",
            "dining",
            "travel",
            "magazine",
            "realestate"
    };

    private NYTimesService nyTimesService;
    private SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-d'T'HH:mm:ssZZZZZ");
    private SimpleDateFormat outputDateFormat = new SimpleDateFormat("MM-dd-yyyy");

    public NYTimesDataLoader() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl("http://api.nytimes.com/")
                .build();
        nyTimesService = retrofit.create(NYTimesService.class);
    }

    public void loadData(String section, final Realm realm, final String apiKey) {
        final Call<NYTimesResponse<List<NYTimesStory>>> listCall =
                nyTimesService.topStories(section, apiKey);
        listCall.enqueue(new Callback<NYTimesResponse<List<NYTimesStory>>>() {
            @Override
            public void onResponse(
                    Response<NYTimesResponse<List<NYTimesStory>>> response,
                    Retrofit retrofit) {
                final NYTimesResponse<List<NYTimesStory>> body = response.body();
                if (body.results.isEmpty()) {
                    return;
                }
                Log.d(TAG, "Success - Data loaded");

                processAndAddData(realm, body.results);
                Log.d(TAG, "Success - Saved to Realm");
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(TAG, "Failure: Data not loaded");
            }
        });
    }

    public void loadAllData(final Realm realm, String apiKey) {
        loadNextSection(0, realm, apiKey);
    }

    private void loadNextSection(final int sectionIndex, final Realm realm, final String apiKey) {
        final Call<NYTimesResponse<List<NYTimesStory>>> listCall =
                nyTimesService.topStories(nytSections[sectionIndex], apiKey);
        listCall.enqueue(new Callback<NYTimesResponse<List<NYTimesStory>>>() {
            @Override
            public void onResponse(
                    Response<NYTimesResponse<List<NYTimesStory>>> response,
                    Retrofit retrofit) {
                final NYTimesResponse<List<NYTimesStory>> body = response.body();
                if (body.results.isEmpty()) {
                    return;
                }
                Log.d(TAG, "Success - Data loaded");

                processAndAddData(realm, body.results);
                Log.d(TAG, "Success - Saved to Realm");

                if (sectionIndex < nytSections.length - 1) {
                    loadNextSection(sectionIndex + 1, realm, apiKey);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(TAG, "Failure: Data not loaded");
            }
        });
    }

    private void processAndAddData(Realm realm, List<NYTimesStory> stories) {
        // Process stories and update the publishedDate to the required format
        for (NYTimesStory nyTimesStory : stories) {
            Date parsedPublishedDate = inputDateFormat.parse(
                    nyTimesStory.getPublishedDate(),
                    new ParsePosition(0));
            nyTimesStory.setSortTimeStamp(parsedPublishedDate.getTime());
            nyTimesStory.setPublishedDate(outputDateFormat.format(parsedPublishedDate));
        }

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(stories);
        realm.commitTransaction();

    }
}
