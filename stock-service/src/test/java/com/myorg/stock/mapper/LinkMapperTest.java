package com.myorg.stock.mapper;

import com.myorg.stock.model.LinkResponse;
import com.myorg.stock.model.Links;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class LinkMapperTest {

  @InjectMocks
  private LinkMapper linkMapper;

  @Test
  void testBuildLinks() {
    Links links = linkMapper.buildLinks(3, 2, 1);
    assertThat(links)
        .hasFieldOrPropertyWithValue("self.href", "/api/stocks?page=2")
        .hasFieldOrPropertyWithValue("next.href", "/api/stocks?page=3")
        .hasFieldOrPropertyWithValue("prev.href", "/api/stocks?page=1")
    ;
  }

  @Test
  void testBuildLinks_prevNull() {
    Links links = linkMapper.buildLinks(3, 1, 0);
    assertThat(links)
        .hasFieldOrPropertyWithValue("self.href", "/api/stocks?page=1")
        .hasFieldOrPropertyWithValue("next.href", "/api/stocks?page=2")
        .hasFieldOrPropertyWithValue("prev.href", null)
    ;
  }

  @Test
  void testBuildLinks_nextNull() {
    Links links = linkMapper.buildLinks(3, 3, 2);
    assertThat(links)
        .hasFieldOrPropertyWithValue("self.href", "/api/stocks?page=3")
        .hasFieldOrPropertyWithValue("next.href", null)
        .hasFieldOrPropertyWithValue("prev.href", "/api/stocks?page=2")
    ;
  }

  @Test
  void testBuildAddedStockLink() {
    LinkResponse linkResponse = linkMapper.buildAddedStockLink(22L);
    assertThat(linkResponse)
        .hasFieldOrPropertyWithValue("href", "/api/stocks/22")
        ;
  }
}