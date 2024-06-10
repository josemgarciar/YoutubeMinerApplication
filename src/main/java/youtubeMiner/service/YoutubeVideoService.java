package youtubeMiner.service;

import exception.VideoNotFoundException;
import exception.VideoWithoutCommentsException;
import org.springframework.beans.factory.annotation.Autowired;
import youtubeMiner.DataBaseModel.Video;
import youtubeMiner.model.video.VideoSnippet;
import youtubeMiner.model.video.VideoSnippetSearch;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import static youtubeMiner.transforms.youtube.*;

@Service
public class YoutubeVideoService {

    static RestTemplate restTemplate = new RestTemplate();
    static String baseUri = "https://www.googleapis.com/youtube/v3";
    static String tokenYT = "AIzaSyBSCMH5ASLuIxXKRN-_AV0ExAY_pr7GDiQ";
    public static List<Video> getVideos(String id, Integer maxVideos, Integer maxComments) throws VideoNotFoundException, VideoWithoutCommentsException {

        String maxVids = "";

        if (maxVideos != null) {
            maxVids = String.valueOf(maxVideos);
        }

        HttpHeaders header = new HttpHeaders();
        HttpEntity<VideoSnippetSearch> request = new HttpEntity<>(null, header);
        ResponseEntity<VideoSnippetSearch> response = restTemplate.exchange(baseUri + "/search" + "?key=" + tokenYT + "&channelId=" + id + "&part=snippet" + "&type=video" + "&maxResults=" + maxVids, HttpMethod.GET, request, VideoSnippetSearch.class);

        List<Video> videos = new ArrayList<>();

        if(response == null){
            throw new VideoNotFoundException();
        }else{
            for (VideoSnippet v : response.getBody().getItems()) {
                Video video = new Video();
                video.setId(v.getId().getVideoId());
                video.setName(v.getSnippet().getTitle());
                video.setDescription(v.getSnippet().getDescription());
                video.setReleaseTime(v.getSnippet().getPublishedAt());
                video.setComments(YoutubeCommentService.getComments(v.getId().getVideoId(), maxComments));
                video.setCaptions(YoutubeCaptionService.getCaption(v.getId().getVideoId()));
                videos.add(video);
            }
        }
        return videos;
    }
}
