package org.telegram.ui.model;

import android.graphics.Bitmap;

public class Flower {
	
	//private int productId;
	private String name;
	private String linkvideo;
    private String link;
	private String photo;
	private Bitmap bitmap;
	
	/*public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
    */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLinkvideo() {
		return linkvideo;
	}
	public void setLinkvideo(String linkvideo) {
		this.linkvideo = linkvideo;
	}
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		//this.photo = photo;


        //StringTokenizer st = new StringTokenizer(photo, "\"");
        //String community = st.nextToken();

        this.photo= photo; //st.nextToken();
	}
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
}
