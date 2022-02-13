package com.shortlinks.service;

import com.shortlinks.dao.LinkHolderCustomRepository;
import com.shortlinks.entity.LinkHolder;
import com.shortlinks.form.LinkForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LinkHolderServiceImpl implements LinkHolderService{

    private LinkHolderCustomRepository repository;

    @Autowired
    public LinkHolderServiceImpl(LinkHolderCustomRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<String> saveLink(LinkForm form) {
        LinkHolder linkHolder = new LinkHolder();
        Pattern patFullLink = Pattern.compile("^(http|https):\\/\\/(www\\.)?([A-Za-z0-9]{1}[A-Za-z0-9\\-]*\\.?)*\\.{1}[A-Za-z0-9-]{2,8}((\\/[A-Za-z0-9-]*)*)");
        Matcher matchFullLink = patFullLink.matcher(form.getFullLink());
        if (matchFullLink.find()) {
            linkHolder.setFullLink(matchFullLink.group(0));
        } else {
            System.out.printf("%s not a link", form.getFullLink());
            return new ResponseEntity<>(String.format("%s not a link", form.getFullLink()), HttpStatus.BAD_REQUEST);
        }
        if (repository.isExistFullLink(linkHolder.getFullLink())) {
            return new ResponseEntity<>("http://localhost:8080/app/redir/" + repository.getShortLinkByLongLink(linkHolder.getFullLink()), HttpStatus.OK);
        }
        Pattern patShortLink = Pattern.compile("(?<=^(http|https):\\/\\/(www\\.)?)([A-Za-z0-9]{1}[A-Za-z0-9\\-]*\\.?)*(?=\\.{1}[A-Za-z0-9-]{2,8}((\\/[A-Za-z0-9]*)*))");
        Matcher matchShortLink = patShortLink.matcher(form.getFullLink());
        matchShortLink.find();
        String shortLink = matchShortLink.group(0);
        linkHolder.setShortLink(shortLink + (repository.countOfShortLink(shortLink) + 1));
        return new ResponseEntity<>("http://localhost:8080/app/redir/" + repository.saveLink(linkHolder), HttpStatus.OK);
    }

    public String getLinkForRedirect(String shortLink) {
        return repository.getFullLinkByShortLink(shortLink);
    }
}
