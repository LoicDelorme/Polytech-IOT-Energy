package fr.polytech.server.deserializers;

public interface Deserializer<I> {

    public <O> O from(I in, Class<O> clazz);
}