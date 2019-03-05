package com.project.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.project.api.data.enums.Language;
import com.project.api.data.model.comment.Comment;
import com.project.api.data.model.comment.PlaceCommentResponse;
import com.project.api.data.model.place.PlaceLandingPage;
import com.project.api.data.model.place.PlaceRequest;
import com.project.web.service.IPlaceService;
import com.project.web.utils.WebUtils;

@Controller
public class PlaceController {

	@Autowired
	private IPlaceService placeService;
	

	final Logger LOG = LoggerFactory.getLogger(PlaceController.class);

	@SuppressWarnings("unchecked")
	@GetMapping({ "/places/{slug}", "/{language}/places/{slug}" })
	public ModelAndView getPlace(Model model, HttpServletRequest request,
			@PathVariable(required = false, name = "language") String language, @PathVariable String slug) throws NoHandlerFoundException {
		long id = WebUtils.getIdOfSlug(slug);
		PlaceLandingPage page = null;
		if (language == null) {
			language = WebUtils.getCookieValue(request, "lang", Language.RUSSIAN.getCode().toLowerCase());

		}
		
		StringBuilder url = new StringBuilder(language);
		url.append("/places/");
		url.append(slug);
		//
		if (id > 0) {
			page = placeService.getPlaceLandingPage(id, language);
		}
		/** REDIRECT - 404 **/
		if (page == null) {
			LOG.warn("::redirect status 404  {}-> {}", url, "404");
			throw new NoHandlerFoundException(HttpMethod.GET.toString(), url.toString(), new HttpHeaders());
		}
		//
		if (page.getSlug() != null && !slug.equalsIgnoreCase(page.getSlug())) {
			StringBuilder redirectUrl = new StringBuilder("redirect:/");
			if (!(request.getHeader("host").contains("vantalii.ru") && "ru".equalsIgnoreCase(language))) {
				redirectUrl.append(page.getLanguage().getCode().toLowerCase());
			}
			redirectUrl.append("/places/");
			redirectUrl.append(page.getSlug());

			LOG.warn("::redirect status 301  {}-> {}", url, redirectUrl);

			return new ModelAndView(redirectUrl.toString(), HttpStatus.PERMANENT_REDIRECT);
		}

		model.addAttribute("page", page);

		PlaceRequest placeRequest = new PlaceRequest();
		placeRequest.setLanguage(Language.getByCode(language));
		placeRequest.setLimit(10);
		placeRequest.setRandom(Boolean.TRUE);
		List<PlaceLandingPage> pages = placeService.getPlaceLandingPages(placeRequest);

		
		String languageCode = WebUtils.getURILanguageCode(request.getRequestURI());
		model.addAttribute("languageCode", languageCode);
		model.addAttribute("pages", pages);

		model.addAttribute("placeId", id);
		return new ModelAndView("places/page");
	}

	@GetMapping({ "/places/comments" })
	public @ResponseBody PlaceCommentResponse getPlaceComments(Model model, @RequestParam(required = true) long id) {
		return placeService.getPlaceCommentsByPlaceId(id);

	}

	@PostMapping("/places/{id}/comments")
	public @ResponseBody PlaceCommentResponse postPlaceComment(@PathVariable long id, RequestEntity<Comment> requestEntity) {
		Comment newComment = requestEntity.getBody();
		if (newComment != null) {
			placeService.savePlaceComment(newComment, id);
		}

		return placeService.getPlaceCommentsByPlaceId(id);
	}

}
