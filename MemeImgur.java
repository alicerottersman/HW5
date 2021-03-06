import java.awt.Image;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import java.util.ArrayList;
import javax.imageio.*;
import org.jsoup.Jsoup;


/**
 * This Meme object will handle all memes from Imgur!
 * @author Cecilia, Alice, Lowell  
 * @version 4/28 1:00
 */

public class MemeImgur extends MemeProgramMeme implements Comparable<MemeProgramMeme> {

	private final static String website = "http://www.imgur.com/";
	private String imageURL;
	private ArrayList<String> imageURLs = new ArrayList<String>();
	private ArrayList<String> jpgs = new ArrayList<String>();
	private ArrayList<String> imageNames = new ArrayList<String>();
	private Elements iURLs;
	
	public MemeImgur() throws Exception {
    	/**
		 * Creates a Meme object, as far as the pieces of it which will be displayed.
		 * @param none
		 */

    	super(website);
    	imageURL = null;	

		//parses main page and gets the URLs of images on the front page
    	Elements images = rootElements.select("div#content")
    	.select("div#imagelist").select("div.posts").select("a[href]"); 		

		//System.out.println("these are some " + images); //debugging

		for (int i=0;i<images.size();i++){
			imageURLs.add(images.get(i).attr("abs:href"));
		}	

		for (int j=0;j<imageURLs.size();j++) { //goes into the arrayList of image URLs and fetches their content
			try{
				Document doc = Jsoup.connect(imageURLs.get(j)).get();
				Elements indivImages = doc.select("div#content").select("div.panel");
				imageNames.add(indivImages.select("h2").text());
				Elements jpgUrl = indivImages.select("div#image").select("img[src$=.jpg");
				for (int s=0;s<jpgUrl.size();s++) {
					String jp = jpgUrl.get(s).absUrl("src");
					if (!jp.equals(null)) {
						jpgs.add(jp);
					}
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		System.out.println("these are names " + imageNames +"\n");
		System.out.println("these are jpg addresses ... " + jpgs +"\n");	
	}

	@Override
	public String findImage() {
		
		int count=0;
		while (imageURL == null) {	
			//TODO FIND the clean, complete link to the meme image in a procedural way.
			imageURL=jpgs.get(count);
		}
		count++;
		return imageURL;	
	}
	
	@Override
	public boolean findImageName() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean cleanImageName() {
		// TODO Auto-generated method stub
		int count=0;
		super.imageName = imageNames.get(count);
		count++;
		return true;
	}

	@Override
	public boolean findUpvote() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean cleanUpvote() {
		// TODO Auto-generated method stub
		super.upvote = 7;
		return true;
	}

}
