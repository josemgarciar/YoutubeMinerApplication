package youtubeMiner.service;

import exception.VideoWithoutCommentsException;
import org.springframework.http.*;
import youtubeMiner.DataBaseModel.User;
import youtubeMiner.DataBaseModel.Comment;
import youtubeMiner.model.comment.CommentSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class YoutubeCommentService {

    static RestTemplate restTemplate = new RestTemplate();

    static String baseUri = "https://www.googleapis.com/youtube/v3";
    static String tokenYT = "AIzaSyBSCMH5ASLuIxXKRN-_AV0ExAY_pr7GDiQ";

    public static List<Comment> getComments(String id, Integer maxComments) throws VideoWithoutCommentsException{

        String maxComm = "";

        if (maxComments != null) {
            maxComm = String.valueOf(maxComments);
        }

        HttpHeaders header = new HttpHeaders();
        HttpEntity<CommentSearch> request = new HttpEntity<>(null, header);
        ResponseEntity<CommentSearch> response = restTemplate.exchange(baseUri + "/commentThreads" + "?key=" + tokenYT + "&videoId=" + id + "&part=snippet" + "&maxResults=" + maxComments, HttpMethod.GET, request, CommentSearch.class);

        List<youtubeMiner.DataBaseModel.Comment> comments = new ArrayList<>();

        if (response.getStatusCode() == HttpStatus.FORBIDDEN) {
            throw new VideoWithoutCommentsException();
        } else {
            for (youtubeMiner.model.comment.Comment c : response.getBody().getItems()) {
                youtubeMiner.DataBaseModel.Comment comment = new youtubeMiner.DataBaseModel.Comment();
                User user  = new User();
                comment.setId(c.getCommentSnippet().getTopLevelComment().getId());
                comment.setCreatedOn(c.getCommentSnippet().getTopLevelComment().getSnippet().getPublishedAt());
                comment.setText(c.getCommentSnippet().getTopLevelComment().getSnippet().getTextOriginal());
                comment.setAuthor(AutorDeComentario(c.getCommentSnippet().getTopLevelComment().getSnippet().getAuthorDisplayName()));
                comments.add(comment);
            }
            return comments;
        }
    }

    private static User AutorDeComentario(String name) {
        User user = new User();
        user.setName(name);
        return user;
    }
}

