package io.flpmartins.jms.activemq.queue2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Pedido implements Serializable {

    private static final AtomicLong autoId = new AtomicLong(0);

    private final Long id = autoId.addAndGet(1);
    private final List<Item> itens = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public List<Item> getItens() {
        return itens;
    }

    public void addItem(Item item) {
        this.itens.add(item);
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", itens=" + itens +
                '}';
    }
}
