package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.POJONode;
import com.google.inject.Inject;
import play.mvc.Result;
import services.HeroesService;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static play.mvc.Controller.request;
import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;


public class Heroes {
  @Inject
  private HeroesService heroesService;

  public CompletionStage<Result> all() {
    return heroesService.all().thenApply((List<HeroesService.Hero> heroes) -> ok(toDataField(heroes)));
  }

  private JsonNode toDataField(Object value) {
    ObjectNode jsonNodes = JsonNodeFactory.instance.objectNode();
    jsonNodes.set("data", new POJONode(value));
    return jsonNodes;
  }

  public CompletionStage<Result> delete(int id) {
    return heroesService.delete(id).thenApply((Optional<HeroesService.Hero> hero) -> ok(""));
  }

  public CompletionStage<Result> create() {
    final String name = request().body().asJson().get("name").textValue();
    if (name != null) {
      return heroesService.create(name).thenApply((HeroesService.Hero hero) -> ok(toDataField(hero)));
    } else {
      return CompletableFuture.completedFuture(badRequest("expected 'name'"));
    }
  }

  public CompletionStage<Result> update(int id) {
    final String name = request().body().asJson().get("name").textValue();
    if (name != null) {
      return heroesService.update(id, name).thenApply((HeroesService.Hero hero) -> ok(toDataField(hero)));
    } else {
      return CompletableFuture.completedFuture(badRequest("expected 'name'"));
    }
  }
}
