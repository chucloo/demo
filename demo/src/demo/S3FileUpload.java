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
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.*;
import com.amazonaws.services.s3.AmazonS3Client.*;
import com.amazonaws.services.s3.model.*;

import java.io.File;



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
		
		//AWSCredentials credentials = new BasicAWSCredentials("AKIAVG57AUNSUMHDRCQ3", "N9ToSs3TRkZI1BTWoU8CBD0Yqwt53YcbDKOxUXoL");
		//AmazonS3 s3client = new AmazonS3Client(credentials);
		
		//create S3 client Test
		//https://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/com/amazonaws/services/s3/AmazonS3Client.html
		
		BasicAWSCredentials creds = new BasicAWSCredentials("AKIAVG57AUNSUMHDRCQ3", "N9ToSs3TRkZI1BTWoU8CBD0Yqwt53YcbDKOxUXoL"); 
		//AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(creds)).build();
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(creds)).withRegion("ap-southeast-1").build();
		//s3Client.setRegion(Region.AP_Singapore);
		
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
        request.setAttribute("message", "Upload has been done successfully!");
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
