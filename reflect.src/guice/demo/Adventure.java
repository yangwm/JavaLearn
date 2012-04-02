package guice.demo;

import java.io.IOException;

import com.google.inject.Guice;
import com.google.inject.Injector;


public class Adventure {
  public static void main(String[] args) throws IOException {
    Injector injector = Guice.createInjector(new HeroModule());
    Saga saga = injector.getInstance(Saga.class);
    saga.start();
  }
}
