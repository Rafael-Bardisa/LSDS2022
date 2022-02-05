package upf.edu.uploader;

import com.amazonaws.AbortedException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.util.List;
import java.io.File;

public class S3Uploader implements Uploader {
    private String BucketName;
    private String Prefix;
    private AmazonS3 Client;

    public void S3Uploader(String bn, String pre, String cre){
        this.BucketName = bn;
        this.Prefix = pre;
        this.Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new ProfileCredentialsProvider(cre))
                .build();
        ;
    }
    public boolean bucketExists(String bN){
        return this.Client.doesBucketExistV2(bN);
    }

    @Override
    public void upload(List<String> files) {

        if (!bucketExists(this.BucketName)) {
            System.out.println("This bucket doesn't exist");
        } else {
            for (String file : files) {
                String dest_path = "s3://"+this.BucketName+"/"+this.Prefix+"/"+file;
                try {
                    Client.putObject(this.BucketName, file, new File(dest_path));
                } catch (AmazonServiceException e){
                    System.err.println(e.getErrorMessage());
                    System.exit(1);
                }
            }
        }
    }
}