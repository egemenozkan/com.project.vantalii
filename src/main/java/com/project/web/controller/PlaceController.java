package com.project.web.controller;

import java.util.Collections;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.view.RedirectView;

import com.google.gson.Gson;
import com.project.api.data.enums.LandingPageType;
import com.project.api.data.enums.Language;
import com.project.api.data.enums.MainType;
import com.project.api.data.enums.PlaceType;
import com.project.api.data.model.comment.Comment;
import com.project.api.data.model.comment.CommentResponse;
import com.project.api.data.model.file.MyFile;
import com.project.api.data.model.place.PlaceLandingPage;
import com.project.api.data.model.place.PlaceRequest;
import com.project.common.utils.WebUtils;
import com.project.web.service.IFileService;
import com.project.web.service.IPlaceService;
import com.project.web.service.ITimeService;

@Controller
public class PlaceController {

	@Autowired
	private IPlaceService placeService;
	@Autowired
	private IFileService fileService;
	@Autowired
	private ITimeService timeService;
	
	@Autowired
	private Gson gson;

	final Logger logger = LoggerFactory.getLogger(PlaceController.class);

	
	private static final String PAGES = "pages";
	
	@SuppressWarnings("unchecked")
	@GetMapping({ "/places", "/{language}/places" })
	public ModelAndView placesHomepage(Model model, HttpServletRequest request,
			@PathVariable(required = false, name = "language") String language) {
		PlaceRequest placeRequest = new PlaceRequest();
		placeRequest.setLanguage(Language.getByCode(language));
		placeRequest.setLimit(10);
		placeRequest.setRandom(Boolean.TRUE);
	//	model.addAttribute("pages", placeService.getPlaceLandingPages(placeRequest));

		return new ModelAndView("places/index");
	}

	@GetMapping({ "/places/{slug}", "/{language}/places/{slug}" })
	public ModelAndView placeLandingPage(ModelMap model, HttpServletRequest request,
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
			logger.warn("::redirect status 404  {}-> {}", url, "404");
			throw new NoHandlerFoundException(HttpMethod.GET.toString(), url.toString(), new HttpHeaders());
		}
		//
		if (page.getSlug() != null && !slug.equalsIgnoreCase(page.getSlug())) {
			StringBuilder redirectUrl = new StringBuilder("redirect:/");
			if (!(request.getHeader("host").contains("vantalii.ru") && "ru".equalsIgnoreCase(language))) {
				redirectUrl.append(page.getLanguage().getCode().toLowerCase());
				redirectUrl.append("/");

			}
			redirectUrl.append("places/");
			redirectUrl.append(page.getSlug());

			logger.warn("::redirect status 301  {}-> {}", url, redirectUrl);
			RedirectView redirect = new RedirectView(redirectUrl.toString(), false);
			redirect.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
			redirect.setExposeModelAttributes(false);
			return new ModelAndView(redirectUrl.toString(), HttpStatus.MOVED_PERMANENTLY);
		}

		model.addAttribute("page", page);

		PlaceRequest placeRequest = new PlaceRequest();
		placeRequest.setLanguage(Language.getByCode(language));
		placeRequest.setLimit(10);
		placeRequest.setMainType(page.getPlace().getType().getMainType());
		placeRequest.setRandom(Boolean.TRUE);
		List<PlaceLandingPage> seoPages = null; //placeService.getPlaceLandingPages(placeRequest);

		String languageCode = WebUtils.getURILanguageCode(request.getRequestURI());
		model.addAttribute("languageCode", languageCode);
		model.addAttribute("seoPages", seoPages);

		model.addAttribute("placeId", id);
		model.addAttribute("dailyWorkingHours", timeService.getDailyWorkingHours(id));
		;
		
		return new ModelAndView("places/detail", model);
	}

	@GetMapping({ "/places/comments" })
	public @ResponseBody CommentResponse getPlaceComments(Model model, @RequestParam(required = true) long id) {
		return placeService.getCommentsByPlaceId(id);

	}

	@PostMapping("/places/{id}/comments")
	public @ResponseBody CommentResponse postPlaceComment(@PathVariable long id, RequestEntity<Comment> requestEntity) {
		Comment newComment = requestEntity.getBody();
		if (newComment != null) {
			placeService.saveComment(newComment, id);
		}

		return placeService.getCommentsByPlaceId(id);
	}

	@GetMapping({ "/places/m/{slug}", "/{language}/places/m/{slug}" })
	public ModelAndView landingPageByMainTypes(Model model, HttpServletRequest request,
			@PathVariable(required = false, name = "language") String language, @PathVariable String slug,
			@RequestParam(required = false, defaultValue = "", name = "districts") String[] districts,
			@RequestParam(required = false, defaultValue = "", name = "regions") String[] regions) {

		PlaceRequest placeRequest = null;
		language = (language ==  null) ? "RU" : language;
		if (MainType.getBySlug(slug) != MainType.NOTSET) {
			placeRequest = new PlaceRequest();
			placeRequest.setMainType(MainType.getBySlug(slug));
			placeRequest.setLanguage(Language.getByCode(language));
			placeRequest.setLimit(20);
			placeRequest.setHideContent(true);
			placeRequest.setHideImages(true);
			placeRequest.setDistricts(districts);
			placeRequest.setRegions(regions);
			
			model.addAttribute(PAGES, placeService.getPlaceLandingPages(placeRequest));
		} else {
			logger.error("not found {}", request.getPathInfo());
			model.addAttribute(PAGES, Collections.emptyList());
		}
		
		model.addAttribute("mainType", MainType.getBySlug(slug));
		model.addAttribute("slug", slug);
		
		return new ModelAndView("places/listMainType");
	}

	@GetMapping({ "/places/t/{slug}", "/{language}/places/t/{slug}" })
	public ModelAndView landingPageByTypes(Model model, HttpServletRequest request,
			@PathVariable(required = false, name = "language") String language, @PathVariable String slug,
			@RequestParam(required = false, defaultValue = "", name = "districts") String[] districts,
			@RequestParam(required = false, defaultValue = "", name = "regions") String[] regions) {

		PlaceRequest placeRequest = null;
		language = (language ==  null) ? "RU" : language;
		if (PlaceType.getBySlug(slug) != PlaceType.NOTSET) {
			placeRequest = new PlaceRequest();
			placeRequest.setType(PlaceType.getBySlug(slug));
			placeRequest.setLanguage(Language.getByCode(language));
			placeRequest.setHideContent(true);
			placeRequest.setHideImages(true);
			placeRequest.setRandom(true);
			model.addAttribute("type", PlaceType.getBySlug(slug));
			model.addAttribute("pages", placeService.getPlaceLandingPages(placeRequest));
		}

		return new ModelAndView("places/list");
	}

	@PostMapping({ "/places/file-upload/single", "/{language}/places/file-upload/single" })
	public @ResponseBody boolean landingPageByTypes(Model model, HttpServletRequest request,
			@PathVariable(required = false, name = "language") String language,
			@RequestParam("file") MultipartFile[] files,
			@RequestParam("userId") long userId, @RequestParam("pageId") long pageId) {
		try {
			fileService.saveFiles(files, userId, LandingPageType.PLACE.getId(), pageId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return true;
	}

	@GetMapping({ "/places/{id}/files" })
	public @ResponseBody List<MyFile> getFilesById(Model model, @PathVariable long id) {
		return fileService.getFilesByPageId(LandingPageType.PLACE.getId(), id);
	}
	

}
