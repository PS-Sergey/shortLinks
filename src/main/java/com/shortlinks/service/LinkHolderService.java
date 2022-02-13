package com.shortlinks.service;

import com.shortlinks.form.LinkForm;
import org.springframework.http.ResponseEntity;

public interface LinkHolderService {

    ResponseEntity<String> saveLink(LinkForm form);

    String getLinkForRedirect(String shortLink);
}
