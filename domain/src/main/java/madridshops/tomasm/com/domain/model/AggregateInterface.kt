package madridshops.tomasm.com.domain.model

//Interfaz segregada con métodos comúnes para ser implementados por mas de una clase

interface ReadAggregate<T> {

    fun count(): Int
    fun returnAll(): List<T>
    fun get(position: Int): T
}

interface WriteAggregate<T> {

    fun add(element: T)
    fun delete(position: Int)
    fun delete(element: T)
}

interface AggregateInterface<T>: ReadAggregate<T>, WriteAggregate<T>