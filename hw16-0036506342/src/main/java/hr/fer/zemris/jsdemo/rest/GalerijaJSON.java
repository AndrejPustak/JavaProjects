package hr.fer.zemris.jsdemo.rest;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;

import hr.fer.zemris.jsdemo.servlets.Picture;
import hr.fer.zemris.jsdemo.servlets.PictureDB;


/**
 * This class represents a rest root class for showing a gallery of images using javascrpit.
 * @author Andrej
 *
 */
@Path("/rest")
public class GalerijaJSON {
	
	@Context
	private ServletContext context;
	
	/**
	 * This method is used to get all tags of the gallery and return them in a 
	 * json format.
	 * @return respons in a json format
	 */
	@Path("/tags")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTags() {
		
		JSONObject result = new JSONObject();
		
		List<String> tags = PictureDB.getDB().getTags();
		
		JSONArray array = new JSONArray();
		tags.stream().forEach(s->array.put(s));
		
		result.put("tags", array);
		
		return Response.status(Status.OK).entity(result.toString()).build();
	}
	
	/**
	 * THis method is used to get the names of all the images with the specified tag
	 * and returns them in json format
	 * @param tag tag of the images you want to get the name of 
	 * @return response in json format
	 */
	@Path("/pictureNames/{tag}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNames(@PathParam("tag") String tag) {
		
		JSONObject result = new JSONObject();
		
		List<Picture> pictures = PictureDB.getDB().getPicturesWtihTag(tag);
		JSONArray array = new JSONArray();
		
		pictures.stream().forEach(p->array.put(p.getName()));
		result.put("names", array);
		
		return Response.status(Status.OK).entity(result.toString()).build();
	}
	
	/**
	 * This method is used to get the description and list of tags with the given name.
	 * @param name name of the image you want to get the information for
	 * @return response in json format
	 */
	@Path("/pictureDesc/{name}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPictureDesc(@PathParam("name") String name) {
		
		JSONObject result = new JSONObject();
		
		Picture picture = PictureDB.getDB().getPicture(name);
		result.put("name", picture.getName());
		result.put("desc", picture.getDescription());
		
		List<String> tags = picture.getTags();
		
		JSONArray array = new JSONArray();
		tags.stream().forEach(s->array.put(s));
		
		result.put("tags", array);
		
		return Response.status(Status.OK).entity(result.toString()).build();
	}
	
	/**
	 * This method is used to get a mini version (150x150) of an image with the given name.
	 * The mini image for an image is created when first needed and is saved in /WEB-INF/thumbnails folder
	 * @param name name of the image
	 * @return response - image in png format
	 */
	@Path("/miniImage/{name}")
	@GET
	@Produces("image/png")
	public Response getMiniImage(@PathParam("name") String name) {
		
		java.nio.file.Path imgPath = Paths.get(context.getRealPath("/WEB-INF/thumbnails/" + name));
		
		if(!Files.exists(imgPath)) {
			generateMiniImage(name);
		}
		
		BufferedImage img = null;
		try {
			img = ImageIO.read(Files.newInputStream(imgPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ImageIO.write(img, "png", bos);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] bytes = bos.toByteArray();
		
		return Response.ok(bytes).build();
	}
	
	/**
	 * This method is used to get the full sized image in png format.
	 * @param name name of the image
	 * @return response
	 */
	@Path("/bigImage/{name}")
	@GET
	@Produces("image/png")
	public Response getBigImage(@PathParam("name") String name) {
		
		java.nio.file.Path imgPath = Paths.get(context.getRealPath("/WEB-INF/slike/" + name));
		
		BufferedImage img = null;
		try {
			img = ImageIO.read(Files.newInputStream(imgPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ImageIO.write(img, "png", bos);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] bytes = bos.toByteArray();
		
		return Response.ok(bytes).build();
	}

	/**
	 * This method is used to create a mini version of an image.
	 * It is saved in /WEB-INF/thumbnails folder
	 * The size of the image is 150x150
	 * @param name name of the image
	 */
	private void generateMiniImage(String name) {
		
		java.nio.file.Path bigImage = Paths.get(context.getRealPath("/WEB-INF/slike/" + name));
		BufferedImage img = null;
		try {
			img = ImageIO.read(Files.newInputStream(bigImage));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BufferedImage newImg = new BufferedImage(150, 150, img.getType());
		Graphics2D g2d = newImg.createGraphics();
		
		g2d.drawImage(img, 0, 0, 150, 150, null);
		g2d.dispose();
		
		java.nio.file.Path smallImage = Paths.get(context.getRealPath("/WEB-INF/thumbnails/" + name));

		if(!Files.exists(Paths.get(context.getRealPath("/WEB-INF/thumbnails")))){
			try {
				Files.createDirectory(Paths.get(context.getRealPath("/WEB-INF/thumbnails")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			ImageIO.write(newImg, "jpg", Files.newOutputStream(smallImage, StandardOpenOption.CREATE));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
