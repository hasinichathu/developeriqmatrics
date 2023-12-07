package com.cloud.developerIQ.repository;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;


@Service
public class DynamoDBRepository {

    public List<Item> getAllItems(String tableName) {
        Region region = Region.EU_NORTH_1; // Change to your desired region

          // Replace "YOUR_ACCESS_KEY" and "YOUR_SECRET_KEY" with your actual AWS access key and secret key
        String accessKey = "AKIA36BQFLWIXA73PI7K";
        String secretKey = "QxtmR2xvmavvPYvpioW6UYi/Hl0X+s7sqMWoXCjm";

        // Create AwsBasicCredentials using your access key and secret key
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

        // Create a DynamoDbClient with the specified credentials and region
        DynamoDbClient dynamoDB = DynamoDbClient.builder()
                .region(region)
                .credentialsProvider(() -> credentials)
                .build();

        Table table = dynamoDB.getTable("DeveloperMetrics");
    
        List<Item> items = new ArrayList<>();
        ScanSpec scanSpec = new ScanSpec();
        try {
            ItemCollection<ScanOutcome> itemsResult = table.scan(scanSpec);
            Iterator<Item> iterator = itemsResult.iterator();
            while (iterator.hasNext()) {
                items.add(iterator.next());
            }
        } catch (Exception e) {
            // Handle the exception based on your application's requirements
            e.printStackTrace();
        }

        dynamoDB.close();
        return items;
    }
}

@DynamoDBTable(tableName = "DeveloperMetrics")  // Replace with your DynamoDB table name
class DeveloperMetrics {

    private String username;
    private int commits;
    private int issues;
    private int pullRequests;

    // Default constructor for DynamoDB mapper
    public DeveloperMetrics() {
    }

    public DeveloperMetrics(String username, int commits, int issues, int pullRequests) {
        this.username = username;
        this.commits = commits;
        this.issues = issues;
        this.pullRequests = pullRequests;
    }

    @DynamoDBHashKey(attributeName = "Username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @DynamoDBHashKey(attributeName = "Commits")
    public int getCommits() {
        return commits;
    }

    public void setCommits(int commits) {
        this.commits = commits;
    }

    @DynamoDBHashKey(attributeName = "Issues")
    public int getIssues() {
        return issues;
    }

    public void setIssues(int issues) {
        this.issues = issues;
    }

    @DynamoDBHashKey(attributeName = "PullRequests")
    public int getPullRequests() {
        return pullRequests;
    }

    public void setPullRequests(int pullRequests) {
        this.pullRequests = pullRequests;
    }
}


