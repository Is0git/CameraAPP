package com.android.cameraapp.ui.base_activity.followers_fragment

import android.annotation.SuppressLint
import android.util.Log
import androidx.paging.PositionalDataSource
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.di.base_activity.followers_fragment.FollowersFragmentScope
import com.android.cameraapp.util.userCollection
import com.android.cameraapp.util.userFollowersCollection
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

const val TAG = "FollowersTAG"

@FollowersFragmentScope
class FollowersDataSource @Inject constructor(
    val auth: FirebaseAuth,
    val firestore: FirebaseFirestore
) :
    PositionalDataSource<DataFlat.Followers>() {
    var lastDocument: DocumentSnapshot? = null
    override fun loadRange(
        params: LoadRangeParams,
        callback: LoadRangeCallback<DataFlat.Followers>
    ) {
        lastDocument?.let {
            val followersWithUser: MutableList<DataFlat.Followers> = mutableListOf()
            getFollowersObservable(params)
                .flatMap { getFollowersWithUser(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : Observer<DataFlat.Followers> {
                    override fun onComplete() {
                        callback.onResult(followersWithUser)
                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: DataFlat.Followers) {
                        followersWithUser.add(t)
                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, "ERROR: ${e.message}")
                    }

                })

        }
    }

    @SuppressLint("CheckResult")
    override fun loadInitial(
        params: LoadInitialParams,
        callback: LoadInitialCallback<DataFlat.Followers>
    ) {
        val followersWithUser: MutableList<DataFlat.Followers> = mutableListOf()
        getFollowersObservable(params)
            .flatMap { getFollowersWithUser(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : Observer<DataFlat.Followers> {
                override fun onComplete() {
                    callback.onResult(followersWithUser, 0, followersWithUser.size)
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: DataFlat.Followers) {
                    followersWithUser.add(t)
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "ERROR: ${e.message}")
                }

            })
    }

    @SuppressLint("CheckResult")
    fun getFollowersWithUser(followers: DataFlat.Followers): Observable<DataFlat.Followers> {
       return Observable.create<UserCollection.User> { getUser(followers.follower_uid!!) }
            .map {
                followers.user = it
                return@map followers
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())

    }

    fun <T> getFollowersObservable(params: T): Observable<DataFlat.Followers> {
        return Observable.create<List<DataFlat.Followers>> { getFireStoreFollower(params) }
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { Observable.fromIterable(it) }


    }

    fun <T> getFireStoreFollower(params: T): List<DataFlat.Followers>? {
        var followers: List<DataFlat.Followers>? = null
        if (params is LoadInitialParams) {
            firestore.collection("$userCollection/${auth.uid}/$userFollowersCollection")
                .limit(params.requestedLoadSize.toLong())
                .orderBy(
                    "following_time_long", Query.Direction.DESCENDING
                )
                .get()
                .addOnCompleteListener {
                    when {
                        it.isSuccessful && it.result?.size() != 0 -> {
                            if (lastDocument != null) lastDocument = it.result?.documents?.last()!!
                            followers = it.result?.toObjects(DataFlat.Followers::class.java)!!
                        }
                        it.isCanceled -> Log.i(TAG, "FAILED: ${it.exception?.message}")
                    }
                }
        } else if (params is LoadRangeParams) {
            if (lastDocument != null) {
                firestore.collection("$userCollection/${auth.uid}/$userFollowersCollection")
                    .startAfter(lastDocument!!)
                    .limit(params.loadSize.toLong())
                    .get()
                    .addOnCompleteListener {
                        when {
                            it.isSuccessful && it.result?.size() != 0 -> {
                                lastDocument = it.result?.documents?.last()!!
                                followers = it.result?.toObjects(DataFlat.Followers::class.java)
                            }
                            it.isCanceled -> Log.i(
                                TAG,
                                "FAILED ON loadRANGE: ${it.exception?.message}"
                            )
                        }
                    }
            }
        }
        return followers
    }

    fun getUser(uid: String): UserCollection.User? {
        var user: UserCollection.User? = null
        firestore.document("$userCollection/$uid").get().addOnCompleteListener {
            when {
                it.isSuccessful -> user = it.result?.toObject(UserCollection.User::class.java)!!
                else -> return@addOnCompleteListener
            }
        }
        return user
    }
}

