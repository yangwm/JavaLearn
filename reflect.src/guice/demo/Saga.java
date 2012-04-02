package guice.demo;

import java.io.IOException;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class Saga {
  private final Provider<Hero> heroProvider;

  @Inject
  public Saga(Provider<Hero> heroProvider) {
    this.heroProvider = heroProvider;
  }

  public void start() throws IOException {
    for (int i = 0; i < 3; i++) {
      Hero hero = heroProvider.get();
      hero.fightCrime(); 
    }    
  }
}
