package youtubeMiner.service;

import youtubeMiner.DataBaseModel.Caption;
import youtubeMiner.model.caption.CaptionSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class YoutubeCaptionService {
    static RestTemplate restTemplate = new RestTemplate();
    static String baseUri = "https://youtube.googleapis.com/youtube/v3";
    static String tokenYT = "AIzaSyBSCMH5ASLuIxXKRN-_AV0ExAY_pr7GDiQ";


    public static List<Caption> getCaption(String id){
        HttpHeaders header = new HttpHeaders();
        HttpEntity<CaptionSearch> request = new HttpEntity<>(null, header);
        ResponseEntity<CaptionSearch> response = restTemplate.exchange(baseUri + "/captions?part=snippet&videoId=" + id + "&key=" + tokenYT, HttpMethod.GET, request, CaptionSearch.class);


        List<Caption> captions = new ArrayList<>();

        if (!response.getBody().getItems().isEmpty()) {

            for (youtubeMiner.model.caption.Caption captionYT : response.getBody().getItems()){
                Caption c = new Caption();
                c.setName(captionYT.getSnippet().getName());
                c.setId(captionYT.getId());
                c.setLanguage(captionYT.getSnippet().getLanguage());
                captions.add(c);
            }
        }
        return captions;
    }
}
