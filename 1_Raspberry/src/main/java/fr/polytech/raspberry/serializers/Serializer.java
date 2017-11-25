package fr.polytech.raspberry.serializers;

public interface Serializer<O> {

    public <I> O to(I in);
}