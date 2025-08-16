package service;

import model.Conta;

public class TransferenciaService {
    private final Banco banco;

    public TransferenciaService(Banco banco) {
        this.banco = banco;
    }

    /**
     * Transferência atômica com prevenção de deadlock:
     * sempre trava as contas na ordem do id.
     */
    public void transferencia(Conta from, Conta to, long cents) {
        if (from == to || cents <= 0) return;

        Conta first  = from.id() < to.id() ? from : to;
        Conta second = from.id() < to.id() ? to   : from;

        first.lock();
        try {
            second.lock();
            try {
                from.retirarInseguro(cents);
                to.depositoInseguro(cents);
            } finally {
                second.unlock();
            }
        } finally {
            first.unlock();
        }
    }

    public Banco banco() { return banco; }
}
