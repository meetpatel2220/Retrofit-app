package com.meet.retrofit_app;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView text;
    private Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        text = findViewById(R.id.text);

       //Gson gson=new GsonBuilder().serializeNulls().create();

        HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();



         Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create(  /*gson*/  ))
                .client(okHttpClient).build();

        api = retrofit.create(Api.class);


        //getPosts();
        // getComments();
        //createPost();
        updatePost();
        // deletePost();

    }

    private void getPosts() {

        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId", "1");
        parameters.put("_sort", "id");
        parameters.put("_order", "desc");

        // Call<List<Post>> call = api.getPosts(parameters);
        Call<List<Post>> call = api.getPosts(1, 2, "id", "desc");

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if (!response.isSuccessful()) {

                    text.setText("code : " + response.code());
                    return;

                }

                List<Post> posts = response.body();
                for (Post post : posts) {

                    String content = "";
                    content += "ID: " + post.getId() + "\n";
                    content += "User ID: " + post.getUserId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Text: " + post.getText() + "\n\n";

                    text.append(content);

                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                text.setText(t.getMessage());
            }
        });


    }

    private void getComments() {

        //  Call<List<Comments>> call = api.getComments("posts/1/comments");
        Call<List<Comments>> call = api.getComments(1);

        call.enqueue(new Callback<List<Comments>>() {
            @Override
            public void onResponse(Call<List<Comments>> call, Response<List<Comments>> response) {

                if (!response.isSuccessful()) {

                    text.setText("code : " + response.code());
                    return;

                }
                List<Comments> comments = response.body();
                for (Comments comments1 : comments) {

                    String content = "";


                    content += "ID: " + comments1.getId() + "\n";
                    content += "Post ID: " + comments1.getPostId() + "\n";
                    content += "Email: " + comments1.getEmail() + "\n";
                    content += "Title: " + comments1.getTitle() + "\n";
                    content += "Text: " + comments1.getText() + "\n\n";

                    text.append(content);

                }


            }

            @Override
            public void onFailure(Call<List<Comments>> call, Throwable t) {
                text.setText(t.getMessage());

            }
        });
    }

    private void createPost() {


        Post post = new Post(23, "new title", "new text");
        Call<Post> call = api.createPost(post);
        //  Call<Post> call=api.createPost(23,"new title","new text");

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if (!response.isSuccessful()) {

                    text.setText("code : " + response.code());
                    return;

                }

                Post post1 = response.body();
                String content = "";
                content += "Code: " + response.code() + "\n";

                content += "ID: " + post1.getId() + "\n";
                content += "User ID: " + post1.getUserId() + "\n";
                content += "Title: " + post1.getTitle() + "\n";
                content += "Text: " + post1.getText() + "\n\n";

                text.setText(content);

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

                text.setText(t.getMessage());

            }
        });


    }

    private void updatePost() {

        Post post = new Post(22, "n", "new text");
        Call<Post> call = api.putPost(5, post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if (!response.isSuccessful()) {

                    text.setText("code : " + response.code());
                    return;

                }

                Post post1 = response.body();
                String content = "";
                content += "Code: " + response.code() + "\n";

                content += "ID: " + post1.getId() + "\n";
                content += "User ID: " + post1.getUserId() + "\n";
                content += "Title: " + post1.getTitle() + "\n";
                content += "Text: " + post1.getText() + "\n\n";

                text.setText(content);


            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                text.setText(t.getMessage());

            }
        });


    }

    private void deletePost(){

      Call<Void> call=api.deletePost(3);
      call.enqueue(new Callback<Void>() {
          @Override
          public void onResponse(Call<Void> call, Response<Void> response) {

                  text.setText("code : " + response.code());



          }

          @Override
          public void onFailure(Call<Void> call, Throwable t) {
              text.setText(t.getMessage());

          }
      });


    }
}
