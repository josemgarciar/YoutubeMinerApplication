package youtubeMiner.service;

import exception.VideoNotFoundException;
import exception.VideoWithoutCommentsException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import youtubeMiner.DataBaseModel.Video;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class YoutubeVideoServiceTest {

    @Test
    void getVideo() throws VideoWithoutCommentsException, VideoNotFoundException {
        List<Video> res = YoutubeVideoService.getVideos("UC4zyoIAzmdsgpDZQfO1-lSA", null, null);
        System.out.println(res);
    }

}