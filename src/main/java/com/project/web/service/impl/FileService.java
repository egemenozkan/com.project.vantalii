package com.project.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import com.project.api.data.model.file.MyFile;
import com.project.api.data.model.file.UploadFileResponse;
import com.project.web.service.IFileService;

@Service
public class FileService extends BaseApiService implements IFileService {
	@Value("${security.api.auth-server-url}")
	private String authServerUrl;

	@Override
	public List<UploadFileResponse> saveFiles(MultipartFile[] files, long userId, int pageType, long pageId) {
		StringBuilder endpoint = new StringBuilder(authServerUrl);
		endpoint.append("/api/v1/files/uploadMultipleFiles");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		headers.add("Content-Type", "application/x-www-form-urlencoded");
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		try {
			for (MultipartFile file : files) {
				ByteArrayResource resource = new ByteArrayResource(file.getBytes()) {
					@Override
					public String getFilename() {
						return file.getOriginalFilename();
					}
				};
				map.add("files", resource);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		map.add("userId", userId);
		map.add("pageType", pageType);
		map.add("pageId", pageId);
		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, headers);

		return (List<UploadFileResponse>) postObject(endpoint.toString(), request, List.class);
	}

	@Override
	public List<MyFile> getFilesByPageId(int pageType, long pageId) {
		StringBuilder endpoint = new StringBuilder(authServerUrl);
		endpoint.append("/api/v1/places/{id}/files");

		return getList(endpoint.toString(), pageId);
	}

}
