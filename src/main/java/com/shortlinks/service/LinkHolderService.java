package com.shortlinks.service;

import com.shortlinks.dto.FullLinkDto;
import com.shortlinks.dto.ShortLinkDto;
import com.shortlinks.form.FullLinkForm;
import com.shortlinks.form.ShortLinkForm;
import org.springframework.http.ResponseEntity;

public interface LinkHolderService {

    ResponseEntity<ShortLinkDto> saveLink(FullLinkForm form);

    String getLinkForRedirect(String shortLink);

    ResponseEntity<FullLinkDto> getFullLinkByShortLink(ShortLinkForm shortLink);
}
