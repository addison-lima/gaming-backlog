package com.addison.gamingbacklog.repository.service;

import static com.addison.gamingbacklog.repository.service.RequestStatus.RequestState.FAILURE;
import static com.addison.gamingbacklog.repository.service.RequestStatus.RequestState.LOADING;
import static com.addison.gamingbacklog.repository.service.RequestStatus.RequestState.SUCCESS;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.addison.gamingbacklog.BuildConfig;
import com.addison.gamingbacklog.repository.service.models.Game;
import com.addison.gamingbacklog.repository.service.models.Video;

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

    private MutableLiveData<RequestStatus> mRequestStatusGamesList = new MutableLiveData<>();
    private MutableLiveData<List<Game>> mGamesListLiveData = new MutableLiveData<>();

    private MutableLiveData<RequestStatus> mRequestStatusVideosList = new MutableLiveData<>();
    private MutableLiveData<List<Video>> mGameVideosListLiveData = new MutableLiveData<>();

    public IGDBService() {
        mService = provideRetrofit().create(IGDBApi.class);
    }

    public void retrieveGames() {
        mRequestStatusGamesList.setValue(new RequestStatus(LOADING));

        String fields = "fields cover.url, first_release_date, name, summary, videos.name, videos.video_id; ";
        String sort = "sort aggregated_rating desc; ";
        String filters = "where aggregated_rating_count >= 10; limit 20;";

        Call<List<Game>> call = mService.getGames(fields + sort + filters);
        call.enqueue(getGamesListCallback());
    }

    public void retrieveGameVideos(Integer id) {
        mRequestStatusVideosList.setValue(new RequestStatus(LOADING));

        String fields = "fields name, video_id; ";
        String filter = "where game = " + id + ";";

        Call<List<Video>> call = mService.getGameVideos(fields + filter);
        call.enqueue(getVideosListCallback());
    }

    public LiveData<RequestStatus> getRequestStatus() {
        return mRequestStatusGamesList;
    }

    public LiveData<List<Game>> getGamesList() {
        return mGamesListLiveData;
    }

    public LiveData<List<Video>> getGameVideosList() {
        return mGameVideosListLiveData;
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
                mRequestStatusGamesList.setValue(response.isSuccessful()
                        ? new RequestStatus(SUCCESS)
                        : new RequestStatus(FAILURE));
                mGamesListLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
                mRequestStatusGamesList.setValue(new RequestStatus(FAILURE));
            }
        };
    }

    private Callback<List<Video>> getVideosListCallback() {
        return new Callback<List<Video>>() {
            @Override
            public void onResponse(Call<List<Video>> call, Response<List<Video>> response) {
                mRequestStatusVideosList.setValue(response.isSuccessful()
                        ? new RequestStatus(SUCCESS)
                        : new RequestStatus(FAILURE));
                mGameVideosListLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Video>> call, Throwable t) {
                mRequestStatusVideosList.setValue(new RequestStatus(FAILURE));
            }
        };
    }
}
