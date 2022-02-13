package com.shortlinks.dao;

import com.shortlinks.entity.LinkHolder;

public interface LinkHolderCustomRepository {

    boolean isExistFullLink(String fullLink);

    String getShortLinkByLongLink(String fullLink);

    String getFullLinkByShortLink(String shortLink);

    int countOfShortLink(String shortLink);

    String saveLink(LinkHolder linkHolder);
}
