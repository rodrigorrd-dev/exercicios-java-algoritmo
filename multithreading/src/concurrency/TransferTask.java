package concurrency;

import model.Conta;
import service.TransferenciaService;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

public class TransferTask implements Runnable {

    private final TransferenciaService transferenciaService;
    private final List<Conta> contas;
    private final int operacoes;
    private final CountDownLatch iniciarSinal;
    private final long maxTransferenciaCentavos;

    public TransferTask(TransferenciaService transferService,
                        int operacoes,
                        CountDownLatch iniciarSinal,
                        long maxTransferenciaCentavos) {
        this.transferenciaService = transferService;
        this.contas = transferService.banco().contas();
        this.operacoes = operacoes;
        this.iniciarSinal = iniciarSinal;
        this.maxTransferenciaCentavos = maxTransferenciaCentavos;
    }

    @Override
    public void run() {
        try {
            iniciarSinal.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }

        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        int n = contas.size();
        for (int i = 0; i < operacoes; i++) {
            int fromIdx = rnd.nextInt(n);
            int toIdx = rnd.nextInt(n - 1);
            if (toIdx >= fromIdx) toIdx++;

            Conta from = contas.get(fromIdx);
            Conta to = contas.get(toIdx);
            long cents = 1 + rnd.nextLong(maxTransferenciaCentavos);

            try {
                transferenciaService.transferencia(from, to, cents);
            } catch (IllegalStateException ignored) {
                // saldo insuficiente — tudo bem, pulamos a operação
            }
        }
    }
}
