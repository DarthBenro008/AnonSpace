package com.benrostudios.anonymouspace.utils

import android.app.Application
import android.content.Context
import android.util.Log
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.*
import org.koin.java.KoinJavaComponent.inject
import java.nio.charset.StandardCharsets

class NearbyApi(private val context: Context, private val application: Application, private val onClick: (String) -> Unit) {

    lateinit var chatroomId: String
    var sender: Boolean = false


    companion object {
        const val TAG = "nearby"
    }

    fun advertise() {
        Nearby.getConnectionsClient(context)
            .startAdvertising(
                "Device A",
                "com.benrostudios.nearbyAPI",
                connectionLifecycleCallback,
                AdvertisingOptions.Builder()
                    .setStrategy(Strategy.P2P_CLUSTER)
                    .build()
            )
            .addOnSuccessListener {
                Log.d(TAG, "ADVERTISING!!")
            }
            .addOnFailureListener {
                Log.d(TAG, "FAIL TO ADVERTISE")
            }
    }

    fun stopNearby() {
        Log.d(TAG, "stopping Nearby")
        Nearby.getConnectionsClient(context).stopAllEndpoints()
    }

    fun discover() {
        Nearby.getConnectionsClient(context)
            .startDiscovery(
                "com.benrostudios.nearbyAPI",
                endpointDiscoveryCallback,
                DiscoveryOptions.Builder()
                    .setStrategy(Strategy.P2P_CLUSTER)
                    .build()
            )
            .addOnFailureListener {
                Log.d(TAG, "Discovery : $it")
            }
            .addOnSuccessListener {
                Log.d(TAG, "DISCOVERING!!")
            }
    }


    private val endpointDiscoveryCallback: EndpointDiscoveryCallback =
        object : EndpointDiscoveryCallback() {
            override fun onEndpointFound(endpointId: String, info: DiscoveredEndpointInfo) {
                // An endpoint was found. We request a connection to it.
                Nearby.getConnectionsClient(application)
                    .requestConnection("Device A", endpointId, connectionLifecycleCallback)
                    .addOnSuccessListener {
                        Log.d(TAG, "Requested Connection?")
                    }
                    .addOnFailureListener {
                        Log.d(TAG, "$it")
                    }
            }

            override fun onEndpointLost(endpointId: String) {
                // A previously discovered endpoint has gone away.
            }
        }

    private val connectionLifecycleCallback: ConnectionLifecycleCallback =
        object : ConnectionLifecycleCallback() {
            override fun onConnectionInitiated(endpointId: String, connectionInfo: ConnectionInfo) {
                // Automatically accept the connection on both sides.
                Nearby.getConnectionsClient(application)
                    .acceptConnection(endpointId, ReceiveBytesPayloadListener(onClick))
            }

            override fun onConnectionResult(endpointId: String, result: ConnectionResolution) {
                when (result.status.statusCode) {
                    ConnectionsStatusCodes.STATUS_OK -> {
                        Log.d(TAG, "STATUS OK")
                        if (sender) {
                            val chatroomPayload =
                                Payload.fromBytes(chatroomId.toByteArray(StandardCharsets.UTF_8))
                            Nearby.getConnectionsClient(context)
                                .sendPayload(endpointId, chatroomPayload);
                        }

                    }
                    ConnectionsStatusCodes.STATUS_CONNECTION_REJECTED -> {
                        Log.d(TAG, "STATUS rejected")
                    }
                    ConnectionsStatusCodes.STATUS_ERROR -> {
                        Log.d(TAG, "STATUS error")
                    }
                    else -> {
                        Log.d(TAG, "STATUS else ")
                    }
                }
            }

            override fun onDisconnected(endpointId: String) {
                // We've been disconnected from this endpoint. No more data can be
                // sent or received.
                Log.d(TAG, "disconnected")
            }
        }

    internal class ReceiveBytesPayloadListener(private val onClick: (String) -> Unit) : PayloadCallback() {
        var chatroomId: String = ""
        override fun onPayloadReceived(endpointId: String, payload: Payload) {
            val receivedBytes = payload.asBytes()
            Log.d(TAG, "Payload!! $receivedBytes")
            if (payload.type == Payload.Type.BYTES) {
                val chatroomId =
                    String(payload.asBytes()!!, StandardCharsets.UTF_8)
                Log.d(TAG, "Payload!! $chatroomId")
                onClick(chatroomId)
            }
        }

        override fun onPayloadTransferUpdate(endpointId: String, update: PayloadTransferUpdate) {
            // Bytes payloads are sent as a single chunk, so you'll receive a SUCCESS update immediately
            // after the call to onPayloadReceived().
        }
    }

}