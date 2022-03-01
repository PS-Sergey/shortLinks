package com.shortlinks.controller;

import com.shortlinks.dto.FullLinkDto;
import com.shortlinks.dto.ShortLinkDto;
import com.shortlinks.form.FullLinkForm;
import com.shortlinks.form.ShortLinkForm;
import com.shortlinks.service.LinkHolderService;
import com.shortlinks.service.LinkHolderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/app")
public class LinkHolderController {

    private LinkHolderService service;

    @Autowired
    public LinkHolderController(LinkHolderServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/shortLink")
    @ResponseBody
    public ResponseEntity<ShortLinkDto> getShortLink(@RequestBody FullLinkForm form) {
        return service.saveLink(form);
    }

    @PostMapping("/fullLink")
    @ResponseBody
    public ResponseEntity<FullLinkDto> getFullLink(@RequestBody ShortLinkForm form) {
        return service.getFullLinkByShortLink(form);
    }

    @GetMapping("/redir/{shortLink}")
    public void redirect(@PathVariable String shortLink, HttpServletResponse httpResponse) throws IOException {
        String fullLink = service.getLinkForRedirect(shortLink);
        httpResponse.sendRedirect(fullLink);
    }
}
