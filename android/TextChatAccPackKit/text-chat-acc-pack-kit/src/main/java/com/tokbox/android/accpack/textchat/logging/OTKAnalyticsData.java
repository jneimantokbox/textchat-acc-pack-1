package com.tokbox.android.accpack.textchat.logging;


import android.os.Build;
import android.util.Log;

import java.util.UUID;

public class OTKAnalyticsData {

    private static final String LOGTAG = OTKAnalyticsData.class.getSimpleName();

    private String logVersion; //optional
    private String guid; //optional

    private String sessionId; //required
    private String partnerId; //required
    private String connectionId; //required

    private String deviceModel; //optional
    private String systemVersion; //optional
    private String systemName; //optional

    private long clientSystemTime=0; //optional

    private String client; //optional

    private String action; //optional
    private String variation; //optional
    private final String clientVersion; //required
    private final String source; // required

    public void setAction(String action) {
        this.action = action;
    }

    public void setVariation(String variation) {
        this.variation = variation;
    }

    public String getLogVersion() {
        return logVersion;
    }

    public String getGuid() {
        return guid;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public String getConnectionId() {
        return connectionId;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public long getClientSystemTime() {
        return clientSystemTime;
    }

    public String getClient() {
        return client;
    }

    public String getAction() {
        return action;
    }

    public String getVariation() {
        return variation;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public String getSystemVersion() { return systemVersion; }

    public String getSystemName() { return systemName; }

    public String getSource() { return source; }


    public void setClientSystemTime(long clientSystemTime) {
        this.clientSystemTime = clientSystemTime;
    }


    public OTKAnalyticsData(Builder builder){

        this.logVersion = builder.logVersion;
        this.guid = builder.guid;
        this.sessionId = builder.sessionId;
        this.partnerId = builder.partnerId;
        this.connectionId = builder.connectionId;
        this.clientSystemTime = builder.clientSystemTime;
        this.action = builder.action;
        this.variation = builder.variation;
        this.source = builder.source;
        this.deviceModel = builder.deviceModel;
        this.clientVersion = builder.clientVersion;
        this.client = builder.client;
        this.systemVersion = builder.systemVersion;
        this.systemName = builder.systemName;

        checkData();
    }

    private void checkData(){
        String uuidPattern = "[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}";

        if ( this.logVersion == null || this.logVersion.isEmpty() ) {
            this.logVersion = "2";
        }
        if ( this.guid == null || !this.guid.matches(uuidPattern) ) {
            this.guid = UUID.randomUUID().toString();
        }
        if ( this.deviceModel == null || this.deviceModel.isEmpty() ){
            this.deviceModel = String.format("mfr=%s,model=%s,abi=%s", Build.MANUFACTURER, Build.MODEL, Build.CPU_ABI);
        }
        if ( this.client == null || this.client.isEmpty()){
            client = "native";
        }
        if ( this.clientSystemTime ==  0 ){
            this.clientSystemTime = System.currentTimeMillis();
        }
        if ( this.systemName == null || this.systemName.isEmpty() ){
            this.systemName = "Android OS";
        }

        if ( this.systemVersion == null || this.systemVersion.isEmpty() ){
            this.systemVersion = String.valueOf(Build.VERSION.SDK_INT);
        }
    }

    public static class Builder {
        private String logVersion; //optional
        private String guid; //optional

        private final String sessionId; //required
        private final String partnerId; //required
        private final String connectionId; //required

        private String deviceModel; //optional
        private String client; //optional
        private String systemVersion; //optional
        private String systemName; //optional

        private long clientSystemTime=0; //optional

        private String action; //optional
        private String variation; //optional
        private final String source; // required
        private final String clientVersion; //required

        public Builder(String sessionId, String partnerId, String connectionId, String clientVersion, String source) {

            this.sessionId = sessionId;
            this.partnerId = partnerId;
            this.connectionId = connectionId;
            this.clientVersion = clientVersion;
            this.source = source;
        }

        public Builder logVersion(String logVersion) {
            this.logVersion = logVersion;
            return this;
        }

        public Builder guid(String guid) {
            this.guid = guid;
            return this;
        }

        public Builder deviceModel(String deviceModel) {
            this.deviceModel = deviceModel;
            return this;
        }

        public Builder clientSystemTime(long clientSystemTime){
            this.clientSystemTime = clientSystemTime;
            return this;
        }

        public Builder client(String client){
            this.client = client;
            return this;
        }

        public Builder action(String action) {
            this.action = action;
            return this;
        }

        public Builder variation(String variation) {
            this.variation = variation;
            return this;
        }

        public Builder systemVersion(String version) {
            this.systemVersion = version;
            return this;
        }

        public Builder systemName(String name) {
            this.systemName = name;
            return this;
        }

        public OTKAnalyticsData build() {
            OTKAnalyticsData data = new OTKAnalyticsData(this);

            data = validateDataObject(data);

            return data;
        }

        private OTKAnalyticsData validateDataObject(OTKAnalyticsData data) {
            if (data.sessionId == null || data.sessionId.isEmpty()) {
                Log.i(LOGTAG, "The sessionId field cannot be null in the log entry");
                return null;
            }
            if (data.partnerId == null || data.partnerId.isEmpty()) {
                Log.i(LOGTAG, "The partnerId field cannot be null in the log entry");
                return null;
            }
            if (data.connectionId == null || data.connectionId.isEmpty()) {
                Log.i(LOGTAG, "The connectionId field cannot be null in the log entry");
                return null;
            }
            if (data.clientVersion == null || data.clientVersion.isEmpty()) {
                Log.i(LOGTAG, "The clientVersion field cannot be null in the log entry");
                return null;
            }
            if (data.source == null || data.source.isEmpty()) {
                Log.i(LOGTAG, "The source field cannot be null in the log entry");
                return null;
            }
            return data;
        }
    }
}
