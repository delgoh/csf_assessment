package vttp2023.batch3.csf.assessment.cnserver.controllers;

import java.io.IOException;
import java.util.List;

import javax.print.attribute.standard.MultipleDocumentHandling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.json.Json;
import vttp2023.batch3.csf.assessment.cnserver.models.TagCount;
import vttp2023.batch3.csf.assessment.cnserver.services.NewsService;

@RestController
@CrossOrigin
@RequestMapping(path = "/api")
public class NewsController {

	@Autowired
	private NewsService newsService;

	// TODO: Task 1
	@PostMapping(
		path = "/news",
		consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<String> savePost(
		@RequestPart("title") String title,
		@RequestPart("photo") MultipartFile photo,
		@RequestPart("description") String description, @RequestPart("tags") String tagString
	) throws IOException {
		// System.out.println(title);
		// System.out.println(description);
		// return ResponseEntity.ok(Json.createObjectBuilder()
		// 	.add("isWorking", true)
		// 	.build()
		// 	.toString());
		String newsId = newsService.postNews(title, photo, description, tagString);
		return ResponseEntity.ok(Json.createObjectBuilder()
			.add("newsId", newsId)
			.build()
			.toString());
		// try {
		// 	String contentType = formData.getContentType();
		// 	InputStream is = audio.getInputStream();
		// 	String id = audioRepo.upload(contentType, is);
		// 	JsonObject resp = Json.createObjectBuilder()
		// 		.add("id", id)
		// 		.build();
		// 	return ResponseEntity.ok(resp.toString());

		// } catch (Exception ex) {
		// 	JsonObject resp = Json.createObjectBuilder()
		// 		.add("error", ex.getMessage())
		// 		.build();
		// 	return ResponseEntity.status(500)
		// 		.body(resp.toString());
		// }
	}


	// TODO: Task 2
	@GetMapping(path = "/tag")
	public ResponseEntity<List<TagCount>> getTagCounts(@RequestParam("tag-count") Integer tagCount) {
		return ResponseEntity.ok(newsService.getTags());
	}


	// TODO: Task 3

}
