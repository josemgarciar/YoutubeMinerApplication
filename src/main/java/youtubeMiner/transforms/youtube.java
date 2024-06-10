package youtubeMiner.transforms;

import exception.VideoNotFoundException;
import youtubeMiner.DataBaseModel.Caption;
import youtubeMiner.DataBaseModel.Comment;
import youtubeMiner.DataBaseModel.User;
import youtubeMiner.DataBaseModel.Video;
import youtubeMiner.model.caption.CaptionSearch;
import youtubeMiner.DataBaseModel.Channel;
import youtubeMiner.model.channel.ChannelSearch;
import youtubeMiner.model.comment.CommentSearch;
import youtubeMiner.model.video.VideoSnippet;
import youtubeMiner.model.video.VideoSnippetSearch;
import exception.VideoWithoutCommentsException;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import youtubeMiner.service.YoutubeVideoService;

import java.util.ArrayList;
import java.util.List;

public class youtube {

    static YoutubeVideoService videoService = new YoutubeVideoService();
    public static Channel convertToChannel(ChannelSearch channelSearch, String id, Integer maxVideos, Integer maxComments) throws VideoNotFoundException, VideoWithoutCommentsException {
        Channel channel = new Channel();
        channel.setId(channelSearch.getItems().get(0).getId());
        channel.setName(channelSearch.getItems().get(0).getSnippet().getTitle());
        channel.setDescription(channelSearch.getItems().get(0).getSnippet().getDescription());
        channel.setCreatedTime(channelSearch.getItems().get(0).getSnippet().getPublishedAt());
        channel.setVideos(videoService.getVideos(id, maxVideos, maxComments));
        return channel;
    }
}
