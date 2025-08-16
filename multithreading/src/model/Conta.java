package model;

import java.util.concurrent.locks.ReentrantLock;

public final class Conta {

    private final int id;
    private long centavosBalance;
    private final ReentrantLock lock = new ReentrantLock();

    public Conta(int id, long inicialCentavos) {
        this.id = id;
        this.centavosBalance = inicialCentavos;
    }

    public int id() {
        return id;
    }

    public long getBalancaInsegura() {
        return centavosBalance;
    }

    public void depositoInseguro(long centavos) {
        centavosBalance += centavos;
    }
    public void retirarInseguro(long centavos) {
        if (centavosBalance < centavos) {
            throw new IllegalStateException("Saldo insuficiente na conta " + id);
        }
        centavosBalance -= centavos;
    }

    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }
}
