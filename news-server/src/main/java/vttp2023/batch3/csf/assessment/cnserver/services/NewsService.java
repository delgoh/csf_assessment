package vttp2023.batch3.csf.assessment.cnserver.services;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import vttp2023.batch3.csf.assessment.cnserver.models.News;
import vttp2023.batch3.csf.assessment.cnserver.models.TagCount;
import vttp2023.batch3.csf.assessment.cnserver.repositories.ImageRepository;
import vttp2023.batch3.csf.assessment.cnserver.repositories.NewsRepository;

@Service
public class NewsService {

	@Autowired
	private ImageRepository imageRepository;

	@Autowired
	private NewsRepository newsRepository;
	
	// TODO: Task 1
	// Do not change the method name and the return type
	// You may add any number of parameters
	// Returns the news id
	public String postNews(String title, MultipartFile photo, String description, String tagString) throws IOException {
		String imageUrl = imageRepository.uploadImage(photo.getContentType(), photo.getInputStream());

		String newsId = newsRepository.insertNews(title, imageUrl, description, tagString);

		return newsId;
	}
	 
	// TODO: Task 2
	// Do not change the method name and the return type
	// You may add any number of parameters
	// Returns a list of tags and their associated count
	public List<TagCount> getTags(Integer minuteOption) {
		System.out.println("minuteOption: " + minuteOption);
		Long offsetTime = System.currentTimeMillis() - minuteOption * 60000; // cut-off time for recent posts
		System.out.println("offsetTime: " + offsetTime);
		System.out.println(">>>> size of array");
		System.out.println(newsRepository.getTagCounts(offsetTime).size());

		return newsRepository.getTagCounts(offsetTime)
			.stream()
			.map(doc -> new TagCount(doc.getString("tag"), doc.getInteger("count")))
			.toList();
	}

	// TODO: Task 3
	// Do not change the method name and the return type
	// You may add any number of parameters
	// Returns a list of news
	public List<News> getNewsByTag(/* Any number of parameters */) {
		return new LinkedList<>();
	}
	
}
