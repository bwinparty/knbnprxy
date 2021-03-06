/*
 * knbnprxy - Kanban Proxy
 *
 * Copyright 2014   bwin.party digital entertainment plc
 *                  http://www.bwinparty.com
 * Developer: Lukas Prettenthaler
 */
package com.bwinparty.kanban.proxy.service;

import com.bwinparty.kanban.proxy.model.Epic;
import com.bwinparty.kanban.proxy.model.Backlog;
import com.bwinparty.kanban.proxy.model.Program;
import com.bwinparty.kanban.proxy.model.Team;
import com.bwinparty.kanban.proxy.model.Member;
import com.bwinparty.kanban.proxy.model.V1Request;
import com.bwinparty.kanban.proxy.util.DateAdapter;
import com.google.api.client.auth.oauth2.*;
import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by zyclonite on 12/03/14.
 */
public class V1Client {
    private static V1Client ourInstance;
    private final AppConfig config = AppConfig.getInstance();
    private static final Logger LOG = LoggerFactory.getLogger(V1Client.class);
    private static final List<String> SCOPES = Arrays.asList("query-api-1.0");
    private static final String USER_ID = "self";
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final JsonFactory JSON_FACTORY = new GsonFactory();
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(Date.class, new DateAdapter()).create();
    private HttpRequestFactory requestFactory;

    static {
        ourInstance = new V1Client();
    }

    public static V1Client getInstance() {
        return ourInstance;
    }

    private V1Client() {
        try {
            AuthorizationCodeFlow codeFlow = initializeAuthorizationFlow();
            final Credential v1credential = obtainCredentials(codeFlow);
            requestFactory = HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
                        public void initialize(HttpRequest request)
                                throws IOException {
                            v1credential.initialize(request);
                        }
                    });
        } catch (IOException ioe) {
            LOG.error("Could not create RequestFactory for HttpClient "+ioe.getMessage());
        }
    }

    private AuthorizationCodeFlow initializeAuthorizationFlow() throws IOException {
        FileDataStoreFactory fileDataStoreFactory = new FileDataStoreFactory(new File(System.getProperty("user.home") + "/.knbnprxy"));
        DataStore<StoredCredential> datastore = fileDataStoreFactory.getDataStore(config.getString("versionone.client-id"));
        AuthorizationCodeFlow codeFlow = new AuthorizationCodeFlow.Builder(
                BearerToken.authorizationHeaderAccessMethod(),
                HTTP_TRANSPORT,
                JSON_FACTORY,
                new GenericUrl(config.getString("versionone.token-uri")),
                new ClientParametersAuthentication(config.getString("versionone.client-id"), config.getString("versionone.client-secret")),
                config.getString("versionone.client-id"),
                config.getString("versionone.auth-uri"))
                .setCredentialDataStore(datastore)
                .setScopes(SCOPES).build();
        return codeFlow;
    }

    private Credential obtainCredentials(AuthorizationCodeFlow codeFlow) throws IOException {
        Credential credential = null;
        credential = codeFlow.loadCredential(USER_ID);
        if (null == credential) {
            requestAuthorization(codeFlow);
            String code = receiveAuthorizationCode();
            credential = requestAccessToken(codeFlow, code);
        }
        return credential;
    }

    private void requestAuthorization(AuthorizationCodeFlow codeFlow) {
        AuthorizationCodeRequestUrl codeUrl = codeFlow.newAuthorizationUrl()
                .setRedirectUri(config.getList("versionone.redirect-uris.uri").get(0).toString())
                .setResponseTypes(Arrays.asList("code"));
        String url = codeUrl.build();
        LOG.warn("No stored credentials were found. Manual action required!");
        LOG.info("goto: " + url);
    }

    private String receiveAuthorizationCode() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String code = null;
        System.out.print("enter code: ");
        code = br.readLine();
        return code;
    }

    private Credential requestAccessToken(AuthorizationCodeFlow codeFlow, String code) throws IOException {
        TokenRequest tokenRequest = codeFlow.newTokenRequest(code)
                .setRedirectUri(config.getList("versionone.redirect-uris.uri").get(0).toString())
                .setScopes(SCOPES);
        TokenResponse tokenResponse = tokenRequest.execute();
        LOG.info("storing request token...");
        return codeFlow.createAndStoreCredential(tokenResponse, USER_ID);
    }

    public List<Epic> getEpics() throws IOException {
        GenericUrl v1url = new GenericUrl(config.getString("versionone.query-uri"));

        V1Request request = new V1Request();
        request.setFrom("Epic");
        List<String> select = new ArrayList<>();
        select.add("Name");
        select.add("Number");
        select.add("Description");
        select.add("Status.Name");
        select.add("Swag");
        select.add("Attachments");
        select.add("Attachments.Name");
        select.add("EstimatedDone");
        select.add("DetailEstimate");
        select.add("AllocatedDetailEstimate");
        select.add("EstimatedAllocatedDone");
        select.add("PlannedEnd");
        select.add("PlannedStart");
        select.add("PlannedStart");
        select.add("Scope.Name");
        select.add("Scope.ID");
        select.add("Custom_Health.Name");
        select.add("Custom_HealthComment");
        select.add("Custom_PlannedLaunchDate");
        select.add("Custom_Patentprotection2.Name");
        select.add("Custom_Capitalizable2.Name");
        select.add("CreatedBy.Name");
        select.add("ChangedBy.Name");
        select.add("CreateDateUTC");
        select.add("ChangeDateUTC");
        select.add("Category.Name");
        select.add("Risk");
        select.add("Value");
        select.add("RequestedBy");
        select.add("Super.Number");
        select.add("Super.Name");
        select.add("StrategicThemes.Name");
        select.add("Custom_InitativeOwner");
        select.add("Custom_LaunchDateWhy");
        select.add("Custom_ElevatorPitch");
        select.add("Custom_Corporateinterest2.Name");
        select.add("IsClosed");
        select.add("IsCompleted");

        request.setSelect(select);
        //Map<String, String> fields = new HashMap<>();
        //fields.put("Custom_Corporateinterest2.Name", "Yes");
        //request.setWhere(fields);

        final HttpContent content = new ByteArrayContent("application/json", GSON.toJson(request).getBytes());
        HttpRequest v1request = requestFactory.buildPostRequest(v1url, content);

        HttpResponse v1response = v1request.execute();
        String json = v1response.parseAsString();
        //LOG.info(json);
        Type listType = new TypeToken<ArrayList<ArrayList<Epic>>>() {
        }.getType();
        List<List<Epic>> epicsList = GSON.fromJson(json, listType);
        return epicsList.get(0);
    }

    public List<Backlog> getBacklogs() throws IOException {
        GenericUrl v1url = new GenericUrl(config.getString("versionone.query-uri"));

        V1Request request = new V1Request();
        request.setFrom("Scope");
        List<String> select = new ArrayList<>();
        select.add("ID");
        select.add("Name");
        select.add("Owner.Name");
        select.add("Parent.Name");
        select.add("BeginDate");

        request.setSelect(select);

        final HttpContent content = new ByteArrayContent("application/json", GSON.toJson(request).getBytes());
        HttpRequest v1request = requestFactory.buildPostRequest(v1url, content);

        HttpResponse v1response = v1request.execute();
        String json = v1response.parseAsString();
        //LOG.info(json);
        Type listType = new TypeToken<ArrayList<ArrayList<Backlog>>>() {
        }.getType();
        List<List<Backlog>> backlogsList = GSON.fromJson(json, listType);
        return backlogsList.get(0);
    }

    public List<Program> getPrograms() throws IOException {
        GenericUrl v1url = new GenericUrl(config.getString("versionone.query-uri"));

        V1Request request = new V1Request();
        request.setFrom("ScopeLabel");
        List<String> select = new ArrayList<>();
        select.add("ID");
        select.add("Name");
        select.add("Description");
        select.add("Scopes.Name");

        request.setSelect(select);


        LOG.info("***getPrograms()");

        final HttpContent content = new ByteArrayContent("application/json", GSON.toJson(request).getBytes());
        HttpRequest v1request = requestFactory.buildPostRequest(v1url, content);

        HttpResponse v1response = v1request.execute();
        String json = v1response.parseAsString();
        LOG.info(json);
        Type listType = new TypeToken<ArrayList<ArrayList<Program>>>() {
        }.getType();
        List<List<Program>> programsList = GSON.fromJson(json, listType);
        return programsList.get(0);
    }


    public List<Team> getTeams() throws IOException {
        GenericUrl v1url = new GenericUrl(config.getString("versionone.query-uri"));

        V1Request request = new V1Request();
        request.setFrom("TeamRoom");
        List<String> select = new ArrayList<>();
        select.add("ID");
        select.add("Name");
        select.add("Scope.Name");
        select.add("ScopeLabel.Name");
        select.add("Schedule.Name");
        select.add("Participants");


        request.setSelect(select);


        final HttpContent content = new ByteArrayContent("application/json", GSON.toJson(request).getBytes());
        HttpRequest v1request = requestFactory.buildPostRequest(v1url, content);

        HttpResponse v1response = v1request.execute();
        String json = v1response.parseAsString();
        LOG.info(json);
        Type listType = new TypeToken<ArrayList<ArrayList<Team>>>() {
        }.getType();
        List<List<Team>> teamsList = GSON.fromJson(json, listType);
        return teamsList.get(0);
    }


    public List<Member> getMembers() throws IOException {
        GenericUrl v1url = new GenericUrl(config.getString("versionone.query-uri"));

        V1Request request = new V1Request();
        request.setFrom("Member");
        List<String> select = new ArrayList<>();
        select.add("ID");
        select.add("Name");
        select.add("Email");
        select.add("Custom_EmployeeID");
        select.add("IsLoginDisabled");
        select.add("ParticipatesIn");



        Map<String, String> fields = new HashMap<>();
        fields.put("IsLoginDisabled", "false");
        request.setWhere(fields);

        request.setSelect(select);

        final HttpContent content = new ByteArrayContent("application/json", GSON.toJson(request).getBytes());
        HttpRequest v1request = requestFactory.buildPostRequest(v1url, content);

        HttpResponse v1response = v1request.execute();
        String json = v1response.parseAsString();
        LOG.info(json);
        Type listType = new TypeToken<ArrayList<ArrayList<Member>>>() {
        }.getType();
        List<List<Member>> membersList = GSON.fromJson(json, listType);
        return membersList.get(0);
    }

    public List<Object> getEpicProgress() throws IOException {
        GenericUrl v1url = new GenericUrl(config.getString("versionone.query-uri"));

        V1Request request = new V1Request();
        request.setFrom("Epic");
        List<String> select = new ArrayList<>();
        select.add("ID");
        select.add("Number");
        select.add("SubsAndDown:PrimaryWorkitem[AssetState='Closed'].Estimate.@Sum");
        select.add("SubsAndDown:PrimaryWorkitem[AssetState!='Dead'].Estimate.@Sum");



        //Map<String, String> fields = new HashMap<>();
        //fields.put("Number", "E-09822");
        //request.setWhere(fields);

        request.setSelect(select);

        final HttpContent content = new ByteArrayContent("application/json", GSON.toJson(request).getBytes());
        HttpRequest v1request = requestFactory.buildPostRequest(v1url, content);

        HttpResponse v1response = v1request.execute();
        String json = v1response.parseAsString();
        LOG.info(json);
        Type listType = new TypeToken<ArrayList<ArrayList<Object>>>() {
        }.getType();
        List<List<Object>> progress = GSON.fromJson(json, listType);
        return progress.get(0);
    }


}
