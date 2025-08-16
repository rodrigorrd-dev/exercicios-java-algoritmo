import concurrency.TransferTask;
import model.Conta;
import service.Banco;
import service.TransferenciaService;
import util.Dinheiro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {

        // Controla se vai começar a simulação com algumas contas já negativas.
        final boolean PERMITIR_NEGATIVO_INICIAL = true;

        final int NUM_CONTAS = 50;
        final int NUM_THREADS = Runtime.getRuntime().availableProcessors() * 2;
        final int OPERACOES_POR_THREAD = 100_000;
        final long MAX_TRANSFER = Dinheiro.toCentavos(50.00);

        try {

            // =========== cria contas com saldos variados ===========
            List<Conta> contas = criarContasDiversificadas(NUM_CONTAS, PERMITIR_NEGATIVO_INICIAL);

            // Deixar valor inicial em map
            Map<Integer, Long> saldosIniciais = new HashMap<>();
            for (Conta c : contas) {
                saldosIniciais.put(c.id(), c.getBalancaInsegura());
            }
            Banco banco = new Banco(contas);
            TransferenciaService transferenciaService = new TransferenciaService(banco);

            long totalInicial = banco.totalBalanca();
            boolean tinhaNegativas = banco.anySaldoNegativo();

            System.out.println("* Total inicial: " + Dinheiro.format(totalInicial));
            System.out.println("* Contas negativas no início? " + (tinhaNegativas ? "SIM" : "NÃO"));
            imprimirResumoContas("Resumo inicial", contas, 10, saldosIniciais);

            // --- estresse de concorrência ---
            ExecutorService pool = Executors.newFixedThreadPool(NUM_THREADS);
            CountDownLatch start = new CountDownLatch(1);

            for (int i = 0; i < NUM_THREADS; i++) {
                pool.submit(new TransferTask(transferenciaService, OPERACOES_POR_THREAD, start, MAX_TRANSFER));
            }

            long t0 = System.nanoTime();
            start.countDown();
            pool.shutdown();
            pool.awaitTermination(5, TimeUnit.MINUTES);
            long t1 = System.nanoTime();

            // --- verificações ---
            long totalFinal = banco.totalBalanca();
            boolean temNegativas = banco.anySaldoNegativo();

            System.out.println("* Total final:   " + Dinheiro.format(totalFinal));
            System.out.println("* Contas negativas ao final? " + (temNegativas ? "SIM" : "NÃO"));
            System.out.printf("* Tempo: %.2f s%n", (t1 - t0) / 1e9);

            imprimirResumoContas("Resumo final", contas, 10, saldosIniciais);

            // Invariantes:
            // 1) Conservação de soma total (sempre deve manter)
            if (totalFinal != totalInicial) {
                throw new AssertionError("Invariante violada: soma total mudou!");
            }

            // 2) Se você não permitir saldos negativos no início,
            //    ninguém deve ficar negativo ao final.
            if (!PERMITIR_NEGATIVO_INICIAL && temNegativas) {
                throw new AssertionError("Invariante violada: houve saldo negativo!");
            }

            System.out.println("********* Consistência preservada. Sem condições de corrida e sem deadlocks. *********");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    /**
     * Cria contas com saldos diversos. Se permitir negativo, injeta alguns saldos abaixo de zero.
     */
    private static List<Conta> criarContasDiversificadas(int n, boolean permitirNegativo) {
        List<Conta> contas = new ArrayList<>(n);
        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        for (int i = 0; i < n; i++) {
            // Base por "perfil" (cíclico): alta, média, baixa
            double baseReais;
            // Se o resto da divisao for um dos casos
            switch (i % 3) {
                case 0:
                    baseReais = 250.00;
                    break; // alta
                case 1:
                    baseReais = 75.00;
                    break; // média
                default:
                    baseReais = 20.00;
                    break; // baixa
            }

            // Ruído aleatório entre -30 e +30 reais para diversificar
            double ruido = rnd.nextDouble(-30.0, 30.0);
            double valor = Math.max(0.0, baseReais + ruido); // mantém positivo por padrão

            // Em ~20% dos casos, injeta saldo levemente negativo (se permitido)
            if (permitirNegativo && rnd.nextDouble() < 0.20) {
                // entre -10 e -50 reais
                valor = -rnd.nextDouble(10.0, 50.0);
            }

            long centavos = Dinheiro.toCentavos(valor);
            contas.add(new Conta(i, centavos));
        }
        return contas;
    }

    /**
     * Imprime um pequeno resumo (primeiras N contas) para visualização.
     */
    private static void imprimirResumoContas(String titulo, List<Conta> contas, int primeiros, Map<Integer, Long> saldosIniciais) {
        System.out.println("=== " + titulo + " ===");
        for (int i = 0; i < Math.min(primeiros, contas.size()); i++) {
            Conta conta = contas.get(i);
            long inicial = saldosIniciais.getOrDefault(conta.id(), 0L);
            long atual   = conta.getBalancaInsegura();
            long delta   = atual - inicial; // (final - inicial)

            System.out.printf(
                    "Conta %02d | Inicial: %s | Transferência: %s | Final: %s%n",
                    conta.id(),
                    Dinheiro.format(inicial),
                    Dinheiro.format(delta),
                    Dinheiro.format(atual)
            );
        }
        System.out.println("=======================");
    }

}