package youtubeMiner.service;

import exception.ChannelNotFoundException;
import exception.VideoNotFoundException;
import exception.VideoWithoutCommentsException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class YoutubeChannelServiceTest {

    @Test
    void getChannel() throws ChannelNotFoundException, VideoNotFoundException, VideoWithoutCommentsException {
        youtubeMiner.DataBaseModel.Channel res = YoutubeChannelService.getChannel("UC4zyoIAzmdsgpDZQfO1-lSA", 1, 1);
        System.out.println(res);
    }

}