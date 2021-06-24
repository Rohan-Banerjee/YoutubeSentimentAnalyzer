package rohan.projects.ContentModeration;


import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.CommentThread;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import com.google.api.services.youtube.model.PlaylistItemListResponse;

import dao.YoutubeDao;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class YoutubeClass {
   
	   
    private static final String DEVELOPER_KEY = "AIzaSyAH_cutc7KGBsUH2DjBvD3GWe4Vw-v1418";
    private static final String APPLICATION_NAME = "Sample Project";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();


    /**
     * Build and return an authorized API client service.
     *
     * @return an authorized API client service
     * @throws GeneralSecurityException, IOException
     */
    public static YouTube getService() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new YouTube.Builder(httpTransport, JSON_FACTORY, null)
            .setApplicationName(APPLICATION_NAME)
            .build();
    }

    /**
     * Call function to create API service object. Define and
     * execute API request. Print API response.
     *
     * @throws GeneralSecurityException, IOException, GoogleJsonResponseException
     */
    public static ArrayList<YoutubeDao> fetchCommentsUsingVid(String vid,int number)
        throws GeneralSecurityException, IOException, GoogleJsonResponseException {
       
    	try
    	{
    	YouTube youtubeService = getService();
        // Define and execute the API request
        YouTube.CommentThreads.List request = youtubeService.commentThreads()
            .list("snippet,replies");
        CommentThreadListResponse response = request.setKey(DEVELOPER_KEY)
            .setVideoId(vid).setMaxResults(100L)
            .execute();
       ArrayList<YoutubeDao> ar = new ArrayList<>(600);
        int i =0 ;   
       
     while(i<number)
     {
    	 YoutubeDao yd = new YoutubeDao();
        CommentThread comment = response.getItems().get(i);
        yd.setUser(comment.getSnippet().getTopLevelComment().getSnippet().getAuthorDisplayName());
        yd.setComment(comment.getSnippet().getTopLevelComment().getSnippet().getTextDisplay());
       ar.add(yd);
        i++;
     }
     return ar;
    	}
    	catch(Exception e)
    	{
    		return null;
    	}
     
     }
    
   
    public static String fetchUploadIdUsingChannelId(String chId,int number)
            throws GeneralSecurityException, IOException, GoogleJsonResponseException {
           
    	 YouTube youtubeService = getService();
         // Define and execute the API request
         YouTube.Channels.List request = youtubeService.channels()
             .list("contentDetails,statistics");
         ChannelListResponse response = request.setKey(DEVELOPER_KEY)
             .setId(chId)
             .execute();
         String upload = response.getItems().get(0).getContentDetails().getRelatedPlaylists().getUploads().toString();
         return upload;
         }
        
    public static ArrayList<String> fetchVidsUsingUploadID(String uploadID)
            throws GeneralSecurityException, IOException, GoogleJsonResponseException {
           ArrayList<String> ar = new ArrayList<>();
    	  YouTube youtubeService = getService();
          // Define and execute the API request
          YouTube.PlaylistItems.List request = youtubeService.playlistItems()
              .list("snippet");
          PlaylistItemListResponse response = request.setKey(DEVELOPER_KEY)
              .setPlaylistId(uploadID)
              .execute();
          
          String nextPageToken = response.getNextPageToken();
          int i = 0;
          while(i<5)
          {
        	  
        	  ar.add(response.getItems().get(i).getSnippet().getTitle().toString());
        	  ar.add(response.getItems().get(i).getSnippet().getResourceId().getVideoId().toString());
        	 i++;
          }
          
          return ar;
         }
    
    public String getChannelID(String url) throws IOException
    {
    	String cid="";
    	Document page = Jsoup.connect(url).userAgent("Rohan").get();
    	Elements info = page.select("meta[itemprop='channelId']");
    	cid = info.get(0).attributes().get("content");
    	return cid;
    }

public static void main(String args[]) throws GoogleJsonResponseException, GeneralSecurityException, IOException
{
	YoutubeClass ae = new YoutubeClass();
	System.out.println(ae.getChannelID("https://www.youtube.com/c/ThomasKruegerPianoman"));
}







}