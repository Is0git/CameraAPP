package com.android.cameraapp.ui.base_activity.followers_fragment

import android.annotation.SuppressLint
import androidx.paging.PositionalDataSource
import com.android.cameraapp.data.data_models.DataFlat
import com.android.cameraapp.data.data_models.UserCollection
import com.android.cameraapp.di.base_activity.followers_fragment.FollowersFragmentScope
import com.android.cameraapp.util.userCollection
import com.android.cameraapp.util.userFollowersCollection
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

const val TAG = "FollowersTAG"

@FollowersFragmentScope
class FollowersDataSource @Inject constructor(
    val auth: FirebaseAuth,
    val firestore: FirebaseFirestore
) :
    PositionalDataSource<DataFlat.Followers>() {
    var lastDocument: DocumentSnapshot? = null
    var finalChannel: Channel<DataFlat.Followers> = Channel()
    var list = mutableListOf<DataFlat.Followers>()
    override fun loadRange(
        params: LoadRangeParams,
        callback: LoadRangeCallback<DataFlat.Followers>
    ) {

    }

    @SuppressLint("CheckResult")
    override fun loadInitial(
        params: LoadInitialParams,
        callback: LoadInitialCallback<DataFlat.Followers>
    ) {
        val channel = Channel<DataFlat.Followers>()
        val finalResultChannel =
            CoroutineScope(Dispatchers.Main).launch {
              getFollowers(channel, params)
              for (i in finalChannel) println(i)

            }
        print("asdsad")

    }

    suspend fun CoroutineScope.getFollowers(
        channel: Channel<DataFlat.Followers>,
        params: LoadInitialParams
    ) = launch {
            val result: List<DataFlat.Followers> =
                firestore.collection("$userCollection/${auth.uid}/$userFollowersCollection")
                    .limit(params.requestedLoadSize.toLong())
                    .get().await().also {
                        lastDocument = it?.documents?.last() ?: throw CancellationException("Empty")
                    }.toObjects(DataFlat.Followers::class.java)

            launch {
                for (i in result) {
                    channel.send(i)
                }
            }

            getFollowersChannel(channel)
        }


     fun CoroutineScope.getFollowersChannel(channel: Channel<DataFlat.Followers>) = launch {
             for (i in channel) {
                 getUsers(i)
             }

         }


     fun CoroutineScope.getUsers(i: DataFlat.Followers) = launch {
        if (i.follower_uid != null) {
            val result: UserCollection.User =
                firestore.document("$userCollection/${i.follower_uid}").get().await()
                    .let { it.toObject(UserCollection.User::class.java)!! }
            i.user = result
            list.add(i)
            finalChannel.send(i)

        }
    }


}

