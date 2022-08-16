package com.luanvv.aws.rdssecrets;


import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import java.util.*;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;
import com.amazonaws.services.secretsmanager.model.InvalidRequestException;
import com.amazonaws.services.secretsmanager.model.InvalidParameterException;


@RestController
class UserController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

   
    
  @GetMapping("/users")
  List<User> all() {
    
    List<User> results=jdbcTemplate.query("SELECT * from user", BeanPropertyRowMapper.newInstance(User.class));
    Iterator iter=results.iterator();
    while(iter.hasNext()){
      System.out.println(iter.next());
    }
    
    
    int result = jdbcTemplate.queryForObject(
    "SELECT COUNT(*) FROM user", Integer.class);
    System.out.println("Value inside");
    System.out.println(result);
    String region="us-east-1";
    String secretName="RDSSecretsDemo";
    String endpoint =("secretsmanager." + region + ".amazonaws.com");
        AwsClientBuilder.EndpointConfiguration config = new AwsClientBuilder.EndpointConfiguration(endpoint, region);
        AWSSecretsManagerClientBuilder clientBuilder = AWSSecretsManagerClientBuilder.standard();
        clientBuilder.setEndpointConfiguration(config);
        AWSSecretsManager client = clientBuilder.build();
        String secret = null;
        ByteBuffer binarySecretData;
        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest().withSecretId(secretName);
        GetSecretValueResult getSecretValueResponse = null;
        try {
            getSecretValueResponse = client.getSecretValue(getSecretValueRequest);

        } catch(ResourceNotFoundException e) {
            System.out.println("The requested secret " + secretName + " was not found");
        } catch (InvalidRequestException e) {
            System.out.println("The request was invalid due to: " + e.getMessage());
        } catch (InvalidParameterException e) {
            System.out.println("The request had invalid params: " + e.getMessage());
        }

        if(getSecretValueResponse == null) {
            System.out.println("The request had invalid params: ");
        }

        // Decrypted secret using the associated KMS CMK
        // Depending on whether the secret was a string or binary, one of these fields will be populated
        if(getSecretValueResponse.getSecretString() != null) {
            secret = getSecretValueResponse.getSecretString();
        }
        else {
            binarySecretData = getSecretValueResponse.getSecretBinary();
        }

        // Your code goes here.
        System.out.println("Secret Name : " + secretName + "\t Secret Value : " + secret + "\n");

    
    return results;
  }
  
  
}
