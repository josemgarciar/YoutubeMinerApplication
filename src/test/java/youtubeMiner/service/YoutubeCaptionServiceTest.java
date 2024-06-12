package youtubeMiner.service;

import exception.ChannelNotFoundException;
import exception.VideoNotFoundException;
import exception.VideoWithoutCommentsException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import youtubeMiner.DataBaseModel.Caption;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class YoutubeCaptionServiceTest {

    @Test
    void getCaption()  {
        List<Caption> res = YoutubeCaptionService.getCaption("U8qJc6znzZc");
        System.out.println(res);
    }

}