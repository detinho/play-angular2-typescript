package services;

import com.google.inject.Singleton;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

@Singleton
public class HeroesService {

  private Map<Integer, Hero> heroes = new HashMap<Integer, Hero>() {{
    put(11, new Hero(11, "Mr. Nice"));
    put(12, new Hero(12, "Narco"));
    put(13, new Hero(13, "Bombasto"));
    put(14, new Hero(14, "Celeritas"));
    put(15, new Hero(15, "Magneta"));
    put(16, new Hero(16, "RubberMan"));
    put(17, new Hero(17, "Dynama"));
    put(18, new Hero(18, "Dr IQ"));
    put(19, new Hero(19, "Magma"));
    put(20, new Hero(20, "Tornado"));
  }};

  public CompletionStage<List<Hero>> all() {
    return CompletableFuture.supplyAsync(() -> heroes.values().stream().sorted((o1, o2) -> Comparator.comparing((Hero h) -> h.id).compare(o1, o2)).collect(Collectors.toList()));
  }

  public CompletionStage<Optional<Hero>> delete(int id) {
    return CompletableFuture.supplyAsync(() -> Optional.ofNullable(heroes.remove(id)));
  }

  public CompletionStage<Hero> create(String name) {
    return CompletableFuture.supplyAsync(() -> {
      final int nextNewId = heroes.keySet().stream().mapToInt(i -> i).max().getAsInt() + 1;
      Hero hero = new Hero(nextNewId, name);
      heroes.put(hero.id, hero);
      return hero;
    });
  }

  public CompletionStage<Hero> update(int id, String name) {
    return CompletableFuture.supplyAsync(() -> {
      Hero hero = new Hero(id, name);
      heroes.put(hero.id, hero);
      return hero;
    });
  }


  public static class Hero {
    private int id;
    private String name;

    public Hero(int id, String name) {
      this.id = id;
      this.name = name;
    }

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }
}
