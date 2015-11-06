package co.moonmonkeylabs.realmnytimesdata;

import co.moonmonkeylabs.realmnytimesdata.model.NYTimesMultimedium;
import co.moonmonkeylabs.realmnytimesdata.model.NYTimesStory;
import io.realm.annotations.RealmModule;

@RealmModule(library = true, classes = { NYTimesStory.class, NYTimesMultimedium.class })
public class NYTimesModule {
}
