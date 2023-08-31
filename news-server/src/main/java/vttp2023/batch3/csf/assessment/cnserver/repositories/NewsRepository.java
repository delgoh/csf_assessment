package vttp2023.batch3.csf.assessment.cnserver.repositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import vttp2023.batch3.csf.assessment.cnserver.models.TagCount;

@Repository
public class NewsRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	// TODO: Task 1 
	// Write the native Mongo query in the comment above the method
	/* 	db.news.insert({
	   		postDate: <currentTime>,
			title: <title>,
			description: <description>,
			image: <imageUrl>,
			tags: <tagsArray>
		}) 
	*/
	public String insertNews(String title, String imageUrl, String description, String tagString) {
		String[] tagsToInsert = tagString.toLowerCase().trim().split("\\s+");
		
		Document doc = new Document();
		doc.put("postDate", System.currentTimeMillis());
		doc.put("title", title);
		doc.put("description", description);
		doc.put("image", imageUrl);
		if (tagsToInsert.length > 0){
			doc.put("tags", tagsToInsert);
		}

		Document newDoc = mongoTemplate.insert(doc, "news");
		return newDoc.getObjectId("_id").toString();
	}


	// TODO: Task 2 
	// Write the native Mongo query in the comment above the method
	/*  db.news.aggregate([
			{ $match: {
				postDate: {$gte: <offsetTime>},
        		tags: {$exists: true}
			} },
			{ $unwind: "$tags" },
			{ $group: {
				_id: "$tags",
				count: {$sum: 1}
			} },
			{ $sort: {count: -1} },
			{ $limit: 10 },
			{ $project: {
				_id: 0,
				tag: "$_id",
				count: 1
			} }
		])
	*/
	public List<Document> getTagCounts(Long offsetTime) {
		MatchOperation matchPostDate = Aggregation.match(
			Criteria.where("postDate").gte(offsetTime)
				.and("tags").exists(true)
		);
		UnwindOperation unwindTags = Aggregation.unwind("tags");
		GroupOperation groupTags = Aggregation.group("tags")
			.count().as("count");
		SortOperation sortByCount = Aggregation.sort(
			Sort.by(Direction.DESC, "count")
		);
		LimitOperation limitResult = Aggregation.limit(10);
		ProjectionOperation projectResults = Aggregation.project("count").and("_id").as("tag");

		Aggregation pipeline = Aggregation.newAggregation(
			matchPostDate,
			unwindTags,
			groupTags,
			sortByCount,
			limitResult,
			projectResults
		);
		return mongoTemplate.aggregate(pipeline, "news", Document.class).getMappedResults();
	}



	// TODO: Task 3
	// Write the native Mongo query in the comment above the method
	/* 	db.news.find({
    		tags: { $in: [<tagName>] }
		})
	*/
	public List<Document> getNewsByTag(String tagName) {
		return mongoTemplate.find(
			Query.query(
				Criteria.where("tags").in(tagName)
			),
			Document.class,
			"news"
		);
	}

}
