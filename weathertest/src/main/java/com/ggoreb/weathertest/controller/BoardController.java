package com.ggoreb.weathertest.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ggoreb.weathertest.model.Board;
import com.ggoreb.weathertest.model.User;
import com.ggoreb.weathertest.repository.BoardRepository;


@Controller
public class BoardController {
	@Autowired
	BoardRepository boardRepository;
	@Autowired
	HttpSession session;

	@GetMapping("/board/write")
	public String boardWrite() {
		return "board/write";
	}

	@PostMapping("/board/write")
	public String boardWritePost(@ModelAttribute Board board) {
	User user = (User) session.getAttribute("user_info");
	String userId = user.getEmail();
	board.setUserId(userId);
	boardRepository.save(board);
	return "board/write";
	}
	
	@GetMapping("/board")
	public String board(Model model, Pageable pageable, @RequestParam(defaultValue = "1") int page) {
		/*
		 * List<Board> list = boardRepository.findAll(Sort.by(Sort.Direction.DESC,
		 * "id")); model.addAttribute("list", list);
		 */
		pageable= PageRequest.of(page-1,10);
	      
	      Page<Board> pageList = boardRepository.findAll(pageable);
	      model.addAttribute("list",pageList);
	      
	      int startPage = (page - 1) / 10 * 10 + 1;
	      int endPage = startPage + 9;
	      
	      model.addAttribute("startPage", startPage);
	      model.addAttribute("endPage", endPage);
	      model.addAttribute("page", page);
	      
		return "board/list";
	}

	@GetMapping("/board/{id}")
	public String boardView(Model model, @PathVariable("id") long id) {
		Optional<Board> data = boardRepository.findById(id);
		Board board = data.get();
		model.addAttribute("board", board);
		return "board/view";
	}

	@GetMapping("/board/update/{id}")
	public String boardUpdate(Model model, @PathVariable("id") long id) {
		Optional<Board> data = boardRepository.findById(id);
		Board board = data.get();
		model.addAttribute("board", board);
		return "board/update";
	}

	@PostMapping("/board/update/{id}")
	public String boardUpdatePost(@ModelAttribute Board board, @PathVariable("id") long id) {
		User user = (User) session.getAttribute("user_info");
		String userId = user.getEmail();
		board.setUserId(userId);
		board.setId(id);
		boardRepository.save(board);
		return "redirect:/board/" + id;
	}
	
	@GetMapping("/board/delete/{id}")
	public String boardDelete(@PathVariable("id") long id) {
		boardRepository.deleteById(id);
		return "redirect:/board";
	}
	

}