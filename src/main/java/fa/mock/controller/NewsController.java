package fa.mock.controller;

import fa.mock.entities.News;
import fa.mock.repository.NewsRepository;
import fa.mock.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class NewsController {
    @Autowired
    NewsRepository newsRepository;
    @Autowired
    NewsService newsService;

    @GetMapping("/news-list")
    public String scheduleListPage(Model model, @RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                                   @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize,
                                   @RequestParam(value = "searchTerm", required = false) String searchTerm
    ) {

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<News> contentPage = null;
        List<Integer> list = new ArrayList<>();


        if (searchTerm == null) {
            return "redirect:/news-list";
        } else {
            contentPage = newsRepository.findAll(pageable);

            if (contentPage.getTotalElements() == 0) {
                model.addAttribute("list", null);
            } else {
                for (int i = 1; i <= contentPage.getTotalPages(); i++) {
                    list.add(i);
                }


                //View list
                contentPage = newsRepository.findById("%" + searchTerm + "%", pageable);

                for (int i = 1; i <= contentPage.getTotalPages(); i++) {
                    list.add(i);
                }
                model.addAttribute("searchTerm", searchTerm);
                model.addAttribute("pageNumList",list);
                model.addAttribute("list",contentPage);
                model.addAttribute("total",contentPage.getTotalElements());
            }

            return "/news/listNews";
        }
    }



    @ModelAttribute("news")
    public List<News> newsList(){
        return newsRepository.findAll();
    }

    @GetMapping("/news-create")
    public String newsCreatePage(Model model) {
        model.addAttribute("news", new News());
        return "/news/addNews";
    }

    @PostMapping("/news-create")
    public String newsSavePage(@Validated @ModelAttribute("news") News news, BindingResult result, Model model) {
        if(result.hasErrors()){
            return "/news/addNews";
        }
        newsService.save(news);
        return "redirect:/news-list";
    }

}

