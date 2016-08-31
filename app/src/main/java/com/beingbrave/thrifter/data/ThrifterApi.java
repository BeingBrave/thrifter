package com.beingbrave.thrifter.data;

import com.beingbrave.thrifter.models.Post;

import java.io.File;

public interface ThrifterApi {

    /**
     * Create post on server, automatically adds/updates location and userid
     * @param post The post object
     */
    void createPost(Post post);

    /**
     * Search for posts
     * @param distance Distance in kilometers
     */
    void searchPosts(double distance, SearchCallback callback);

    interface SearchCallback {

        void onNewPost(Post post);

        void onError(String error);

    }
}
