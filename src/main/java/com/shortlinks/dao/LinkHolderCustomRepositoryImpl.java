package com.shortlinks.dao;

import com.shortlinks.entity.LinkHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LinkHolderCustomRepositoryImpl implements LinkHolderCustomRepository {

    private LinkHolderRepository repository;

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public LinkHolderCustomRepositoryImpl(LinkHolderRepository repository, NamedParameterJdbcTemplate jdbcTemplate) {
        this.repository = repository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean isExistFullLink(String fullLink) {
        String sql = "select count(FULL_LINK) from LINK_HOLDER where FULL_LINK = :fullLink";
        MapSqlParameterSource params = new MapSqlParameterSource("fullLink", fullLink);
        return jdbcTemplate.queryForObject(sql, params, Integer.class) > 0;
    }

    @Override
    public String getShortLinkByLongLink(String fullLink) {
        String sql = "select SHORT_LINK from LINK_HOLDER where FULL_LINK = :fullLink";
        MapSqlParameterSource params = new MapSqlParameterSource("fullLink", fullLink);
        return jdbcTemplate.queryForObject(sql, params, String.class);
    }

    @Override
    public String getFullLinkByShortLink(String shortLink) {
        String sql = "select FULL_LINK from LINK_HOLDER where SHORT_LINK = :shortLink";
        MapSqlParameterSource params = new MapSqlParameterSource("shortLink", shortLink);
        return jdbcTemplate.queryForObject(sql, params, String.class);
    }

    @Override
    public int countOfShortLink(String shortLink) {
        String sql = "select count(SHORT_LINK) from LINK_HOLDER where SHORT_LINK = :shortLink";
        MapSqlParameterSource params = new MapSqlParameterSource("shortLink", shortLink);
        return jdbcTemplate.queryForObject(sql, params, Integer.class);
    }

    @Override
    public String saveLink(LinkHolder linkHolder) {
        return repository.save(linkHolder).getShortLink();
    }
}
