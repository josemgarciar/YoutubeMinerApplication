package youtubeMiner.service;

import exception.ChannelNotFoundException;
import exception.VideoNotFoundException;
import exception.VideoWithoutCommentsException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import youtubeMiner.DataBaseModel.Channel;
import youtubeMiner.model.channel.ChannelSearch;
import static youtubeMiner.transforms.youtube.convertToChannel;

@Service
public class YoutubeChannelService {

    static RestTemplate restTemplate = new RestTemplate();

    static String tokenYT = "AIzaSyBSCMH5ASLuIxXKRN-_AV0ExAY_pr7GDiQ";
    static String baseUriYT ="https://www.googleapis.com/youtube/v3";

    public static Channel getChannel(String id, Integer maxVideos, Integer maxComments)  throws ChannelNotFoundException, VideoNotFoundException, VideoWithoutCommentsException {
        HttpHeaders header = new HttpHeaders();
        HttpEntity<ChannelSearch> request = new HttpEntity<>(null, header);
        ResponseEntity<ChannelSearch> response = restTemplate.exchange(baseUriYT + "/channels" + "?key=" + tokenYT + "&id=" + id + "&part=snippet" , HttpMethod.GET, request, ChannelSearch.class);

        if (response == null) {
            throw new ChannelNotFoundException();
        } else {
            return convertToChannel(response.getBody(), id, maxVideos, maxComments);
        }
    }

    public static Channel postChannel(String id, Integer maxVideos, Integer maxComments)  throws ChannelNotFoundException, VideoNotFoundException, VideoWithoutCommentsException {

        Channel channel = getChannel(id, maxVideos, maxComments);

        if (channel != null) {
            restTemplate.postForObject("http://localhost:8080/videominer/channels", channel, Channel.class);
            return channel;
        } else {
            throw new ChannelNotFoundException();
        }

    }
}
