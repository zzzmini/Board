package com.example.myBoard.controller;

import com.example.myBoard.dto.ArticleDto;
import com.example.myBoard.entity.Article;
import com.example.myBoard.service.ArticleService;
import com.example.myBoard.service.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService articleService;
    @Autowired
    PaginationService paginationService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("paging")
    public String mainView(Model model,
                           @PageableDefault(page = 0, size = 10, sort = "id",
                           direction = Sort.Direction.DESC)Pageable pageable) {
        // 넘겨온 페이지 번호로 리스트 받아오기
        Page<Article> paging = articleService.pagingList(pageable);

        // 페이지 블럭 처리(1, 2, 3, 4, 5)
        int totalPage = paging.getTotalPages();
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(
                pageable.getPageNumber(), totalPage);

        model.addAttribute("paginationBarNumbers", barNumbers);
        model.addAttribute("paging", paging);
        return "articles/show_all_list";
    }
    @GetMapping("insert")
    public String insertView(Model model){
        model.addAttribute("dto", new ArticleDto());
        return "/articles/new";
    }

    @PostMapping("insert")
    public String insert(@ModelAttribute("dto")ArticleDto dto){
        articleService.insertArticle(dto);
        return "redirect:/";
    }

    @GetMapping("show/{id}")
    public String viewOne(@PathVariable("id")Long id,
            Model model){
        ArticleDto dto = articleService.findById(id);
        model.addAttribute("dto", dto);
        return "/articles/show";
    }
    @PostMapping("update")
    public String update(@ModelAttribute("dto")ArticleDto dto){
        articleService.updateArticle(dto);
        return "redirect:/";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id")Long id,
                          RedirectAttributes redirectAttributes){
        //1. 삭제할 대상이 존재하는지 확인
        ArticleDto dto = articleService.findById(id);
        //2. 대상 엔티티가 존재하면 삭제처리 후 메시지를 전송
        if(dto != null){
            articleService.deleteById(id);
            redirectAttributes.addFlashAttribute("msg", "정상적으로 삭제되었습니다.");
        }
        return "redirect:/";
    }
}
