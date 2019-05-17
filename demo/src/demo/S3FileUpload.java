package demo;



import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;

import com.amazonaws.auth.*;
import com.amazonaws.services.s3.*;
import com.amazonaws.services.s3.AmazonS3Client.*;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.model.ObjectListing;

import com.amazonaws.services.sns.*;
import com.amazonaws.services.sns.AmazonSNSClient.*;
import com.amazonaws.services.sns.model.*;




import java.io.File;
import java.util.*;




/**
 * Servlet implementation class S3FileUpload cc
 */
@WebServlet("/S3FileUpload")
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
maxFileSize=1024*1024*10,      // 10MB
maxRequestSize=1024*1024*50)   // 50MB

public class S3FileUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public S3FileUpload() {
        super();
        // TODO Auto-generated constructor stubxxx
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub xx
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * Handles file upload to S3
	 */
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		
		//create S3 client Test
		//https://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/com/amazonaws/services/s3/AmazonS3Client.html
		
		/**
		// Create user with hard coded ID - Password
		BasicAWSCredentials creds = new BasicAWSCredentials("xxx", "yyyy"); 
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(creds)).withRegion("ap-southeast-1").build();
		
		**/
		
		// Create user with environment ID/ Password
		// https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion("ap-southeast-1").build();
		AmazonSNS snsClient = AmazonSNSClientBuilder.standard().withRegion("ap-southeast-1").build();
	
		
		for (Part part : request.getParts()) {
            String fileName = extractFileName(part);
            //refines the fileName in case it is an absolute pat
            fileName = new File(fileName).getName();
            
            //part.write(savePath + File.separator + fileName);
			
			// create a PutObjectRequest passing the folder name suffixed by /
            ObjectMetadata s3ObjectMetadata = new ObjectMetadata();
            PutObjectRequest putObjectRequest = new PutObjectRequest("ufinityiam","NETS/" + fileName, part.getInputStream(),s3ObjectMetadata);
            s3Client.putObject(putObjectRequest);
        }
		
		//API 2: Get folder list
		
		String folderObject = "";

		ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
			    .withBucketName("ufinityiam")
			    .withPrefix("NETS/");
		ObjectListing objectListing;
		
		do {
	        objectListing = s3Client.listObjects(listObjectsRequest);
	        for (S3ObjectSummary objectSummary : 
	            objectListing.getObjectSummaries()) {
	        	folderObject=  folderObject + "<br>"  + objectSummary.getKey() + "  " + "(size = " + objectSummary.getSize() +  ")";
	        	System.out.println("....." + folderObject );
	        }
	        listObjectsRequest.setMarker(objectListing.getNextMarker());
	} while (objectListing.isTruncated());
		
		/**** if demo sms
		// Publish a message to an Amazon SNS topic.
		final String msg = "SNS - File Uploaded";
		final PublishRequest publishRequest = new PublishRequest("arn:aws:sns:ap-southeast-1:358493496165:LambdaBreach", msg);
		final PublishResult publishResponse = snsClient.publish(publishRequest);
		 // Print the MessageId of the message.
		System.out.println("MessageId: " + publishResponse.getMessageId());
		****/
		
		
        request.setAttribute("message", "Upload has been done successfully!");
        request.setAttribute("folderobject", folderObject);
        getServletContext().getRequestDispatcher("/message.jsp").forward(
                request, response);
		
		
	}

	 /**
     * Extracts file name from HTTP header content-disposition
     */
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
    }
	;
	
}
