package com.shortlinks.service;

import com.shortlinks.dao.LinkHolderCustomRepository;
import com.shortlinks.dto.FullLinkDto;
import com.shortlinks.dto.ShortLinkDto;
import com.shortlinks.entity.LinkHolder;
import com.shortlinks.form.FullLinkForm;
import com.shortlinks.form.ShortLinkForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LinkHolderServiceImpl implements LinkHolderService{

    private LinkHolderCustomRepository repository;

    @Autowired
    public LinkHolderServiceImpl(LinkHolderCustomRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseEntity<ShortLinkDto> saveLink(FullLinkForm form) {
        LinkHolder linkHolder = new LinkHolder();
        Pattern patFullLink = Pattern.compile("^(http|https):\\/\\/(www\\.)?([A-Za-z0-9]{1}[A-Za-z0-9\\-]*\\.?)*\\.{1}[A-Za-z0-9-]{2,8}((\\/[A-Za-z0-9-]*)*)");
        Matcher matchFullLink = patFullLink.matcher(form.getFullLink());
        if (matchFullLink.find()) {
            linkHolder.setFullLink(matchFullLink.group(0));
        } else {
            System.out.printf("%s not a link", form.getFullLink());
            throw new IllegalArgumentException(String.format("%s not a link", form.getFullLink()));
        }
        ShortLinkDto response = new ShortLinkDto();
        if (repository.isExistFullLink(linkHolder.getFullLink())) {
            response.setShortLink(repository.getShortLinkByFullLink(linkHolder.getFullLink()));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            Pattern patShortLink = Pattern.compile("(?<=^(http|https):\\/\\/(www\\.)?)([A-Za-z0-9]{1}[A-Za-z0-9\\-]*\\.?)*(?=\\.{1}[A-Za-z0-9-]{2,8}((\\/[A-Za-z0-9]*)*))");
            Matcher matchShortLink = patShortLink.matcher(form.getFullLink());
            matchShortLink.find();
            String shortLink = "http://localhost:8080/app/redir/" + matchShortLink.group(0);
            linkHolder.setShortLink(shortLink + (repository.countOfShortLink(shortLink) + 1));
            response.setShortLink(repository.saveLink(linkHolder));
        }
        return new ResponseEntity<ShortLinkDto>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<FullLinkDto> getFullLinkByShortLink(ShortLinkForm form) {
        if (!repository.isExistShortLink(form.getShortLink())) {
            throw new EntityNotFoundException(String.format("Short link %s not exist", form.getShortLink()));
        }
        FullLinkDto fullLinkDto = new FullLinkDto(repository.getFullLinkByShortLink(form.getShortLink()));
        return new ResponseEntity<FullLinkDto>(fullLinkDto, HttpStatus.OK);
    }

    @Override
    public String getLinkForRedirect(String shortLink) {
        return repository.getFullLinkByShortLink(shortLink);
    }
}
