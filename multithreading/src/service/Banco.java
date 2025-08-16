package service;

import model.Conta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class Banco {
    // imutável (conteúdo gerenciado internamente)
    private final List<Conta> contas;

    public Banco(List<Conta> contas) {
        // copiei e travei a lista para evitar modificações externas
        this.contas = Collections.unmodifiableList(new ArrayList<>(contas));
    }

    public List<Conta> contas() {
        return contas;
    }

    public long totalBalanca() {
        List<Conta> ordered = orderedContas();
        lockAll(ordered);
        try {
            long sum = 0L;
            for (Conta a : ordered) sum += a.getBalancaInsegura();
            return sum;
        } finally {
            unlockAllReverse(ordered);
        }
    }

    public boolean anySaldoNegativo() {
        List<Conta> ordered = orderedContas();
        lockAll(ordered);
        try {
            for (Conta a : ordered) if (a.getBalancaInsegura() < 0) return true;
            return false;
        } finally {
            unlockAllReverse(ordered);
        }
    }

    private List<Conta> orderedContas() {
        return contas.stream().sorted(Comparator.comparingInt(Conta::id)).collect(Collectors.toList());
    }

    private static void lockAll(List<Conta> list) {
        for (Conta a : list) a.lock();
    }

    private static void unlockAllReverse(List<Conta> list) {
        for (int i = list.size() - 1; i >= 0; i--) list.get(i).unlock();
    }
}
