package com.addison.gamingbacklog.repository.service;

import static com.addison.gamingbacklog.repository.service.RequestStatus.RequestState.FAILURE;
import static com.addison.gamingbacklog.repository.service.RequestStatus.RequestState.LOADING;
import static com.addison.gamingbacklog.repository.service.RequestStatus.RequestState.SUCCESS;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.addison.gamingbacklog.BuildConfig;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class IGDBService {

    private static final String IGDB_END_POINT = "https://api-v3.igdb.com/";
    private static final String IGDB_API_KEY_PARAM_NAME = "user-key";
    private static final String IGDB_API_KEY_VALUE = BuildConfig.IGDB_API_KEY;

    private final IGDBApi mService;

    private MutableLiveData<RequestStatus> mRequestStatusLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Game>> mGamesListLiveData = new MutableLiveData<>();

    public IGDBService() {
        mService = provideRetrofit().create(IGDBApi.class);
    }

    public void retrieveGames() {
        mRequestStatusLiveData.setValue(new RequestStatus(LOADING));

        String fields = "fields name, summary; ";
        String sort = "sort aggregated_rating desc; ";
        String filters = "where aggregated_rating_count >= 10; limit 20;";

        Call<List<Game>> call = mService.getGames(fields + sort + filters);
        call.enqueue(getGamesListCallback());
    }

    public LiveData<RequestStatus> getRequestStatus() {
        return mRequestStatusLiveData;
    }

    public LiveData<List<Game>> getGamesList() {
        return mGamesListLiveData;
    }

    private Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(IGDB_END_POINT)
                .client(provideOkHttpClient())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(getInterceptor())
                .build();
    }

    private Interceptor getInterceptor() {
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                okhttp3.Request request = chain.request();
                request = request.newBuilder()
                        .addHeader(IGDB_API_KEY_PARAM_NAME, IGDB_API_KEY_VALUE)
                        .build();
                return chain.proceed(request);
            }
        };
    }

    private Callback<List<Game>> getGamesListCallback() {
        return new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                mRequestStatusLiveData.setValue(response.isSuccessful()
                        ? new RequestStatus(SUCCESS)
                        : new RequestStatus(FAILURE));
                mGamesListLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
                mRequestStatusLiveData.setValue(new RequestStatus(FAILURE));
            }
        };
    }
}
