package youtubeMiner.controller;

import exception.ChannelNotFoundException;
import exception.VideoNotFoundException;
import exception.VideoWithoutCommentsException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import youtubeMiner.DataBaseModel.Channel;
import youtubeMiner.DataBaseModel.Video;
import youtubeMiner.model.channel.ChannelSearch;
import youtubeMiner.DataBaseModel.Channel;
import youtubeMiner.service.YoutubeChannelService;

@Tag(name = "Youtube", description ="Youtube API")
@RestController
@RequestMapping("/youtube")
public class ChannelController {
    RestTemplate restTemplate = new RestTemplate();


    @Operation(summary = "Find a channel by id", description = "Find a channel by id in Youtube API", tags = { "Youtube", "Get operations"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Channel from Youtube",
                    content = {@Content(schema = @Schema(implementation = Channel.class),
                            mediaType = "application/json")
                    }),

            @ApiResponse(responseCode = "404", description = "Channel not found",
                    content = {@Content(schema = @Schema())
                    })
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Channel findOneYoutube(@Parameter(description ="ID of the channel to be searched") @PathVariable String id,
                                  @Parameter(description = "Maximum number of videos to be posted. Only used when called from the POST operation")@RequestParam(required = false, defaultValue = "10") Integer maxVideos,
                                  @Parameter(description = "Maximum number of comments to be posted. Only used when called from the POST operation")@RequestParam(required = false, defaultValue = "10")Integer maxComments)
            throws ChannelNotFoundException, VideoWithoutCommentsException, VideoNotFoundException {

        Channel channel = YoutubeChannelService.getChannel(id, maxVideos, maxComments);

        if(channel == null){
            throw new ChannelNotFoundException();
        } else {
            if(channel.getVideos().size() > maxVideos){
                channel.setVideos(channel.getVideos().subList(0, maxVideos));
            }
            for (Video v : channel.getVideos()){
                if(v.getComments().size() > maxComments) {
                    v.setComments(v.getComments().subList(0,maxComments));
                }
            }
            return channel;
        }
    }

    @Operation(summary = "Post a Youtube Channel to the DataBase", description = "Post a Youtube Channel in DataBase giving the id of the Youtube channel", tags = { "Youtube", "Post operations"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Post a channel from Youtube to the database",
                    content = {@Content(schema = @Schema(implementation = Channel.class),
                            mediaType = "application/json")
                    }),

            @ApiResponse(responseCode = "404", description = "Channel not found",
                    content = {@Content(schema = @Schema())
                    })
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{id}")
    public Channel saveYouTube(@Parameter(description = "ID of the channel to be searched in Youtube and saved in the DataBase")@PathVariable String id,
            @Parameter(description= "Maximum number of videos to post")@RequestParam(required = false, defaultValue = "10") Integer maxVideos,
            @Parameter(description= "Maximum number of comments to post")@RequestParam(required = false, defaultValue = "10") Integer maxComments) throws ChannelNotFoundException, VideoWithoutCommentsException, VideoNotFoundException {
        Channel channel = findOneYoutube(id, maxVideos, maxComments);
        return channel;
    }
}
