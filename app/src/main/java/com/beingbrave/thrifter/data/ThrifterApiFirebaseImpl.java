package com.beingbrave.thrifter.data;

import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.beingbrave.thrifter.models.Post;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ThrifterApiFirebaseImpl implements ThrifterApi {

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private FirebaseStorage storage;
    private GeoFire geoFire;

    public ThrifterApiFirebaseImpl() {
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        DatabaseReference geofireRef = database.getReference("post_locations");
        geoFire = new GeoFire(geofireRef);
    }

    @Override
    public void createPost(Post post) {
        DatabaseReference postRef = database.getReference("posts").push();

        Location location = LocationApi.getCurrentLocation();

        post.setUserId(auth.getCurrentUser().getUid());
        post.setLatitude(location.getLatitude());
        post.setLongitude(location.getLongitude());
        postRef.setValue(post, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, DatabaseReference databaseReference) {
                if (error != null) {
                    System.err.println("There was an error saving the post");
                } else {
                    System.out.println("Post saved on server successfully!");
                }
            }
        });

        geoFire.setLocation(postRef.getKey(), new GeoLocation(location.getLatitude(), location.getLongitude()), new GeoFire.CompletionListener() {
            @Override
            public void onComplete(String key, DatabaseError error) {
                if (error != null) {
                    System.err.println("There was an error saving the location to GeoFire: " + error);
                } else {
                    System.out.println("Location saved on server successfully!");
                }
            }
        });

        StorageReference storageRef = storage.getReferenceFromUrl("gs://thrifter-adad1.appspot.com")
                .child("images/" + post.getUserId() + "/" + postRef.getKey());
        UploadTask uploadTask = storageRef.putFile(post.getImageUri());
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("FAIL SILENTLY");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                System.out.println("Yes! It uploaded!");
            }
        });
    }

    @Override
    public void searchPosts(double distance, final SearchCallback postResult) {
        Location location = LocationApi.getCurrentLocation();

        final GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(location.getLatitude(), location.getLongitude()), distance);
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(final String key, GeoLocation location) {
                System.out.println(String.format("Key %s entered the search area at [%f,%f]", key, location.latitude, location.longitude));

                DatabaseReference postRef = database.getReference("posts");

                postRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final Post post = dataSnapshot.getValue(Post.class);
                        StorageReference storageRef = storage.getReferenceFromUrl("gs://thrifter-adad1.appspot.com")
                                .child("images/" + post.getUserId() + "/" + key);
                        storageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if(task.isSuccessful()) {
                                    post.setImageUri(task.getResult());
                                    postResult.onNewPost(post);
                                } else {
                                    System.out.println("FAIL SILENTLY");
                                }
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("FAIL SILENTLY");
                    }
                });
            }

            @Override
            public void onKeyExited(String key) {
                System.out.println(String.format("Key %s is no longer in the search area", key));
            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {
                System.out.println(String.format("Key %s moved within the search area to [%f,%f]", key, location.latitude, location.longitude));
            }

            @Override
            public void onGeoQueryReady() {
                System.out.println("All initial data has been loaded and events have been fired!");
            }

            @Override
            public void onGeoQueryError(DatabaseError error) {
                System.err.println("There was an error with this query: " + error);
                postResult.onError(error.toString());
                geoQuery.removeAllListeners();
                LocationApi.activeQuery = null;
            }
        });

        LocationApi.activeQuery = geoQuery;

    }

}
