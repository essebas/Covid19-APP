package co.sebasdeveloper.pruebaservinformacion.di.modules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import co.sebasdeveloper.pruebaservinformacion.remote.LocationDataService;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
public abstract class NetworkModule {

    private static final String BASE_URL = "https://www.trackcorona.live/api/";

    @Provides
    @Singleton
    static Retrofit prRetrofit(){
        Gson gson = new GsonBuilder().setLenient().create();

        OkHttpClient.Builder okHttp = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        //httpClient.addInterceptor(new NetworkInterceptor());
        httpClient.addInterceptor(logging);
        httpClient.connectTimeout(30, TimeUnit.SECONDS);
        httpClient.readTimeout(30, TimeUnit.SECONDS);

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build();
    }

    @Provides
    @Singleton
    static LocationDataService prLocationDataService(Retrofit retrofit){
        return retrofit.create(LocationDataService.class);
    }

}
