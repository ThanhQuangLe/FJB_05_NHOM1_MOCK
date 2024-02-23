package fa.mock.service;

import fa.mock.entities.News;
import fa.mock.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsService {
    @Autowired
    NewsRepository newsRepository;

    public News save(News news){
        newsRepository.save(news);
        return news;
    }
}
