package com.myorg.stock.mapper;

import com.myorg.stock.model.LinkResponse;
import com.myorg.stock.model.Links;
import com.myorg.stock.resource.StockEndpoints;
import lombok.val;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class LinkMapper {

  public Links buildLinks(int totalPages, int requestedPage, int currentPageIndex) {
    Links links = new Links();
    val self = linkTo(methodOn(StockEndpoints.class).getAllStock(requestedPage)).withRel("self");
    links.setSelf(buildLinkResponse(self.getHref()));
    if (totalPages - requestedPage > 0) {
      int next = requestedPage + 1;
      val nxt = linkTo(methodOn(StockEndpoints.class).getAllStock(next)).withRel("next");
      links.setNext(buildLinkResponse(nxt.getHref()));
    }
    if (currentPageIndex > 0) {
      int prev = requestedPage - 1;
      Link previous = linkTo(methodOn(StockEndpoints.class).getAllStock(prev)).withRel("pev");
      links.setPrev(buildLinkResponse(previous.getHref()));
    }
    return links;
  }

  public LinkResponse buildAddedStockLink(Long stockId) {
    val selfRel = linkTo(methodOn(StockEndpoints.class).getStockById(stockId)).withSelfRel();
    return buildLinkResponse(selfRel.getHref());
  }


  private LinkResponse buildLinkResponse(String link) {
    LinkResponse linkResponse = new LinkResponse();
    linkResponse.setHref(link);
    return linkResponse;
  }
}
