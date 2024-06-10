package youtubeMiner.service;

import exception.VideoNotFoundException;
import exception.VideoWithoutCommentsException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import youtubeMiner.DataBaseModel.Comment;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class YoutubeCommentServiceTest {

    @Test
    void getComment() throws VideoWithoutCommentsException {
        List<Comment> res = YoutubeCommentService.getComments("U8qJc6znzZc", 1);
        System.out.println(res);
    }

}