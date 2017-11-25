package fr.polytech.raspberry.deserializers;

public interface Deserializer<I> {

    public <O> O from(I in, Class<O> clazz);
}